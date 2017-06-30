package au.com.mason.expensemanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.dao.RecurringExpenseDao;
import au.com.mason.expensemanager.domain.RecurringExpense;
import au.com.mason.expensemanager.dto.RecurringExpenseDto;
import au.com.mason.expensemanager.mapper.RecurringExpenseMapperWrapper;

@Component
public class RecurringExpenseService {
	
	@Autowired
	private RecurringExpenseMapperWrapper recurringExpenseMapperWrapper;
	
	@Autowired
	private RecurringExpenseDao recurringExpenseDao;
	
	public List<RecurringExpenseDto> getAll() throws Exception {
		List<RecurringExpense> recurringExpenses = recurringExpenseDao.getAll();
		List<RecurringExpenseDto> recurringExpenseDtos = new ArrayList<>();
		
		for (RecurringExpense recurringExpense : recurringExpenses) {
			recurringExpenseDtos.add(recurringExpenseMapperWrapper.recurringExpenseToRecurringExpenseDto(recurringExpense));
		}
		
		return recurringExpenseDtos;
	}
	
	public RecurringExpense getRecurringExpenseById(Long id) {
		return recurringExpenseDao.getById(id);
	}
	
	public RecurringExpenseDto getById(Long id) throws Exception {
		RecurringExpense recurringExpense = recurringExpenseDao.getById(id);

		return recurringExpenseMapperWrapper.recurringExpenseToRecurringExpenseDto(recurringExpense);
	}
	
	public RecurringExpenseDto addRecurringExpense(RecurringExpenseDto recurringExpenseDto) throws Exception {
		RecurringExpense recurringExpense = recurringExpenseMapperWrapper.recurringExpenseDtoToRecurringExpense(recurringExpenseDto);
		
		return recurringExpenseMapperWrapper.recurringExpenseToRecurringExpenseDto(recurringExpense);
	}
	
	public RecurringExpenseDto updateRecurringExpense(RecurringExpenseDto recurringExpenseDto) throws Exception {
		RecurringExpense recurringExpense = recurringExpenseDao.getById(recurringExpenseDto.getId());
		recurringExpenseMapperWrapper.recurringExpenseDtoToRecurringExpense(recurringExpenseDto, recurringExpense);
		
		return recurringExpenseMapperWrapper.recurringExpenseToRecurringExpenseDto(recurringExpenseDao.update(recurringExpense));
	}
	
	public void deleteRecurringExpense(Long id) {
		recurringExpenseDao.deleteById(id);
	}
	
}
