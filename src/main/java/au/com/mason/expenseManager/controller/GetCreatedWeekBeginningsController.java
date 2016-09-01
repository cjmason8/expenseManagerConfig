package au.com.mason.expenseManager.controller;

import au.com.mason.expenseManager.service.WeekBeginningService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GetCreatedWeekBeginningsController
{
  @Autowired
  private WeekBeginningService weekBeginningService;
  
  @RequestMapping(value={"/getCreatedWeekBeginnings"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public List<String> getCreatedWeekBeginnings(int month, int year)
  {
    return this.weekBeginningService.findForMonthAndYear(month, year);
  }
}

