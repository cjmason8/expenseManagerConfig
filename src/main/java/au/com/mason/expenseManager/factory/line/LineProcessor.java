package au.com.mason.expenseManager.factory.line;

import au.com.mason.expenseManager.domain.Persistable;
import au.com.mason.expenseManager.domain.WeekBeginning;
import au.com.mason.expenseManager.dto.LineResultSet;

public abstract interface LineProcessor
{
  public abstract LineResultSet create(WeekBeginning paramWeekBeginning);
  
  public abstract Integer save(LineResultSet paramLineResultSet, WeekBeginning paramWeekBeginning);
  
  public abstract void delete(Persistable paramPersistable);
}

