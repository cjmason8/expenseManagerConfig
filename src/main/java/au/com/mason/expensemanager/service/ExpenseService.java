package au.com.mason.expensemanager.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.dao.ExpenseDao;
import au.com.mason.expensemanager.domain.Expense;
import au.com.mason.expensemanager.domain.RecurringUnit;
import au.com.mason.expensemanager.domain.Transaction;
import au.com.mason.expensemanager.dto.ExpenseDto;
import au.com.mason.expensemanager.mapper.ExpenseMapperWrapper;
import au.com.mason.expensemanager.util.DateUtil;

@Component
public class ExpenseService {
	
	@Autowired
	private ExpenseMapperWrapper expenseMapperWrapper;
	
	@Autowired
	private ExpenseDao expenseDao;
	
	@Autowired
	private IncomeService incomeService;
	
	public List<ExpenseDto> getAllRecurring() throws Exception {
		List<Expense> expenses = expenseDao.getAllRecurring();
		List<ExpenseDto> expenseDtos = new ArrayList<>();
		
		for (Expense expense : expenses) {
			expenseDtos.add(expenseMapperWrapper.expenseToExpenseDto(expense));
		}
		
		return expenseDtos;
	}
	
	public void initialiseWeek(LocalDate localDate, Transaction currentRecurringTransaction) throws Exception {
		if (countForWeek(localDate) == 0) {
			incomeService.createRecurringIncomes(localDate, currentRecurringTransaction);
			createRecurringExpenses(localDate, currentRecurringTransaction);
		}
	}
	
	public List<ExpenseDto> getForWeek(LocalDate startOfWeek) throws Exception {
		List<Expense> expenses = expenseDao.getForWeek(startOfWeek);
		
		List<ExpenseDto> expenseDtos = new ArrayList<>();
		
		for (Expense expense : expenses) {
			expenseDtos.add(expenseMapperWrapper.expenseToExpenseDto(expense));
		}
		
		return expenseDtos;
	}
	
	public List<ExpenseDto> getUnpaidBeforeWeek(LocalDate startOfWeek) throws Exception {
		List<Expense> expenses = expenseDao.getUnpaidBeforeWeek(startOfWeek);
		
		List<ExpenseDto> expenseDtos = new ArrayList<>();
		
		for (Expense expense : expenses) {
			expenseDtos.add(expenseMapperWrapper.expenseToExpenseDto(expense));
		}
		
		return expenseDtos;
	}	
	
	public int countForWeek(LocalDate startOfWeek) throws Exception {
		return expenseDao.getForWeek(startOfWeek).size() + incomeService.getForWeek(startOfWeek).size();
	}

	public void createRecurringExpenses(LocalDate startOfWeek, Transaction currentRecurringExpense) {
		List<Expense> recurringExpenses = expenseDao.getAllRecurring();
		
		for (Expense recurringExpense : recurringExpenses) {
			if (currentRecurringExpense != null && recurringExpense.getId() == currentRecurringExpense.getId()) {
				continue;
			}

			LocalDate dueDate = recurringExpense.getStartDate();
			while (DateUtil.getMonday(dueDate).isBefore(startOfWeek)) {
				RecurringUnit recurringUnit = 
						RecurringUnit.valueOf(recurringExpense.getRecurringType().getDescriptionUpper());
				dueDate = dueDate.plus(recurringUnit.getUnits(), recurringUnit.getUnitType());
			}
			
			if (DateUtil.getMonday(dueDate).isEqual(startOfWeek)) {
				Expense newExpense = new Expense();
				newExpense.setEntryType(recurringExpense.getEntryType());
				newExpense.setAmount(recurringExpense.getAmount());
				newExpense.setDueDate(dueDate);
				newExpense.setRecurringTransaction(recurringExpense);
				
				expenseDao.create(newExpense);
			}
		}
	}	
	
