package au.com.mason.expenseManager.domain.type;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("INCOME")
public class IncomeType
  extends DatagridType
{
	
}

