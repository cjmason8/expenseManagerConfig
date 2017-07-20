package au.com.mason.expensemanager.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("INCOME")
public class Income extends Transaction {
	
	@OneToOne
	@JoinColumn(name = "recurringTransactionId")
	private Income recurringTransaction;
	
	@Override
	public Transaction getRecurringTransaction() {
		return recurringTransaction;
	}

	@Override
	public void setRecurringTransaction(Transaction recurringTransaction) {
		this.recurringTransaction = (Income) recurringTransaction;
	}

}
