package au.com.mason.expensemanager.mapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.dao.RefDataDao;
import au.com.mason.expensemanager.domain.Income;
import au.com.mason.expensemanager.dto.IncomeDto;

@Component
public class IncomeMapperWrapper {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	private IncomeMapper incomeMapper = IncomeMapper.INSTANCE;
	
	@Autowired
	private RefDataDao refDataDao;
	
	public Income incomeDtoToIncome(IncomeDto incomeDto) throws Exception {
		Income income = incomeMapper.incomeDtoToIncome(incomeDto);
		if (incomeDto.getDueDateString() != null) {
			income.setDueDate(LocalDate.parse(incomeDto.getDueDateString(), FORMATTER));
		}
		income.setEntryType(refDataDao.getById(Long.valueOf(incomeDto.getIncomeType())));
		if (incomeDto.getRecurringTypeId()!= null) {
			income.setRecurringType(refDataDao.getById(Long.valueOf(incomeDto.getRecurringTypeId())));
		}
		
		if (incomeDto.getStartDateString() != null) {
			income.setStartDate(LocalDate.parse(incomeDto.getStartDateString(), FORMATTER));
		}
		if (incomeDto.getEndDateString() != null) {
			income.setEndDate(LocalDate.parse(incomeDto.getEndDateString(), FORMATTER));
		}
		
		return income;
	}
    
    public Income incomeDtoToIncome(IncomeDto incomeDto, Income income) throws Exception {
    	Income existingIncome = incomeMapper.incomeDtoToIncome(incomeDto, income);
    	if (incomeDto.getDueDateString()!= null) {
    		existingIncome.setDueDate(LocalDate.parse(incomeDto.getDueDateString(), FORMATTER));
    	}
    	existingIncome.setEntryType(refDataDao.getById(Long.valueOf(incomeDto.getIncomeType())));
    	if (incomeDto.getRecurringTypeId()!= null) {
    		existingIncome.setRecurringType(refDataDao.getById(Long.valueOf(incomeDto.getRecurringTypeId())));
    	}
    	
		if (incomeDto.getStartDateString() != null) {
			existingIncome.setStartDate(LocalDate.parse(incomeDto.getStartDateString(), FORMATTER));
		}
		if (incomeDto.getEndDateString() != null) {
			existingIncome.setEndDate(LocalDate.parse(incomeDto.getEndDateString(), FORMATTER));
		}
		
		return existingIncome;    	
    }
    
    public IncomeDto incomeToIncomeDto(Income income) throws Exception {
    	IncomeDto incomeDto = incomeMapper.incomeToIncomeDto(income);
    	incomeDto.setIncomeTypeDescription(income.getEntryType().getDescription());
    	incomeDto.setIncomeType(String.valueOf(income.getEntryType().getId()));
    	if (income.getDueDate() != null) {
    		incomeDto.setDueDateString(FORMATTER.format(income.getDueDate()));
    		incomeDto.setWeek(FORMATTER.format(income.getDueDate().with(DayOfWeek.MONDAY)));
    	}
    	
    	if (income.getStartDate() != null) {
    		incomeDto.setStartDateString(FORMATTER.format(income.getStartDate()));
    		incomeDto.setWeek(FORMATTER.format(income.getStartDate().with(DayOfWeek.MONDAY)));
    	}
    	if (income.getEndDate() != null) {
    		incomeDto.setEndDateString(FORMATTER.format(income.getEndDate()));
    	}
    	
    	return incomeDto;
    }

}
