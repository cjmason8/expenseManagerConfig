package au.com.mason.expensemanager.dao;

import java.time.LocalDate;
import java.util.List;

import au.com.mason.expensemanager.domain.Expense;

public interface TransactionDao<T> {
	
	public T create(T transation);	
	public void delete(T transation);
	public List<T> getAllRecurring();
	public T getById(long id);
	public T update(T transation);
	public List<T> getForWeek(LocalDate weekStartDate);
	public List<T> getPastDate(LocalDate date);
	public List<T> getPastDate(LocalDate date, T recurringTransation);
	public List<T> getForRecurring(T recurringTransation);
	public void deleteTransactions(Long recurringTransactionId);
	public List<Expense> getUnpaidBeforeWeek(LocalDate weekStartDate);
	public List<Expense> findExpenses(Expense expense);
	public List<T> getAll();
}
