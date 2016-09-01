package au.com.mason.expenseManager.factory.type;

import au.com.mason.expenseManager.domain.Persistable;
import au.com.mason.expenseManager.form.DatagridTypeForm;
import java.util.List;

public abstract interface TypeProcessor
{
  public abstract List<Persistable> get();
  
  public abstract void delete(DatagridTypeForm paramDatagridTypeForm);
}

