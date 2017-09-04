package au.com.mason.expensemanager.dto;

import java.util.List;

public class ExpenseSearchDto {

	private List<ExpenseDto> expenseDtos;
	private ExpenseGraphDto expenseGraphDto;
	
	public ExpenseSearchDto(List<ExpenseDto> expenseDtos, ExpenseGraphDto expenseGraphDto) {
		this.expenseDtos = expenseDtos;
		this.expenseGraphDto = expenseGraphDto;
	}

	public List<ExpenseDto> getExpenseDtos() {
		return expenseDtos;
	}
	
	public void setExpenseDtos(List<ExpenseDto> expenseDtos) {
		this.expenseDtos = expenseDtos;
	}
	
	public ExpenseGraphDto getExpenseGraphDto() {
		return expenseGraphDto;
	}
	
	public void setExpenseGraphDto(ExpenseGraphDto expenseGraphDto) {
		this.expenseGraphDto = expenseGraphDto;
	}
	
}
