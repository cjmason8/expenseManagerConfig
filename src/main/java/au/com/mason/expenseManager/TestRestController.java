package au.com.mason.expenseManager;

import org.fluentd.logger.FluentLogger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class TestRestController {
	
	private static FluentLogger LOG = FluentLogger.getLogger("fluentd.test", "localhost", 24227);

	@CrossOrigin(origins = {"http://54.191.108.166:45612", "http://localhost:45612"})
	@RequestMapping("/test")
    String home(@RequestParam String type) {
		LOG.log("test_label", "test_key", "Entering test");
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
