package au.com.mason.expensemanager.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import au.com.mason.expensemanager.service.ExpenseService;

@RestController
public class TestingController {
	
	@Autowired
	private ExpenseService expensesService;
	
	@RequestMapping(value = "/testing", method = RequestMethod.GET, produces = "application/json")
	String expensesForWeek() throws Exception {
		
		LocalDate localDate = LocalDate.of(2018, 11, 30);
		
		expensesService.initialiseWeek(localDate, null);
		
		return "{\"success\":\"true\"}";
    }

}
