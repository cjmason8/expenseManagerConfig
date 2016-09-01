package au.com.mason.expenseManager.controller;

import au.com.mason.expenseManager.domain.Discriminator;
import au.com.mason.expenseManager.domain.Persistable;
import au.com.mason.expenseManager.factory.type.TypeProcessor;
import au.com.mason.expenseManager.factory.type.TypeProcessorFactory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GetTypesController
{
  @Autowired
  private TypeProcessorFactory typeProcessorFactory;
  
  @RequestMapping(value={"/getTypes"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public List<Persistable> getTypes(@RequestParam Discriminator discriminator)
  {
    return this.typeProcessorFactory.getProcessor(discriminator).get();
  }
}

