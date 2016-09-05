package au.com.mason.expenseManager.service;

import java.util.List;

import au.com.mason.expenseManager.dao.BaseDao;
import au.com.mason.expenseManager.domain.Persistable;

public abstract class BaseService
{
  protected abstract BaseDao getDao();
  
  //@Transactional
  public void update(Persistable entity)
  {
    getDao().update(entity);
  }
  
  //@Transactional
  public void delete(Persistable entity)
  {
    getDao().delete(entity);
  }
  
  public Persistable findById(Integer id)
  {
    return getDao().findById(id);
  }
  
  public List<Persistable> getAllExcludeDeleted()
  {
    return getDao().getAllExcludeDeleted();
  }
  
  public List<Persistable> getAll()
  {
    return getDao().getAll();
  }
}
