package au.com.mason.expenseManager.dao.line;

import au.com.mason.expenseManager.dao.BaseDao;
import au.com.mason.expenseManager.domain.line.IncomeLine;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class IncomeLineDao
  extends BaseDao
{
  public List<IncomeLine> getCarryOverLine()
  {
    Query query = this.entityManager.createQuery("from IncomeLine where incomeType.type = 'Carry Over'");
    
    return query.getResultList();
  }
  
  protected String getClassName()
  {
    return "IncomeLine";
  }
}
