package au.com.mason.expenseManager.service.type;

import au.com.mason.expenseManager.dao.BaseDao;
import au.com.mason.expenseManager.dao.type.IncomeTypeDao;
import au.com.mason.expenseManager.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeTypeService
  extends BaseService
{
  @Autowired
  private IncomeTypeDao incomeTypeDao;
  
  protected BaseDao getDao()
  {
    return this.incomeTypeDao;
  }
}
