package au.com.mason.expensemanager.dto;

import java.util.List;

public class ExpensesForWeekDto {

	private List<ExpenseDto> expenses;
	private String previousWeek;
	private String nextWeek;
	private String thisWeek;

	public ExpensesForWeekDto(List<ExpenseDto> expenses, String previousWeek, String nextWeek, String thisWeek) {
		this.expenses = expenses;
		this.previousWeek = previousWeek;
		this.nextWeek = nextWeek;
		this.thisWeek = thisWeek;
	}

	public List<ExpenseDto> getExpenses() {
		return expenses;
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
