package au.com.mason.expensemanager.dto;

public class ExpenseDto {

	private Long id;
	private String expenseType;
	private String expenseTypeDescription;
	private String amount;
	private String dueDateString;
	private Boolean paid;

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

	public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

}
