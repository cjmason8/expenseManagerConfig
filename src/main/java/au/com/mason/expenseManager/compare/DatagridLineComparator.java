package au.com.mason.expenseManager.compare;

import au.com.mason.expenseManager.domain.line.DatagridLine;
import java.util.Comparator;

public class DatagridLineComparator
  implements Comparator<DatagridLine>
{
  public int compare(DatagridLine datagridLine1, DatagridLine datagridLine2)
  {
    return datagridLine1.getSequence() - datagridLine2.getSequence();
  }
}

