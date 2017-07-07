package au.com.mason.expensemanager.domain;

import java.time.temporal.ChronoUnit;

public enum RecurringType implements RefData {

	WEEKLY("Weekly", 7, ChronoUnit.DAYS), FORTNIGHTLY("Fortnightly", 14, ChronoUnit.DAYS), 
	MONTHLY("Monthly", 1, ChronoUnit.MONTHS), YEARLY("Yearly", 1, ChronoUnit.YEARS),
	QUARTERLY("Quarterly", 3, ChronoUnit.MONTHS);
	
	private String description;
	private Integer units;
	private ChronoUnit unitType;

	private RecurringType(String description, Integer units, ChronoUnit unitType) {
		this.description = description;
		this.units = units;
		this.unitType = unitType;
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
	
	public Integer getUnits() {
		return units;
	}

	public ChronoUnit getUnitType() {
		return unitType;
	}
	
}
