package au.com.mason.expensemanager.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="transactions")
@DiscriminatorColumn(name = "transactionType")
public abstract class Transaction<T extends RefData> {
	
	public Transaction() {}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private BigDecimal amount;
	private Date dueDate;
	
	@Enumerated(EnumType.STRING)
	private RecurringType recurringType;
	private Date startDate;
	private Date endDate;
	
	private Boolean deleted = Boolean.FALSE;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDate getDueDate() {
		return new java.sql.Date(dueDate.getTime()).toLocalDate();
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = java.sql.Date.valueOf(dueDate);
	}

	public RecurringType getRecurringType() {
		return recurringType;
	}

	public void setRecurringType(RecurringType recurringType) {
		this.recurringType = recurringType;
	}

	public LocalDate getStartDate() {
		if (startDate != null) {
			return new java.sql.Date(startDate.getTime()).toLocalDate();
		}
		
		return null;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = java.sql.Date.valueOf(startDate);
	}

	public LocalDate getEndDate() {
		if (endDate != null) {
			return new java.sql.Date(endDate.getTime()).toLocalDate();
		}
		
		return null;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = java.sql.Date.valueOf(endDate);
	}
	
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public abstract void setEntryType(T entryType);
	
	public abstract T getEntryType();
	
	public abstract Transaction<T> getRecurringTransaction();
	
	public abstract void setRecurringTransaction(Transaction<T> recurringTransaction);

}
