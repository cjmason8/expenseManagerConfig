package au.com.mason.expensemanager.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.LogManager;
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
	
	private static Logger LOGGER = LogManager.getLogger(HomeController.class);
	
	@RequestMapping(value = "/logging", method = RequestMethod.GET)
	String logging() throws Exception {

		LOGGER.info("INFO MESSAGE!!!");
		LOGGER.warn("WARN MESSAGE!!!");
		LOGGER.debug("DEBUG MESSAGE!!!");
		LOGGER.trace("TRACE MESSAGE!!!");
		LOGGER.error("ERROR MESSAGE!!!");
		
		if (true) {
			throw new RuntimeException("Aaaarrrhhh!!!");
		}

		return "success";
    }
	
	@RequestMapping(value = "/week", method = RequestMethod.GET, produces = "application/json")
	TransactionsForWeekDto expensesForWeek() throws Exception {

		LocalDate monday = DateUtil.getMonday(LocalDate.now());
		expenseService.initialiseWeek(monday, null);

		return getTransactionsForWeekDto(monday);
    }

	@RequestMapping(value = "/week/{date}", method = RequestMethod.GET, produces = "application/json")
	TransactionsForWeekDto expensesForSpecificWeek(@PathVariable String date) throws Exception {

		LocalDate localDate = DateUtil.getMonday(LocalDate.parse(date, FORMATTER));
		if (localDate.isAfter(LocalDate.now())) {
			expenseService.initialiseWeek(localDate, null);
		}
		
		return getTransactionsForWeekDto(localDate);
    }
	
	@RequestMapping(value = "/recurring", method = RequestMethod.GET, produces = "application/json")
	TransactionsDto getRecurring() throws Exception {
		return new TransactionsDto(incomeService.getAllRecurring(), expenseService.getAllRecurring());
        
    }
	
	private TransactionsForWeekDto getTransactionsForWeekDto(LocalDate localDate) throws Exception {
		TransactionsForWeekDto transactionsForWeekDto = new TransactionsForWeekDto();
		transactionsForWeekDto.setIncomes(incomeService.getForWeek(localDate)); 
		transactionsForWeekDto.setExpenses(expenseService.getForWeek(localDate));
		transactionsForWeekDto.setPreviousWeek(localDate.minusDays(7).format(FORMATTER));
		transactionsForWeekDto.setNextWeek(localDate.plusDays(7).format(FORMATTER));
		transactionsForWeekDto.setThisWeek(localDate.format(FORMATTER));
		if (localDate.isEqual(DateUtil.getMonday(LocalDate.now()))) {
			transactionsForWeekDto.setUnpaidExpenses(expenseService.getUnpaidBeforeWeek(localDate));
		}
		transactionsForWeekDto.setTotals();
		
		return transactionsForWeekDto;
	}
	
}
