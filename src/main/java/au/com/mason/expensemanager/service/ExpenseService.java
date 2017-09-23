package au.com.mason.expensemanager.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.dao.ExpenseDao;
import au.com.mason.expensemanager.dao.TransactionDao;
import au.com.mason.expensemanager.domain.Expense;
import au.com.mason.expensemanager.domain.Transaction;
import au.com.mason.expensemanager.dto.ExpenseDto;
import au.com.mason.expensemanager.dto.ExpenseGraphDto;
import au.com.mason.expensemanager.dto.ExpenseSearchDto;
import au.com.mason.expensemanager.dto.GraphDto;

@Component
public class ExpenseService extends TransactionService<ExpenseDto, Expense, ExpenseDao> {
	
	@Autowired
	private IncomeService incomeService;
	
	@Autowired
	private TransactionDao<Expense> expenseDao;
	
	@Override
	Expense createTransaction() {
		return new Expense();
	}

	@Override
	public void initialiseWeek(LocalDate localDate, Transaction currentRecurringTransaction) throws Exception {
		if (countForWeekForAll(localDate) == 0) {
			incomeService.createRecurringTransactions(localDate, currentRecurringTransaction);
			createRecurringTransactions(localDate, currentRecurringTransaction);
		}
	}
	
	@Override
	public int getPastDate(LocalDate startOfWeek) throws Exception {
		return getPastDateList(startOfWeek).size() + incomeService.getPastDateList(startOfWeek).size();
	}
	
	public List<ExpenseDto> getUnpaidBeforeWeek(LocalDate startOfWeek) throws Exception {
		List<Expense> expenses = expenseDao.getUnpaidBeforeWeek(startOfWeek);
		
		List<ExpenseDto> expenseDtos = new ArrayList<>();
		
		for (Expense expense : expenses) {
			expenseDtos.add(transactionMapperWrapper.transactionToTransactionDto(expense));
		}
		
		return expenseDtos;
	}

	@Override
	int countForWeekForAll(LocalDate startOfWeek) throws Exception {
		return countForWeek(startOfWeek) + incomeService.countForWeek(startOfWeek);
	}
	
	public ExpenseDto payExpense(Long id) throws Exception {
		Expense expense = expenseDao.getById(id);
		expense.setPaid(true);
		
		expenseDao.update(expense);
		
		return transactionMapperWrapper.transactionToTransactionDto(expense);
	}
	
	public ExpenseDto unPayExpense(Long id) throws Exception {
		Expense expense = expenseDao.getById(id);
		expense.setPaid(false);
		
		expenseDao.update(expense);
		
		return transactionMapperWrapper.transactionToTransactionDto(expense);
	}
	
	public ExpenseSearchDto findExpenses(ExpenseDto expenseDto) throws Exception {
		List<Expense> expenses = expenseDao.findExpenses(
				transactionMapperWrapper.transactionDtoToTransaction(expenseDto));
		
		List<ExpenseDto> expenseDtos = new ArrayList<>();
		String[] labels = new String[expenses.size()];
		BigDecimal[] data = new BigDecimal[expenses.size()];
		int count = 0;
		
		for (Expense expense : expenses) {
			expenseDtos.add(transactionMapperWrapper.transactionToTransactionDto(expense));
			if (expense.getPaid()) {
				labels[count] = expense.getDueDate().getMonthValue() + "/" + expense.getDueDate().getYear();
				data[count++] = expense.getAmount();
			}
		}
		
		GraphDto graphDto = new GraphDto(expenseDto.getTransactionType().getDescription(), data);
		
		return new ExpenseSearchDto(expenseDtos, 
				new ExpenseGraphDto((String[]) labels, new GraphDto[] {graphDto}));
	}
	
	public List<ExpenseDto> getAll() throws Exception {
		List<Expense> expenses = expenseDao.getAll();
		
		List<ExpenseDto> expenseDtos = new ArrayList<>();
		
		for (Expense expense : expenses) {
			expenseDtos.add(transactionMapperWrapper.transactionToTransactionDto(expense));
		}
		
		return expenseDtos;
	}	
	
}
