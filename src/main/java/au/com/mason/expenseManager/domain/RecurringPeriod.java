package au.com.mason.expenseManager.domain;

public enum RecurringPeriod
{
  WEEKLY("Weekly"),  FORT_NIGHTLY("Fort Nightly"),  MONTHLY("Monthly"),  FOUR_WEEKLY("Four Weekly"),  YEARLY("Yearly");
  
  private String displayName;
  
  private RecurringPeriod(String displayName)
  {
    this.displayName = displayName;
  }
  
  public String getDisplayName()
  {
    return this.displayName;
  }
}

