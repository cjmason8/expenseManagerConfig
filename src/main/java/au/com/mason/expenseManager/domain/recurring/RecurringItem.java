package au.com.mason.expenseManager.domain.recurring;

import au.com.mason.expenseManager.domain.Persistable;
import au.com.mason.expenseManager.domain.RecurringPeriod;
import au.com.mason.expenseManager.util.DateUtils;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Table(name="recurring_item")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="discriminator")
@DiscriminatorOptions(force=true)
public class RecurringItem
  implements Persistable
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;
  @Column
  protected BigDecimal amount;
  @Column(name="start_date")
  @Type(type="org.joda.time.contrib.hibernate.PersistentLocalDate")
  protected LocalDate startDate;
  @Column
  @Enumerated(EnumType.STRING)
  private RecurringPeriod recurringPeriod;
  @Column
  private boolean deleted;
  @Column
  private boolean noDueDateRequired;
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public BigDecimal getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(BigDecimal amount)
  {
    this.amount = amount;
  }
  
  public RecurringPeriod getRecurringPeriod()
  {
    return this.recurringPeriod;
  }
  
  public String getRecurringPeriodString()
  {
    if (this.recurringPeriod != null) {
      return this.recurringPeriod.getDisplayName();
    }
    return "";
  }
  
  public void setRecurringPeriod(RecurringPeriod recurringPeriod)
  {
    this.recurringPeriod = recurringPeriod;
  }
  
  public LocalDate getStartDate()
  {
    return this.startDate;
  }
  
  public String getStartDateString()
  {
    return DateUtils.getDateString(this.startDate);
  }
  
  public void setStartDate(LocalDate startDate)
  {
    this.startDate = startDate;
  }
  
  public boolean isDeleted()
  {
    return this.deleted;
  }
  
  public void setDeleted(boolean deleted)
  {
    this.deleted = deleted;
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

