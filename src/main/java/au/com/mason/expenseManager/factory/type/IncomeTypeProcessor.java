package au.com.mason.expenseManager.factory.type;

import au.com.mason.expenseManager.domain.Persistable;
import au.com.mason.expenseManager.domain.type.IncomeType;
import au.com.mason.expenseManager.form.DatagridTypeForm;
import au.com.mason.expenseManager.service.type.IncomeTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeTypeProcessor
  implements TypeProcessor
{
  @Autowired
  private IncomeTypeService incomeTypeService;
  
  public List<Persistable> get()
  {
    return this.incomeTypeService.getAllExcludeDeleted();
  }
  
  public void delete(DatagridTypeForm datagridTypeForm)
  {
    IncomeType incomeType = (IncomeType)this.incomeTypeService.findById(datagridTypeForm.getId());
    incomeType.setDeleted(true);
    
    this.incomeTypeService.update(incomeType);
  }
}

