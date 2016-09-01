package au.com.mason.expenseManager.dao.type;

import au.com.mason.expenseManager.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class RentalExpenseTypeDao
  extends BaseDao
{
  protected String getClassName()
  {
    return "RentalExpenseType";
  }
}
