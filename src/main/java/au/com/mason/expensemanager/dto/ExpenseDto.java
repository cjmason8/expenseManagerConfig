package au.com.mason.expensemanager.dto;

public class ExpenseDto {

	private Long id;
	private String expenseType;
	private String expenseTypeDescription;
	private String amount;
	private String dueDateString;
	private boolean paid;
	private String week;

	private Long recurringTypeId;
	private String recurringTypeDescription;
	private String startDateString;
	private String endDateString;
	private String notes;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getExpenseType() {
		return expenseType;
	}
	
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}
	
	public String getExpenseTypeDescription() {
		return expenseTypeDescription;
	}

	public void setExpenseTypeDescription(String expenseTypeDescription) {
		this.expenseTypeDescription = expenseTypeDescription;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDueDateString() {
		return dueDateString;
	}

	public void setDueDateString(String dueDateString) {
		this.dueDateString = dueDateString;
	}

	public boolean getPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
