package au.com.mason.expensemanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import au.com.mason.expensemanager.dto.RefDataDto;
import au.com.mason.expensemanager.service.RefDataService;

@RestController
public class RefDataController {
	
	@Autowired
	private RefDataService refDataService;
	
	@RequestMapping(value = "/refData/{type}", method = RequestMethod.GET, produces = "application/json")
	List<RefDataDto> getRefData(@PathVariable String type) throws Exception {
		return refDataService.getRefData(type);
    }
	
}
