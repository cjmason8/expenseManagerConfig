package au.com.mason.expenseManager.controller;

import au.com.mason.expenseManager.controller.BaseController;
import au.com.mason.expenseManager.domain.WeekBeginning;
import au.com.mason.expenseManager.domain.recurring.RecurringItem;
import au.com.mason.expenseManager.factory.recurring.RecurringItemFactory;
import au.com.mason.expenseManager.factory.recurring.RecurringItemProcessor;
import au.com.mason.expenseManager.form.RecurringItemForm;
import au.com.mason.expenseManager.service.WeekBeginningService;
import au.com.mason.expenseManager.service.recurring.RecurringItemService;
import au.com.mason.expenseManager.util.DateUtils;
import java.util.List;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SaveRecurringItemController
  extends BaseController
{
  @Autowired
  private RecurringItemFactory datagridLineFactory;
  @Autowired
  private RecurringItemService recurringItemService;
  
  @RequestMapping(value={"/saveRecurringItem"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String saveRecurringItem(@ModelAttribute RecurringItemForm recurringItemForm, ModelMap model)
  {
    LocalDate weekBeginningStartDate = DateUtils.getMonday(recurringItemForm.getStartDate());
    WeekBeginning weekBeginning = this.weekBeginningService.findByStartDate(weekBeginningStartDate);
    if (weekBeginning == null) {
      weekBeginning = this.weekBeginningService.create(weekBeginningStartDate);
    }
    RecurringItem recurringItem = this.datagridLineFactory.getProcessor(recurringItemForm.getDiscriminator()).create(recurringItemForm, weekBeginning);
    
    this.weekBeginningService.update(weekBeginning);
    
    List<WeekBeginning> weeksPastStartDate = this.weekBeginningService.findAllWeeksPastWeekBeginning(weekBeginningStartDate);
    for (WeekBeginning week : weeksPastStartDate) {
      if (this.recurringItemService.checkIfValid(recurringItem, week.getStartDate())) {
        this.weekBeginningService.processRecurringItem(week, recurringItem);
      }
    }
    model.put("recurringItemDiscriminator", recurringItemForm.getDiscriminator());
    
    return "admin";
  }
}

