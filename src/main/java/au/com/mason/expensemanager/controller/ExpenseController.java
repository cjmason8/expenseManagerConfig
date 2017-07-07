package au.com.mason.expensemanager.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import au.com.mason.expensemanager.dto.ExpenseDto;
import au.com.mason.expensemanager.dto.ExpensesForWeekDto;
import au.com.mason.expensemanager.service.ExpenseService;
import au.com.mason.expensemanager.util.DateUtil;

@RestController
public class ExpenseController {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	@Autowired
	private ExpenseService expenseService;
	
	@RequestMapping(value = "/expenses/week", method = RequestMethod.GET, produces = "application/json")
	ExpensesForWeekDto expensesForWeek() throws Exception {

		LocalDate monday = DateUtil.getMonday(LocalDate.now());
		return new ExpensesForWeekDto(expenseService.getExpensesForWeek(monday), 
				monday.minusDays(7).format(FORMATTER), monday.plusDays(7).format(FORMATTER), monday.format(FORMATTER));
    }
	
	@RequestMapping(value = "/expenses/week/{date}", method = RequestMethod.GET, produces = "application/json")
	ExpensesForWeekDto expensesForSpecificWeek(@PathVariable String date) throws Exception {

		LocalDate localDate = LocalDate.parse(date, FORMATTER);
		return new ExpensesForWeekDto(expenseService.getExpensesForWeek(localDate), 
				localDate.minusDays(7).format(FORMATTER), localDate.plusDays(7).format(FORMATTER), date);
    }
	
	@RequestMapping(value = "/expenses/{id}", method = RequestMethod.GET, produces = "application/json")
	ExpenseDto getExpense(@PathVariable Long id) throws Exception {
		return expenseService.getById(id);
        
    }
	
	@RequestMapping(value = "/expenses", method = RequestMethod.POST, produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	ExpenseDto addExpense(@RequestBody ExpenseDto expense) throws Exception {
		
		return expenseService.addExpense(expense);
    }
	
	@RequestMapping(value = "/expenses/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	ExpenseDto updateExpense(@RequestBody ExpenseDto expense, Long id) throws Exception {
		return expenseService.updateExpense(expense);
    }
	
	@RequestMapping(value = "/expenses/{id}", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	String updateExpense(@PathVariable Long id) throws Exception {
		
		expenseService.deleteExpense(id);
		
		return "{\"status\":\"success\"}";
    }	
}
