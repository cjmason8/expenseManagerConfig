package au.com.mason.expensemanager.service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.domain.ExpenseType;
import au.com.mason.expensemanager.dto.RefDataDto;
import au.com.mason.expensemanager.mapper.RefDataMapper;

@Component
public class RefDataService {
	
	private RefDataMapper refDataMapper = RefDataMapper.INSTANCE;
	
	public List<RefDataDto> getRefData(String type) {
		List<RefDataDto> refDataDtos = new ArrayList<>();		
		if (type.equals("expenseType")) {
			Arrays.asList(ExpenseType.values()).forEach(refData -> {
				refDataDtos.add(refDataMapper.refDataToRefDataDto(refData));
			});
			
		}
		else {
			throw new InvalidParameterException("value " + type + " for parameter type not valid.");
		}
		
		return refDataDtos;
	}

}
