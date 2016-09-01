package au.com.mason.expenseManager.dao.type;

import au.com.mason.expenseManager.dao.BaseDao;
import au.com.mason.expenseManager.domain.type.IncomeType;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class IncomeTypeDao
  extends BaseDao
{
  public IncomeType getCarryOver()
  {
    Query query = this.entityManager.createQuery("from IncomeType where type = 'Carry Over'");
    
    return (IncomeType)query.getSingleResult();
  }
  
  protected String getClassName()
  {
    return "IncomeType";
  }
}
