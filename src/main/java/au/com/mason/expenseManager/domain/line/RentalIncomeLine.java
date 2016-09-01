package au.com.mason.expenseManager.domain.line;

import au.com.mason.expenseManager.domain.type.RentalIncomeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("RENTAL_INCOME")
public class RentalIncomeLine
  extends DatagridLine
{
  @ManyToOne
  @JoinColumn(name="datagrid_type_id")
  private RentalIncomeType rentalIncomeType;
  
  public RentalIncomeType getRentalIncomeType()
  {
    return this.rentalIncomeType;
  }
  
  public void setIncomeType(RentalIncomeType rentalIncomeType)
  {
    this.rentalIncomeType = rentalIncomeType;
  }
  
  public void copyData(RentalIncomeLine incomeLine)
  {
    this.rentalIncomeType = incomeLine.getRentalIncomeType();
    this.description = incomeLine.getDescription();
    this.amount = incomeLine.getAmount();
    this.done = incomeLine.isDone();
  }
}

