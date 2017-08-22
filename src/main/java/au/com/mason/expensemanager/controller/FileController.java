package au.com.mason.expensemanager.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import au.com.mason.expensemanager.dto.DonationDto;
import au.com.mason.expensemanager.dto.ExpenseDto;
import au.com.mason.expensemanager.dto.IncomeDto;
import au.com.mason.expensemanager.service.DonationService;
import au.com.mason.expensemanager.service.ExpenseService;
import au.com.mason.expensemanager.service.IncomeService;

@RestController
public class FileController {
	
	@Autowired
	private DonationService donationService;
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private IncomeService incomeService;	
	
	@PostMapping(value = "/file/upload", consumes = {"multipart/form-data"})
	String uploadFile(@RequestPart("uploadFile") MultipartFile file, @RequestParam String type) throws Exception {
		
		byte[] bytes = file.getBytes();
		String folderPathString = "/docs/expenseManager/" + type + "/";
        String filePathString = folderPathString + file.getOriginalFilename(); 
        Path folderPath = Paths.get(folderPathString);
        Path filePath = Paths.get(filePathString);
        if (!Files.exists(folderPath)) {
       	 Files.createDirectory(folderPath);
        }
        Files.write(filePath, bytes);
        
        return "{\"filePath\":\"" + filePathString + "\"}";
    }
	
	@RequestMapping(value="/file/get/{type}/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getFile(@PathVariable Long id, @PathVariable String type) throws Exception {
		
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType("application/pdf"));
	    String filename = "output.pdf";
	    headers.setContentDispositionFormData(filename, filename);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    
		return new ResponseEntity<byte[]>(
				Files.readAllBytes(Paths.get(getPath(id, type))), headers, HttpStatus.OK);
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
	
}
