package au.com.mason.expensemanager.dto;

import org.springframework.web.multipart.MultipartFile;

public class DonationDto {

	private Long id;
	private RefDataDto cause;
	private String description;
	private String dueDateString;
	private String notes;
	private MultipartFile documentation;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDueDateString() {
		return dueDateString;
	}

	public void setDueDateString(String dueDateString) {
		this.dueDateString = dueDateString;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public MultipartFile getDocumentation() {
		return documentation;
	}

	public void setDocumentation(MultipartFile documentation) {
		this.documentation = documentation;
	}

}
