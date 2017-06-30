package au.com.mason.expensemanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.domain.RecurringExpense;
import au.com.mason.expensemanager.dto.RecurringExpenseDto;

@Component
@Mapper
public interface RecurringExpenseMapper {
	
	RecurringExpenseMapper INSTANCE = Mappers.getMapper( RecurringExpenseMapper.class );
	 
    RecurringExpense recurringExpenseDtoToRecurringExpense(RecurringExpenseDto recurringExpenseDto) throws Exception;
    
    RecurringExpense recurringExpenseDtoToRecurringExpense(RecurringExpenseDto recurringExpenseDto, @MappingTarget RecurringExpense recurringExpense) throws Exception;
    
    RecurringExpenseDto recurringExpenseToRecurringExpenseDto(RecurringExpense recurringExpense) throws Exception;

}
