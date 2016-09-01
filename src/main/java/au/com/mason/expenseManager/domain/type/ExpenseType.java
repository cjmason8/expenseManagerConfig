package au.com.mason.expenseManager.domain.type;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("EXPENSE")
public class ExpenseType
  extends DatagridType
{
	
}

