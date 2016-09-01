package au.com.mason.expenseManager.controller;

import au.com.mason.expenseManager.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DisplayHomePageController
  extends BaseController
{
  @RequestMapping(value={"/displayHomePage"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String displayHomePage(ModelMap model)
  {
    model.put("homePageDetails", getHomePageDetails());
    
    return "homePage";
  }
  
  @RequestMapping(value={"/displayHomePageWithWeekBeginningId"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String displayHomePageWithWeekBeginningId(ModelMap model, Integer weekBeginningId)
  {
    model.put("homePageDetails", getHomePageDetailsWithWeekBeginningId(weekBeginningId));
    
    return "homePage";
  }
  
  @RequestMapping(value={"/displayHomePageWithDate"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String displayHomePageWithDate(ModelMap model, String date, Integer oldWeekBeginningId)
  {
    model.put("homePageDetails", getHomePageDetailsWithDate(date, oldWeekBeginningId));
    
    return "homePage";
  }
}

