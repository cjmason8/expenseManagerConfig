package au.com.mason.expensemanager.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import au.com.mason.expensemanager.dto.TransactionsDto;
import au.com.mason.expensemanager.dto.TransactionsForWeekDto;
import au.com.mason.expensemanager.service.ExpenseService;
import au.com.mason.expensemanager.service.IncomeService;
import au.com.mason.expensemanager.util.DateUtil;

@RestController
public class HomeController {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private IncomeService incomeService;	
	
	@RequestMapping(value = "/week", method = RequestMethod.GET, produces = "application/json")
	TransactionsForWeekDto expensesForWeek() throws Exception {

		LocalDate monday = DateUtil.getMonday(LocalDate.now());
		expenseService.initialiseWeek(monday, null);

		return new TransactionsForWeekDto(incomeService.getForWeek(monday), 
				expenseService.getForWeek(monday), monday.minusDays(7).format(FORMATTER), 
				monday.plusDays(7).format(FORMATTER), monday.format(FORMATTER));
    }
	
	@RequestMapping(value = "/week/{date}", method = RequestMethod.GET, produces = "application/json")
	TransactionsForWeekDto expensesForSpecificWeek(@PathVariable String date) throws Exception {

		LocalDate localDate = DateUtil.getMonday(LocalDate.parse(date, FORMATTER));
		expenseService.initialiseWeek(localDate, null);
		
		return new TransactionsForWeekDto(incomeService.getForWeek(localDate), 
				expenseService.getForWeek(localDate), localDate.minusDays(7).format(FORMATTER), 
				localDate.plusDays(7).format(FORMATTER), localDate.format(FORMATTER));
    }
	
	@RequestMapping(value = "/recurring", method = RequestMethod.GET, produces = "application/json")
	TransactionsDto getRecurring() throws Exception {
		return new TransactionsDto(incomeService.getAllRecurring(), expenseService.getAllRecurring());
        
    }	
	
}
