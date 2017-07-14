package au.com.mason.expensemanager.dto;

public class RecurringIncomeDto {

	private Integer id;
	private String incomeType;
	private String incomeTypeDescription;
	private String recurringType;
	private String recurringTypeDescription;
	private String amount;
	private String startDateString;
	private String endDateString;

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getRecurringType() {
		return recurringType;
	}
	
	public void setRecurringType(String recurringType) {
		this.recurringType = recurringType;
	}
	
	public String getRecurringTypeDescription() {
		return recurringTypeDescription;
	}
	
	public void setRecurringTypeDescription(String recurringTypeDescription) {
		this.recurringTypeDescription = recurringTypeDescription;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getStartDateString() {
		return startDateString;
	}
	
	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}
	
	public String getEndDateString() {
		return endDateString;
	}
	
	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}

	public String getIncomeType() {
		return incomeType;
	}

	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}

	public String getIncomeTypeDescription() {
		return incomeTypeDescription;
	}

	public void setIncomeTypeDescription(String expenseTypeDescription) {
		this.incomeTypeDescription = expenseTypeDescription;
	}
	
}
