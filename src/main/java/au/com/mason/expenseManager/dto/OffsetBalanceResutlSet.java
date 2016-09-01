package au.com.mason.expenseManager.dto;

import java.math.BigDecimal;

public class OffsetBalanceResutlSet
{
  private BigDecimal newBalance;
  
  public BigDecimal getNewBalance()
  {
    return this.newBalance;
  }
  
  public void setNewBalance(BigDecimal newBalance)
  {
    this.newBalance = newBalance;
  }
}

