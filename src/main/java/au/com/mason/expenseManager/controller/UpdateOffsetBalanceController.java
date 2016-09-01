package au.com.mason.expenseManager.controller;

import au.com.mason.expenseManager.dto.JsonResponse;
import au.com.mason.expenseManager.dto.OffsetBalanceResutlSet;
import au.com.mason.expenseManager.service.SettingsService;
import au.com.mason.expenseManager.service.WeekBeginningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UpdateOffsetBalanceController
{
  @Autowired
  private SettingsService settingsService;
  @Autowired
  private WeekBeginningService weekBeginningService;
  
  @RequestMapping(value={"/updateOffsetBalance"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Content-type=application/json"})
  @ResponseBody
  public JsonResponse saveIncomeLine(@RequestBody OffsetBalanceResutlSet offsetBalanceResutlSet)
  {
    this.settingsService.updateOffsetBalance(offsetBalanceResutlSet.getNewBalance());
    this.weekBeginningService.updateCarryOvers();
    
    return new JsonResponse("OK", "", null);
  }
}

