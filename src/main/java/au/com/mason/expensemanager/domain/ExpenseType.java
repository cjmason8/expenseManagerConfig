package au.com.mason.expensemanager.domain;

public enum ExpenseType implements RefData {
	
	ELECTRICITY("Electricity"),
	GAS("Gas"),
	RATES_DINGLEY("Rates - Dingley"),
	RATES_NOBLE_PARK("Rates - Noble Park"),
	RATES_LAUNCESTON("Rates - Launceston"),
	RATES_WODONGA("Rates - Wodonga"),
	RATES_SOUTH_KINGSVILLE("Rates - Sth Kingsville");

	
	private String description;

	private ExpenseType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getValue() {
		return this.name();
	}
}
