package au.com.mason.expensemanager.domain;

import java.time.temporal.ChronoUnit;

public enum RecurringUnit {
	WEEKLY(7, ChronoUnit.DAYS), FORTNIGHTLY(14, ChronoUnit.DAYS), 
	MONTHLY(1, ChronoUnit.MONTHS), YEARLY(1, ChronoUnit.YEARS),
	QUARTERLY(3, ChronoUnit.MONTHS);
	
	private Integer units;
	private ChronoUnit unitType;

	private RecurringUnit(Integer units, ChronoUnit unitType) {
		this.units = units;
		this.unitType = unitType;
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
