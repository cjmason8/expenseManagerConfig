package au.com.mason.expensemanager.dto;

import java.math.BigDecimal;
import java.util.List;

public class TransactionsForWeekDto {

	private List<ExpenseDto> expenses;
	private List<IncomeDto> incomes;
	private String previousWeek;
	private String nextWeek;
	private String thisWeek;
	private BigDecimal incomeTotal = new BigDecimal(0);
	private BigDecimal expenseTotal = new BigDecimal(0);
	private BigDecimal difference = new BigDecimal(0);

	public TransactionsForWeekDto(List<IncomeDto> incomes, List<ExpenseDto> expenses, 
			String previousWeek, String nextWeek, String thisWeek) {
		this.expenses = expenses;
		this.incomes = incomes;
		this.previousWeek = previousWeek;
		this.nextWeek = nextWeek;
		this.thisWeek = thisWeek;
		
		for (IncomeDto income : incomes) {
			incomeTotal = incomeTotal.add(new BigDecimal(income.getAmount()));
		}
		
		for (ExpenseDto expense : expenses) {
			expenseTotal = expenseTotal.add(new BigDecimal(expense.getAmount()));
		}
		
		difference = incomeTotal.subtract(expenseTotal);
	}

	public List<ExpenseDto> getExpenses() {
		return expenses;
	}
	
	public List<IncomeDto> getIncomes() {
		return incomes;
	}	

	public String getPreviousWeek() {
		return previousWeek;
	}

	public String getNextWeek() {
		return nextWeek;
	}

	public String getThisWeek() {
		return thisWeek;
	}

	public BigDecimal getIncomeTotal() {
		return incomeTotal;
	}

	public void setIncomeTotal(BigDecimal incomeTotal) {
		this.incomeTotal = incomeTotal;
	}

	public BigDecimal getExpenseTotal() {
		return expenseTotal;
	}

	public void setExpenseTotal(BigDecimal expenseTotal) {
		this.expenseTotal = expenseTotal;
	}

	public BigDecimal getDifference() {
		return difference;
	}

	public void setDifference(BigDecimal difference) {
		this.difference = difference;
	}
	
}
