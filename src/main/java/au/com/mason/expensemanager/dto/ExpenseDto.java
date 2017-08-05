package au.com.mason.expensemanager.dto;

public class ExpenseDto extends TransactionDto {

<<<<<<< Updated upstream
	private Long id;
	private RefDataDto expenseType;
	private String amount;
	private String dueDateString;
	private boolean paid;
	private String week;

	private RefDataDto recurringType;
	private String startDateString;
	private String endDateString;
	private String notes;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public RefDataDto getExpenseType() {
		return expenseType;
	}
	
	public void setExpenseType(RefDataDto expenseType) {
		this.expenseType = expenseType;
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
=======
	private boolean paid;
>>>>>>> Stashed changes

	public boolean getPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

<<<<<<< Updated upstream
	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

=======
>>>>>>> Stashed changes
}
