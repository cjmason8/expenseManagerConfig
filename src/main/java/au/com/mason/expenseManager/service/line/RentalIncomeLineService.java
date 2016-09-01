package au.com.mason.expenseManager.service.line;

import au.com.mason.expenseManager.dao.BaseDao;
import au.com.mason.expenseManager.dao.line.RentalIncomeLineDao;
import au.com.mason.expenseManager.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalIncomeLineService
  extends BaseService
{
  @Autowired
  private RentalIncomeLineDao rentalIncomeLineDao;
  
  protected BaseDao getDao()
  {
    return this.rentalIncomeLineDao;
  }
}
