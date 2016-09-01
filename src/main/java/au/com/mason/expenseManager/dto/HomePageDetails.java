package au.com.mason.expenseManager.dto;

import au.com.mason.expenseManager.domain.WeekBeginning;
import java.math.BigDecimal;
import java.util.List;

public class HomePageDetails
{
  private WeekBeginning currentWeek;
  private String weekDoesNotExist;
  private Integer nextWeekBeginningId;
  private Integer prevWeekBeginningId;
  private List<String> createdWeekBeginnings;
  private BigDecimal receivedRentalTotal;
  private BigDecimal offsetBalance;
  
  public WeekBeginning getCurrentWeek()
  {
    return this.currentWeek;
  }
  
  public void setCurrentWeek(WeekBeginning currentWeek)
  {
    this.currentWeek = currentWeek;
  }
  
  public String getWeekDoesNotExist()
  {
    return this.weekDoesNotExist;
  }
  
  public void setWeekDoesNotExist(String weekDoesNotExist)
  {
    this.weekDoesNotExist = weekDoesNotExist;
  }
  
  public Integer getNextWeekBeginningId()
  {
    return this.nextWeekBeginningId;
  }
  
  public void setNextWeekBeginningId(Integer nextWeekBeginningId)
  {
    this.nextWeekBeginningId = nextWeekBeginningId;
  }
  
  public Integer getPrevWeekBeginningId()
  {
    return this.prevWeekBeginningId;
  }
  
  public void setPrevWeekBeginningId(Integer prevWeekBeginningId)
  {
    this.prevWeekBeginningId = prevWeekBeginningId;
  }
  
  public List<String> getCreatedWeekBeginnings()
  {
    return this.createdWeekBeginnings;
  }
  
  public void setCreatedWeekBeginnings(List<String> createdWeekBeginnings)
  {
    this.createdWeekBeginnings = createdWeekBeginnings;
  }
  
  public BigDecimal getReceivedRentalTotal()
  {
    return this.receivedRentalTotal;
  }
  
  public void setReceivedRentalTotal(BigDecimal receivedRentalTotal)
  {
    this.receivedRentalTotal = receivedRentalTotal;
  }
  
  public BigDecimal getOffsetBalance()
  {
    return this.offsetBalance;
  }
  
  public void setOffsetBalance(BigDecimal offsetBalance)
  {
    this.offsetBalance = offsetBalance;
  }
}

