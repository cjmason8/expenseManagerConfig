package au.com.mason.expenseManager.service.line;

import au.com.mason.expenseManager.dao.BaseDao;
import au.com.mason.expenseManager.dao.line.ExpenseLineDao;
import au.com.mason.expenseManager.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseLineService
  extends BaseService
{
  @Autowired
  private ExpenseLineDao expenseLineDao;
  
  protected BaseDao getDao()
  {
    return this.expenseLineDao;
  }
}

