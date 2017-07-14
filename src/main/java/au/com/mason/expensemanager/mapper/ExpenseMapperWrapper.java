package au.com.mason.expensemanager.mapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.domain.Expense;
import au.com.mason.expensemanager.domain.ExpenseType;
import au.com.mason.expensemanager.dto.ExpenseDto;

@Component
public class ExpenseMapperWrapper {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	private ExpenseMapper expenseMapper = ExpenseMapper.INSTANCE;
	
	public Expense expenseDtoToExpense(ExpenseDto expenseDto) throws Exception {
		Expense expense = expenseMapper.expenseDtoToExpense(expenseDto);
		expense.setDueDate(LocalDate.parse(expenseDto.getDueDateString(), FORMATTER));
		expense.setEntryType(ExpenseType.valueOf(ExpenseType.class, expenseDto.getExpenseType()));
		
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
    	existingExpense.setDueDate(LocalDate.parse(expenseDto.getDueDateString(), FORMATTER));
    	existingExpense.setEntryType(ExpenseType.valueOf(ExpenseType.class, expenseDto.getExpenseType()));
    	
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
    	expenseDto.setWeek(FORMATTER.format(expense.getDueDate().with(DayOfWeek.MONDAY)));
    	expenseDto.setExpenseType(expense.getEntryType().name());
    	
    	if (expense.getStartDate() != null) {
    		expenseDto.setStartDateString(FORMATTER.format(expense.getStartDate()));
    	}
    	if (expense.getEndDate() != null) {
    		expenseDto.setEndDateString(FORMATTER.format(expense.getEndDate()));
    	}
    	
    	return expenseDto;
    }

}
