package au.com.mason.expenseManager.factory.line;

import au.com.mason.expenseManager.domain.Discriminator;
import au.com.mason.expenseManager.domain.Persistable;
import au.com.mason.expenseManager.domain.WeekBeginning;
import au.com.mason.expenseManager.domain.line.IncomeLine;
import au.com.mason.expenseManager.dto.LineResultSet;
import au.com.mason.expenseManager.service.line.IncomeLineService;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeLineProcessor
  implements LineProcessor
{
  @Autowired
  private IncomeLineService incomeLineService;
  
  public LineResultSet create(WeekBeginning weekBeginning)
  {
    LineResultSet resultSet = new LineResultSet();
    resultSet.setDiscriminator(Discriminator.INCOME);
    resultSet.setTotal(weekBeginning.getIncomeLines().size());
    resultSet.setIncomeLineRows(weekBeginning.getIncomeLines());
    IncomeLine footer = new IncomeLine();
    footer.setDescription("<b>TOTAL:</b>");
    footer.setAmount(weekBeginning.getIncomeTotal());
    Set<IncomeLine> footerList = new HashSet();
    footerList.add(footer);
    resultSet.setIncomeLineFooter(footerList);
    
    return resultSet;
  }
  
  public Integer save(LineResultSet resultSet, WeekBeginning weekBeginning)
  {
    IncomeLine newIncomeLine = (IncomeLine)resultSet.getIncomeLineRows().iterator().next();
    if (newIncomeLine.getId() == null)
    {
      weekBeginning.addIncomeLine(newIncomeLine);
      this.incomeLineService.update(newIncomeLine);
    }
    else
    {
      for (IncomeLine incomeLine : weekBeginning.getIncomeLines()) {
        if (incomeLine.getId().equals(newIncomeLine.getId()))
        {
          incomeLine.copyData(newIncomeLine);
          this.incomeLineService.update(incomeLine);
        }
      }
    }
    return newIncomeLine.getId();
  }
  
  public void delete(Persistable item)
  {
    this.incomeLineService.delete(item);
  }
}

