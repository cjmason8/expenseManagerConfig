package au.com.mason.expensemanager.dto;

public class NotificationDto {

	private Long id;
	private ExpenseDto expense;
	private String message;
	private String createdDateString;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public ExpenseDto getExpense() {
		return expense;
	}

	public void setExpense(ExpenseDto expense) {
		this.expense = expense;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCreatedDateString() {
		return createdDateString;
	}

	public void setCreatedDateString(String createdDateString) {
		this.createdDateString = createdDateString;
	}
	
}
