package au.com.mason.expensemanager.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import au.com.mason.expensemanager.dto.LoginInput;
import au.com.mason.expensemanager.service.UserAuthenticationService;

@RestController
public class LoginController {
	
	@Autowired
	private UserAuthenticationService userAuthenticationService;
	
	private static Logger LOGGER = LogManager.getLogger(LoginController.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	String login(@RequestBody LoginInput loginInput) {
		try {
			LOGGER.info("entering LoginController login - " + loginInput.getUserName());
			String login = userAuthenticationService.login(loginInput);
			LOGGER.info("leaving LoginController login - " + loginInput.getUserName());
			return login;
		}
		catch (Exception e) {
			LOGGER.error("Error logging in", e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
    }
}
