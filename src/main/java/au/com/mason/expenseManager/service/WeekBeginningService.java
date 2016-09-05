package au.com.mason.expenseManager.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.mason.expenseManager.dao.BaseDao;
import au.com.mason.expenseManager.dao.WeekBeginningDao;
import au.com.mason.expenseManager.dao.type.IncomeTypeDao;
import au.com.mason.expenseManager.domain.RecurringPeriod;
import au.com.mason.expenseManager.domain.WeekBeginning;
import au.com.mason.expenseManager.domain.line.ExpenseLine;
import au.com.mason.expenseManager.domain.line.IncomeLine;
import au.com.mason.expenseManager.domain.line.RentalExpenseLine;
import au.com.mason.expenseManager.domain.line.RentalIncomeLine;
import au.com.mason.expenseManager.domain.recurring.ExpenseLineRecurringItem;
import au.com.mason.expenseManager.domain.recurring.IncomeLineRecurringItem;
import au.com.mason.expenseManager.domain.recurring.RecurringItem;
import au.com.mason.expenseManager.domain.recurring.RentalExpenseLineRecurringItem;
import au.com.mason.expenseManager.domain.recurring.RentalIncomeLineRecurringItem;
import au.com.mason.expenseManager.service.line.ExpenseLineService;
import au.com.mason.expenseManager.service.line.IncomeLineService;
import au.com.mason.expenseManager.service.line.RentalExpenseLineService;
import au.com.mason.expenseManager.service.line.RentalIncomeLineService;
import au.com.mason.expenseManager.service.recurring.RecurringItemService;
import au.com.mason.expenseManager.service.type.RentalIncomeTypeService;
import au.com.mason.expenseManager.util.DateUtils;

