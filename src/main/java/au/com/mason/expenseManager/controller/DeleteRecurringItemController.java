package au.com.mason.expenseManager.controller;

import au.com.mason.expenseManager.domain.recurring.RecurringItem;
import au.com.mason.expenseManager.service.recurring.RecurringItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeleteRecurringItemController
{
  @Autowired
  private RecurringItemService recurringItemService;
  
  @RequestMapping(value={"/deleteRecurringItem"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String deleteRecurringItem(String discriminator, int recurringItemId, ModelMap model)
  {
    RecurringItem recurringItem = (RecurringItem)this.recurringItemService.findById(Integer.valueOf(recurringItemId));
    recurringItem.setDeleted(true);
    
    this.recurringItemService.update(recurringItem);
    
    model.put("recurringItemDiscriminator", discriminator);
    
    return "admin";
  }
}

