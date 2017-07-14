package au.com.mason.expensemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import au.com.mason.expensemanager.dto.IncomeDto;
import au.com.mason.expensemanager.service.IncomeService;

@RestController
public class IncomeController {
	
	@Autowired
	private IncomeService incomeService;	
	
	@RequestMapping(value = "/incomes/{id}", method = RequestMethod.GET, produces = "application/json")
	IncomeDto getIncome(@PathVariable Long id) throws Exception {
		return incomeService.getById(id);
        
    }
	
	@RequestMapping(value = "/incomes", method = RequestMethod.POST, produces = "application/json", 
			consumes = "application/json", headers = "Accept=application/json")
	IncomeDto addIncome(@RequestBody IncomeDto income) throws Exception {
		
		return incomeService.addIncome(income);
    }
	
	@RequestMapping(value = "/incomes/{id}", method = RequestMethod.PUT, produces = "application/json", 
			consumes = "application/json", headers = "Accept=application/json")
	IncomeDto updateIncome(@RequestBody IncomeDto income, Long id) throws Exception {
		return incomeService.updateIncome(income);
    }
	
	@RequestMapping(value = "/incomes/{id}", method = RequestMethod.DELETE, produces = "application/json",
			consumes = "application/json", headers = "Accept=application/json")
	String updateIncome(@PathVariable Long id) throws Exception {
		
		incomeService.deleteIncome(id);
		
		return "{\"status\":\"success\"}";
    }	
}
