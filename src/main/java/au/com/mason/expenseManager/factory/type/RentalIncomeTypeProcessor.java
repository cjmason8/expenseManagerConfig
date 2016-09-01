package au.com.mason.expenseManager.factory.type;

import au.com.mason.expenseManager.domain.Persistable;
import au.com.mason.expenseManager.domain.type.RentalIncomeType;
import au.com.mason.expenseManager.form.DatagridTypeForm;
import au.com.mason.expenseManager.service.type.RentalIncomeTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalIncomeTypeProcessor
  implements TypeProcessor
{
  @Autowired
  private RentalIncomeTypeService rentalIncomeTypeService;
  
  public List<Persistable> get()
  {
    return this.rentalIncomeTypeService.getAllExcludeDeleted();
  }
  
  public void delete(DatagridTypeForm datagridTypeForm)
  {
    RentalIncomeType rentalIncomeType = (RentalIncomeType)this.rentalIncomeTypeService.findById(datagridTypeForm.getId());
    rentalIncomeType.setDeleted(true);
    
    this.rentalIncomeTypeService.update(rentalIncomeType);
  }
}

