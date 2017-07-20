package au.com.mason.expensemanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import au.com.mason.expensemanager.dto.RefDataDto;
import au.com.mason.expensemanager.service.RefDataService;

@RestController
public class RefDataController {
	
	@Autowired
	private RefDataService refDataService;
	
	@RequestMapping(value = "/refDatas/type/{type}", method = RequestMethod.GET, produces = "application/json")
	List<RefDataDto> getRefDatas(@PathVariable String type) throws Exception {
		return refDataService.getRefData(type);
    }
	
	@RequestMapping(value = "/refDatas", method = RequestMethod.GET, produces = "application/json")
	List<RefDataDto> getRefDatas() throws Exception {
		return refDataService.getAll();
    }
	
	@RequestMapping(value = "/refDatas", method = RequestMethod.POST, produces = "application/json", 
			consumes = "application/json", headers = "Accept=application/json")
	RefDataDto addRefData(@RequestBody RefDataDto refData) throws Exception {
		
		return refDataService.createRefData(refData);
    }
	
	@RequestMapping(value = "/refDatas/{id}", method = RequestMethod.PUT, produces = "application/json", 
			consumes = "application/json", headers = "Accept=application/json")
	RefDataDto updateReefData(@RequestBody RefDataDto refData, Long id) throws Exception {
		return refDataService.updateRefData(refData);
    }
	
	@RequestMapping(value = "/refDatas/{id}", method = RequestMethod.GET, produces = "application/json")
	RefDataDto getRefDatas(@PathVariable Long id) throws Exception {
		return refDataService.getById(id);
        
    }
	
	@RequestMapping(value = "/refDatas/{id}", method = RequestMethod.DELETE, produces = "application/json",
			consumes = "application/json", headers = "Accept=application/json")
	String deleteRefData(@PathVariable Long id) throws Exception {
		
		refDataService.deleteRefData(id);
		
		return "{\"status\":\"success\"}";
    }
	
}
