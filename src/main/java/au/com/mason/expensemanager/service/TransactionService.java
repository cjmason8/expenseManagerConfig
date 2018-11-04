package au.com.mason.expensemanager.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.dao.TransactionDao;
import au.com.mason.expensemanager.domain.RecurringUnit;
import au.com.mason.expensemanager.domain.Transaction;
import au.com.mason.expensemanager.dto.TransactionDto;
import au.com.mason.expensemanager.mapper.TransactionMapperWrapper;
import au.com.mason.expensemanager.util.DateUtil;

@Component
public abstract class TransactionService<T extends TransactionDto, V extends Transaction, D extends TransactionDao<V>> {
	
	@Autowired
	protected TransactionMapperWrapper<V, T> transactionMapperWrapper;
	
	@Autowired
	protected D transactionDao;
	
	@Autowired
	protected DocumentService documentService;
	
	public List<T> getAllRecurring() throws Exception {
		List<V> expenses = transactionDao.getAllRecurring();
		List<T> expenseDtos = new ArrayList<>();
		
		for (V expense : expenses) {
			expenseDtos.add(transactionMapperWrapper.transactionToTransactionDto(expense));
		}
		
		return expenseDtos;
	}
	
	public List<T> getForWeek(LocalDate startOfWeek) throws Exception {
		List<V> expenses = transactionDao.getForWeek(startOfWeek);
		
		List<T> expenseDtos = new ArrayList<>();
		
		for (V expense : expenses) {
			expenseDtos.add(transactionMapperWrapper.transactionToTransactionDto(expense));
		}
		
		return expenseDtos;
	}
	
	public T getById(Long id) throws Exception {
		V expense = transactionDao.getById(id);

		return transactionMapperWrapper.transactionToTransactionDto(expense);
	}
	
	public T addTransaction(T expenseDto) throws Exception {
		
		if (expenseDto.getDocumentDto() != null && expenseDto.getDocumentDto().getOriginalFileName() != null) {
			updateDocument(expenseDto);
		}
		else {
			expenseDto.setDocumentDto(null);
		}
		
		V expense = transactionMapperWrapper.transactionDtoToTransaction(expenseDto);
		
		createTransaction(expense);
		handleRecurring(expense);
		
		return transactionMapperWrapper.transactionToTransactionDto(expense);
	}

	private void updateDocument(T expenseDto) throws IOException, Exception {
		if (!expenseDto.getDocumentDto().getOriginalFileName().equals(expenseDto.getDocumentDto().getFileName())) {
			Files.move(Paths.get(expenseDto.getDocumentDto().getFolderPath() + "/" + expenseDto.getDocumentDto().getOriginalFileName()),
					Paths.get(expenseDto.getDocumentDto().getFolderPath() + "/" + expenseDto.getDocumentDto().getFileName()));
			
			documentService.updateDocument(expenseDto.getDocumentDto());
		}
	}

	private void handleRecurring(V expense) throws Exception {
		if (expense.getRecurringType() != null) {
			V newExpenseForStartDate = createTransaction();
			newExpenseForStartDate.setEntryType(expense.getEntryType());
			newExpenseForStartDate.setMetaData(expense.getMetaData());
			newExpenseForStartDate.setAmount(expense.getAmount());
			newExpenseForStartDate.setDueDate(expense.getStartDate());
			newExpenseForStartDate.setNotes(expense.getNotes());
			newExpenseForStartDate.setRecurringTransaction(expense);
			
			createTransaction(newExpenseForStartDate);
			createSubsequentWeeks(expense);
		}
	}
	
	private void createTransaction(V expense) throws Exception {
		if (expense.getRecurringType() == null) {
			initialiseWeek(DateUtil.getMonday(expense.getDueDate()), expense.getRecurringTransaction());	
		}
		
		transactionDao.create(expense);
	}
	
