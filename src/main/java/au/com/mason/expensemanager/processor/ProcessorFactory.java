package au.com.mason.expensemanager.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessorFactory {
	
	@Autowired
	private DingleyElectricityProccesor dingleyElectricityProccesor;
	
	@Autowired
	private WodongaWaterProccesor wodongaWaterProccesor;
	
	@Autowired
	private SouthKingsvilleRatesProccesor southKingsvilleRatesProccesor;

	public Processor getProcessor(String processorKey) {
		if (processorKey.equals(EmailProcessor.DINGLEY_ELECTRICITY.name())) {
			return dingleyElectricityProccesor;
		}
		else if (processorKey.equals(EmailProcessor.WODONGA_WATER.name())) {
			return wodongaWaterProccesor;
		}
		else if (processorKey.equals(EmailProcessor.SOUTH_KINGSVILLE_RATES.name())) {
			return southKingsvilleRatesProccesor;
		}
		
		return null;
	}
}
