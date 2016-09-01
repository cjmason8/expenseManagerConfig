package au.com.mason.expenseManager.service.line;

import au.com.mason.expenseManager.dao.BaseDao;
import au.com.mason.expenseManager.dao.line.IncomeLineDao;
import au.com.mason.expenseManager.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeLineService
  extends BaseService
{
  @Autowired
  private IncomeLineDao incomeLineDao;
  
  protected BaseDao getDao()
  {
    return this.incomeLineDao;
  }
}

