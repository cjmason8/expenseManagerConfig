package au.com.mason.expensemanager.service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.dao.RefDataDao;
import au.com.mason.expensemanager.domain.RefData;
import au.com.mason.expensemanager.domain.RefDataType;
import au.com.mason.expensemanager.dto.RefDataDto;
import au.com.mason.expensemanager.mapper.RefDataMapperWrapper;

@Component
public class RefDataService {
	
	@Autowired
	private RefDataDao refDataDao;
	
	@Autowired
	private RefDataMapperWrapper refDataMapperWrapper;
	
	public List<RefDataDto> getAll() throws Exception {
		List<RefDataDto> refDataDtos = new ArrayList<>();
		for(RefData refData : refDataDao.getAll()) {
			refDataDtos.add(refDataMapperWrapper.refDataToRefDataDto(refData));
		};
		
		return refDataDtos;
	}
	
	public List<RefDataDto> getRefData(String type) throws Exception {
		List<RefDataDto> refDataDtos = new ArrayList<>();
		String typeVal = "";
		if (type.equals("expenseType")) {
			typeVal = RefDataType.EXPENSE_TYPE.name();
		}
		else if (type.equals("recurringType")) {
			typeVal = RefDataType.RECURRING_TYPE.name();
		}
		else if (type.equals("cause")) {
			typeVal = RefDataType.CAUSE.name();
		}		
		else if (type.equals("incomeType")) {
			typeVal = RefDataType.INCOME_TYPE.name();
		}		
		else {
			throw new InvalidParameterException("value " + type + " for parameter type not valid.");
		}
		
		for(RefData refData : refDataDao.getAll(typeVal)) {
			refDataDtos.add(refDataMapperWrapper.refDataToRefDataDto(refData));
		};
		
		return refDataDtos;
	}
	
	public RefDataDto updateRefData(RefDataDto refDataDto) throws Exception {
		RefData updatedRefData = refDataDao.getById(refDataDto.getId());
		updatedRefData = refDataMapperWrapper.refDataDtoToRefData(refDataDto, updatedRefData);
		
		refDataDao.update(updatedRefData);
		
		return refDataMapperWrapper.refDataToRefDataDto(updatedRefData);
	}
	
	public RefDataDto createRefData(RefDataDto refDataDto) throws Exception {
		RefData refData = refDataMapperWrapper.refDataDtoToRefData(refDataDto);
		
		refDataDao.create(refData);
		
		return refDataDto;
	}
	
	public void deleteRefData(Long id) {
		refDataDao.deleteById(id);
	}
	
	public RefDataDto getById(Long id) throws Exception {
		RefData refData = refDataDao.getById(id);
		
		return refDataMapperWrapper.refDataToRefDataDto(refData);
	}
	
	public List<RefDataDto> findRefDatas(RefDataDto refDataDto) throws Exception {
		List<RefData> refDatas = refDataDao.findRefDatas(refDataDto);
		
		List<RefDataDto> refDataDtos = new ArrayList<>();
		
		for (RefData refData : refDatas) {
			refDataDtos.add(refDataMapperWrapper.refDataToRefDataDto(refData));
		}
		
		return refDataDtos;
	}
	
	public List<RefData> getAllWithEmailKey() {
		return refDataDao.getAllWithEmailKey();
	}

}
