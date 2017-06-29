package au.com.mason.expensemanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.dao.ExpenseDao;
import au.com.mason.expensemanager.domain.Expense;
import au.com.mason.expensemanager.dto.ExpenseDto;
import au.com.mason.expensemanager.mapper.ExpenseMapper;

@Component
public class ExpenseService {
	
	private ExpenseMapper expenseMapper = ExpenseMapper.INSTANCE;
	
	@Autowired
	private ExpenseDao expenseDao;
	
	public List<ExpenseDto> getAll() {
		List<Expense> expenses = expenseDao.getAll();
		List<ExpenseDto> expenseDtos = new ArrayList<>();
		
		expenses.forEach(expense -> {
			expenseDtos.add(expenseMapper.expenseToExpenseDto(expense));
		});
		
		return expenseDtos;
	}
	
	public Expense getExpenseById(Long id) {
		return expenseDao.getById(id);
	}
	
	public ExpenseDto getById(Long id) {
		Expense expense = expenseDao.getById(id);

		return expenseMapper.expenseToExpenseDto(expense);
	}
	
	public ExpenseDto addExpense(ExpenseDto expenseDto) {
		Expense expense = expenseMapper.expenseDtoToExpense(expenseDto);
		
		return expenseMapper.expenseToExpenseDto(expenseDao.create(expense));
	}
	
	public ExpenseDto updateExpense(ExpenseDto expenseDto) {
		Expense expense = expenseDao.getById(expenseDto.getId());
		expenseMapper.expenseDtoToExpense(expenseDto, expense);
		
		return expenseMapper.expenseToExpenseDto(expenseDao.update(expense));
	}
	
	public void deleteExpense(Long id) {
		expenseDao.deleteById(id);
	}
	
}
