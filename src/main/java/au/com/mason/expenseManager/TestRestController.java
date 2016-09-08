package au.com.mason.expenseManager;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class TestRestController {

	@CrossOrigin(origins = "http://54.213.123.227:45612")
	@RequestMapping("/test")
    String home(@RequestParam String type) {
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
