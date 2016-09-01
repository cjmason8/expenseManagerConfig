package au.com.mason.expenseManager.domain.recurring;

import au.com.mason.expenseManager.domain.line.IncomeLine;
import au.com.mason.expenseManager.domain.type.IncomeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("INCOME")
public class IncomeLineRecurringItem
  extends RecurringItem
{
  @ManyToOne
  @JoinColumn(name="datagrid_type_id")
  private IncomeType incomeType;
  
  public IncomeType getIncomeType()
  {
    return this.incomeType;
  }
  
  public IncomeLine createIncomeLine()
  {
    IncomeLine incomeLine = new IncomeLine();
    incomeLine.setIncomeType(this.incomeType);
    incomeLine.setAmount(this.amount);
    
    return incomeLine;
  }
  
  public void setIncomeType(IncomeType incomeType)
  {
    this.incomeType = incomeType;
  }
}