@Service
public class WeekBeginningService
  extends BaseService
{
  @Autowired
  private WeekBeginningDao weekBeginningDao;
  @Autowired
  private RecurringItemService recurringItemService;
  @Autowired
  private ExpenseLineService expenseLineService;
  @Autowired
  private IncomeTypeDao incomeTypeDao;
  @Autowired
  private RentalIncomeTypeService rentalIncomeTypeService;
  @Autowired
  private RentalExpenseLineService rentalExpenseLineService;
  @Autowired
  private IncomeLineService incomeLineService;
  @Autowired
  private SettingsService settingsService;
  @Autowired
  private RentalIncomeLineService rentalIncomeLineService;
  
  public WeekBeginning findCurrent()
  {
    return this.weekBeginningDao.findCurrent();
  }
  
  public WeekBeginning findForDate(LocalDate date)
  {
    return this.weekBeginningDao.findForDate(date);
  }
  
  public WeekBeginning findByStartDate(LocalDate startDate)
  {
    return this.weekBeginningDao.findByStartDate(startDate);
  }
  
  public List<String> findForMonthAndYear(int month, int year)
  {
    List<WeekBeginning> weekBeginnings = this.weekBeginningDao.findForMonth(month, year);
    List<String> results = new ArrayList();
    for (WeekBeginning weekBeginning : weekBeginnings) {
      results.add(String.valueOf(DateUtils.getTime(weekBeginning.getStartDate())));
    }
    return results;
  }
  
  public BigDecimal getCurrentRentBalance()
  {
    WeekBeginning currentWeek = findCurrent();
    LocalDate weekOfTheFirst = new LocalDate(currentWeek.getStartDate().getYear(), currentWeek.getStartDate().getMonthOfYear(), 1);
    WeekBeginning requiredWeek = findForDate(weekOfTheFirst);
    if (requiredWeek.getStartDate().getMonthOfYear() != currentWeek.getStartDate().getMonthOfYear())
    {
      weekOfTheFirst = weekOfTheFirst.plusWeeks(1);
      requiredWeek = findForDate(weekOfTheFirst);
    }
    List<WeekBeginning> weeks = new ArrayList();
    weeks.add(requiredWeek);
    weekOfTheFirst = requiredWeek.getStartDate();
    while (!requiredWeek.getStartDate().equals(currentWeek.getStartDate()))
    {
      weekOfTheFirst = weekOfTheFirst.plusWeeks(1);
      requiredWeek = findForDate(weekOfTheFirst);
      weeks.add(requiredWeek);
    }
    BigDecimal total = new BigDecimal("0");
    for (WeekBeginning weekBeginning : weeks) {
      total = total.add(weekBeginning.getReceivedRentalIncomeTotal());
    }
    for (WeekBeginning weekBeginning : weeks) {
      total = total.subtract(weekBeginning.getRentalExpenseDoneTotal());
    }
    return total;
  }
  
  //@Transactional
  public WeekBeginning create(LocalDate startDate)
  {
    WeekBeginning weekBeginning = new WeekBeginning();
    weekBeginning.setStartDate(startDate);
    
    LocalDate firstEverWeekStartDate = this.weekBeginningDao.findFirstWeek().getStartDate();
    addIncomeCarryOver(weekBeginning, firstEverWeekStartDate);
    addRentalIncomeCarryOver(weekBeginning, firstEverWeekStartDate);
    
    List<RecurringItem> items = this.recurringItemService.getItemsForWeekBeginning(weekBeginning.getStartDate());
    for (RecurringItem item : items) {
      processRecurringItem(weekBeginning, item);
    }
    this.weekBeginningDao.update(weekBeginning);
    
    return weekBeginning;
  }
  
  public void processRecurringItem(WeekBeginning weekBeginning, RecurringItem item)
  {
    if ((item instanceof ExpenseLineRecurringItem))
    {
      ExpenseLineRecurringItem expenseLineRecurringItem = (ExpenseLineRecurringItem)item;
      ExpenseLine expenseLine = expenseLineRecurringItem.createExpenseLine();
      if ((expenseLine.getDueDate() == null) && (!expenseLineRecurringItem.isNoDueDateRequired())) {
        if ((!expenseLineRecurringItem.getRecurringPeriod().equals(RecurringPeriod.MONTHLY)) && (!expenseLineRecurringItem.getRecurringPeriod().equals(RecurringPeriod.YEARLY))) {
          expenseLine.setDueDate(weekBeginning.getStartDate().withDayOfWeek(expenseLineRecurringItem.getStartDate().getDayOfWeek()));
        } else {
          expenseLine.setDueDate(DateUtils.getDateForWeek(expenseLineRecurringItem, weekBeginning.getStartDate()));
        }
      }
      weekBeginning.addExpenseLine(expenseLine);
      this.expenseLineService.update(expenseLine);
    }
    else if ((item instanceof IncomeLineRecurringItem))
    {
      IncomeLineRecurringItem incomeLineRecurringItem = (IncomeLineRecurringItem)item;
      IncomeLine incomeLine = incomeLineRecurringItem.createIncomeLine();
      weekBeginning.addIncomeLine(incomeLine);
      this.incomeLineService.update(incomeLine);
    }
    else if ((item instanceof RentalExpenseLineRecurringItem))
    {
      RentalExpenseLineRecurringItem rentalExpenseLineRecurringItem = (RentalExpenseLineRecurringItem)item;
      RentalExpenseLine rentalExpenseLine = rentalExpenseLineRecurringItem.createRentalExpenseLine();
      if (rentalExpenseLineRecurringItem.getInterestRate() != null)
      {
        BigDecimal daysInYear = BigDecimal.valueOf(DateUtils.getNumberDaysInYear(weekBeginning.getStartDate()));
        BigDecimal interestDaysInMonth = BigDecimal.valueOf(DateUtils.findNumberOfInterestDaysInMonth(weekBeginning.getStartDate()));
        BigDecimal rateAsDecimal = rentalExpenseLineRecurringItem.getInterestRate().divide(new BigDecimal("100"));
        BigDecimal amount = rentalExpenseLineRecurringItem.getPrincipal().multiply(rateAsDecimal);
        amount = amount.divide(daysInYear, 3, RoundingMode.UP).multiply(interestDaysInMonth);
        
        rentalExpenseLine.setDueDate(DateUtils.getLastInterestDayOfMonth(weekBeginning.getStartDate()));
        rentalExpenseLine.setAmount(amount);
        rentalExpenseLine.setComment("Principal: " + rentalExpenseLineRecurringItem.getPrincipal() + ", Rate: " + rentalExpenseLineRecurringItem.getInterestRate() + "%");
      }
      weekBeginning.addRentalExpenseLine(rentalExpenseLine);
      this.rentalExpenseLineService.update(rentalExpenseLine);
    }
    else if ((item instanceof RentalIncomeLineRecurringItem))
    {
      RentalIncomeLineRecurringItem rentalIncomeLineRecurringItem = (RentalIncomeLineRecurringItem)item;
      RentalIncomeLine rentalIncomeLine = rentalIncomeLineRecurringItem.createRentalIncomeLine();
      weekBeginning.addRentalIncomeLine(rentalIncomeLine);
      this.rentalIncomeLineService.update(rentalIncomeLine);
    }
  }
  
  private void addIncomeCarryOver(WeekBeginning weekBeginning, LocalDate firstEverWeekStartDate)
  {
    IncomeLine carryOver = new IncomeLine();
    carryOver.setIncomeType(this.incomeTypeDao.getCarryOver());
    
    LocalDate previousWeekStartDate = weekBeginning.getStartDate().minusWeeks(1);
    
    WeekBeginning previousWeek = this.weekBeginningDao.findByStartDate(previousWeekStartDate);
    while ((previousWeek == null) && (firstEverWeekStartDate.isBefore(previousWeekStartDate)))
    {
      previousWeekStartDate = previousWeekStartDate.minusWeeks(1);
      previousWeek = this.weekBeginningDao.findByStartDate(previousWeekStartDate);
    }
    carryOver.setAmount(previousWeek == null ? new BigDecimal("0") : previousWeek.getOverAllTotal());
    weekBeginning.addIncomeLine(carryOver);
    this.incomeLineService.update(carryOver);
  }
  
  private void addRentalIncomeCarryOver(WeekBeginning weekBeginning, LocalDate firstEverWeekStartDate)
  {
    RentalIncomeLine carryOver = new RentalIncomeLine();
    carryOver.setIncomeType(this.rentalIncomeTypeService.getCarryOver());
    
    LocalDate previousWeekStartDate = weekBeginning.getStartDate().minusWeeks(1);
    
    WeekBeginning previousWeek = this.weekBeginningDao.findByStartDate(previousWeekStartDate);
    while ((previousWeek == null) && (firstEverWeekStartDate.isBefore(previousWeekStartDate)))
    {
      previousWeekStartDate = previousWeekStartDate.minusWeeks(1);
      previousWeek = this.weekBeginningDao.findByStartDate(previousWeekStartDate);
    }
    carryOver.setAmount(previousWeek == null ? new BigDecimal("0") : previousWeek.getOverAllRentalTotal());
    weekBeginning.addRentalIncomeLine(carryOver);
    this.rentalIncomeLineService.update(carryOver);
  }
  
  //@Transactional
  public void updateCarryOvers()
  {
    BigDecimal offSetBalance = this.settingsService.getOffsetBalance();
    
    List<WeekBeginning> weeks = this.weekBeginningDao.findAllWeeksPastAndIncludingCurrent();
    
    boolean firstWeek = true;
    WeekBeginning previousWeek = null;
    for (WeekBeginning weekBeginning : weeks)
    {
      if (firstWeek)
      {
        BigDecimal newCarryOver = offSetBalance.subtract(getCurrentRentBalance());
        weekBeginning.getIncomeCarryOver().setAmount(newCarryOver);
        firstWeek = false;
      }
      else
      {
        weekBeginning.getIncomeCarryOver().setAmount(previousWeek.getIncomeTotal().subtract(previousWeek.getExpenseTotal()));
      }
      previousWeek = weekBeginning;
      this.weekBeginningDao.update(weekBeginning);
    }
  }
  
  //@Transactional
  public void changeOverCurrentWeek(WeekBeginning newCurrentWeek)
  {
    LocalDate previosWeekStartDate = newCurrentWeek.getStartDate().minusWeeks(1);
    WeekBeginning previousWeek = this.weekBeginningDao.findByStartDate(previosWeekStartDate);
    for (ExpenseLine expenseLine : previousWeek.getExpenseLines()) {
      if (!expenseLine.isDone())
      {
        ExpenseLine newExpenseLine = expenseLine.createCopy();
        if (StringUtils.isNotEmpty(newExpenseLine.getComment())) {
          newExpenseLine.setComment(newExpenseLine.getComment() + " - Copied from previous week.");
        } else {
          newExpenseLine.setComment("Copied from previous week.");
        }
        this.expenseLineService.update(newExpenseLine);
        newCurrentWeek.addExpenseLine(newExpenseLine);
        expenseLine.setDone(true);
      }
    }
    newCurrentWeek.setCurrent(true);
    previousWeek.setCurrent(false);
    this.weekBeginningDao.update(newCurrentWeek);
    this.weekBeginningDao.update(previousWeek);
  }
  
  public List<WeekBeginning> findAllWeeksPastWeekBeginning(LocalDate startDate)
  {
    return this.weekBeginningDao.findAllWeeksPastWeekBeginning(startDate);
  }
  
  protected BaseDao getDao()
  {
    return this.weekBeginningDao;
  }
}
