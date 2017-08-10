package au.com.mason.expensemanager.mapper;

import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.domain.RefData;
import au.com.mason.expensemanager.dto.RefDataDto;

@Component
public class RefDataMapperWrapper {
	
	private RefDataMapper refDataMapper = RefDataMapper.INSTANCE;
	//private Gson gson = new GsonBuilder().serializeNulls().create();
	
	public RefData refDataDtoToRefData(RefDataDto refDataDto) throws Exception {
		RefData refData = refDataMapper.refDataDtoToRefData(refDataDto);
		//refData.setMetaData(gson.fromJson(refDataDto.getMetaDataChunk(), Map.class));
		return refData;
	}
    
    public RefData refDataDtoToRefData(RefDataDto refDataDto, RefData refDataParam) throws Exception {
    	RefData refData = refDataMapper.refDataDtoToRefData(refDataDto, refDataParam);
		//refData.setMetaData(gson.fromJson(refDataDto.getMetaDataChunk(), Map.class));
    	
		return refData;
    }
    
    public RefDataDto refDataToRefDataDto(RefData refData) throws Exception {
    	RefDataDto refDataDto = refDataMapper.refDataToRefDataDto(refData);
    	refDataDto.setValue(String.valueOf(refData.getId()));
    	refDataDto.setTypeDescription(refData.getType().getDescription());
    	//refDataDto.setMetaDataChunk(gson.toJson(refData.getMetaData(), Map.class));
    	 

		return refDataDto;
    }

}
