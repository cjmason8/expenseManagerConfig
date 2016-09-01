package au.com.mason.expenseManager.controller;

import au.com.mason.expenseManager.controller.BaseController;
import au.com.mason.expenseManager.domain.WeekBeginning;
import au.com.mason.expenseManager.service.WeekBeginningService;
import au.com.mason.expenseManager.util.DateUtils;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CreateWeekBeginningController
  extends BaseController
{
  @RequestMapping(value={"/createWeekBeginning"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String createWeekBeginning(String createDate, ModelMap model)
  {
    LocalDate newDate = DateUtils.getDateFromString(createDate);
    LocalDate startDate = DateUtils.getMonday(newDate);
    
    WeekBeginning weekBeginning = this.weekBeginningService.create(startDate);
    model.put("homePageDetails", getHomePageDetailsWithWeekBeginningId(weekBeginning.getId()));
    
    return "homePage";
  }
}

