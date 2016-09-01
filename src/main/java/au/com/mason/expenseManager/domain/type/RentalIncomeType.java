package au.com.mason.expenseManager.domain.type;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RENTAL_INCOME")
public class RentalIncomeType
  extends DatagridType
{
	
}

