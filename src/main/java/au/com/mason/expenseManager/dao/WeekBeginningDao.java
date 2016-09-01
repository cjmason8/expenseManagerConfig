package au.com.mason.expenseManager.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import au.com.mason.expenseManager.domain.WeekBeginning;

@Repository
public class WeekBeginningDao
  extends BaseDao
{
  public WeekBeginning findCurrent()
  {
    LocalDate todaysDate = new LocalDate().minusDays(7);
    Query query = this.entityManager.createQuery("from WeekBeginning where startDate > :todaysDate ORDER BY startDate");
    query.setParameter("todaysDate", todaysDate);
    
    return (WeekBeginning)query.getResultList().get(0);
  }
  
  public List<WeekBeginning> findAllWeeksPastAndIncludingCurrent()
  {
    LocalDate todaysDate = new LocalDate().minusDays(7);
    Query query = this.entityManager.createQuery("from WeekBeginning where startDate > :todaysDate ORDER BY startDate");
    query.setParameter("todaysDate", todaysDate);
    
    return query.getResultList();
  }
  
  public List<WeekBeginning> findAllWeeksPastWeekBeginning(LocalDate startDate)
  {
    Query query = this.entityManager.createQuery("from WeekBeginning where startDate > :startDate ORDER BY startDate");
    query.setParameter("startDate", startDate);
    
    return query.getResultList();
  }
  
  public WeekBeginning findByStartDate(LocalDate startDate)
  {
    Query query = this.entityManager.createQuery("from WeekBeginning where startDate = :startDate");
    query.setParameter("startDate", startDate);
    
    List<WeekBeginning> weekBeginnings = query.getResultList();
    if (!CollectionUtils.isEmpty(weekBeginnings)) {
      return (WeekBeginning)weekBeginnings.get(0);
    }
    return null;
  }
  
  public WeekBeginning findFirstWeek()
  {
    Query query = this.entityManager.createQuery("from WeekBeginning ORDER BY startDate DESC");
    
    List<WeekBeginning> weekBeginnings = query.getResultList();
    if (!CollectionUtils.isEmpty(weekBeginnings)) {
      return (WeekBeginning)weekBeginnings.get(0);
    }
    return null;
  }
  
  public WeekBeginning findForDate(LocalDate date)
  {
    date = date.minusDays(7);
    Query query = this.entityManager.createQuery("from WeekBeginning where startDate > :date ORDER BY startDate");
    query.setParameter("date", date);
    
    List<WeekBeginning> resultList = query.getResultList();
    if (!CollectionUtils.isEmpty(resultList)) {
      return (WeekBeginning)resultList.get(0);
    }
    return null;
  }
  
  public List<WeekBeginning> findForMonth(int month, int year)
  {
    Query query = this.entityManager.createQuery("from WeekBeginning where MONTH(startDate) = :month and YEAR(startDate) = :year");
    query.setParameter("month", Integer.valueOf(month));
    query.setParameter("year", Integer.valueOf(year));
    
    return query.getResultList();
  }
  
  protected String getClassName()
  {
    return "WeekBeginning";
  }
}
