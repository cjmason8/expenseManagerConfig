package au.com.mason.expensemanager.dto;

public class DonationDto {

	private Long id;
	private RefDataDto causeType;
	
	public Long getId() {
		return id;
	}
	
	public RefDataDto getCauseType() {
		return causeType;
	}
	
	public void setCauseType(RefDataDto causeType) {
		this.causeType = causeType;
	}

}
