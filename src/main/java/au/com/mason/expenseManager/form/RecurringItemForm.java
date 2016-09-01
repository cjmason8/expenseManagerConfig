package au.com.mason.expenseManager.form;

import au.com.mason.expenseManager.domain.Discriminator;
import au.com.mason.expenseManager.domain.RecurringPeriod;
import java.math.BigDecimal;
import org.joda.time.LocalDate;

public class RecurringItemForm
{
  private Integer id;
  private Discriminator discriminator;
  private Integer recurringItemType;
  private RecurringPeriod period;
  private BigDecimal amount;
  private BigDecimal rate;
  private BigDecimal principal;
  private LocalDate startDate;
  private boolean noDueDateRequired;
  
  public Discriminator getDiscriminator()
  {
    return this.discriminator;
  }
  
  public void setDiscriminator(Discriminator discriminator)
  {
    this.discriminator = discriminator;
  }
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public Integer getRecurringItemType()
  {
    return this.recurringItemType;
  }
  
  public void setRecurringItemType(Integer recurringItemType)
  {
    this.recurringItemType = recurringItemType;
  }
  
  public RecurringPeriod getPeriod()
  {
    return this.period;
  }
  
  public void setPeriod(RecurringPeriod period)
  {
    this.period = period;
  }
  
  public BigDecimal getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(BigDecimal amount)
  {
    this.amount = amount;
  }
  
  public BigDecimal getRate()
  {
    return this.rate;
  }
  
  public void setRate(BigDecimal rate)
  {
    this.rate = rate;
  }
  
  public BigDecimal getPrincipal()
  {
    return this.principal;
  }
  
  public void setPrincipal(BigDecimal principal)
  {
    this.principal = principal;
  }
  
  public LocalDate getStartDate()
  {
    return this.startDate;
  }
  
  public void setStartDate(LocalDate startDate)
  {
    this.startDate = startDate;
  }
  
  public boolean isNoDueDateRequired()
  {
    return this.noDueDateRequired;
  }
  
  public void setNoDueDateRequired(boolean noDueDateRequired)
  {
    this.noDueDateRequired = noDueDateRequired;
  }
}

