package au.com.mason.expenseManager.domain.recurring;

import au.com.mason.expenseManager.domain.line.RentalExpenseLine;
import au.com.mason.expenseManager.domain.type.RentalExpenseType;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@DiscriminatorValue("RENTAL_EXPENSE")
public class RentalExpenseLineRecurringItem
  extends RecurringItem
{
  @ManyToOne
  @JoinColumn(name="datagrid_type_id")
  private RentalExpenseType rentalExpenseType;
  @Column(name="interest_rate")
  private BigDecimal interestRate;
  @Column
  private BigDecimal principal;
  @Column(name="due_date")
  @Type(type="org.joda.time.contrib.hibernate.PersistentLocalDate")
  private LocalDate dueDate;
  
  public RentalExpenseType getRentalExpenseType()
  {
    return this.rentalExpenseType;
  }
  
  public RentalExpenseLine createRentalExpenseLine()
  {
    RentalExpenseLine rentalExpenseLine = new RentalExpenseLine();
    rentalExpenseLine.setExpenseType(this.rentalExpenseType);
    rentalExpenseLine.setDueDate(this.dueDate);
    rentalExpenseLine.setAmount(this.amount);
    
    return rentalExpenseLine;
  }
  
  public void setRentalExpenseType(RentalExpenseType rentalExpenseType)
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
  
  public BigDecimal getInterestRate()
  {
    return this.interestRate;
  }
  
  public void setInterestRate(BigDecimal interestRate)
  {
    this.interestRate = interestRate;
  }
  
  public BigDecimal getPrincipal()
  {
    return this.principal;
  }
  
  public void setPrincipal(BigDecimal principal)
  {
    this.principal = principal;
  }
}

