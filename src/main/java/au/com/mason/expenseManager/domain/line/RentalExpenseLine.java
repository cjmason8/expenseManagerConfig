package au.com.mason.expenseManager.domain.line;

import au.com.mason.expenseManager.domain.type.RentalExpenseType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@DiscriminatorValue("RENTAL_EXPENSE")
public class RentalExpenseLine
  extends DatagridLine
{
  @ManyToOne
  @JoinColumn(name="datagrid_type_id")
  private RentalExpenseType rentalExpenseType;
  @Column(name="due_date")
  @Type(type="org.joda.time.contrib.hibernate.PersistentLocalDate")
  private LocalDate dueDate;
  @Column
  private String comment;
  
  public RentalExpenseType getRentalExpenseType()
  {
    return this.rentalExpenseType;
  }
  
  public void copyData(RentalExpenseLine rentalExpenseLine)
  {
    this.rentalExpenseType = rentalExpenseLine.getRentalExpenseType();
    this.description = rentalExpenseLine.getDescription();
    this.dueDate = rentalExpenseLine.getDueDate();
    this.amount = rentalExpenseLine.getAmount();
    this.comment = rentalExpenseLine.getComment();
    this.done = rentalExpenseLine.isDone();
  }
  
  public void setExpenseType(RentalExpenseType rentalExpenseType)
  {
    this.rentalExpenseType = rentalExpenseType;
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

