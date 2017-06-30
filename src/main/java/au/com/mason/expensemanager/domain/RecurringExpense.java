package au.com.mason.expensemanager.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "expenses")
public class RecurringExpense {
	
	public RecurringExpense() {}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Enumerated(EnumType.STRING)
	private ExpenseType expenseType;
	private BigDecimal amount;
	@Enumerated(EnumType.STRING)
	private RecurringType recurringType;
	private Date startDate;
	private Date endDate;
	
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

	public RecurringType getRecurringType() {
		return recurringType;
	}

	public void setRecurringType(RecurringType recurringType) {
		this.recurringType = recurringType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
