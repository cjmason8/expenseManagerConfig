package au.com.mason.expenseManager.domain.line;

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
public class ExpenseLine
  extends DatagridLine
{
  @ManyToOne
  @JoinColumn(name="datagrid_type_id")
  private ExpenseType expenseType;
  @Column(name="due_date")
  @Type(type="org.joda.time.contrib.hibernate.PersistentLocalDate")
  private LocalDate dueDate;
  @Column
  private String comment;
  
  public ExpenseType getExpenseType()
  {
    return this.expenseType;
  }
  
  public void copyData(ExpenseLine expenseLine)
  {
    this.expenseType = expenseLine.getExpenseType();
    this.description = expenseLine.getDescription();
    this.dueDate = expenseLine.getDueDate();
    this.amount = expenseLine.getAmount();
    this.comment = expenseLine.getComment();
    this.done = expenseLine.isDone();
  }
  
  public ExpenseLine createCopy()
  {
    ExpenseLine newExpenseLine = new ExpenseLine();
    newExpenseLine.setExpenseType(this.expenseType);
    newExpenseLine.setDescription(this.description);
    newExpenseLine.setDueDate(this.dueDate);
    newExpenseLine.setAmount(this.amount);
    newExpenseLine.setComment(this.comment);
    
    return newExpenseLine;
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
  
  public String getComment()
  {
    return this.comment;
  }
  
  public void setComment(String comment)
  {
    this.comment = comment;
  }
}

