package au.com.mason.expenseManager.domain.line;

import au.com.mason.expenseManager.domain.Persistable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import org.hibernate.annotations.DiscriminatorOptions;

@Entity
@Table(name="datagrid_line")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="discriminator")
@DiscriminatorOptions(force=true)
public class DatagridLine
  implements SortableLine, Persistable
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  protected Integer id;
  @Column
  protected String description;
  @Column
  protected BigDecimal amount;
  @Column
  protected boolean done;
  @Column
  protected int sequence;
  
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
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String description)
  {
    this.description = description;
  }
  
  public boolean isDone()
  {
    return this.done;
  }
  
  public void setDone(boolean done)
  {
    this.done = done;
  }
  
  public int getSequence()
  {
    return this.sequence;
  }
  
  public void setSequence(int sequence)
  {
    this.sequence = sequence;
  }
}

