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
public class IncomeMapperWrapper implements TransactionMapperWrapper<Income, IncomeDto> {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	private IncomeMapper incomeMapper = IncomeMapper.INSTANCE;
	
	@Autowired
	private RefDataDao refDataDao;
	
	public Income transactionDtoToTransaction(IncomeDto incomeDto) throws Exception {
		Income income = incomeMapper.incomeDtoToIncome(incomeDto);
		if (incomeDto.getDueDateString() != null) {
			income.setDueDate(LocalDate.parse(incomeDto.getDueDateString(), FORMATTER));
		}
<<<<<<< Updated upstream
		income.setEntryType(refDataDao.getById(Long.valueOf(incomeDto.getIncomeType())));
		if (incomeDto.getRecurringTypeId()!= null) {
			income.setRecurringType(refDataDao.getById(Long.valueOf(incomeDto.getRecurringTypeId())));
=======
		income.setEntryType(refDataMapper.refDataDtoToRefData(incomeDto.getTransactionType()));
		if (incomeDto.getRecurringType()!= null) {
			income.setRecurringType(refDataMapper.refDataDtoToRefData(incomeDto.getRecurringType()));
>>>>>>> Stashed changes
		}
		
		if (incomeDto.getStartDateString() != null) {
			income.setStartDate(LocalDate.parse(incomeDto.getStartDateString(), FORMATTER));
		}
		if (incomeDto.getEndDateString() != null) {
			income.setEndDate(LocalDate.parse(incomeDto.getEndDateString(), FORMATTER));
		}
		
		return income;
	}
    
    public Income transactionDtoToTransaction(IncomeDto incomeDto, Income income) throws Exception {
    	Income existingIncome = incomeMapper.incomeDtoToIncome(incomeDto, income);
    	if (incomeDto.getDueDateString()!= null) {
    		existingIncome.setDueDate(LocalDate.parse(incomeDto.getDueDateString(), FORMATTER));
    	}
<<<<<<< Updated upstream
    	existingIncome.setEntryType(refDataDao.getById(Long.valueOf(incomeDto.getIncomeType())));
    	if (incomeDto.getRecurringTypeId()!= null) {
    		existingIncome.setRecurringType(refDataDao.getById(Long.valueOf(incomeDto.getRecurringTypeId())));
=======
    	existingIncome.setEntryType(refDataMapper.refDataDtoToRefData(incomeDto.getTransactionType()));
    	if (incomeDto.getRecurringType() != null) {
    		existingIncome.setRecurringType(refDataMapper.refDataDtoToRefData(incomeDto.getRecurringType()));
>>>>>>> Stashed changes
    	}
    	
		if (incomeDto.getStartDateString() != null) {
			existingIncome.setStartDate(LocalDate.parse(incomeDto.getStartDateString(), FORMATTER));
		}
		if (incomeDto.getEndDateString() != null) {
			existingIncome.setEndDate(LocalDate.parse(incomeDto.getEndDateString(), FORMATTER));
		}
		
		return existingIncome;    	
    }
    
    public IncomeDto transactionToTransactionDto(Income income) throws Exception {
    	IncomeDto incomeDto = incomeMapper.incomeToIncomeDto(income);
<<<<<<< Updated upstream
    	incomeDto.setIncomeTypeDescription(income.getEntryType().getDescription());
    	incomeDto.setIncomeType(String.valueOf(income.getEntryType().getId()));
=======
    	incomeDto.setTransactionType(refDataMapper.refDataToRefDataDto(income.getEntryType()));
>>>>>>> Stashed changes
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
