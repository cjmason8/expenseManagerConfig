package au.com.mason.expensemanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	
}
