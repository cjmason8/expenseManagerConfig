package au.com.mason.expensemanager.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("EXPENSE")
public class Expense extends Transaction {
	
	@OneToOne
	@JoinColumn(name = "recurringTransactionId")
	private Expense recurringTransaction;
	
	private boolean paid = false;

	@Override
	public Expense getRecurringTransaction() {
		return recurringTransaction;
	}

	@Override
	public void setRecurringTransaction(Transaction recurringTransaction) {
		this.recurringTransaction = (Expense) recurringTransaction;
	}
	
	public boolean getPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

}
