package au.com.mason.expenseManager.service.line;

import au.com.mason.expenseManager.dao.BaseDao;
import au.com.mason.expenseManager.dao.line.RentalExpenseLineDao;
import au.com.mason.expenseManager.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalExpenseLineService
  extends BaseService
{
  @Autowired
  private RentalExpenseLineDao rentalExpenseLineDao;
  
  protected BaseDao getDao()
  {
    return this.rentalExpenseLineDao;
  }
}
