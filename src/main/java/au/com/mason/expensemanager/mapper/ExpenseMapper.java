package au.com.mason.expensemanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
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
    
    ExpenseDto expenseToExpenseDto(Expense expense) throws Exception;

}
