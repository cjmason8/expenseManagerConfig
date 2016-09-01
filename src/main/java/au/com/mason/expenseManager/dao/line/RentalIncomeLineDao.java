package au.com.mason.expenseManager.dao.line;

import au.com.mason.expenseManager.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class RentalIncomeLineDao
  extends BaseDao
{
  protected String getClassName()
  {
    return "RentalIncomeLine";
  }
}
