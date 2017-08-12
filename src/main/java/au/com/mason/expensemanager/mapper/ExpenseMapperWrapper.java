package au.com.mason.expensemanager.mapper;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import au.com.mason.expensemanager.domain.Expense;
import au.com.mason.expensemanager.dto.ExpenseDto;
import au.com.mason.expensemanager.util.DateUtil;

@Component
public class ExpenseMapperWrapper implements TransactionMapperWrapper<Expense, ExpenseDto> {
	
	private ExpenseMapper expenseMapper = ExpenseMapper.INSTANCE;
	
	public Expense transactionDtoToTransaction(ExpenseDto expenseDto) throws Exception {
		Expense expense = expenseMapper.expenseDtoToExpense(expenseDto);
		if (!StringUtils.isEmpty(expenseDto.getDueDateString())) {
			expense.setDueDate(DateUtil.getFormattedDate(expenseDto.getDueDateString()));
		}
		
		if (!StringUtils.isEmpty(expenseDto.getStartDateString())) {
			expense.setStartDate(DateUtil.getFormattedDate(expenseDto.getStartDateString()));
		}
		if (!StringUtils.isEmpty(expenseDto.getEndDateString())) {
			expense.setEndDate(DateUtil.getFormattedDate(expenseDto.getEndDateString()));
		}
		expense.setMetaData(expenseDto.getMetaDataChunk());
		
		return expense;
	}
    
	public Expense transactionDtoToTransaction(ExpenseDto expenseDto, Expense expense) throws Exception {
    	Expense existingExpense = expenseMapper.expenseDtoToExpense(expenseDto, expense);
    	if (expenseDto.getRecurringType() == null) {
    		existingExpense.setDueDate(DateUtil.getFormattedDate(expenseDto.getDueDateString()));
    	}
    	
    	if (!StringUtils.isEmpty(expenseDto.getStartDateString())) {
    		existingExpense.setStartDate(DateUtil.getFormattedDate(expenseDto.getStartDateString()));
		}
		if (!StringUtils.isEmpty(expenseDto.getEndDateString())) {
			existingExpense.setEndDate(DateUtil.getFormattedDate(expenseDto.getEndDateString()));
		}
		existingExpense.setMetaData(expenseDto.getMetaDataChunk());
		
		return existingExpense;    	
    }
    
    public ExpenseDto transactionToTransactionDto(Expense expense) throws Exception {
    	ExpenseDto expenseDto = expenseMapper.expenseToExpenseDto(expense);
    	if (expense.getDueDate() != null) {
    		expenseDto.setDueDateString(DateUtil.getFormattedDateString(expense.getDueDate()));
    		expenseDto.setWeek(DateUtil.getFormattedDateString(expense.getDueDate().with(DayOfWeek.MONDAY)));
    	}
    	
    	if (expense.getStartDate() != null) {
    		expenseDto.setStartDateString(DateUtil.getFormattedDateString(expense.getStartDate()));
    		expenseDto.setWeek(DateUtil.getFormattedDateString(expense.getStartDate().with(DayOfWeek.MONDAY)));
    	}
    	if (expense.getEndDate() != null) {
    		expenseDto.setEndDateString(DateUtil.getFormattedDateString(expense.getEndDate()));
    	}
    	expenseDto.setMetaDataChunk(expense.getMetaData());
    	
    	return expenseDto;
    }

}
