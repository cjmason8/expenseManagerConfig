package au.com.mason.expensemanager.mapper;

import java.time.DayOfWeek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.dao.RefDataDao;
import au.com.mason.expensemanager.domain.Expense;
import au.com.mason.expensemanager.dto.ExpenseDto;
import au.com.mason.expensemanager.util.DateUtil;

@Component
public class ExpenseMapperWrapper implements TransactionMapperWrapper<Expense, ExpenseDto> {
	
	private ExpenseMapper expenseMapper = ExpenseMapper.INSTANCE;
	private RefDataMapper refDataMapper = RefDataMapper.INSTANCE;
	
<<<<<<< Updated upstream
	@Autowired
	private RefDataDao refDataDao;
	
	public Expense expenseDtoToExpense(ExpenseDto expenseDto) throws Exception {
		Expense expense = expenseMapper.expenseDtoToExpense(expenseDto);
		expense.setEntryType(refDataMapper.refDataDtoToRefData(expenseDto.getExpenseType()));
		if (expenseDto.getRecurringTypeId() != null) {
			expense.setRecurringType(refDataDao.getById(Long.valueOf(expenseDto.getRecurringTypeId())));
=======
	public Expense transactionDtoToTransaction(ExpenseDto expenseDto) throws Exception {
		Expense expense = expenseMapper.expenseDtoToExpense(expenseDto);
		expense.setEntryType(refDataMapper.refDataDtoToRefData(expenseDto.getTransactionType()));
		if (expenseDto.getRecurringType() != null) {
			expense.setRecurringType(refDataMapper.refDataDtoToRefData(expenseDto.getRecurringType()));
>>>>>>> Stashed changes
		}
		if (expenseDto.getDueDateString() != null) {
			expense.setDueDate(DateUtil.getFormattedDate(expenseDto.getDueDateString()));
		}
		
		if (expenseDto.getStartDateString() != null) {
			expense.setStartDate(DateUtil.getFormattedDate(expenseDto.getStartDateString()));
		}
		if (expenseDto.getEndDateString() != null) {
			expense.setEndDate(DateUtil.getFormattedDate(expenseDto.getEndDateString()));
		}
		
		return expense;
	}
    
	public Expense transactionDtoToTransaction(ExpenseDto expenseDto, Expense expense) throws Exception {
    	Expense existingExpense = expenseMapper.expenseDtoToExpense(expenseDto, expense);
<<<<<<< Updated upstream
    	existingExpense.setEntryType(refDataMapper.refDataDtoToRefData(expenseDto.getExpenseType()));
    	if (expenseDto.getRecurringTypeId() != null) {
    		existingExpense.setRecurringType(refDataDao.getById(Long.valueOf(expenseDto.getRecurringTypeId())));
=======
    	existingExpense.setEntryType(refDataMapper.refDataDtoToRefData(expenseDto.getTransactionType()));
    	if (expenseDto.getRecurringType() != null) {
    		existingExpense.setRecurringType(refDataMapper.refDataDtoToRefData(expenseDto.getRecurringType()));
>>>>>>> Stashed changes
    	}
    	else {
    		existingExpense.setDueDate(DateUtil.getFormattedDate(expenseDto.getDueDateString()));
    	}
    	
    	if (expenseDto.getStartDateString() != null) {
    		existingExpense.setStartDate(DateUtil.getFormattedDate(expenseDto.getStartDateString()));
		}
		if (expenseDto.getEndDateString() != null) {
			existingExpense.setEndDate(DateUtil.getFormattedDate(expenseDto.getEndDateString()));
		}
		
		return existingExpense;    	
    }
    
    public ExpenseDto transactionToTransactionDto(Expense expense) throws Exception {
    	ExpenseDto expenseDto = expenseMapper.expenseToExpenseDto(expense);
    	if (expense.getDueDate() != null) {
    		expenseDto.setDueDateString(DateUtil.getFormattedDateString(expense.getDueDate()));
    		expenseDto.setWeek(DateUtil.getFormattedDateString(expense.getDueDate().with(DayOfWeek.MONDAY)));
    	}
<<<<<<< Updated upstream
    	expenseDto.setExpenseTypeDescription(expense.getEntryType().getDescription());
    	expenseDto.setExpenseType(refDataMapper.refDataToRefDataDto(expense.getEntryType()));
=======
    	expenseDto.setTransactionType(refDataMapper.refDataToRefDataDto(expense.getEntryType()));
>>>>>>> Stashed changes
    	
    	if (expense.getStartDate() != null) {
    		expenseDto.setStartDateString(DateUtil.getFormattedDateString(expense.getStartDate()));
    		expenseDto.setWeek(DateUtil.getFormattedDateString(expense.getStartDate().with(DayOfWeek.MONDAY)));
    	}
    	if (expense.getEndDate() != null) {
    		expenseDto.setEndDateString(DateUtil.getFormattedDateString(expense.getEndDate()));
    	}
    	
    	return expenseDto;
    }

}
