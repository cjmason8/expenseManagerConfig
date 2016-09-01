package au.com.mason.expenseManager.controller;

import au.com.mason.expenseManager.domain.WeekBeginning;
import au.com.mason.expenseManager.dto.HomePageDetails;
import au.com.mason.expenseManager.service.SettingsService;
import au.com.mason.expenseManager.service.WeekBeginningService;
import au.com.mason.expenseManager.util.DateUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController
{
  @Autowired
  protected WeekBeginningService weekBeginningService;
  @Autowired
  private SettingsService settingsService;
  
  protected HomePageDetails getHomePageDetailsWithWeekBeginningId(Integer weekBeginningId)
  {
    HomePageDetails homePageDetails = new HomePageDetails();
    WeekBeginning currentWeek = (WeekBeginning)this.weekBeginningService.findById(weekBeginningId);
    homePageDetails.setCurrentWeek(currentWeek);
    
    return getHomePageDetails(homePageDetails, currentWeek);
  }
  
  protected HomePageDetails getHomePageDetails()
  {
    HomePageDetails homePageDetails = new HomePageDetails();
    WeekBeginning currentWeek = this.weekBeginningService.findCurrent();
    if (!currentWeek.isCurrent()) {
      this.weekBeginningService.changeOverCurrentWeek(currentWeek);
    }
    homePageDetails.setCurrentWeek(currentWeek);
    
    return getHomePageDetails(homePageDetails, currentWeek);
  }
  
  protected HomePageDetails getHomePageDetailsWithDate(String date, Integer oldWeekBeginningId)
  {
    HomePageDetails homePageDetails = new HomePageDetails();
    
    LocalDate newDate = DateUtils.getDateFromString(date);
    LocalDate startDate = DateUtils.getMonday(newDate);
    
    WeekBeginning currentWeek = this.weekBeginningService.findByStartDate(startDate);
    if (currentWeek == null)
    {
      currentWeek = (WeekBeginning)this.weekBeginningService.findById(oldWeekBeginningId);
      homePageDetails.setCurrentWeek(currentWeek);
      homePageDetails.setWeekDoesNotExist(date);
    }
    homePageDetails.setCurrentWeek(currentWeek);
    
    return getHomePageDetails(homePageDetails, currentWeek);
  }
  
  private HomePageDetails getHomePageDetails(HomePageDetails homePageDetails, WeekBeginning currentWeek)
  {
    LocalDate newDate = currentWeek.getStartDate();
    
    WeekBeginning nextWeek = this.weekBeginningService.findByStartDate(newDate.plusWeeks(1));
    
    WeekBeginning prevWeek = this.weekBeginningService.findByStartDate(newDate.minusWeeks(1));
    if (nextWeek != null) {
      homePageDetails.setNextWeekBeginningId(nextWeek.getId());
    }
    if (prevWeek != null) {
      homePageDetails.setPrevWeekBeginningId(prevWeek.getId());
    }
    homePageDetails.setCreatedWeekBeginnings(this.weekBeginningService.findForMonthAndYear(newDate.getMonthOfYear(), newDate.getYear()));
    
    homePageDetails.setReceivedRentalTotal(this.weekBeginningService.getCurrentRentBalance());
    
    homePageDetails.setOffsetBalance(this.settingsService.getOffsetBalance());
    
    return homePageDetails;
  }
}

