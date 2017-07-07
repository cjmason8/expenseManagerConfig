package au.com.mason.expensemanager.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.dao.ExpenseDao;
import au.com.mason.expensemanager.dao.RecurringExpenseDao;
import au.com.mason.expensemanager.domain.Expense;
import au.com.mason.expensemanager.domain.RecurringExpense;
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
	private RecurringExpenseDao recurringExpenseDao;	
	
	public List<ExpenseDto> getAll() throws Exception {
		List<Expense> expenses = expenseDao.getAll();
		List<ExpenseDto> expenseDtos = new ArrayList<>();
		
		for (Expense expense : expenses) {
			expenseDtos.add(expenseMapperWrapper.expenseToExpenseDto(expense));
		}
		
		return expenseDtos;
	}
	
	public List<ExpenseDto> getExpensesForWeek(LocalDate startOfWeek) throws Exception {
		List<Expense> expenses = expenseDao.getExpensesForWeek(startOfWeek);
		
		if (expenses.size() == 0) {
			expenses = createRecurringExpenses(startOfWeek);
		}
		
		List<ExpenseDto> expenseDtos = new ArrayList<>();
		
		for (Expense expense : expenses) {
			expenseDtos.add(expenseMapperWrapper.expenseToExpenseDto(expense));
		}
		
		return expenseDtos;
	}

	private List<Expense> createRecurringExpenses(LocalDate startOfWeek) {
		List<Expense> expenses;
		List<RecurringExpense> recurringExpenses = recurringExpenseDao.getAll();
		
		for (RecurringExpense recurringExpense : recurringExpenses) {
			LocalDate dueDate = recurringExpense.getStartDate();
			while (DateUtil.getMonday(dueDate).isBefore(startOfWeek)) {
				dueDate = dueDate.plus(recurringExpense.getRecurringType().getUnits(), recurringExpense.getRecurringType().getUnitType());
			}
			
			if (DateUtil.getMonday(dueDate).isEqual(startOfWeek)) {
				Expense newExpense = new Expense();
				newExpense.setExpenseType(recurringExpense.getExpenseType());
				newExpense.setAmount(recurringExpense.getAmount());
				newExpense.setDueDate(dueDate);
				newExpense.setRecurringExpense(recurringExpense);
				
				expenseDao.create(newExpense);
			}
		}
		
		expenses = expenseDao.getExpensesForWeek(startOfWeek);
		return expenses;
	}	
	
	public Expense getExpenseById(Long id) {
		return expenseDao.getById(id);
	}
	
	public ExpenseDto getById(Long id) throws Exception {
		Expense expense = expenseDao.getById(id);

		return expenseMapperWrapper.expenseToExpenseDto(expense);
	}
	
	public ExpenseDto addExpense(ExpenseDto expenseDto) throws Exception {
		Expense expense = expenseMapperWrapper.expenseDtoToExpense(expenseDto);
		
		return expenseMapperWrapper.expenseToExpenseDto(expenseDao.create(expense));
	}
	
	public ExpenseDto updateExpense(ExpenseDto expenseDto) throws Exception {
		Expense expense = expenseDao.getById(expenseDto.getId());
		expense = expenseMapperWrapper.expenseDtoToExpense(expenseDto, expense);
		
		return expenseMapperWrapper.expenseToExpenseDto(expenseDao.update(expense));
	}
	
	public void deleteExpense(Long id) {
		expenseDao.deleteById(id);
	}
	
}
