package au.com.mason.expenseManager.domain.recurring;

import au.com.mason.expenseManager.domain.line.ExpenseLine;
import au.com.mason.expenseManager.domain.type.ExpenseType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@DiscriminatorValue("EXPENSE")
public class ExpenseLineRecurringItem
  extends RecurringItem
{
  @ManyToOne
  @JoinColumn(name="datagrid_type_id")
  private ExpenseType expenseType;
  @Column(name="due_date")
  @Type(type="org.joda.time.contrib.hibernate.PersistentLocalDate")
  private LocalDate dueDate;
  
  public ExpenseType getExpenseType()
  {
    return this.expenseType;
  }
  
  public ExpenseLine createExpenseLine()
  {
    ExpenseLine expenseLine = new ExpenseLine();
    expenseLine.setExpenseType(this.expenseType);
    expenseLine.setDueDate(this.dueDate);
    expenseLine.setAmount(this.amount);
    
    return expenseLine;
  }
  
  public void setExpenseType(ExpenseType expenseType)
  {
    this.expenseType = expenseType;
  }
  
  public LocalDate getDueDate()
  {
    return this.dueDate;
  }
  
  public void setDueDate(LocalDate dueDate)
  {
    this.dueDate = dueDate;
  }
}