	public ExpenseDto getById(Long id) throws Exception {
		Expense expense = expenseDao.getById(id);

		return expenseMapperWrapper.expenseToExpenseDto(expense);
	}
	
	public ExpenseDto addExpense(ExpenseDto expenseDto) throws Exception {
		Expense expense = expenseMapperWrapper.expenseDtoToExpense(expenseDto);
		
		createExpense(expense);
		handleRecurring(expense);
		
		return expenseMapperWrapper.expenseToExpenseDto(expense);
	}

	private void handleRecurring(Expense expense) throws Exception {
		if (expense.getRecurringType() != null) {
			Expense newExpenseForStartDate = new Expense();
			newExpenseForStartDate.setEntryType(expense.getEntryType());
			newExpenseForStartDate.setAmount(expense.getAmount());
			newExpenseForStartDate.setDueDate(expense.getStartDate());
			newExpenseForStartDate.setRecurringTransaction(expense);
			
			createExpense(newExpenseForStartDate);
			createSubsequentWeeks(expense);
		}
	}
	
	private void createExpense(Expense expense) throws Exception {
		if (expense.getRecurringType() == null) {
			initialiseWeek(DateUtil.getMonday(expense.getDueDate()), expense.getRecurringTransaction());	
		}
		
		expenseDao.create(expense);
	}
	
	public int getPastDate(LocalDate startOfWeek) throws Exception {
		return expenseDao.getPastDate(startOfWeek).size() + incomeService.getPastDate(startOfWeek).size();
	}
	
	private void createSubsequentWeeks(Expense newExpense) throws Exception {
		RecurringUnit recurringUnit = 
				RecurringUnit.valueOf(newExpense.getRecurringType().getDescriptionUpper());
		
		LocalDate dueDate = newExpense.getStartDate().plus(recurringUnit.getUnits(), recurringUnit.getUnitType());
		
		while (getPastDate(DateUtil.getMonday(dueDate)) > 0) {
			if (countForWeek(DateUtil.getMonday(dueDate)) > 0) {
				Expense newExpenseForSubsequent = new Expense();
				newExpenseForSubsequent.setEntryType(newExpense.getEntryType());
				newExpenseForSubsequent.setAmount(newExpense.getAmount());
				newExpenseForSubsequent.setDueDate(dueDate);
				newExpenseForSubsequent.setRecurringTransaction(newExpense);
				
				expenseDao.create(newExpenseForSubsequent);
			}
			
			dueDate = dueDate.plus(recurringUnit.getUnits(), recurringUnit.getUnitType());
			
			if (newExpense.getEndDate() != null && dueDate.isAfter(newExpense.getEndDate())) {
				break;
			}
		}
	}
	
	public ExpenseDto updateExpense(ExpenseDto expenseDto) throws Exception {
		Expense updatedExpense = expenseDao.getById(expenseDto.getId());
		updatedExpense = expenseMapperWrapper.expenseDtoToExpense(expenseDto, updatedExpense);
		expenseDao.update(updatedExpense);
		
		handleRecurringForUpdate(updatedExpense);
		
		return expenseMapperWrapper.expenseToExpenseDto(updatedExpense);
	}

	private void handleRecurringForUpdate(Expense updatedExpense) {
		if (updatedExpense.getRecurringType() != null) {
			List<Expense> expenses = expenseDao.getPastDate(LocalDate.now(), updatedExpense);
			for (Expense expense : expenses) {
				expense.setAmount(updatedExpense.getAmount());
				expenseDao.update(expense);			
			}
		}
	}
	
	public void deleteExpense(Long id) {
		Expense expense = expenseDao.getById(id);
		if (expense.getRecurringType() != null) {
			expenseDao.deleteExpenses(id);
			if (expenseDao.getForRecurring(expense).size() > 0) {
				expense.setDeleted(true);
				expenseDao.update(expense);
			}
			else {
				expenseDao.delete(expense);
			}
		}
		else {
			expenseDao.delete(expense);
		}
	}
	
}
