package au.com.mason.expenseManager.service.recurring;

import au.com.mason.expenseManager.dao.BaseDao;
import au.com.mason.expenseManager.dao.recurring.RecurringItemDao;
import au.com.mason.expenseManager.domain.Persistable;
import au.com.mason.expenseManager.domain.RecurringPeriod;
import au.com.mason.expenseManager.domain.recurring.RecurringItem;
import au.com.mason.expenseManager.domain.recurring.RentalExpenseLineRecurringItem;
import au.com.mason.expenseManager.service.BaseService;
import au.com.mason.expenseManager.util.DateUtils;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecurringItemService
  extends BaseService
{
  @Autowired
  private RecurringItemDao recurringItemDao;
  
  public List<RecurringItem> getItemsForWeekBeginning(LocalDate weekBeginningDate)
  {
    List<Persistable> items = this.recurringItemDao.getAllExcludeDeleted();
    List<RecurringItem> results = new ArrayList();
    for (Persistable item : items)
    {
      RecurringItem recurringItem = (RecurringItem)item;
      if (checkIfValid(recurringItem, weekBeginningDate)) {
        results.add(recurringItem);
      }
    }
    return results;
  }
  
  public boolean checkIfValid(RecurringItem recurringItem, LocalDate weekBeginningDate)
  {
    boolean result = false;
    if (recurringItem.getRecurringPeriod().equals(RecurringPeriod.WEEKLY))
    {
      result = true;
    }
    else if (recurringItem.getRecurringPeriod().equals(RecurringPeriod.FORT_NIGHTLY))
    {
      LocalDate tmpDate = DateUtils.getMonday(recurringItem.getStartDate());
      LocalDate sundayOfWeekBeginning = weekBeginningDate.plusWeeks(1);
      boolean isValid = false;
      if (tmpDate.isEqual(weekBeginningDate)) {
        isValid = true;
      }
      while (tmpDate.isBefore(sundayOfWeekBeginning))
      {
        tmpDate = tmpDate.plusWeeks(2);
        if (tmpDate.isEqual(weekBeginningDate)) {
          isValid = true;
        }
      }
      if (isValid) {
        result = true;
      }
    }
    else if (recurringItem.getRecurringPeriod().equals(RecurringPeriod.FOUR_WEEKLY))
    {
      LocalDate tmpDate = DateUtils.getMonday(recurringItem.getStartDate());
      LocalDate sundayOfWeekBeginning = weekBeginningDate.plusWeeks(1);
      boolean isValid = false;
      if (tmpDate.isEqual(weekBeginningDate)) {
        isValid = true;
      }
      while (tmpDate.isBefore(sundayOfWeekBeginning))
      {
        tmpDate = tmpDate.plusWeeks(4);
        if (tmpDate.isEqual(weekBeginningDate)) {
          isValid = true;
        }
      }
      if (isValid) {
        result = true;
      }
    }
    else if (recurringItem.getRecurringPeriod().equals(RecurringPeriod.MONTHLY))
    {
      if (((recurringItem instanceof RentalExpenseLineRecurringItem)) && (((RentalExpenseLineRecurringItem)recurringItem).getInterestRate() != null))
      {
        if (weekBeginningDate.getMonthOfYear() != weekBeginningDate.plusWeeks(1).getMonthOfYear()) {
          result = true;
        }
      }
      else
      {
        LocalDate newDate = DateUtils.getDateForWeek(recurringItem, weekBeginningDate);
        if (DateUtils.getMonday(newDate).isEqual(weekBeginningDate)) {
          result = true;
        }
      }
    }
    else if (recurringItem.getRecurringPeriod().equals(RecurringPeriod.YEARLY))
    {
      LocalDate newDate = new LocalDate(weekBeginningDate.getYear(), recurringItem.getStartDate().getMonthOfYear(), recurringItem.getStartDate().getDayOfMonth());
      if (DateUtils.getMonday(newDate).isEqual(weekBeginningDate)) {
        result = true;
      }
    }
    return result;
  }
  
  public List<RecurringItem> getAllByDiscriminator(String discriminator)
  {
    return this.recurringItemDao.getAllByDiscriminator(discriminator);
  }
  
  protected BaseDao getDao()
  {
    return this.recurringItemDao;
  }
}

