package au.com.mason.expenseManager.controller;

import au.com.mason.expenseManager.domain.Discriminator;
import au.com.mason.expenseManager.domain.WeekBeginning;
import au.com.mason.expenseManager.dto.LineResultSet;
import au.com.mason.expenseManager.factory.line.LineProcessor;
import au.com.mason.expenseManager.factory.line.LineProcessorFactory;
import au.com.mason.expenseManager.service.WeekBeginningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GetWeekBeginningLinesController
{
  @Autowired
  private WeekBeginningService weekBeginningService;
  @Autowired
  private LineProcessorFactory resultSetCreatorFactory;
  
  @RequestMapping(value={"/getWeekBeginningLines"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public LineResultSet getWeekBeginningExpenseLines(@RequestParam Integer weekBeginningId, @RequestParam Discriminator discriminator)
  {
    WeekBeginning weekBeginning = (WeekBeginning)this.weekBeginningService.findById(weekBeginningId);
    
    LineResultSet resultSet = this.resultSetCreatorFactory.getCreator(discriminator).create(weekBeginning);
    
    return resultSet;
  }
}

