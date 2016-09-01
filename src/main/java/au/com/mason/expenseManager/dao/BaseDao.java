package au.com.mason.expenseManager.dao;

import au.com.mason.expenseManager.domain.Persistable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public abstract class BaseDao
{
  @PersistenceContext(unitName="ref-persistance-unit")
  protected EntityManager entityManager;
  
  protected abstract String getClassName();
  
  public void update(Persistable entity)
  {
    if (entity.getId() == null) {
      this.entityManager.persist(entity);
    } else {
      this.entityManager.merge(entity);
    }
  }
  
  public Persistable findById(Integer id)
  {
    Query query = this.entityManager.createQuery("from " + getClassName() + " where id = :id");
    query.setParameter("id", id);
    
    return (Persistable)query.getSingleResult();
  }
  
  public List<Persistable> getAll()
  {
    Query query = this.entityManager.createQuery("from " + getClassName());
    
    return query.getResultList();
  }
  
  public List<Persistable> getAllExcludeDeleted()
  {
    Query query = this.entityManager.createQuery("from " + getClassName() + " where deleted = false");
    
    return query.getResultList();
  }
  
  public void delete(Persistable item)
  {
    this.entityManager.remove(this.entityManager.contains(item) ? item : (Persistable)this.entityManager.merge(item));
  }
}
