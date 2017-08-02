package au.com.mason.expensemanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.domain.Income;
import au.com.mason.expensemanager.dto.IncomeDto;

@Component
@Mapper(uses = {RefDataMapper.class})
public interface IncomeMapper {
	
	IncomeMapper INSTANCE = Mappers.getMapper( IncomeMapper.class );
	 
	Income incomeDtoToIncome(IncomeDto incomeDto) throws Exception;
    
    Income incomeDtoToIncome(IncomeDto incomeDto, @MappingTarget Income income) throws Exception;
    
    IncomeDto incomeToIncomeDto(Income income) throws Exception;

}
