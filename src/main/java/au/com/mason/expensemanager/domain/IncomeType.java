package au.com.mason.expensemanager.domain;

public enum IncomeType implements RefData {
	
	CHRIS_PAY("Chris Pay"),
	JADE_PAY("Jade Pay"),
	WODONGA_RENT("Wodonga Rent"),
	SOUTH_KINGSVILLE_RENT("South Kingsville Rent");
	
	private String description;

	private IncomeType(String description) {
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
