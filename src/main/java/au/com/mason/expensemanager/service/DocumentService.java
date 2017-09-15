package au.com.mason.expensemanager.service;

import java.util.ArrayList;
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
		
		return documentMapperWrapper.documentToDocumentDto(updatedDocument);
	}
	
	public DocumentDto createDocument(DocumentDto documentDto) throws Exception {
		Document document = documentMapperWrapper.documentDtoToDocument(documentDto);
		
		documentDao.create(document);
		
		return documentDto;
	}
	
	public void deleteDocument(Long id) {
		documentDao.deleteById(id);
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
	
}
