package au.com.mason.expenseManager.form;

import au.com.mason.expenseManager.domain.Discriminator;

public class DatagridTypeForm
{
  private Integer id;
  private Discriminator discriminator;
  private String name;
  
  public Discriminator getDiscriminator()
  {
    return this.discriminator;
  }
  
  public void setDiscriminator(Discriminator discriminator)
  {
    this.discriminator = discriminator;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
}

