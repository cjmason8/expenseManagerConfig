package au.com.mason.expensemanager.domain;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import au.com.mason.expensemanager.dao.MyJsonType;

@Entity
@Table(name="documents")
@TypeDef(name = "MyJsonType", typeClass = MyJsonType.class)
public class Document {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String fileName;
	private String filePath;
	private boolean isFolder;
	
    @Column
	@Type(type = "MyJsonType")
    private Map<String, String> metaData;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Map<String, String> getMetaData() {
		return metaData;
	}

	public void setMetaData(Map<String, String> metaData) {
		this.metaData = metaData;
	}

	public boolean isFolder() {
		return isFolder;
	}

	public void setFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}

}
