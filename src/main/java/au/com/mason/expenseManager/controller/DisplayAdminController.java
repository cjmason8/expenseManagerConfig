package au.com.mason.expenseManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DisplayAdminController
{
  @RequestMapping(value={"/displayAdmin"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String displayAdmin(String recurringItemDiscriminator, String discriminator, ModelMap model)
  {
    model.put("discriminator", discriminator);
    model.put("recurringItemDiscriminator", recurringItemDiscriminator);
    
    return "admin";
  }
}

