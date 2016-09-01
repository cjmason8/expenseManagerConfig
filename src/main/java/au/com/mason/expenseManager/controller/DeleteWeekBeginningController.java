package au.com.mason.expenseManager.controller;

import au.com.mason.expenseManager.controller.BaseController;
import au.com.mason.expenseManager.service.WeekBeginningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeleteWeekBeginningController
  extends BaseController
{
  @Autowired
  private WeekBeginningService weekBeginningService;
  
  @RequestMapping(value={"/deleteWeekBeginning"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String deleteWeekBeginning(ModelMap model, Integer weekBeginningId)
  {
    this.weekBeginningService.delete(this.weekBeginningService.findById(weekBeginningId));
    
    model.put("homePageDetails", getHomePageDetails());
    
    return "homePage";
  }
}