	private void createSubsequentWeeks(V newExpense) throws Exception {
		RecurringUnit recurringUnit = 
				RecurringUnit.valueOf(newExpense.getRecurringType().getDescriptionUpper());
		
		LocalDate dueDate = newExpense.getStartDate().plus(recurringUnit.getUnits(), recurringUnit.getUnitType());
		
		while (getPastDate(DateUtil.getMonday(dueDate)) > 0) {
			if (countForWeekForAll(DateUtil.getMonday(dueDate)) > 0) {
				V newExpenseForSubsequent = createTransaction();
				newExpenseForSubsequent.setEntryType(newExpense.getEntryType());
				newExpenseForSubsequent.setMetaData(newExpense.getMetaData());
				newExpenseForSubsequent.setAmount(newExpense.getAmount());
				newExpenseForSubsequent.setDueDate(dueDate);
				newExpenseForSubsequent.setNotes(newExpense.getNotes());
				newExpenseForSubsequent.setRecurringTransaction(newExpense);
				
				transactionDao.create(newExpenseForSubsequent);
			}
			
			dueDate = dueDate.plus(recurringUnit.getUnits(), recurringUnit.getUnitType());
			
			if (newExpense.getEndDate() != null && dueDate.isAfter(newExpense.getEndDate())) {
				break;
			}
		}
	}
	
	public V update(V transaction) {
		return transactionDao.update(transaction);
	}
	
	public V create(V transaction) {
		return transactionDao.create(transaction);
	}
	
	public T updateTransaction(T expenseDto) throws Exception {
		
		if (expenseDto.getDocumentDto() != null && expenseDto.getDocumentDto().getOriginalFileName() != null) {
			updateDocument(expenseDto);
		}

		V updatedExpense = transactionDao.getById(expenseDto.getId());
		updatedExpense = transactionMapperWrapper.transactionDtoToTransaction(expenseDto, updatedExpense);
		transactionDao.update(updatedExpense);
		
		handleRecurringForUpdate(updatedExpense);
		
		return transactionMapperWrapper.transactionToTransactionDto(updatedExpense);
	}

	private void handleRecurringForUpdate(V updatedExpense) {
		if (updatedExpense.getRecurringType() != null) {
			List<V> expenses = transactionDao.getPastDate(LocalDate.now(), updatedExpense);
			for (V expense : expenses) {
				expense.setAmount(updatedExpense.getAmount());
				transactionDao.update(expense);			
			}
		}
	}
	
	public void deleteTransaction(Long id) {
		V expense = transactionDao.getById(id);
		if (expense.getRecurringType() != null) {
			transactionDao.deleteTransactions(id);
			if (transactionDao.getForRecurring(expense).size() > 0) {
				expense.setDeleted(true);
				transactionDao.update(expense);
			}
			else {
				transactionDao.delete(expense);
			}
		}
		else {
			transactionDao.delete(expense);
		}
	}
	
	public void createRecurringTransactions(LocalDate startOfWeek, Transaction currentRecurringExpense) {
		List<V> recurringExpenses = transactionDao.getAllRecurring();
		
		for (V recurringExpense : recurringExpenses) {
			if (currentRecurringExpense != null && recurringExpense.getId() == currentRecurringExpense.getId()) {
				continue;
			}

			LocalDate dueDate = recurringExpense.getStartDate();
			while (DateUtil.getMonday(dueDate).isBefore(startOfWeek)) {
				RecurringUnit recurringUnit = 
						RecurringUnit.valueOf(recurringExpense.getRecurringType().getDescriptionUpper());
				dueDate = dueDate.plus(recurringUnit.getUnits(), recurringUnit.getUnitType());
			}
			
			if (DateUtil.getMonday(dueDate).isEqual(startOfWeek) && checkEndDate(recurringExpense, dueDate)) {
				V newExpense = createTransaction();
				newExpense.setEntryType(recurringExpense.getEntryType());
				newExpense.setAmount(recurringExpense.getAmount());
				newExpense.setDueDate(dueDate);
				newExpense.setRecurringTransaction(recurringExpense);
				newExpense.setMetaData(recurringExpense.getMetaData());
				
				transactionDao.create(newExpense);
			}
		}
	}
	
	private boolean checkEndDate(V recurringExpense, LocalDate dueDate) {
		return recurringExpense.getEndDate() == null || dueDate.isBefore(recurringExpense.getEndDate());
	}
	
	public int countForWeek(LocalDate startOfWeek) throws Exception {
		return transactionDao.getForWeek(startOfWeek).size();
	}
	
	public List<V> getPastDateList(LocalDate date) {
		return transactionDao.getPastDate(date);
	}
	
	abstract int countForWeekForAll(LocalDate startOfWeek) throws Exception;
	
	abstract V createTransaction();
	
	abstract void initialiseWeek(LocalDate localDate, Transaction currentRecurringTransaction) throws Exception;
	
	abstract int getPastDate(LocalDate date) throws Exception;
	
}
