package au.com.mason.expensemanager.mapper;

import java.time.DayOfWeek;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import au.com.mason.expensemanager.domain.Income;
import au.com.mason.expensemanager.dto.IncomeDto;
import au.com.mason.expensemanager.util.DateUtil;

@Component
public class IncomeMapperWrapper implements TransactionMapperWrapper<Income, IncomeDto> {
	
	private IncomeMapper incomeMapper = IncomeMapper.INSTANCE;
	private RefDataMapper refDataMapper = RefDataMapper.INSTANCE;
	private Gson gson = new GsonBuilder().serializeNulls().create();
	
	public Income transactionDtoToTransaction(IncomeDto incomeDto) throws Exception {
		Income income = incomeMapper.incomeDtoToIncome(incomeDto);
		if (incomeDto.getDueDateString() != null) {
			income.setDueDate(DateUtil.getFormattedDate(incomeDto.getDueDateString()));
		}
		
		income.setEntryType(refDataMapper.refDataDtoToRefData(incomeDto.getTransactionType()));
		if (incomeDto.getRecurringType() != null) {
			income.setRecurringType(refDataMapper.refDataDtoToRefData(incomeDto.getRecurringType()));
		}
		
		if (incomeDto.getStartDateString() != null) {
			income.setStartDate(DateUtil.getFormattedDate(incomeDto.getStartDateString()));
		}
		if (incomeDto.getEndDateString() != null) {
			income.setEndDate(DateUtil.getFormattedDate(incomeDto.getEndDateString()));
		}
		income.setMetaData(gson.fromJson(incomeDto.getMetaDataChunk(), Map.class));
		
		return income;
	}
    
    public Income transactionDtoToTransaction(IncomeDto incomeDto, Income income) throws Exception {
    	Income existingIncome = incomeMapper.incomeDtoToIncome(incomeDto, income);
    	if (incomeDto.getDueDateString() != null) {
    		existingIncome.setDueDate(DateUtil.getFormattedDate(incomeDto.getDueDateString()));
    	}
    	
	existingIncome.setEntryType(refDataMapper.refDataDtoToRefData(incomeDto.getTransactionType()));
    	if (incomeDto.getRecurringType() != null) {
    		existingIncome.setRecurringType(refDataMapper.refDataDtoToRefData(incomeDto.getRecurringType()));
    	}
    	
		if (incomeDto.getStartDateString() != null) {
			existingIncome.setStartDate(DateUtil.getFormattedDate(incomeDto.getStartDateString()));
		}
		if (incomeDto.getEndDateString() != null) {
			existingIncome.setEndDate(DateUtil.getFormattedDate(incomeDto.getEndDateString()));
		}
		existingIncome.setMetaData(gson.fromJson(incomeDto.getMetaDataChunk(), Map.class));
		
		return existingIncome;    	
    }
    
    public IncomeDto transactionToTransactionDto(Income income) throws Exception {
    	IncomeDto incomeDto = incomeMapper.incomeToIncomeDto(income);
    	incomeDto.setTransactionType(refDataMapper.refDataToRefDataDto(income.getEntryType()));
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
    	incomeDto.setMetaDataChunk(gson.toJson(income.getMetaData(), Map.class));
    	
    	return incomeDto;
    }

}
