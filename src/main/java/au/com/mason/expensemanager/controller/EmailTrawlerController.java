package au.com.mason.expensemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import au.com.mason.expensemanager.robot.EmailTrawler;

@RestController
public class EmailTrawlerController {
	
	@Autowired
	private EmailTrawler emailTrawler;
	
	@RequestMapping(value = "/runEmailTrawler", method = RequestMethod.GET, produces = "application/json")
	String expensesForWeek() throws Exception {
		
		emailTrawler.check();
		
		return "{\"success\":\"true\"}";
    }

}
