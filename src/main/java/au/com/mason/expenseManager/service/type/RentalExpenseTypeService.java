package au.com.mason.expenseManager.service.type;

import au.com.mason.expenseManager.dao.BaseDao;
import au.com.mason.expenseManager.dao.type.RentalExpenseTypeDao;
import au.com.mason.expenseManager.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalExpenseTypeService
  extends BaseService
{
  @Autowired
  private RentalExpenseTypeDao rentalExpenseTypeDao;
  
  protected BaseDao getDao()
  {
    return this.rentalExpenseTypeDao;
  }
}
