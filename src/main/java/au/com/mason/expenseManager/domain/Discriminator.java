package au.com.mason.expenseManager.domain;

public enum Discriminator
{
  EXPENSE,  INCOME,  RENTAL_EXPENSE,  RENTAL_INCOME;
  
  private Discriminator() {
  }
  
}

