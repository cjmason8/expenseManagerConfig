package au.com.mason.expenseManager.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.mason.expenseManager.dao.BaseDao;
import au.com.mason.expenseManager.dao.SettingsDao;
import au.com.mason.expenseManager.domain.Settings;
import au.com.mason.expenseManager.domain.SettingsKeys;

@Service
public class SettingsService
  extends BaseService
{
  @Autowired
  private SettingsDao settingsDao;
  
  public BigDecimal getOffsetBalance()
  {
    return new BigDecimal(this.settingsDao.findByName(SettingsKeys.OFFSET_BALANCE).getValue());
  }
  
  //@Transactional
  public void updateOffsetBalance(BigDecimal newBalance)
  {
    Settings offsetBalance = this.settingsDao.findByName(SettingsKeys.OFFSET_BALANCE);
    offsetBalance.setValue(newBalance.toString());
    
    this.settingsDao.update(offsetBalance);
  }
  
  protected BaseDao getDao()
  {
    return this.settingsDao;
  }
}
