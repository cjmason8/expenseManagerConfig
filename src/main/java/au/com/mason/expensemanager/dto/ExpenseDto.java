package au.com.mason.expensemanager.dto;

public class ExpenseDto extends TransactionDto {

	private boolean paid;

	public boolean getPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

}
