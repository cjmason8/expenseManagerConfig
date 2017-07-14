package au.com.mason.expensemanager.mapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.domain.Income;
import au.com.mason.expensemanager.domain.IncomeType;
import au.com.mason.expensemanager.dto.IncomeDto;

@Component
public class IncomeMapperWrapper {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	private IncomeMapper incomeMapper = IncomeMapper.INSTANCE;
	
	public Income incomeDtoToIncome(IncomeDto incomeDto) throws Exception {
		Income income = incomeMapper.incomeDtoToIncome(incomeDto);
		income.setDueDate(LocalDate.parse(incomeDto.getDueDateString(), FORMATTER));
		income.setEntryType(IncomeType.valueOf(IncomeType.class, incomeDto.getIncomeType()));
		
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
    	existingIncome.setDueDate(LocalDate.parse(incomeDto.getDueDateString(), FORMATTER));
    	existingIncome.setEntryType(IncomeType.valueOf(IncomeType.class, incomeDto.getIncomeType()));
    	
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
    	incomeDto.setIncomeType(income.getEntryType().name());
    	incomeDto.setDueDateString(FORMATTER.format(income.getDueDate()));
    	incomeDto.setWeek(FORMATTER.format(income.getDueDate().with(DayOfWeek.MONDAY)));
    	
    	if (income.getStartDate() != null) {
    		incomeDto.setStartDateString(FORMATTER.format(income.getStartDate()));
    	}
    	if (income.getEndDate() != null) {
    		incomeDto.setEndDateString(FORMATTER.format(income.getEndDate()));
    	}
    	
    	return incomeDto;
    }

}
