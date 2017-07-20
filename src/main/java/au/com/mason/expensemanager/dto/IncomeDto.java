package au.com.mason.expensemanager.dto;

public class IncomeDto {

	private Long id;
	private String incomeType;
	private String incomeTypeDescription;
	private String amount;
	private String dueDateString;

	private Long recurringTypeId;
	private String recurringTypeDescription;
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
	
	public String getIncomeType() {
		return incomeType;
	}
	
	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}
	
	public String getIncomeTypeDescription() {
		return incomeTypeDescription;
	}

	public void setIncomeTypeDescription(String expenseTypeDescription) {
		this.incomeTypeDescription = expenseTypeDescription;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Long getRecurringTypeId() {
		return recurringTypeId;
	}

	public void setRecurringTypeId(Long recurringTypeId) {
		this.recurringTypeId = recurringTypeId;
	}

	public String getRecurringTypeDescription() {
		return recurringTypeDescription;
	}

	public void setRecurringTypeDescription(String recurringTypeDescription) {
		this.recurringTypeDescription = recurringTypeDescription;
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
