package au.com.mason.expensemanager.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("EXPENSE")
public class Expense extends Transaction<ExpenseType> {
	
	@Enumerated(EnumType.STRING)
	private ExpenseType entryType;
	
	@OneToOne
	@JoinColumn(name = "recurringTransactionId")
	private Expense recurringTransaction;
	
	private Boolean paid = Boolean.FALSE;

	@Override
	public void setEntryType(ExpenseType entryType) {
		this.entryType = entryType;
	}

	@Override
	public ExpenseType getEntryType() {
		return entryType;
	}

	@Override
	public Expense getRecurringTransaction() {
		return recurringTransaction;
	}

	@Override
	public void setRecurringTransaction(Transaction<ExpenseType> recurringTransaction) {
		this.recurringTransaction = (Expense) recurringTransaction;
	}
	
	public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

}
