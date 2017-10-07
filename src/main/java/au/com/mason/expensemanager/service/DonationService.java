package au.com.mason.expensemanager.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.dao.DonationDao;
import au.com.mason.expensemanager.domain.Donation;
import au.com.mason.expensemanager.dto.DonationDto;
import au.com.mason.expensemanager.dto.DonationSearchDto;
import au.com.mason.expensemanager.mapper.DonationMapperWrapper;

@Component
public class DonationService {
	
	@Autowired
	private DonationMapperWrapper donationMapperWrapper;
	
	@Autowired
	private DonationDao donationDao;
	
	@Autowired
	protected DocumentService documentService;
	
	public DonationDto updateDonation(DonationDto donationDto) throws Exception {
		
		updateDocument(donationDto);
		
		Donation updatedDonation = donationDao.getById(donationDto.getId());
		updatedDonation = donationMapperWrapper.donationDtoToDonation(donationDto, updatedDonation);
		
		donationDao.update(updatedDonation);
		
		return donationMapperWrapper.donationToDonationDto(updatedDonation);
	}
	
	public DonationDto createDonation(DonationDto donationDto) throws Exception {
		
		updateDocument(donationDto);
		
		Donation donation = donationMapperWrapper.donationDtoToDonation(donationDto);
		
		donationDao.create(donation);
		
		return donationDto;
	}
	
	private void updateDocument(DonationDto donationDto) throws IOException, Exception {
		if (donationDto.getDocumentDto() != null &&
				!donationDto.getDocumentDto().getOriginalFileName().equals(donationDto.getDocumentDto().getFileName())) {
			Files.move(Paths.get(donationDto.getDocumentDto().getFolderPath() + "/" + donationDto.getDocumentDto().getOriginalFileName()),
					Paths.get(donationDto.getDocumentDto().getFolderPath() + "/" + donationDto.getDocumentDto().getFileName()));
			
			documentService.updateDocument(donationDto.getDocumentDto());
		}
	}
	
	public void deleteDonation(Long id) {
		donationDao.deleteById(id);
	}
	
	public DonationDto getById(Long id) throws Exception {
		Donation donation = donationDao.getById(id);
		
		return donationMapperWrapper.donationToDonationDto(donation);
	}
	
	public List<DonationDto> getAll() throws Exception {
		List<DonationDto> donationDtos = new ArrayList<>();
		for(Donation donation : donationDao.getAll()) {
			donationDtos.add(donationMapperWrapper.donationToDonationDto(donation));
		};
		
		return donationDtos;
	}
	
	public List<DonationDto> findDonations(DonationSearchDto donationSearchDto) throws Exception {
		List<Donation> donations = donationDao.findDonations(donationSearchDto);
		
		List<DonationDto> donationDtos = new ArrayList<>();
		
		for (Donation donation : donations) {
			donationDtos.add(donationMapperWrapper.donationToDonationDto(donation));
		}
		
		return donationDtos;
	}
	
}
