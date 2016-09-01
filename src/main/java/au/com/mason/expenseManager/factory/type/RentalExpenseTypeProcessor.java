package au.com.mason.expenseManager.factory.type;

import au.com.mason.expenseManager.domain.Persistable;
import au.com.mason.expenseManager.domain.type.RentalExpenseType;
import au.com.mason.expenseManager.form.DatagridTypeForm;
import au.com.mason.expenseManager.service.type.RentalExpenseTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalExpenseTypeProcessor
  implements TypeProcessor
{
  @Autowired
  private RentalExpenseTypeService rentalExpenseTypeService;
  
  public List<Persistable> get()
  {
    return this.rentalExpenseTypeService.getAllExcludeDeleted();
  }
  
  public void delete(DatagridTypeForm datagridTypeForm)
  {
    RentalExpenseType rentalExpenseType = (RentalExpenseType)this.rentalExpenseTypeService.findById(datagridTypeForm.getId());
    rentalExpenseType.setDeleted(true);
    
    this.rentalExpenseTypeService.update(rentalExpenseType);
  }
}

