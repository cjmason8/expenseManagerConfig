package au.com.mason.expensemanager.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("INCOME")
public class Income extends Transaction<IncomeType> {
	
	@Enumerated(EnumType.STRING)
	private IncomeType entryType;
	
	@OneToOne
	@JoinColumn(name = "recurringTransactionId")
	private Income recurringTransaction;

	@Override
	public void setEntryType(IncomeType entryType) {
		this.entryType = entryType;
	}

	@Override
	public IncomeType getEntryType() {
		return entryType;
	}

	@Override
	public Transaction<IncomeType> getRecurringTransaction() {
		return recurringTransaction;
	}

	@Override
	public void setRecurringTransaction(Transaction<IncomeType> recurringTransaction) {
		this.recurringTransaction = (Income) recurringTransaction;
	}

}
