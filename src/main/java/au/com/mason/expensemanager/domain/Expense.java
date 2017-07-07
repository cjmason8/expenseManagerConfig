package au.com.mason.expensemanager.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "expenses")
public class Expense {
	
	public Expense() {}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Enumerated(EnumType.STRING)
	private ExpenseType expenseType;
	private BigDecimal amount;
	private Date dueDate;
	private Boolean paid = Boolean.FALSE;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "recurringExpenseId")
	private RecurringExpense recurringExpense;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ExpenseType getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	}

	public BigDecimal getAmount() {
		return amount.setScale(2);
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

	public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

	public RecurringExpense getRecurringExpense() {
		return recurringExpense;
	}

	public void setRecurringExpense(RecurringExpense recurringExpense) {
		this.recurringExpense = recurringExpense;
	}
	
}
