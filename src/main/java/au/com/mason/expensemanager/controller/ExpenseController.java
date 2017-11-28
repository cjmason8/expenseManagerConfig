package au.com.mason.expensemanager.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import au.com.mason.expensemanager.dto.ExpenseDto;
import au.com.mason.expensemanager.service.ExpenseService;

@RestController
public class ExpenseController {
	
	private static Logger LOGGER = LogManager.getLogger(ExpenseController.class);
	
	@Autowired
	private ExpenseService expenseService;
	
	@RequestMapping(value = "/expenses", method = RequestMethod.GET, produces = "application/json")
	List<ExpenseDto> getExpenses() throws Exception {
		LOGGER.info("entering getExpenses");		
		try {
			List<ExpenseDto> results = expenseService.getAll();
			
			LOGGER.info("leaving getExpenses");
			
			return results;
		}
		catch (Exception e) {
			LOGGER.error("ExpenseController getExpenses failed!!!", e);
			
			throw e;
		}
    }
	
	@RequestMapping(value = "/expenses/{id}", method = RequestMethod.GET, produces = "application/json")
	ExpenseDto getExpense(@PathVariable Long id) throws Exception {
		LOGGER.info("entering getExpense");		
		try {
			ExpenseDto result = expenseService.getById(id);
			
			LOGGER.info("leaving getExpense");
			
			return result;
		}
		catch (Exception e) {
			LOGGER.error("ExpenseController getExpense failed for id - " + id, e);
			
			throw e;
		}			
        
    }
	
	@RequestMapping(value = "/expenses/pay/{id}", method = RequestMethod.GET, produces = "application/json")
	ExpenseDto payExpense(@PathVariable Long id) throws Exception {
		LOGGER.info("entering payExpense");		
		try {
			ExpenseDto result = expenseService.payExpense(id);
			
			LOGGER.info("leaving payExpense");
			
			return result;
		}
		catch (Exception e) {
			LOGGER.error("ExpenseController payExpense failed for id - " + id, e);
			
			throw e;
		}
    }
	
	@RequestMapping(value = "/expenses/unpay/{id}", method = RequestMethod.GET, produces = "application/json")
	ExpenseDto unPayExpense(@PathVariable Long id) throws Exception {
		LOGGER.info("entering unPayExpense");		
		try {
			ExpenseDto result = expenseService.unPayExpense(id);
			
			LOGGER.info("leaving unPayExpense");
			
			return result;
		}
		catch (Exception e) {
			LOGGER.error("ExpenseController unPayExpense failed for id - " + id, e);
			
			throw e;
		}
    }	
	
	@RequestMapping(value = "/expenses", method = RequestMethod.POST, produces = "application/json", 
			consumes = "application/json", headers = "Accept=application/json")
	ExpenseDto addExpense(@RequestBody ExpenseDto expense) throws Exception {
		LOGGER.info("entering addExpense");		
		try {
			ExpenseDto result = expenseService.addTransaction(expense);
			
			LOGGER.info("leaving addExpense");
			
			return result;
		}
		catch (Exception e) {
			LOGGER.error("ExpenseController addExpense failed for type, amount - " + expense.getTransactionType() + ", " + expense.getAmount(), e);
			
			throw e;
		}			
    }
	
	@RequestMapping(value = "/expenses/{id}", method = RequestMethod.PUT, produces = "application/json", 
			consumes = "application/json", headers = "Accept=application/json")
	ExpenseDto updateExpense(@RequestBody ExpenseDto expense, Long id) throws Exception {
		LOGGER.info("entering updateExpense");		
		try {
			ExpenseDto result = expenseService.updateTransaction(expense);
			
			LOGGER.info("leaving updateExpense");

			return result;
		}
		catch (Exception e) {
			LOGGER.error("ExpenseController updateExpense failed for id - " + expense.getId(), e);
			
			throw e;
		}			
    }
	
	@RequestMapping(value = "/expenses/{id}", method = RequestMethod.DELETE, produces = "application/json",
			consumes = "application/json", headers = "Accept=application/json")
	String deleteExpense(@PathVariable Long id) throws Exception {
		LOGGER.info("entering deleteExpense");		
		try {
			expenseService.deleteTransaction(id);
			
			LOGGER.info("leaving deleteExpense");
		}
		catch (Exception e) {
			LOGGER.error("ExpenseController deleteExpense failed for id - " + id, e);
			
			throw e;
		}			
		
		return "{\"status\":\"success\"}";
    }	
}
