package au.com.mason.expensemanager.domain;

public enum RecurringType {

	WEEKLY("Weekly"), FORTNIGHTLY("Fortnightly"), MONTHLY("Monthly"), YEARLY("Yearly");
	
	private String description;

	private RecurringType(String description) {
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
