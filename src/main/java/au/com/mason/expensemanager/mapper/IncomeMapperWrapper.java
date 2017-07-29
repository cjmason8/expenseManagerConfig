package au.com.mason.expensemanager.mapper;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.domain.Income;
import au.com.mason.expensemanager.dto.IncomeDto;
import au.com.mason.expensemanager.util.DateUtil;

@Component
public class IncomeMapperWrapper {
	
	private IncomeMapper incomeMapper = IncomeMapper.INSTANCE;
	private RefDataMapper refDataMapper = RefDataMapper.INSTANCE;
	
	public Income incomeDtoToIncome(IncomeDto incomeDto) throws Exception {
		Income income = incomeMapper.incomeDtoToIncome(incomeDto);
		if (incomeDto.getDueDateString() != null) {
			income.setDueDate(DateUtil.getFormattedDate(incomeDto.getDueDateString()));
		}
		income.setEntryType(refDataMapper.refDataDtoToRefData(incomeDto.getIncomeType()));
		if (incomeDto.getRecurringType()!= null) {
			income.setRecurringType(refDataMapper.refDataDtoToRefData(incomeDto.getRecurringType()));
		}
		
		if (incomeDto.getStartDateString() != null) {
			income.setStartDate(DateUtil.getFormattedDate(incomeDto.getStartDateString()));
		}
		if (incomeDto.getEndDateString() != null) {
			income.setEndDate(DateUtil.getFormattedDate(incomeDto.getEndDateString()));
		}
		
		return income;
	}
    
    public Income incomeDtoToIncome(IncomeDto incomeDto, Income income) throws Exception {
    	Income existingIncome = incomeMapper.incomeDtoToIncome(incomeDto, income);
    	if (incomeDto.getDueDateString() != null) {
    		existingIncome.setDueDate(DateUtil.getFormattedDate(incomeDto.getDueDateString()));
    	}
    	existingIncome.setEntryType(refDataMapper.refDataDtoToRefData(incomeDto.getIncomeType()));
    	if (incomeDto.getRecurringType() != null) {
    		existingIncome.setRecurringType(refDataMapper.refDataDtoToRefData(incomeDto.getRecurringType()));
    	}
    	
		if (incomeDto.getStartDateString() != null) {
			existingIncome.setStartDate(DateUtil.getFormattedDate(incomeDto.getStartDateString()));
		}
		if (incomeDto.getEndDateString() != null) {
			existingIncome.setEndDate(DateUtil.getFormattedDate(incomeDto.getEndDateString()));
		}
		
		return existingIncome;    	
    }
    
    public IncomeDto incomeToIncomeDto(Income income) throws Exception {
    	IncomeDto incomeDto = incomeMapper.incomeToIncomeDto(income);
    	incomeDto.setIncomeType(refDataMapper.refDataToRefDataDto(income.getEntryType()));
    	if (income.getDueDate() != null) {
    		incomeDto.setDueDateString(DateUtil.getFormattedDateString(income.getDueDate()));
    		incomeDto.setWeek(DateUtil.getFormattedDateString(income.getDueDate().with(DayOfWeek.MONDAY)));
    	}
    	
    	if (income.getStartDate() != null) {
    		incomeDto.setStartDateString(DateUtil.getFormattedDateString(income.getStartDate()));
    		incomeDto.setWeek(DateUtil.getFormattedDateString(income.getStartDate().with(DayOfWeek.MONDAY)));
    	}
    	if (income.getEndDate() != null) {
    		incomeDto.setEndDateString(DateUtil.getFormattedDateString(income.getEndDate()));
    	}
    	
    	return incomeDto;
    }

}
