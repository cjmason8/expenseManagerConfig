package au.com.mason.expensemanager.dto;

public class MoveFilesDto {

	private String directoryTo;
	private Long[] fileIds;
	
	public String getDirectoryTo() {
		return directoryTo;
	}

	public void setDirectoryTo(String directoryTo) {
		this.directoryTo = directoryTo;
	}

	public Long[] getFileIds() {
		return fileIds;
	}

	public void setFileIds(Long[] fileIds) {
		this.fileIds = fileIds;
	}
	
}
