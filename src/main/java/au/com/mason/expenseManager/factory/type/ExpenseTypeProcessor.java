package au.com.mason.expenseManager.factory.type;

import au.com.mason.expenseManager.domain.Persistable;
import au.com.mason.expenseManager.domain.type.ExpenseType;
import au.com.mason.expenseManager.form.DatagridTypeForm;
import au.com.mason.expenseManager.service.type.ExpenseTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseTypeProcessor
  implements TypeProcessor
{
  @Autowired
  private ExpenseTypeService expenseTypeService;
  
  public List<Persistable> get()
  {
    return this.expenseTypeService.getAllExcludeDeleted();
  }
  
  public void delete(DatagridTypeForm datagridTypeForm)
  {
    ExpenseType expenseType = (ExpenseType)this.expenseTypeService.findById(datagridTypeForm.getId());
    expenseType.setDeleted(true);
    
    this.expenseTypeService.update(expenseType);
  }
}

