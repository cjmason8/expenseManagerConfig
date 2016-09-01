package au.com.mason.expenseManager.factory.line;

import au.com.mason.expenseManager.domain.Discriminator;
import au.com.mason.expenseManager.domain.Persistable;
import au.com.mason.expenseManager.domain.WeekBeginning;
import au.com.mason.expenseManager.domain.line.RentalExpenseLine;
import au.com.mason.expenseManager.dto.LineResultSet;
import au.com.mason.expenseManager.service.line.RentalExpenseLineService;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalExpenseLineProcessor
  implements LineProcessor
{
  @Autowired
  private RentalExpenseLineService rentalExpenseLineService;
  
  public LineResultSet create(WeekBeginning weekBeginning)
  {
    LineResultSet resultSet = new LineResultSet();
    resultSet.setDiscriminator(Discriminator.RENTAL_EXPENSE);
    resultSet.setTotal(weekBeginning.getRentalExpenseLines().size());
    resultSet.setRentalExpenseLineRows(weekBeginning.getRentalExpenseLines());
    RentalExpenseLine footer = new RentalExpenseLine();
    footer.setDescription("<b>TOTAL:</b>");
    footer.setAmount(weekBeginning.getRentalExpenseTotal());
    Set<RentalExpenseLine> footerList = new HashSet();
    footerList.add(footer);
    resultSet.setRentalExpenseLineFooter(footerList);
    
    return resultSet;
  }
  
  public Integer save(LineResultSet resultSet, WeekBeginning weekBeginning)
  {
    RentalExpenseLine newRentalExpenseLine = (RentalExpenseLine)resultSet.getRows().iterator().next();
    if (newRentalExpenseLine.getId() == null)
    {
      weekBeginning.addRentalExpenseLine(newRentalExpenseLine);
      this.rentalExpenseLineService.update(newRentalExpenseLine);
    }
    else
    {
      for (RentalExpenseLine rentalExpenseLine : weekBeginning.getRentalExpenseLines()) {
        if (rentalExpenseLine.getId().equals(newRentalExpenseLine.getId()))
        {
          rentalExpenseLine.copyData(newRentalExpenseLine);
          this.rentalExpenseLineService.update(rentalExpenseLine);
        }
      }
    }
    return newRentalExpenseLine.getId();
  }
  
  public void delete(Persistable item) {}
}

