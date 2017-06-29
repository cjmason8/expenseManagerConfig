package au.com.mason.expensemanager.controller;

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
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	String login(@RequestBody LoginInput loginInput) {
		try {
			return userAuthenticationService.login(loginInput);
		}
		catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
    }
}
