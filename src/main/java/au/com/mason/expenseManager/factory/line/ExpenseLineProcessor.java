package au.com.mason.expenseManager.factory.line;

import au.com.mason.expenseManager.domain.Discriminator;
import au.com.mason.expenseManager.domain.Persistable;
import au.com.mason.expenseManager.domain.WeekBeginning;
import au.com.mason.expenseManager.domain.line.ExpenseLine;
import au.com.mason.expenseManager.dto.LineResultSet;
import au.com.mason.expenseManager.service.line.ExpenseLineService;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseLineProcessor
  implements LineProcessor
{
  @Autowired
  private ExpenseLineService expenseLineService;
  
  public LineResultSet create(WeekBeginning weekBeginning)
  {
    LineResultSet resultSet = new LineResultSet();
    resultSet.setDiscriminator(Discriminator.EXPENSE);
    resultSet.setTotal(weekBeginning.getExpenseLines().size());
    resultSet.setExpenseLineRows(weekBeginning.getExpenseLines());
    ExpenseLine footer = new ExpenseLine();
    footer.setDescription("<b>TOTAL:</b>");
    footer.setAmount(weekBeginning.getExpenseTotal());
    Set<ExpenseLine> footerList = new HashSet();
    footerList.add(footer);
    resultSet.setExpenseLineFooter(footerList);
    
    return resultSet;
  }
  
  public Integer save(LineResultSet resultSet, WeekBeginning weekBeginning)
  {
    ExpenseLine newExpenseLine = (ExpenseLine)resultSet.getExpenseLineRows().iterator().next();
    if (newExpenseLine.getId() == null)
    {
      weekBeginning.addExpenseLine(newExpenseLine);
      this.expenseLineService.update(newExpenseLine);
    }
    else
    {
      for (ExpenseLine expenseLine : weekBeginning.getExpenseLines()) {
        if (expenseLine.getId().equals(newExpenseLine.getId()))
        {
          expenseLine.copyData(newExpenseLine);
          this.expenseLineService.update(expenseLine);
        }
      }
    }
    return newExpenseLine.getId();
  }
  
  public void delete(Persistable item)
  {
    this.expenseLineService.delete(item);
  }
}

