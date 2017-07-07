package au.com.mason.expensemanager.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.dao.ExpenseDao;
import au.com.mason.expensemanager.dao.RecurringExpenseDao;
import au.com.mason.expensemanager.domain.Expense;
import au.com.mason.expensemanager.domain.RecurringExpense;
import au.com.mason.expensemanager.dto.RecurringExpenseDto;
import au.com.mason.expensemanager.mapper.RecurringExpenseMapperWrapper;
import au.com.mason.expensemanager.util.DateUtil;

@Component
public class RecurringExpenseService {
	
	@Autowired
	private RecurringExpenseMapperWrapper recurringExpenseMapperWrapper;
	
	@Autowired
	private RecurringExpenseDao recurringExpenseDao;
	
	@Autowired
	private ExpenseDao expenseDao;
	
	public List<RecurringExpenseDto> getAll() throws Exception {
		List<RecurringExpense> recurringExpenses = recurringExpenseDao.getAll();
		List<RecurringExpenseDto> recurringExpenseDtos = new ArrayList<>();
		
		for (RecurringExpense recurringExpense : recurringExpenses) {
			recurringExpenseDtos.add(recurringExpenseMapperWrapper.recurringExpenseToRecurringExpenseDto(recurringExpense));
		}
		
		return recurringExpenseDtos;
	}
	
	public RecurringExpense getRecurringExpenseById(Long id) {
		return recurringExpenseDao.getById(id);
	}
	
	public RecurringExpenseDto getById(Long id) throws Exception {
		RecurringExpense recurringExpense = recurringExpenseDao.getById(id);

		return recurringExpenseMapperWrapper.recurringExpenseToRecurringExpenseDto(recurringExpense);
	}
	
	public RecurringExpenseDto addRecurringExpense(RecurringExpenseDto recurringExpenseDto) throws Exception {
		RecurringExpense recurringExpense = recurringExpenseMapperWrapper.recurringExpenseDtoToRecurringExpense(recurringExpenseDto);
		
		RecurringExpense newRecurringExpense = recurringExpenseDao.create(recurringExpense);
		
		Expense newExpenseForStartDate = new Expense();
		newExpenseForStartDate.setExpenseType(newRecurringExpense.getExpenseType());
		newExpenseForStartDate.setAmount(newRecurringExpense.getAmount());
		newExpenseForStartDate.setDueDate(newRecurringExpense.getStartDate());
		newExpenseForStartDate.setRecurringExpense(newRecurringExpense);
		
		expenseDao.create(newExpenseForStartDate);
		
		createSubsequentWeeks(newRecurringExpense);
		
		return recurringExpenseMapperWrapper.recurringExpenseToRecurringExpenseDto(newRecurringExpense);
	}

	private void createSubsequentWeeks(RecurringExpense newRecurringExpense) {
		LocalDate dueDate = newRecurringExpense.getStartDate().plus(newRecurringExpense.getRecurringType().getUnits(), 
				newRecurringExpense.getRecurringType().getUnitType());
		
		while (expenseDao.getExpensesPastDate(DateUtil.getMonday(dueDate)).size() > 0) {
			if (expenseDao.getExpensesForWeek(DateUtil.getMonday(dueDate)).size() > 0) {
				Expense newExpenseForSubsequent = new Expense();
				newExpenseForSubsequent.setExpenseType(newRecurringExpense.getExpenseType());
				newExpenseForSubsequent.setAmount(newRecurringExpense.getAmount());
				newExpenseForSubsequent.setDueDate(dueDate);
				newExpenseForSubsequent.setRecurringExpense(newRecurringExpense);
				
				expenseDao.create(newExpenseForSubsequent);
			}
			
			dueDate = dueDate.plus(newRecurringExpense.getRecurringType().getUnits(), newRecurringExpense.getRecurringType().getUnitType());
			
			if (newRecurringExpense.getEndDate() != null && dueDate.isAfter(newRecurringExpense.getEndDate())) {
				break;
			}
		}
	}
	
	public RecurringExpenseDto updateRecurringExpense(RecurringExpenseDto recurringExpenseDto) throws Exception {
		RecurringExpense recurringExpense = recurringExpenseDao.getById(recurringExpenseDto.getId());
		recurringExpense = recurringExpenseMapperWrapper.recurringExpenseDtoToRecurringExpense(recurringExpenseDto, recurringExpense);
		recurringExpenseDao.update(recurringExpense);
		
		List<Expense> expenses = expenseDao.getExpensesPastDate(LocalDate.now(), recurringExpense);
		for (Expense expense : expenses) {
			expense.setAmount(recurringExpense.getAmount());
			expenseDao.update(expense);			
		}
		
		return recurringExpenseMapperWrapper.recurringExpenseToRecurringExpenseDto(recurringExpense);
	}
	
	public void deleteRecurringExpense(Long id) {
		expenseDao.deleteExpenses(id);
		recurringExpenseDao.deleteById(id);
	}
	
}
