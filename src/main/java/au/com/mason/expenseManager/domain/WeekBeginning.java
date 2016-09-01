package au.com.mason.expenseManager.domain;

import au.com.mason.expenseManager.compare.DatagridLineComparator;
import au.com.mason.expenseManager.domain.line.ExpenseLine;
import au.com.mason.expenseManager.domain.line.IncomeLine;
import au.com.mason.expenseManager.domain.line.RentalExpenseLine;
import au.com.mason.expenseManager.domain.line.RentalIncomeLine;
import au.com.mason.expenseManager.domain.type.IncomeType;
import au.com.mason.expenseManager.domain.type.RentalIncomeType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Table(name="week_beginning")
public class WeekBeginning
  implements Persistable
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;
  @Column
  private boolean current;
  @Column(name="start_date")
  @Type(type="org.joda.time.contrib.hibernate.PersistentLocalDate")
  private LocalDate startDate;
  @OneToMany(fetch=FetchType.EAGER)
  @JoinColumn(name="week_beginning_id")
  @Sort(type=SortType.COMPARATOR, comparator=DatagridLineComparator.class)
  private SortedSet<ExpenseLine> expenseLines = new TreeSet(new DatagridLineComparator());
  @OneToMany(fetch=FetchType.EAGER)
  @JoinColumn(name="week_beginning_id")
  @Sort(type=SortType.COMPARATOR, comparator=DatagridLineComparator.class)
  private SortedSet<IncomeLine> incomeLines = new TreeSet(new DatagridLineComparator());
  @OneToMany(fetch=FetchType.EAGER)
  @JoinColumn(name="week_beginning_id")
  @Sort(type=SortType.COMPARATOR, comparator=DatagridLineComparator.class)
  private SortedSet<RentalIncomeLine> rentalIncomeLines = new TreeSet(new DatagridLineComparator());
  @OneToMany(fetch=FetchType.EAGER)
  @JoinColumn(name="week_beginning_id")
  @Sort(type=SortType.COMPARATOR, comparator=DatagridLineComparator.class)
  private SortedSet<RentalExpenseLine> rentalExpenseLines = new TreeSet(new DatagridLineComparator());
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public LocalDate getStartDate()
  {
    return this.startDate;
  }
  
  public Date getStartDateValue()
  {
    return this.startDate.toDate();
  }
  
  public void setStartDate(LocalDate startDate)
  {
    this.startDate = startDate;
  }
  
  public SortedSet<ExpenseLine> getExpenseLines()
  {
    return this.expenseLines;
  }
  
  public void addExpenseLine(ExpenseLine expenseLine)
  {
    if (CollectionUtils.isEmpty(getExpenseLines())) {
      expenseLine.setSequence(1);
    } else {
      expenseLine.setSequence(((ExpenseLine)this.expenseLines.last()).getSequence() + 1);
    }
    this.expenseLines.add(expenseLine);
  }
  
  public void setExpenseLines(SortedSet<ExpenseLine> expenseLines)
  {
    this.expenseLines = expenseLines;
  }
  
  public SortedSet<IncomeLine> getIncomeLines()
  {
    return this.incomeLines;
  }
  
  public void setIncomeLines(SortedSet<IncomeLine> incomeLines)
  {
    this.incomeLines = incomeLines;
  }
  
  public void addIncomeLine(IncomeLine incomeLine)
  {
    if (CollectionUtils.isEmpty(getIncomeLines())) {
      incomeLine.setSequence(1);
    } else {
      incomeLine.setSequence(((IncomeLine)this.incomeLines.last()).getSequence() + 1);
    }
    this.incomeLines.add(incomeLine);
  }
  
  public SortedSet<RentalIncomeLine> getRentalIncomeLines()
  {
    return this.rentalIncomeLines;
  }
  
  public void setRentalIncomeLines(SortedSet<RentalIncomeLine> rentalIncomeLines)
  {
    this.rentalIncomeLines = rentalIncomeLines;
  }
  
  public void addRentalIncomeLine(RentalIncomeLine rentalIncomeLine)
  {
    if (CollectionUtils.isEmpty(getRentalIncomeLines())) {
      rentalIncomeLine.setSequence(1);
    } else {
      rentalIncomeLine.setSequence(((RentalIncomeLine)getRentalIncomeLines().last()).getSequence() + 1);
    }
    this.rentalIncomeLines.add(rentalIncomeLine);
  }
  
  public SortedSet<RentalExpenseLine> getRentalExpenseLines()
  {
    return this.rentalExpenseLines;
  }
  
  public void setRentalExpenseLines(SortedSet<RentalExpenseLine> rentalExpenseLines)
  {
    this.rentalExpenseLines = rentalExpenseLines;
  }
  
  public void addRentalExpenseLine(RentalExpenseLine rentalExpenseLine)
  {
    if (CollectionUtils.isEmpty(getRentalExpenseLines())) {
      rentalExpenseLine.setSequence(1);
    } else {
      rentalExpenseLine.setSequence(((RentalExpenseLine)this.rentalExpenseLines.last()).getSequence() + 1);
    }
    this.rentalExpenseLines.add(rentalExpenseLine);
  }
  
  public BigDecimal getExpenseTotal()
  {
    BigDecimal total = new BigDecimal("0");
    for (ExpenseLine expenseLine : this.expenseLines) {
      if (!expenseLine.isDone()) {
        total = total.add(expenseLine.getAmount());
      }
    }
    return total;
  }
  
  public BigDecimal getIncomeTotal()
  {
    BigDecimal total = new BigDecimal("0");
    for (IncomeLine incomeLine : this.incomeLines) {
      if (!incomeLine.isDone()) {
        total = total.add(incomeLine.getAmount());
      }
    }
    return total;
  }
  
  public IncomeLine getIncomeCarryOver()
  {
    for (IncomeLine incomeLine : this.incomeLines) {
      if (incomeLine.getIncomeType().getType().equals("Carry Over")) {
        return incomeLine;
      }
    }
    return null;
  }
  
  public RentalIncomeLine getRentalIncomeCarryOver()
  {
    for (RentalIncomeLine rentalIncomeLine : this.rentalIncomeLines) {
      if (rentalIncomeLine.getRentalIncomeType().getType().equals("Carry Over")) {
        return rentalIncomeLine;
      }
    }
    return null;
  }
  
  public BigDecimal getRentalIncomeTotal()
  {
    BigDecimal total = new BigDecimal("0");
    for (RentalIncomeLine rentalIncomeLine : this.rentalIncomeLines) {
      total = total.add(rentalIncomeLine.getAmount());
    }
    return total.setScale(2, RoundingMode.DOWN);
  }
  
  public BigDecimal getReceivedRentalIncomeTotal()
  {
    BigDecimal total = new BigDecimal("0");
    for (RentalIncomeLine rentalIncomeLine : this.rentalIncomeLines) {
      if (rentalIncomeLine.isDone()) {
        total = total.add(rentalIncomeLine.getAmount());
      }
    }
    return total.setScale(2, RoundingMode.DOWN);
  }
  
  public BigDecimal getRentalExpenseTotal()
  {
    BigDecimal total = new BigDecimal("0");
    for (RentalExpenseLine rentalExpenseLine : this.rentalExpenseLines) {
      if (!rentalExpenseLine.isDone()) {
        total = total.add(rentalExpenseLine.getAmount());
      }
    }
    return total.setScale(2, RoundingMode.DOWN);
  }
  
  public BigDecimal getRentalExpenseDoneTotal()
  {
    BigDecimal total = new BigDecimal("0");
    for (RentalExpenseLine rentalExpenseLine : this.rentalExpenseLines) {
      if (rentalExpenseLine.isDone()) {
        total = total.add(rentalExpenseLine.getAmount());
      }
    }
    return total.setScale(2, RoundingMode.DOWN);
  }
  
  public BigDecimal getOverAllTotal()
  {
    return getIncomeTotal().subtract(getExpenseTotal()).setScale(2, RoundingMode.DOWN);
  }
  
  public BigDecimal getOverAllRentalTotal()
  {
    return getRentalIncomeTotal().subtract(getRentalExpenseTotal()).setScale(2, RoundingMode.DOWN);
  }
  
  public boolean isCurrent()
  {
    return this.current;
  }
  
  public void setCurrent(boolean current)
  {
    this.current = current;
  }
}

