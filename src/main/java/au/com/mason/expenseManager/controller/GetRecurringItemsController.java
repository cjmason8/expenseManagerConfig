package au.com.mason.expenseManager.controller;

import au.com.mason.expenseManager.domain.recurring.RecurringItem;
import au.com.mason.expenseManager.service.recurring.RecurringItemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GetRecurringItemsController
{
  @Autowired
  private RecurringItemService recurringItemService;
  
  @RequestMapping(value={"/getRecurringItems"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public List<RecurringItem> getRecurringItems(String discriminator)
  {
    return this.recurringItemService.getAllByDiscriminator(discriminator);
  }
}

