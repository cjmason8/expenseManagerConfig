package au.com.mason.expensemanager.dto;

public class IncomeDto {

	private Long id;
	private RefDataDto incomeType;
	private String amount;
	private String dueDateString;

	private RefDataDto recurringType;
	private String startDateString;
	private String endDateString;
	private String week;
	private String notes;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public RefDataDto getIncomeType() {
		return incomeType;
	}
	
	public void setIncomeType(RefDataDto incomeType) {
		this.incomeType = incomeType;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public RefDataDto getRecurringType() {
		return recurringType;
	}

	public void setRecurringType(RefDataDto recurringType) {
		this.recurringType = recurringType;
	}

	public String getStartDateString() {
		return startDateString;
	}

	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}

	public String getEndDateString() {
		return endDateString;
	}

	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getDueDateString() {
		return dueDateString;
	}

	public void setDueDateString(String dueDateString) {
		this.dueDateString = dueDateString;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
