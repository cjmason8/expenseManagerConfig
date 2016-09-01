package au.com.mason.expenseManager.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import au.com.mason.expenseManager.domain.Settings;
import au.com.mason.expenseManager.domain.SettingsKeys;

@Repository
public class SettingsDao
  extends BaseDao
{
  public Settings findByName(SettingsKeys settingsKeys)
  {
    Query query = this.entityManager.createQuery("from Settings where name = :name");
    query.setParameter("name", settingsKeys.name());
    
    return (Settings)query.getSingleResult();
  }
  
  protected String getClassName()
  {
    return "Settings";
  }
}
