package au.com.mason.expenseManager.controller;

import au.com.mason.expenseManager.domain.WeekBeginning;
import au.com.mason.expenseManager.dto.UpdatedTotalsResutlSet;
import au.com.mason.expenseManager.service.WeekBeginningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GetUpdatedTotalsController
{
  @Autowired
  private WeekBeginningService weekBeginningService;
  
  @RequestMapping(value={"/getUpdatedTotals"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public UpdatedTotalsResutlSet getUpdatedTotals(int weekBeginningId)
  {
    UpdatedTotalsResutlSet updatedTotalsResutlSet = new UpdatedTotalsResutlSet();
    updatedTotalsResutlSet.setReceivedRental(this.weekBeginningService.getCurrentRentBalance());
    
    WeekBeginning weekBeginning = (WeekBeginning)this.weekBeginningService.findById(Integer.valueOf(weekBeginningId));
    
    updatedTotalsResutlSet.setRentalTotal(weekBeginning.getOverAllRentalTotal());
    updatedTotalsResutlSet.setTotal(weekBeginning.getOverAllTotal());
    
    return updatedTotalsResutlSet;
  }
}

