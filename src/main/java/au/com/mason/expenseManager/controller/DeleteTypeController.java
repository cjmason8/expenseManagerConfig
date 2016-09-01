package au.com.mason.expenseManager.controller;

import au.com.mason.expenseManager.factory.type.TypeProcessor;
import au.com.mason.expenseManager.factory.type.TypeProcessorFactory;
import au.com.mason.expenseManager.form.DatagridTypeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeleteTypeController
{
  @Autowired
  private TypeProcessorFactory typeProcessorFactory;
  
  @RequestMapping(value={"/deleteType"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deleteType(@ModelAttribute DatagridTypeForm datagridTypeForm, ModelMap model)
  {
    this.typeProcessorFactory.getProcessor(datagridTypeForm.getDiscriminator()).delete(datagridTypeForm);
    
    model.put("discriminator", datagridTypeForm.getDiscriminator());
    
    return "admin";
  }
}

