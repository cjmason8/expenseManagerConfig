package au.com.mason.expensemanager.mapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.dao.RefDataDao;
import au.com.mason.expensemanager.domain.Expense;
import au.com.mason.expensemanager.dto.ExpenseDto;

@Component
public class ExpenseMapperWrapper {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	private ExpenseMapper expenseMapper = ExpenseMapper.INSTANCE;
	
	@Autowired
	private RefDataDao refDataDao;
	
	public Expense expenseDtoToExpense(ExpenseDto expenseDto) throws Exception {
		Expense expense = expenseMapper.expenseDtoToExpense(expenseDto);
		expense.setEntryType(refDataDao.getById(Long.valueOf(expenseDto.getExpenseType())));
		if (expenseDto.getRecurringTypeId()!= null) {
			expense.setRecurringType(refDataDao.getById(Long.valueOf(expenseDto.getRecurringTypeId())));
		}
		expense.setDueDate(LocalDate.parse(expenseDto.getDueDateString(), FORMATTER));
		
		if (expenseDto.getStartDateString() != null) {
			expense.setStartDate(LocalDate.parse(expenseDto.getStartDateString(), FORMATTER));
		}
		if (expenseDto.getEndDateString() != null) {
			expense.setEndDate(LocalDate.parse(expenseDto.getEndDateString(), FORMATTER));
		}
		
		return expense;
	}
    
    public Expense expenseDtoToExpense(ExpenseDto expenseDto, Expense expense) throws Exception {
    	Expense existingExpense = expenseMapper.expenseDtoToExpense(expenseDto, expense);
    	existingExpense.setEntryType(refDataDao.getById(Long.valueOf(expenseDto.getExpenseType())));
    	if (expenseDto.getRecurringTypeId()!= null) {
    		existingExpense.setRecurringType(refDataDao.getById(Long.valueOf(expenseDto.getRecurringTypeId())));
    	}
    	existingExpense.setDueDate(LocalDate.parse(expenseDto.getDueDateString(), FORMATTER));
    	
    	if (expenseDto.getStartDateString() != null) {
    		existingExpense.setStartDate(LocalDate.parse(expenseDto.getStartDateString(), FORMATTER));
		}
		if (expenseDto.getEndDateString() != null) {
			existingExpense.setEndDate(LocalDate.parse(expenseDto.getEndDateString(), FORMATTER));
		}
		
		return existingExpense;    	
    }
    
    public ExpenseDto expenseToExpenseDto(Expense expense) throws Exception {
    	ExpenseDto expenseDto = expenseMapper.expenseToExpenseDto(expense);
    	expenseDto.setDueDateString(FORMATTER.format(expense.getDueDate()));
    	expenseDto.setExpenseTypeDescription(expense.getEntryType().getDescription());
    	expenseDto.setExpenseType(String.valueOf(expense.getEntryType().getId()));
    	expenseDto.setWeek(FORMATTER.format(expense.getDueDate().with(DayOfWeek.MONDAY)));
    	
    	if (expense.getStartDate() != null) {
    		expenseDto.setStartDateString(FORMATTER.format(expense.getStartDate()));
    	}
    	if (expense.getEndDate() != null) {
    		expenseDto.setEndDateString(FORMATTER.format(expense.getEndDate()));
    	}
    	
    	return expenseDto;
    }

}
