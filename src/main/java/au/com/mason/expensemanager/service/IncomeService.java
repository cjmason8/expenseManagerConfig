package au.com.mason.expensemanager.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.dao.IncomeDao;
import au.com.mason.expensemanager.domain.Income;
import au.com.mason.expensemanager.domain.RecurringUnit;
import au.com.mason.expensemanager.domain.Transaction;
import au.com.mason.expensemanager.dto.IncomeDto;
import au.com.mason.expensemanager.mapper.IncomeMapperWrapper;
import au.com.mason.expensemanager.util.DateUtil;

@Component
public class IncomeService {
	
	@Autowired
	private IncomeMapperWrapper incomeMapperWrapper;	
	
	@Autowired
	private IncomeDao incomeDao;
	
	@Autowired
	private ExpenseService expenseService;
	
	public List<IncomeDto> getAllRecurring() throws Exception {
		List<Income> incomes = incomeDao.getAllRecurring();
		List<IncomeDto> incomeDtos = new ArrayList<>();
		
		for (Income income : incomes) {
			incomeDtos.add(incomeMapperWrapper.incomeToIncomeDto(income));
		}
		
		return incomeDtos;
	}
	
	public List<IncomeDto> getForWeek(LocalDate startOfWeek) throws Exception {
		List<Income> incomes = incomeDao.getForWeek(startOfWeek);
		
		List<IncomeDto> incomeDtos = new ArrayList<>();
		
		for (Income income : incomes) {
			incomeDtos.add(incomeMapperWrapper.incomeToIncomeDto(income));
		}
		
		return incomeDtos;
	}
	
	private void createIncome(Income income) throws Exception {
		if (income.getRecurringType() == null) {
			expenseService.initialiseWeek(DateUtil.getMonday(income.getDueDate()), 
					income.getRecurringTransaction());	
		}
		
		incomeDao.create(income);
	}
	
	public List<Income> getPastDate(LocalDate date) {
		return incomeDao.getPastDate(date);
	}

	public List<Income> createRecurringIncomes(LocalDate startOfWeek, Transaction currentRecurringTransaction) {
		List<Income> recurringIncomes = incomeDao.getAllRecurring();
		
		for (Income recurringIncome : recurringIncomes) {
			if (currentRecurringTransaction != null 
					&& recurringIncome.getId() == currentRecurringTransaction.getId()) {
				continue;
			}
			LocalDate dueDate = recurringIncome.getStartDate();
			while (DateUtil.getMonday(dueDate).isBefore(startOfWeek)) {
				RecurringUnit recurringUnit = 
						RecurringUnit.valueOf(recurringIncome.getRecurringType().getDescription().toUpperCase());
				dueDate = dueDate.plus(recurringUnit.getUnits(), recurringUnit.getUnitType());
			}
			
			if (DateUtil.getMonday(dueDate).isEqual(startOfWeek)) {
				Income newIncome = new Income();
				newIncome.setEntryType(recurringIncome.getEntryType());
				newIncome.setAmount(recurringIncome.getAmount());
				newIncome.setDueDate(dueDate);
				newIncome.setRecurringTransaction(recurringIncome);
				
				incomeDao.create(newIncome);
			}
		}
		
		return incomeDao.getForWeek(startOfWeek);
	}	
	
	public IncomeDto getById(Long id) throws Exception {
		Income income = incomeDao.getById(id);

		return incomeMapperWrapper.incomeToIncomeDto(income);
	}
	
	public IncomeDto addIncome(IncomeDto incomeDto) throws Exception {
		Income income = incomeMapperWrapper.incomeDtoToIncome(incomeDto);
		createIncome(income);
		handleRecurring(income);
		
		return incomeMapperWrapper.incomeToIncomeDto(income);
	}
	
	private void handleRecurring(Income income) throws Exception {
		if (income.getRecurringType() != null) {
			Income newIncomeForStartDate = new Income();
			newIncomeForStartDate.setEntryType(income.getEntryType());
			newIncomeForStartDate.setAmount(income.getAmount());
			newIncomeForStartDate.setDueDate(income.getStartDate());
			newIncomeForStartDate.setRecurringTransaction(income);
			
			createIncome(newIncomeForStartDate);
			
			createSubsequentWeeks(income);
		}
	}
	
	private void createSubsequentWeeks(Income newIncome) throws Exception {
		RecurringUnit recurringUnit = 
				RecurringUnit.valueOf(newIncome.getRecurringType().getDescription().toUpperCase());
		
		LocalDate dueDate = newIncome.getStartDate().plus(recurringUnit.getUnits(), recurringUnit.getUnitType());
		
		while (expenseService.getPastDate(DateUtil.getMonday(dueDate)) > 0) {
			if (expenseService.countForWeek(DateUtil.getMonday(dueDate)) > 0) {
				Income newIncomeForSubsequent = new Income();
				newIncomeForSubsequent.setEntryType(newIncome.getEntryType());
				newIncomeForSubsequent.setAmount(newIncome.getAmount());
				newIncomeForSubsequent.setDueDate(dueDate);
				newIncomeForSubsequent.setRecurringTransaction(newIncome);
				
				incomeDao.create(newIncomeForSubsequent);
			}
			
			dueDate = dueDate.plus(recurringUnit.getUnits(), recurringUnit.getUnitType());
			
			if (newIncome.getEndDate() != null && dueDate.isAfter(newIncome.getEndDate())) {
				break;
			}
		}
	}
	
	public IncomeDto updateIncome(IncomeDto incomeDto) throws Exception {
		Income income = incomeDao.getById(incomeDto.getId());
		income = incomeMapperWrapper.incomeDtoToIncome(incomeDto, income);
		incomeDao.update(income);
		
		handleRecurringForUpdate(income);
		
		return incomeMapperWrapper.incomeToIncomeDto(income);
	}
	
	private void handleRecurringForUpdate(Income updatedIncome) {
		if (updatedIncome.getRecurringType() != null) {
			List<Income> incomes = incomeDao.getPastDate(LocalDate.now(), updatedIncome);
			for (Income income : incomes) {
				income.setAmount(updatedIncome.getAmount());
				incomeDao.update(income);			
			}
		}
	}
	
	public void deleteIncome(Long id) {
		Income income = incomeDao.getById(id);
		if (income.getRecurringType() != null) {
			incomeDao.deleteIncomes(id);
			if (incomeDao.getForRecurring(income).size() > 0) {
				income.setDeleted(true);
				incomeDao.update(income);
			}
			else {
				incomeDao.delete(income);
			}
		}
		else {
			incomeDao.delete(income);
		}
	}
	
}
