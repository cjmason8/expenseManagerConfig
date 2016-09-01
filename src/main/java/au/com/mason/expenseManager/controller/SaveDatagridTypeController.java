package au.com.mason.expenseManager.controller;

import au.com.mason.expenseManager.controller.BaseController;
import au.com.mason.expenseManager.domain.Discriminator;
import au.com.mason.expenseManager.domain.type.ExpenseType;
import au.com.mason.expenseManager.domain.type.IncomeType;
import au.com.mason.expenseManager.domain.type.RentalExpenseType;
import au.com.mason.expenseManager.domain.type.RentalIncomeType;
import au.com.mason.expenseManager.form.DatagridTypeForm;
import au.com.mason.expenseManager.service.type.ExpenseTypeService;
import au.com.mason.expenseManager.service.type.IncomeTypeService;
import au.com.mason.expenseManager.service.type.RentalExpenseTypeService;
import au.com.mason.expenseManager.service.type.RentalIncomeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SaveDatagridTypeController
  extends BaseController
{
  @Autowired
  private ExpenseTypeService expenseTypeService;
  @Autowired
  private RentalIncomeTypeService rentalIncomeTypeService;
  @Autowired
  private IncomeTypeService incomeTypeService;
  @Autowired
  private RentalExpenseTypeService rentalExpenseTypeService;
  
  @RequestMapping(value={"/saveDatagridType"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String saveDatagridType(@ModelAttribute DatagridTypeForm datagridTypeForm, ModelMap model)
  {
    if (datagridTypeForm.getDiscriminator().equals(Discriminator.EXPENSE))
    {
      ExpenseType expenseType = null;
      if (datagridTypeForm.getId() != null) {
        expenseType = (ExpenseType)this.expenseTypeService.findById(datagridTypeForm.getId());
      } else {
        expenseType = new ExpenseType();
      }
      expenseType.setType(datagridTypeForm.getName());
      
      this.expenseTypeService.update(expenseType);
    }
    else if (datagridTypeForm.getDiscriminator().equals(Discriminator.RENTAL_EXPENSE))
    {
      RentalExpenseType rentalExpenseType = null;
      if (datagridTypeForm.getId() != null) {
        rentalExpenseType = (RentalExpenseType)this.rentalExpenseTypeService.findById(datagridTypeForm.getId());
      } else {
        rentalExpenseType = new RentalExpenseType();
      }
      rentalExpenseType.setType(datagridTypeForm.getName());
      
      this.rentalExpenseTypeService.update(rentalExpenseType);
    }
    else if (datagridTypeForm.getDiscriminator().equals(Discriminator.RENTAL_INCOME))
    {
      RentalIncomeType rentalIncomeType = null;
      if (datagridTypeForm.getId() != null) {
        rentalIncomeType = (RentalIncomeType)this.rentalIncomeTypeService.findById(datagridTypeForm.getId());
      } else {
        rentalIncomeType = new RentalIncomeType();
      }
      rentalIncomeType.setType(datagridTypeForm.getName());
      
      this.rentalIncomeTypeService.update(rentalIncomeType);
    }
    else if (datagridTypeForm.getDiscriminator().equals(Discriminator.INCOME))
    {
      IncomeType incomeType = null;
      if (datagridTypeForm.getId() != null) {
        incomeType = (IncomeType)this.incomeTypeService.findById(datagridTypeForm.getId());
      } else {
        incomeType = new IncomeType();
      }
      incomeType.setType(datagridTypeForm.getName());
      
      this.incomeTypeService.update(incomeType);
    }
    model.put("discriminator", datagridTypeForm.getDiscriminator());
    
    return "admin";
  }
}

