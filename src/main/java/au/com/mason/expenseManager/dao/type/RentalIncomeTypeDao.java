package au.com.mason.expenseManager.dao.type;

import au.com.mason.expenseManager.dao.BaseDao;
import au.com.mason.expenseManager.domain.type.RentalIncomeType;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RentalIncomeTypeDao
  extends BaseDao
{
  public RentalIncomeType getCarryOver()
  {
    Query query = this.entityManager.createQuery("from RentalIncomeType where type = 'Carry Over'");
    
    return (RentalIncomeType)query.getSingleResult();
  }
  
  protected String getClassName()
  {
    return "RentalIncomeType";
  }
}
