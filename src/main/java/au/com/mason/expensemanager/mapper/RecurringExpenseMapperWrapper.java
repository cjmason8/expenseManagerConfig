package au.com.mason.expensemanager.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.domain.RecurringExpense;
import au.com.mason.expensemanager.dto.RecurringExpenseDto;

@Component
public class RecurringExpenseMapperWrapper {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	private RecurringExpenseMapper recurringExpenseMapper = RecurringExpenseMapper.INSTANCE;
	
	public RecurringExpense recurringExpenseDtoToRecurringExpense(RecurringExpenseDto recurringExpenseDto) throws Exception {
		RecurringExpense recurringExpense = recurringExpenseMapper.recurringExpenseDtoToRecurringExpense(recurringExpenseDto);
		recurringExpense.setStartDate(LocalDate.parse(recurringExpenseDto.getStartDateString(), FORMATTER));
		if (recurringExpenseDto.getEndDateString() != null) {
			recurringExpense.setEndDate(LocalDate.parse(recurringExpenseDto.getEndDateString(), FORMATTER));
		}
		
		return recurringExpense;
	}
    
    public RecurringExpense recurringExpenseDtoToRecurringExpense(RecurringExpenseDto recurringExpenseDto, RecurringExpense recurringExpense) throws Exception {
    	RecurringExpense existingRecurringExpense = recurringExpenseMapper.recurringExpenseDtoToRecurringExpense(recurringExpenseDto, recurringExpense);
    	existingRecurringExpense.setStartDate(LocalDate.parse(recurringExpenseDto.getStartDateString(), FORMATTER));
    	if (recurringExpenseDto.getEndDateString() != null) {
    		existingRecurringExpense.setEndDate(LocalDate.parse(recurringExpenseDto.getEndDateString(), FORMATTER));
    	}
		
		return existingRecurringExpense;    	
    }
    
    public RecurringExpenseDto recurringExpenseToRecurringExpenseDto(RecurringExpense recurringExpense) throws Exception {
    	RecurringExpenseDto recurringExpenseDto = recurringExpenseMapper.recurringExpenseToRecurringExpenseDto(recurringExpense);
    	if (recurringExpense.getStartDate()!= null) {
    		recurringExpenseDto.setStartDateString(FORMATTER.format(recurringExpense.getStartDate()));
    	}
    	if (recurringExpense.getEndDate() != null) {
    		recurringExpenseDto.setEndDateString(FORMATTER.format(recurringExpense.getEndDate()));
    	}
    	recurringExpenseDto.setExpenseTypeDescription(recurringExpense.getExpenseType().getDescription());
    	recurringExpenseDto.setRecurringTypeDescription(recurringExpense.getRecurringType().getDescription());
    	
    	return recurringExpenseDto;
    }

}
