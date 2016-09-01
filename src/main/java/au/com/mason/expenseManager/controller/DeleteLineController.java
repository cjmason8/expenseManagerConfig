package au.com.mason.expenseManager.controller;

import au.com.mason.expenseManager.domain.Persistable;
import au.com.mason.expenseManager.dto.JsonResponse;
import au.com.mason.expenseManager.dto.LineResultSet;
import au.com.mason.expenseManager.factory.line.LineProcessor;
import au.com.mason.expenseManager.factory.line.LineProcessorFactory;
import java.util.Iterator;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DeleteLineController
{
  @Autowired
  private LineProcessorFactory resultSetCreatorFactory;
  
  @RequestMapping(value={"/deleteLine"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Content-type=application/json"})
  @ResponseBody
  public JsonResponse deleteIncomeLine(@RequestBody LineResultSet result)
  {
    this.resultSetCreatorFactory.getCreator(result.getDiscriminator()).delete((Persistable)result.getRows().iterator().next());
    
    return new JsonResponse("OK", "", null);
  }
}

