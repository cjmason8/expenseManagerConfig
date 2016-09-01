package au.com.mason.expenseManager.domain.line;

import au.com.mason.expenseManager.domain.type.IncomeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("INCOME")
public class IncomeLine
  extends DatagridLine
{
  @ManyToOne
  @JoinColumn(name="datagrid_type_id")
  private IncomeType incomeType;
  
  public IncomeType getIncomeType()
  {
    return this.incomeType;
  }
  
  public void setIncomeType(IncomeType incomeType)
  {
    this.incomeType = incomeType;
  }
  
  public void copyData(IncomeLine incomeLine)
  {
    this.incomeType = incomeLine.getIncomeType();
    this.description = incomeLine.getDescription();
    this.amount = incomeLine.getAmount();
    this.done = incomeLine.isDone();
  }
}

