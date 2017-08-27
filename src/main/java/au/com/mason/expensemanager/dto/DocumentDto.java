package au.com.mason.expensemanager.dto;

public class DocumentDto {

	private String fileName;
	private boolean isFolder;
	private String filePath;
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public boolean isFolder() {
		return isFolder;
	}
	
	public void setFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
