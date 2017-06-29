package au.com.mason.expensemanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.domain.RefData;
import au.com.mason.expensemanager.dto.RefDataDto;

@Component
@Mapper
public interface RefDataMapper {
	
	RefDataMapper INSTANCE = Mappers.getMapper( RefDataMapper.class );
	 
    RefDataDto refDataToRefDataDto(RefData refData);

}
