package au.com.mason.expenseManager.factory.line;

import au.com.mason.expenseManager.domain.Discriminator;
import au.com.mason.expenseManager.domain.Persistable;
import au.com.mason.expenseManager.domain.WeekBeginning;
import au.com.mason.expenseManager.domain.line.RentalIncomeLine;
import au.com.mason.expenseManager.dto.LineResultSet;
import au.com.mason.expenseManager.service.line.RentalIncomeLineService;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalIncomeLineProcessor
  implements LineProcessor
{
  @Autowired
  private RentalIncomeLineService rentalIncomeLineService;
  
  public LineResultSet create(WeekBeginning weekBeginning)
  {
    LineResultSet resultSet = new LineResultSet();
    resultSet.setDiscriminator(Discriminator.RENTAL_INCOME);
    resultSet.setTotal(weekBeginning.getRentalIncomeLines().size());
    resultSet.setRentalIncomeLineRows(weekBeginning.getRentalIncomeLines());
    RentalIncomeLine footer = new RentalIncomeLine();
    footer.setDescription("<b>TOTAL:</b>");
    footer.setAmount(weekBeginning.getRentalIncomeTotal());
    Set<RentalIncomeLine> footerList = new HashSet();
    footerList.add(footer);
    resultSet.setRentalIncomeLineFooter(footerList);
    
    return resultSet;
  }
  
  public Integer save(LineResultSet resultSet, WeekBeginning weekBeginning)
  {
    RentalIncomeLine newRentalIncomeLine = (RentalIncomeLine)resultSet.getRentalIncomeLineRows().iterator().next();
    if (newRentalIncomeLine.getId() == null)
    {
      weekBeginning.addRentalIncomeLine(newRentalIncomeLine);
      this.rentalIncomeLineService.update(newRentalIncomeLine);
    }
    else
    {
      for (RentalIncomeLine rentalIncomeLine : weekBeginning.getRentalIncomeLines()) {
        if (rentalIncomeLine.getId().equals(newRentalIncomeLine.getId()))
        {
          rentalIncomeLine.copyData(newRentalIncomeLine);
          this.rentalIncomeLineService.update(rentalIncomeLine);
        }
      }
    }
    return newRentalIncomeLine.getId();
  }
  
  public void delete(Persistable item)
  {
    this.rentalIncomeLineService.delete(item);
  }
}

