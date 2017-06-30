package au.com.mason.expensemanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.dao.ExpenseDao;
import au.com.mason.expensemanager.domain.Expense;
import au.com.mason.expensemanager.dto.ExpenseDto;
import au.com.mason.expensemanager.mapper.ExpenseMapperWrapper;

@Component
public class ExpenseService {
	
	@Autowired
	private ExpenseMapperWrapper expenseMapperWrapper;
	
	@Autowired
	private ExpenseDao expenseDao;
	
	public List<ExpenseDto> getAll() throws Exception {
		List<Expense> expenses = expenseDao.getAll();
		List<ExpenseDto> expenseDtos = new ArrayList<>();
		
		for (Expense expense : expenses) {
			expenseDtos.add(expenseMapperWrapper.expenseToExpenseDto(expense));
		}
		
		return expenseDtos;
	}
	
	public Expense getExpenseById(Long id) {
		return expenseDao.getById(id);
	}
	
	public ExpenseDto getById(Long id) throws Exception {
		Expense expense = expenseDao.getById(id);

		return expenseMapperWrapper.expenseToExpenseDto(expense);
	}
	
	public ExpenseDto addExpense(ExpenseDto expenseDto) throws Exception {
		Expense expense = expenseMapperWrapper.expenseDtoToExpense(expenseDto);
		
		return expenseMapperWrapper.expenseToExpenseDto(expenseDao.create(expense));
	}
	
	public ExpenseDto updateExpense(ExpenseDto expenseDto) throws Exception {
		Expense expense = expenseDao.getById(expenseDto.getId());
		expenseMapperWrapper.expenseDtoToExpense(expenseDto, expense);
		
		return expenseMapperWrapper.expenseToExpenseDto(expenseDao.update(expense));
	}
	
	public void deleteExpense(Long id) {
		expenseDao.deleteById(id);
	}
	
}
