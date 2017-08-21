package au.com.mason.expensemanager.mapper;

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
		
		return donation;
	}

    public Donation donationDtoToDonation(DonationDto donationDto, Donation donationParam) throws Exception {
    	Donation donation = donationMapper.donationDtoToDonation(donationDto, donationParam);
    	donation.setDueDate(DateUtil.getFormattedDate(donationDto.getDueDateString()));
    	
		return donation;
    }
    
    public DonationDto donationToDonationDto(Donation donation) throws Exception {
    	DonationDto donationDto = donationMapper.donationToDonationDto(donation);
    	donationDto.setDueDateString(DateUtil.getFormattedDateString(donation.getDueDate()));

		return donationDto;
    }

}
