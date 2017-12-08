package au.com.mason.expensemanager.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import au.com.mason.expensemanager.service.UserAuthenticationService;

@RequestMapping
@RestController
public class UsersController {
	
	private static Logger LOGGER = LogManager.getLogger(UsersController.class);

	@Autowired
	private UserAuthenticationService userAuthenticationService;

	@RequestMapping(value = "/users/{token}/authenticate", method = RequestMethod.GET)
	String authenticate(@PathVariable String token) throws Exception {
		LOGGER.info("entering UsersController authenticate");
		JSONObject json = userAuthenticationService.authenticate(token);
		String status = json.getString("tokenStatus");
		LOGGER.info("leaving UsersController authenticate");

		if (status.equals("valid")) {
			return "{\"status\":\"success\",\"user\":\"" + json.getString("user") + "\"}";
		} else {
			return "{\"status\":\"failed\"}";
		}

	}
}