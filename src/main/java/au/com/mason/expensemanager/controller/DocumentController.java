package au.com.mason.expensemanager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
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

import au.com.mason.expensemanager.domain.Document;
import au.com.mason.expensemanager.dto.DirectoryDto;
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

	@PostMapping(value = "/documents/upload", consumes = { "multipart/form-data" })
	String uploadFile(@RequestPart("uploadFile") MultipartFile file, @RequestParam String type,
			@RequestParam(required = false) String path) throws Exception {

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

		return "{\"filePath\":\"" + filePathString + "\"}";
	}

	@PostMapping(value = "/documents", produces = "application/json", consumes = "application/json")
	String createFile(@RequestBody DocumentDto document) throws Exception {

		if (!document.getOriginalFileName().equals(document.getFileName())) {
			Files.move(Paths.get(document.getFolderPath() + "/" + document.getOriginalFileName()),
					Paths.get(document.getFolderPath() + "/" + document.getFileName()));
		}

		documentService.createDocument(document);

		return "{\"filePath\":\"" + document.getFolderPath() + "\"}";
	}
	
	@RequestMapping(value = "/documents/{id}", method = RequestMethod.PUT, produces = "application/json", 
			consumes = "application/json", headers = "Accept=application/json")
	String updateFile(@RequestBody DocumentDto document, Long id) throws Exception {

		if (!document.getOriginalFileName().equals(document.getFileName())) {
			Files.move(Paths.get(document.getFolderPath() + "/" + document.getOriginalFileName()),
					Paths.get(document.getFolderPath() + "/" + document.getFileName()));
		}

		documentService.updateDocument(document);

		return "{\"filePath\":\"" + document.getFolderPath() + "\"}";
	}
	
	@PostMapping(value = "/documents/directory", produces = "application/json", consumes = "application/json")
	String createDirectory(@RequestBody DocumentDto directory) throws Exception {

		String folderPathString = "";
		if (directory.getFolderPath().indexOf("root") != -1) {
			folderPathString = "/docs/expenseManager/filofax/" + directory.getFolderPath().replace("root", "") + "/";
		} else {
			folderPathString = directory.getFolderPath();
		}

		File folder = new File(folderPathString + "/" + directory.getFileName());
		folder.mkdir();

		DocumentDto document = new DocumentDto();
		document.setFileName(folder.getName());
		document.setFolderPath(folder.getParent());
		document.setIsFolder(true);
		documentService.createDocument(document);

		return "{\"filePath\":\"" + folder.getPath() + "\"}";
	}
	
	@RequestMapping(value = "/documents/directory", produces = "application/json", consumes = "application/json", method = RequestMethod.PUT)
	String updateDirectory(@RequestBody DocumentDto directory) throws Exception {
		Files.move(Paths.get(directory.getFolderPath() + "/" + directory.getOriginalFileName()),
				Paths.get(directory.getFolderPath() + "/" + directory.getFileName()));
		
		documentService.updateDocument(directory);
		
		return "{\"filePath\":\"" + directory.getFolderPath() + "\"}";
	}
	
	@RequestMapping(value = "/documents/{id}", method = RequestMethod.DELETE, produces = "application/json",
			consumes = "application/json", headers = "Accept=application/json")
	String deleteDocument(@PathVariable Long id) throws Exception {
		
		DocumentDto document = documentService.getById(id);
		String parentFolder = document.getFolderPath();
		if (document.getIsFolder()) {
			FileUtils.deleteDirectory(new File(document.getFolderPath() + "/" + document.getFileName()));
		}
		else {
			Files.delete(Paths.get(document.getFolderPath() + "/" + document.getFileName()));
		}
		
		documentService.deleteDocument(document);
		
		return "{\"filePath\":\"" + parentFolder + "\"}";
    }

	@RequestMapping(value = "/documents/get/{type}/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getFile(@PathVariable Long id, @PathVariable String type) throws Exception {
		Path path = Paths.get(getPath(id, type));
		String mediaType = getContentType(path.getFileName().toString());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(mediaType));
		String filename = "output"
				+ path.getFileName().toString().substring(path.getFileName().toString().lastIndexOf("."));
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

		return new ResponseEntity<byte[]>(Files.readAllBytes(path), headers, HttpStatus.OK);
	}

	private String getContentType(String path) {
		String mediaType = "application/pdf";
		if (path.endsWith("doc") || path.endsWith("docx")) {
			mediaType = "application/msword";
		}
		if (path.endsWith("jpg") || path.endsWith("jpeg")) {
			mediaType = "image/jpeg";
		} else if (path.endsWith("xls") || path.endsWith("xlsx")) {
			mediaType = "application/vnd.ms-excel";
		}
		return mediaType;
	}

	@RequestMapping(value = "/documents/getByPath", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getFileByPath(@RequestBody String filePath) throws Exception {

		HttpHeaders headers = new HttpHeaders();
		String mediaType = getContentType(filePath);
		headers.setContentType(MediaType.parseMediaType(mediaType));
		String filename = "output.pdf";
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

		return new ResponseEntity<byte[]>(Files.readAllBytes(Paths.get(filePath)), headers, HttpStatus.OK);
	}

	private String getPath(Long id, String type) throws Exception {
		if (type.equals("donations")) {
			DonationDto donation = donationService.getById(id);

			return donation.getDocumentationFilePath();
		} else if (type.equals("expenses")) {
			ExpenseDto expense = expenseService.getById(id);

			return expense.getDocumentationFilePath();
		} else if (type.equals("incomes")) {
			IncomeDto income = incomeService.getById(id);

			return income.getDocumentationFilePath();
		}

		return null;
	}

	@RequestMapping(value = "/documents/list", method = RequestMethod.POST)
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
