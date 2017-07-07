package au.com.mason.expensemanager.mapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.domain.Expense;
import au.com.mason.expensemanager.dto.ExpenseDto;

@Component
public class ExpenseMapperWrapper {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	private ExpenseMapper expenseMapper = ExpenseMapper.INSTANCE;
	
	public Expense expenseDtoToExpense(ExpenseDto expenseDto) throws Exception {
		Expense expense = expenseMapper.expenseDtoToExpense(expenseDto);
		expense.setDueDate(LocalDate.parse(expenseDto.getDueDateString(), FORMATTER));
		
		return expense;
	}
    
    public Expense expenseDtoToExpense(ExpenseDto expenseDto, Expense expense) throws Exception {
    	Expense existingExpense = expenseMapper.expenseDtoToExpense(expenseDto, expense);
    	existingExpense.setDueDate(LocalDate.parse(expenseDto.getDueDateString(), FORMATTER));
		
		return existingExpense;    	
    }
    
    public ExpenseDto expenseToExpenseDto(Expense expense) throws Exception {
    	ExpenseDto expenseDto = expenseMapper.expenseToExpenseDto(expense);
    	expenseDto.setDueDateString(FORMATTER.format(expense.getDueDate()));
    	expenseDto.setExpenseTypeDescription(expense.getExpenseType().getDescription());
    	expenseDto.setWeek(FORMATTER.format(expense.getDueDate().with(DayOfWeek.MONDAY)));
    	
    	return expenseDto;
    }

}
