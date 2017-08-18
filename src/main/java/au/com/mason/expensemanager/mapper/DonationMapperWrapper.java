package au.com.mason.expensemanager.mapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.domain.Donation;
import au.com.mason.expensemanager.dto.DonationDto;
import au.com.mason.expensemanager.util.DateUtil;

@Component
public class DonationMapperWrapper {
	
	private DonationMapper donationMapper = DonationMapper.INSTANCE;
	
	public Donation donationDtoToDonation(DonationDto donationDto) throws Exception {
		Donation donation = donationMapper.donationDtoToDonation(donationDto);
		donation.setDueDate(DateUtil.getFormattedDate(donationDto.getDueDateString()));
		
		 saveDocumentation(donationDto, donation);

		return donation;
	}

	private void saveDocumentation(DonationDto donationDto, Donation donation) throws IOException {
		byte[] bytes = donationDto.getDocumentation().getBytes();
         String folderPathString = "/docs/expenseManager/donations/" + donationDto.getId() + "/";
         String filePathString = folderPathString + donationDto.getDocumentation().getOriginalFilename(); 
         Path folderPath = Paths.get(folderPathString);
         Path filePath = Paths.get(filePathString);
         if (!Files.exists(folderPath)) {
        	 Files.createDirectory(folderPath);
         }
         Files.write(filePath, bytes);
         donation.setDocumentationPath(filePathString);
	}
    
    public Donation donationDtoToDonation(DonationDto donationDto, Donation donationParam) throws Exception {
    	Donation donation = donationMapper.donationDtoToDonation(donationDto, donationParam);
    	donation.setDueDate(DateUtil.getFormattedDate(donationDto.getDueDateString()));
    	
		 saveDocumentation(donationDto, donation);
		
		return donation;
    }
    
    public DonationDto donationToDonationDto(Donation donation) throws Exception {
    	DonationDto donationDto = donationMapper.donationToDonationDto(donation);
    	donationDto.setDueDateString(DateUtil.getFormattedDateString(donation.getDueDate()));

		return donationDto;
    }

}
