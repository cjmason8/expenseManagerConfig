package au.com.mason.expenseManager.dto;

import java.math.BigDecimal;

public class UpdatedTotalsResutlSet
{
  private BigDecimal receivedRental;
  private BigDecimal total;
  private BigDecimal rentalTotal;
  
  public BigDecimal getReceivedRental()
  {
    return this.receivedRental;
  }
  
  public void setReceivedRental(BigDecimal receivedRental)
  {
    this.receivedRental = receivedRental;
  }
  
  public BigDecimal getTotal()
  {
    return this.total;
  }
  
  public void setTotal(BigDecimal total)
  {
    this.total = total;
  }
  
  public BigDecimal getRentalTotal()
  {
    return this.rentalTotal;
  }
  
  public void setRentalTotal(BigDecimal rentalTotal)
  {
    this.rentalTotal = rentalTotal;
  }
}

