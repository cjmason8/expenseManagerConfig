package au.com.mason.expenseManager.service.type;

import au.com.mason.expenseManager.dao.BaseDao;
import au.com.mason.expenseManager.dao.type.ExpenseTypeDao;
import au.com.mason.expenseManager.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseTypeService
  extends BaseService
{
  @Autowired
  private ExpenseTypeDao expenseTypeDao;
  
  protected BaseDao getDao()
  {
    return this.expenseTypeDao;
  }
}
