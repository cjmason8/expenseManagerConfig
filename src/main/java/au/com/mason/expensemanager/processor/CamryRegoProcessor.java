package au.com.mason.expensemanager.processor;

import org.springframework.stereotype.Component;

@Component
public class CamryRegoProcessor extends VicRoadsProcessor {

	@Override
	String getFilePrefix() {
		return "CamryRego";
	}

}
