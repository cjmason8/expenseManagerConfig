package au.com.mason.expensemanager.controller;

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

	@Autowired
	private UserAuthenticationService userAuthenticationService;

	@RequestMapping(value = "/users/{token}/authenticate", method = RequestMethod.GET)
	String authenticate(@PathVariable String token) throws Exception {
		JSONObject json = userAuthenticationService.authenticate(token);
		String status = json.getString("tokenStatus");

		if (status.equals("valid")) {
			return "{\"status\":\"success\"}";
		} else {
			return "{\"status\":\"failed\"}";
		}

	}
}