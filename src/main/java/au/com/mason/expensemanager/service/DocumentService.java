package au.com.mason.expensemanager.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.dao.DocumentDao;
import au.com.mason.expensemanager.domain.Document;
import au.com.mason.expensemanager.dto.DocumentDto;
import au.com.mason.expensemanager.mapper.DocumentMapperWrapper;

@Component
public class DocumentService {
	
	@Autowired
	private DocumentMapperWrapper documentMapperWrapper;
	
	@Autowired
	private DocumentDao documentDao;
	
	public DocumentDto updateDocument(DocumentDto documentDto) throws Exception {
		Document updatedDocument = documentDao.getById(documentDto.getId());
		updatedDocument = documentMapperWrapper.documentDtoToDocument(documentDto, updatedDocument);
		
		documentDao.update(updatedDocument);
		
		if (updatedDocument.isFolder() && !documentDto.getOriginalFileName().equals(documentDto.getFileName())) {
			documentDao.updateDirectoryPaths(documentDto.getFolderPath() + "/" + documentDto.getOriginalFileName(), documentDto.getFolderPath() + "/" + documentDto.getFileName());
		}
		
		return documentMapperWrapper.documentToDocumentDto(updatedDocument);
	}
	
	public DocumentDto createDocument(DocumentDto documentDto) throws Exception {
		Document document = documentMapperWrapper.documentDtoToDocument(documentDto);
		
		return documentMapperWrapper.documentToDocumentDto(documentDao.create(document));
	}
	
	public void deleteDocument(DocumentDto documentDto) {
		if (documentDto.getIsFolder()) {
			documentDao.deleteDirectory(documentDto.getFolderPath() + "/" + documentDto.getFileName());
		}
		documentDao.deleteById(documentDto.getId());
	}
	
	public DocumentDto getById(Long id) throws Exception {
		Document document = documentDao.getById(id);
		
		return documentMapperWrapper.documentToDocumentDto(document);
	}
	
	public List<DocumentDto> getAll(String folder) throws Exception {
		List<DocumentDto> documentDtos = new ArrayList<>();
		for(Document document : documentDao.getAll(folder)) {
			documentDtos.add(documentMapperWrapper.documentToDocumentDto(document));
		};
		
		return documentDtos;
	}
	
	public void moveFiles(String folderPath, Long[] files) {
		Arrays.asList(files).forEach(fileId -> {
			Document file = documentDao.getById(fileId);
			
			try {
				Files.move(Paths.get(file.getFolderPath() + "/" + file.getFileName()),
						Paths.get(folderPath + "/" + file.getFileName()));
			}
			catch (IOException e) {
				throw new RuntimeException("error moving file", e);
			}
			
			file.setFolderPath(folderPath);
			documentDao.update(file);
		});
		
	}
	
}
