package au.com.mason.expensemanager.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import au.com.mason.expensemanager.dto.DonationDto;
import au.com.mason.expensemanager.service.DonationService;

@RestController
public class DonationController {
	
	@Autowired
	private DonationService donationService;
	
	@RequestMapping(value = "/donations", method = RequestMethod.GET, produces = "application/json")
	List<DonationDto> getDonations() throws Exception {
		return donationService.getAll();
    }
	
	@PostMapping(value = "/donations", produces = "application/json", consumes = "application/json")
	DonationDto addDonation(@RequestBody DonationDto donation) throws Exception {

		return donationService.createDonation(donation);
    }
	
	@RequestMapping(value = "/donations/{id}", method = RequestMethod.PUT, produces = "application/json", 
			consumes = "application/json", headers = "Accept=application/json")
	DonationDto updateDonation(@RequestBody DonationDto refData, Long id) throws Exception {
		return donationService.updateDonation(refData);
    }
	
	@RequestMapping(value = "/donations/{id}", method = RequestMethod.GET, produces = "application/json")
	DonationDto getDonations(@PathVariable Long id) throws Exception {
		return donationService.getById(id);
        
    }
	
	@RequestMapping(value = "/donations/{id}", method = RequestMethod.DELETE, produces = "application/json",
			consumes = "application/json", headers = "Accept=application/json")
	String deleteDonation(@PathVariable Long id) throws Exception {
		
		donationService.deleteDonation(id);
		
		return "{\"status\":\"success\"}";
    }
	
	@PostMapping(value = "/donations/uploadFile", consumes = {"multipart/form-data"})
	String uploadFile(@RequestPart("uploadFile") MultipartFile file) throws Exception {
		
		byte[] bytes = file.getBytes();
		String folderPathString = "/docs/expenseManager/donations/";
        String filePathString = folderPathString + file.getOriginalFilename(); 
        Path folderPath = Paths.get(folderPathString);
        Path filePath = Paths.get(filePathString);
        if (!Files.exists(folderPath)) {
       	 Files.createDirectory(folderPath);
        }
        Files.write(filePath, bytes);
        
        return "{\"filePath\":\"" + filePathString + "\"}";
    }
	
	@RequestMapping(value="/donations/getFile/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getFile(@PathVariable Long id) throws Exception {
		DonationDto donation = donationService.getById(id);
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType("application/pdf"));
	    String filename = "output.pdf";
	    headers.setContentDispositionFormData(filename, filename);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    
		return new ResponseEntity<byte[]>(
				Files.readAllBytes(Paths.get(donation.getDocumentationFilePath())), headers, HttpStatus.OK);
	}
	
}
