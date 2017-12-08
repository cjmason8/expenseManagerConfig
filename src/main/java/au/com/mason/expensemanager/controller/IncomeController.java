package au.com.mason.expensemanager.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	
	private static Logger LOGGER = LogManager.getLogger(IncomeController.class);
	
	@RequestMapping(value = "/incomes/{id}", method = RequestMethod.GET, produces = "application/json")
	IncomeDto getIncome(@PathVariable Long id) throws Exception {
		LOGGER.info("entering IncomeController getIncome - " + id);
		IncomeDto income = incomeService.getById(id);
		LOGGER.info("leaving IncomeController getIncome - " + id);
		return income;
        
    }
	
	@RequestMapping(value = "/incomes", method = RequestMethod.POST, produces = "application/json", 
			consumes = "application/json", headers = "Accept=application/json")
	IncomeDto addIncome(@RequestBody IncomeDto incomeDto) throws Exception {
		LOGGER.info("entering IncomeController addIncome - " + incomeDto.getAmount());
		IncomeDto income = incomeService.addTransaction(incomeDto);
		LOGGER.info("leaving IncomeController addIncome - " + incomeDto.getAmount());
		return income;
    }
	
	@RequestMapping(value = "/incomes/{id}", method = RequestMethod.PUT, produces = "application/json", 
			consumes = "application/json", headers = "Accept=application/json")
	IncomeDto updateIncome(@RequestBody IncomeDto incomeDto, Long id) throws Exception {
		LOGGER.info("entering IncomeController updateIncome - " + id);
		IncomeDto income = incomeService.updateTransaction(incomeDto);
		LOGGER.info("leaving IncomeController updateIncome - " + id);
		return income;
    }
	
	@RequestMapping(value = "/incomes/{id}", method = RequestMethod.DELETE, produces = "application/json",
			consumes = "application/json", headers = "Accept=application/json")
	String updateIncome(@PathVariable Long id) throws Exception {
		LOGGER.info("entering IncomeController deleteIncome - " + id);
		incomeService.deleteTransaction(id);
		LOGGER.info("leaving IncomeController deleteIncome - " + id);
		
		return "{\"status\":\"success\"}";
    }	
}
