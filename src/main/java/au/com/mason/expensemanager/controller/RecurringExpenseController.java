package au.com.mason.expensemanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import au.com.mason.expensemanager.dto.RecurringExpenseDto;
import au.com.mason.expensemanager.service.RecurringExpenseService;

@RestController
public class RecurringExpenseController {
	
	@Autowired
	private RecurringExpenseService recurringExpenseService;
	
	@RequestMapping(value = "/recurringExpenses", method = RequestMethod.GET, produces = "application/json")
	List<RecurringExpenseDto> recurringExpenses() throws Exception {
		return recurringExpenseService.getAll();
        
    }
	
	@RequestMapping(value = "/recurringExpenses/{id}", method = RequestMethod.GET, produces = "application/json")
	RecurringExpenseDto getRecurringExpense(@PathVariable Long id) throws Exception {
		return recurringExpenseService.getById(id);
        
    }
	
	@RequestMapping(value = "/recurringExpenses", method = RequestMethod.POST, produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	RecurringExpenseDto addRecurringExpense(@RequestBody RecurringExpenseDto recurringExpense) throws Exception {
		
		return recurringExpenseService.addRecurringExpense(recurringExpense);
    }
	
	@RequestMapping(value = "/recurringExpenses/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	RecurringExpenseDto updateRecurringExpense(@RequestBody RecurringExpenseDto recurringExpense, Long id) throws Exception {
		return recurringExpenseService.updateRecurringExpense(recurringExpense);
    }
	
	@RequestMapping(value = "/recurringExpenses/{id}", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	String updateRecurringExpense(@PathVariable Long id) throws Exception {
		
		recurringExpenseService.deleteRecurringExpense(id);
		
		return "{\"status\":\"success\"}";
    }	
}
