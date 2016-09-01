package au.com.mason.expenseManager.service.type;

import au.com.mason.expenseManager.dao.BaseDao;
import au.com.mason.expenseManager.dao.type.RentalIncomeTypeDao;
import au.com.mason.expenseManager.domain.type.RentalIncomeType;
import au.com.mason.expenseManager.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalIncomeTypeService
  extends BaseService
{
  @Autowired
  private RentalIncomeTypeDao rentalIncomeTypeDao;
  
  public RentalIncomeType getCarryOver()
  {
    return this.rentalIncomeTypeDao.getCarryOver();
  }
  
  protected BaseDao getDao()
  {
    return this.rentalIncomeTypeDao;
  }
}
