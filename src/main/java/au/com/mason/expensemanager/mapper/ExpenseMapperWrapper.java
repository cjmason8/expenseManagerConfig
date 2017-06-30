package au.com.mason.expensemanager.mapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.domain.Expense;
import au.com.mason.expensemanager.dto.ExpenseDto;

@Component
public class ExpenseMapperWrapper {
	
	private static final DateFormat FORMATTER = new SimpleDateFormat("dd/mm/yyyy");
	
	private ExpenseMapper expenseMapper = ExpenseMapper.INSTANCE;
	
	public Expense expenseDtoToExpense(ExpenseDto expenseDto) throws Exception {
		Expense expense = expenseMapper.expenseDtoToExpense(expenseDto);
		expense.setDueDate(FORMATTER.parse(expenseDto.getDueDateString()));
		
		return expense;
	}
    
    public Expense expenseDtoToExpense(ExpenseDto expenseDto, Expense expense) throws Exception {
    	Expense existingExpense = expenseMapper.expenseDtoToExpense(expenseDto, expense);
    	existingExpense.setDueDate(FORMATTER.parse(expenseDto.getDueDateString()));
		
		return existingExpense;    	
    }
    
    public ExpenseDto expenseToExpenseDto(Expense expense) throws Exception {
    	ExpenseDto expenseDto = expenseMapper.expenseToExpenseDto(expense);
    	expenseDto.setDueDateString(FORMATTER.format(expense.getDueDate()));
    	expenseDto.setExpenseTypeDescription(expense.getExpenseType().getDescription());
    	
    	return expenseDto;
    }

}
