package au.com.mason.expensemanager.dto;

public class DonationDto {

	private Long id;
	private RefDataDto cause;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public RefDataDto getCause() {
		return cause;
	}
	
	public void setCause(RefDataDto cause) {
		this.cause = cause;
	}

}
