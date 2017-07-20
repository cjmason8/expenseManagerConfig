package au.com.mason.expensemanager.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="transactions")
@DiscriminatorColumn(name = "transactionType")
public abstract class Transaction {
	
	public Transaction() {}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private BigDecimal amount;
	private Date dueDate;
	
	@OneToOne
	@JoinColumn(name = "recurringTypeId")
	private RefData recurringType;
	private Date startDate;
	private Date endDate;
	private String notes;
	
	@OneToOne
	@JoinColumn(name = "entryTypeId")
	private RefData entryType;
	
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

	public RefData getRecurringType() {
		return recurringType;
	}

	public void setRecurringType(RefData recurringType) {
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
	
	public RefData getEntryType() {
		return entryType;
	}

	public void setEntryType(RefData entryType) {
		this.entryType = entryType;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public abstract void setRecurringTransaction(Transaction recurringTransaction);
	
	public abstract Transaction getRecurringTransaction();

}
