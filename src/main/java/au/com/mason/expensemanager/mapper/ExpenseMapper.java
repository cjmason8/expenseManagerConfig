package au.com.mason.expensemanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.domain.Expense;
import au.com.mason.expensemanager.dto.ExpenseDto;

@Component
@Mapper
public interface ExpenseMapper {
	
	ExpenseMapper INSTANCE = Mappers.getMapper( ExpenseMapper.class );
	 
    Expense expenseDtoToExpense(ExpenseDto expenseDto) throws Exception;
    
    Expense expenseDtoToExpense(ExpenseDto expenseDto, 
    		@MappingTarget Expense expense) throws Exception;
    
	@Mappings({
	      @Mapping(target="recurringTypeId", source="expense.recurringType.id")
	    })
    ExpenseDto expenseToExpenseDto(Expense expense) throws Exception;

}
