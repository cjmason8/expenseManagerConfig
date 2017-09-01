package au.com.mason.expensemanager.dto;

import java.util.Comparator;

public class DocumentDto implements Comparator<DocumentDto>, Comparable<DocumentDto> {

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

	@Override
	public int compareTo(DocumentDto o) {
		if (o.isFolder() == isFolder) {
			return fileName.toLowerCase().compareTo(o.getFileName().toLowerCase());
		}
		else if (o.isFolder()) {
			return 1;
		}
		else {
			return -1;
		}
	}

	@Override
	public int compare(DocumentDto o1, DocumentDto o2) {
		if (o1.isFolder() == o2.isFolder()) {
			return o2.getFileName().toLowerCase().compareTo(o1.getFileName().toLowerCase());
		}
		else if (o1.isFolder()) {
			return 1;
		}
		else {
			return -1;
		}
	}
	
}
