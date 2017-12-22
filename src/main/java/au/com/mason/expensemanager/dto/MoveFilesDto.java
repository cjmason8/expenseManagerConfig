package au.com.mason.expensemanager.dto;

public class MoveFilesDto {

	private String destDirectoryPath;
	private Long[] files;
	
	public String getDestDirectoryPath() {
		return destDirectoryPath;
	}

	public void setDestDirectoryPath(String destDirectoryName) {
		this.destDirectoryPath = destDirectoryName;
	}

	public Long[] getFiles() {
		return files;
	}

	public void setFiles(Long[] files) {
		this.files = files;
	}
	
}
