package au.com.mason.expenseManager.controller;

import au.com.mason.expenseManager.domain.WeekBeginning;
import au.com.mason.expenseManager.dto.JsonResponse;
import au.com.mason.expenseManager.dto.LineResultSet;
import au.com.mason.expenseManager.factory.line.LineProcessor;
import au.com.mason.expenseManager.factory.line.LineProcessorFactory;
import au.com.mason.expenseManager.service.WeekBeginningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SaveLineChangesController
{
  @Autowired
  private WeekBeginningService weekBeginningService;
  @Autowired
  private LineProcessorFactory resultSetCreatorFactory;
  
  @RequestMapping(value={"/saveLineChanges"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Content-type=application/json"})
  @ResponseBody
  public JsonResponse saveLine(@RequestBody LineResultSet result)
  {
    WeekBeginning weekBeginning = (WeekBeginning)this.weekBeginningService.findById(Integer.valueOf(result.getWeekBeginningId()));
    
    Integer newLineId = this.resultSetCreatorFactory.getCreator(result.getDiscriminator()).save(result, weekBeginning);
    
    this.weekBeginningService.update(weekBeginning);
    
    return new JsonResponse("OK", "", String.valueOf(newLineId));
  }
}

