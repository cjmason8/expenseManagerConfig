package au.com.mason.expenseManager;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class TestRestController {
	
	private static final Logger LOGGER = LogManager.getLogger(TestRestController.class.getName());
	//private static FluentLogger LOG = FluentLogger.getLogger("fluentd.test", "172.17.0.1", 24224);

	@CrossOrigin(origins = {"http://54.191.108.166:45612", "http://localhost:45612"})
	@RequestMapping("/test")
    String home(@RequestParam String type) {
		LOGGER.info("{\"msg\":\"All is good in the world of bazookers !!!\", \"dateTime\":\"" + new Date() + "\"}");
		//LOG.log("test_label", "test_key", "All is good in the world of bazookers!!!");
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
