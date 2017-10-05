package au.com.mason.expensemanager.dto;

import java.util.Comparator;

public class DocumentDto implements Comparator<DocumentDto>, Comparable<DocumentDto> {

	private Long id;
	private String fileName;
	private String originalFileName;
	private boolean isFolder;
	private String metaDataChunk;
	private String folderPath;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public boolean getIsFolder() {
		return isFolder;
	}
	
	public void setIsFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}
	
	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public String getMetaDataChunk() {
		return metaDataChunk;
	}

	public void setMetaDataChunk(String metaDataChunk) {
		this.metaDataChunk = metaDataChunk;
	}
	
	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	
	public String getFilePath() {
		return folderPath + "/" + fileName;
	}

	@Override
	public int compareTo(DocumentDto o) {
		if (o.getIsFolder() == isFolder) {
			return fileName.toLowerCase().compareTo(o.getFileName().toLowerCase());
		}
		else if (o.getIsFolder()) {
			return 1;
		}
		else {
			return -1;
		}
	}

	@Override
	public int compare(DocumentDto o1, DocumentDto o2) {
		if (o1.getIsFolder() == o2.getIsFolder()) {
			return o2.getFileName().toLowerCase().compareTo(o1.getFileName().toLowerCase());
		}
		else if (o1.getIsFolder()) {
			return 1;
		}
		else {
			return -1;
		}
	}
	
}
