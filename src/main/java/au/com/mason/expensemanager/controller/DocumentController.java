package au.com.mason.expensemanager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import au.com.mason.expensemanager.service.DonationService;
import au.com.mason.expensemanager.service.ExpenseService;
import au.com.mason.expensemanager.service.IncomeService;

@RestController
public class DocumentController {
	
	@Autowired
	private DonationService donationService;
	
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
			folderPathString = path + "/";
		}
        String filePathString = folderPathString + file.getOriginalFilename(); 
        Path folderPath = Paths.get(folderPathString);
        Path filePath = Paths.get(filePathString);
        if (!Files.exists(folderPath)) {
       	 Files.createDirectory(folderPath);
        }
        Files.write(filePath, bytes);
        
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
        
        return "{\"filePath\":\"" + folder.getPath() + "\"}";
    }	
	
	@RequestMapping(value="/document/get/{type}/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getFile(@PathVariable Long id, @PathVariable String type) throws Exception {
		
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType("application/pdf"));
	    String filename = "output.pdf";
	    headers.setContentDispositionFormData(filename, filename);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    
		return new ResponseEntity<byte[]>(
				Files.readAllBytes(Paths.get(getPath(id, type))), headers, HttpStatus.OK);
	}
	
	@RequestMapping(value="/document/getByPath", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getFileByPath(@RequestBody String filePath) throws Exception {
		
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType("application/pdf"));
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
			folderPath = "/docs/expenseManager/filofax/";
		}
		List<DocumentDto> documents = new ArrayList<>();
		File reqFolder = new File(folderPath);
		for (File file : reqFolder.listFiles()) {
			DocumentDto document = new DocumentDto();
			document.setFileName(file.getName());
			document.setFolder(!file.isFile());
			document.setFilePath(file.getAbsolutePath());
			documents.add(document);
		}
		
		return documents;
	}
	
}
