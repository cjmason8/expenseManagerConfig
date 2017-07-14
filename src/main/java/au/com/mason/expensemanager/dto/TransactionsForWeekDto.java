package au.com.mason.expensemanager.dto;

import java.util.List;

public class TransactionsForWeekDto {

	private List<ExpenseDto> expenses;
	private List<IncomeDto> incomes;
	private String previousWeek;
	private String nextWeek;
	private String thisWeek;

	public TransactionsForWeekDto(List<IncomeDto> incomes, List<ExpenseDto> expenses, 
			String previousWeek, String nextWeek, String thisWeek) {
		this.expenses = expenses;
		this.incomes = incomes;
		this.previousWeek = previousWeek;
		this.nextWeek = nextWeek;
		this.thisWeek = thisWeek;
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
	
}
