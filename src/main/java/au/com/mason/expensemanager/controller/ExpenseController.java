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
		LOGGER.info("entering ExpenseController getExpenses");		
		List<ExpenseDto> results = expenseService.getAll();
		
		LOGGER.info("leaving ExpenseController getExpenses");
		
		return results;
    }
	
	@RequestMapping(value = "/expenses/{id}", method = RequestMethod.GET, produces = "application/json")
	ExpenseDto getExpense(@PathVariable Long id) throws Exception {
		LOGGER.info("entering ExpenseController getExpense - " + id);		
		ExpenseDto result = expenseService.getById(id);
		
		LOGGER.info("leaving ExpenseController getExpense - " + id);
		
		return result;
    }
	
	@RequestMapping(value = "/expenses/pay/{id}", method = RequestMethod.GET, produces = "application/json")
	ExpenseDto payExpense(@PathVariable Long id) throws Exception {
		LOGGER.info("entering ExpenseController payExpense - " + id);		
		ExpenseDto result = expenseService.payExpense(id);
		
		LOGGER.info("leaving ExpenseController payExpense - " + id);
		
		return result;
    }
	
	@RequestMapping(value = "/expenses/unpay/{id}", method = RequestMethod.GET, produces = "application/json")
	ExpenseDto unPayExpense(@PathVariable Long id) throws Exception {
		LOGGER.info("entering ExpenseController unPayExpense - " + id);
		ExpenseDto result = expenseService.unPayExpense(id);
		
		LOGGER.info("leaving ExpenseController unPayExpense - " + id);
		
		return result;
    }	
	
	@RequestMapping(value = "/expenses", method = RequestMethod.POST, produces = "application/json", 
			consumes = "application/json", headers = "Accept=application/json")
	ExpenseDto addExpense(@RequestBody ExpenseDto expense) throws Exception {
		LOGGER.info("entering ExpenseController addExpense - " + expense.getTransactionType() + ", " + expense.getAmount());		
		ExpenseDto result = expenseService.addTransaction(expense);
		
		LOGGER.info("leaving ExpenseController addExpense - " + expense.getTransactionType() + ", " + expense.getAmount());
		
		return result;
    }
	
	@RequestMapping(value = "/expenses/{id}", method = RequestMethod.PUT, produces = "application/json", 
			consumes = "application/json", headers = "Accept=application/json")
	ExpenseDto updateExpense(@RequestBody ExpenseDto expense, Long id) throws Exception {
		LOGGER.info("entering ExpenseController updateExpense - " + id);		
		ExpenseDto result = expenseService.updateTransaction(expense);
		
		LOGGER.info("leaving ExpenseController updateExpense - " + id);

		return result;
    }
	
	@RequestMapping(value = "/expenses/{id}", method = RequestMethod.DELETE, produces = "application/json",
			consumes = "application/json", headers = "Accept=application/json")
	String deleteExpense(@PathVariable Long id) throws Exception {
		LOGGER.info("entering ExpenseController deleteExpense - " + id);		
		expenseService.deleteTransaction(id);
		
		LOGGER.info("leaving ExpenseController deleteExpense - " + id);
		
		return "{\"status\":\"success\"}";
    }	
}
