package au.com.mason.expenseManager.dao.recurring;

import au.com.mason.expenseManager.dao.BaseDao;
import au.com.mason.expenseManager.domain.recurring.RecurringItem;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RecurringItemDao
  extends BaseDao
{
  public List<RecurringItem> getAllByDiscriminator(String discriminator)
  {
    Query query = this.entityManager.createQuery("from RecurringItem where discriminator = :discriminator and deleted = false");
    query.setParameter("discriminator", discriminator);
    
    return query.getResultList();
  }
  
  protected String getClassName()
  {
    return "RecurringItem";
  }
}
