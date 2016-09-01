package au.com.mason.expenseManager.domain.type;

import au.com.mason.expenseManager.domain.Persistable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.DiscriminatorOptions;

@Entity
@Table(name="datagrid_type")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="discriminator")
@DiscriminatorOptions(force=true)
public class DatagridType
  implements Persistable
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;
  @Transient
  private String name;
  @Column
  private boolean deleted;
  @Column(name="datagrid_type")
  private String type;
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public boolean isDeleted()
  {
    return this.deleted;
  }
  
  public void setDeleted(boolean deleted)
  {
    this.deleted = deleted;
  }
}

