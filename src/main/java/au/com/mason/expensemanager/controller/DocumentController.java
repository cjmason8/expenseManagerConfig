package au.com.mason.expensemanager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import au.com.mason.expensemanager.dto.DocumentDto;
import au.com.mason.expensemanager.dto.DonationDto;
import au.com.mason.expensemanager.dto.ExpenseDto;
import au.com.mason.expensemanager.dto.IncomeDto;
import au.com.mason.expensemanager.service.DocumentService;
import au.com.mason.expensemanager.service.DonationService;
import au.com.mason.expensemanager.service.ExpenseService;
import au.com.mason.expensemanager.service.IncomeService;

@RestController
public class DocumentController {
	
	@Autowired
	private DonationService donationService;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private IncomeService incomeService;	
	
	@PostMapping(value = "/document/upload", consumes = {"multipart/form-data"})
	String uploadFile(@RequestPart("uploadFile") MultipartFile file, @RequestParam String type, 
			@RequestParam(required=false) String path) throws Exception {
		
		byte[] bytes = file.getBytes();
		String folderPathString = "/docs/expenseManager/" + type + "/";
		if (path != null) {
			folderPathString = path.replace("root", "/docs/expenseManager/filofax") + "/";
		}
        String filePathString = folderPathString + file.getOriginalFilename(); 
        Path folderPath = Paths.get(folderPathString);
        Path filePath = Paths.get(filePathString);
        if (!Files.exists(folderPath)) {
       	 Files.createDirectory(folderPath);
        }
        Files.write(filePath, bytes);
        
        DocumentDto document = new DocumentDto();
		document.setFileName(file.getOriginalFilename());
		document.setFolderPath(folderPathString.substring(0, folderPathString.length() - 1));
		document.setFolder(false);
		documentService.createDocument(document);
        
        return "{\"filePath\":\"" + filePathString + "\"}";
    }
	
	@PostMapping(value = "/document/directory/create")
	String createDirectory(@RequestBody String path) throws Exception {
		
		String folderPathString = "";
		if (path.indexOf("root") != -1) {
			folderPathString = "/docs/expenseManager/filofax/" + path.replace("root", "") + "/";
		}
		else {
			folderPathString = path;
		}
		
		File folder = new File(folderPathString);
		folder.mkdir();
		
		DocumentDto document = new DocumentDto();
		document.setFileName(folder.getName());
		document.setFolderPath(folder.getParent());
		document.setFolder(true);
		documentService.createDocument(document);
        
        return "{\"filePath\":\"" + folder.getPath() + "\"}";
    }	
	
	@RequestMapping(value="/document/get/{type}/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getFile(@PathVariable Long id, @PathVariable String type) throws Exception {
		Path path = Paths.get(getPath(id, type));
		String mediaType = getContentType(path.getFileName().toString());
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType(mediaType));
	    String filename = "output" + path.getFileName().toString().substring(path.getFileName().toString().lastIndexOf("."));
	    headers.setContentDispositionFormData(filename, filename);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		
		return new ResponseEntity<byte[]>(
				Files.readAllBytes(path), headers, HttpStatus.OK);
	}

	private String getContentType(String path) {
		String mediaType = "application/pdf";
		if (path.endsWith("doc") || path.endsWith("docx")) {
			mediaType = "application/msword";
		}
		if (path.endsWith("jpg") || path.endsWith("jpeg")) {
			mediaType = "image/jpeg";
		}
		else if(path.endsWith("xls") || path.endsWith("xlsx")) {
			mediaType = "application/vnd.ms-excel";
	    }
		return mediaType;
	}
	
	@RequestMapping(value="/document/getByPath", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getFileByPath(@RequestBody String filePath) throws Exception {
		
		
		HttpHeaders headers = new HttpHeaders();
		String mediaType = getContentType(filePath);
	    headers.setContentType(MediaType.parseMediaType(mediaType));
	    String filename = "output.pdf";
	    headers.setContentDispositionFormData(filename, filename);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    
		return new ResponseEntity<byte[]>(
				Files.readAllBytes(Paths.get(filePath)), headers, HttpStatus.OK);
	}	
	
	private String getPath(Long id, String type) throws Exception {
		if (type.equals("donations")) {
			DonationDto donation = donationService.getById(id);
			
			return donation.getDocumentationFilePath();
		}
		else if (type.equals("expenses")) {
			ExpenseDto expense = expenseService.getById(id);
			
			return expense.getDocumentationFilePath();
		}
		else if (type.equals("incomes")) {
			IncomeDto income = incomeService.getById(id);
			
			return income.getDocumentationFilePath();
		}
		
		return null;
	}
	
	@RequestMapping(value="/document", method = RequestMethod.POST)
	public List<DocumentDto> getFiles(@RequestBody String folder) throws Exception {
		String folderPath = folder;
		if ("root".equals(folder)) {
			folderPath = "/docs/expenseManager/filofax";
		}
		List<DocumentDto> documents = documentService.getAll(folderPath);
		
		Collections.sort(documents);
		
		return documents;
	}
	
}
