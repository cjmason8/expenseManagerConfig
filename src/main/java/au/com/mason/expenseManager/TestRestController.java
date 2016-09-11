package au.com.mason.expenseManager;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class TestRestController {
	
	private static final Logger LOGGER = LogManager.getLogger(TestRestController.class);

	@CrossOrigin(origins = {"http://54.191.108.166:45612", "http://localhost:45612"})
	@RequestMapping("/test")
    String home(@RequestParam String type) {
		LOGGER.debug("Entering test");
		if (type.equals("Joe")) {
			return "{\"msg\":\"Your the best Joe!\"}";
		}
		else if (type.equals("Jeff")) {
			return "{\"msg\":\"Keep going JEFF!\"}";
		}
		else {
			return "{\"msg\":\"Dont stop now Andy!\"}";
		}
    }

}
