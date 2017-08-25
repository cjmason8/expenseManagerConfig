package au.com.mason.expensemanager.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import au.com.mason.expensemanager.domain.Donation;
import au.com.mason.expensemanager.dto.DonationSearchDto;
import au.com.mason.expensemanager.util.DateUtil;

@Repository
@Transactional
public class DonationDao {
	
	private Gson gson = new GsonBuilder().serializeNulls().create();
	
	public List<Donation> getAll() {
		return entityManager.createQuery("FROM Donation ORDER BY dueDate DESC, cause.description").getResultList();
	}
	
	public Donation create(Donation donation) {
		entityManager.persist(donation);

		return donation;
	}
	
	public void delete(Donation donation) {
		if (entityManager.contains(donation))
			entityManager.remove(donation);
		else
			entityManager.remove(entityManager.merge(donation));
		return;
	}
	
	public void deleteById(Long id) {
		Donation donation = entityManager.find(Donation.class, id);
		entityManager.remove(donation);
		return;
	}
	
	public Donation getById(long id) {
		return entityManager.find(Donation.class, id);
	}
	
	public Donation update(Donation donation) {
		return entityManager.merge(donation);
	}
	
	public List<Donation> findDonations(DonationSearchDto donationSearchDto) {
		String sql = "SELECT * from donations d LEFT JOIN refdata r on d.causeId = r.id where ";
		boolean addAnd = false;
		if (donationSearchDto.getCause() != null) {
			addAnd = true;
			sql += "r.description = '" + donationSearchDto.getCause().getDescription() + "'";
		}
		if (donationSearchDto.getStartDate() != null) {
			if (addAnd) {
				sql += " AND ";
			}
			addAnd = true;
			sql += "d.dueDate >= to_date('" + DateUtil.getFormattedDbDate(donationSearchDto.getStartDate()) + "', 'yyyy-mm-dd') ";
		}
		if (donationSearchDto.getEndDate() != null) {
			if (addAnd) {
				sql += " AND ";
			}
			addAnd = true;
			sql += "d.dueDate <= to_date('" + DateUtil.getFormattedDbDate(donationSearchDto.getEndDate()) + "', 'yyyy-mm-dd') ";
		}
		if (donationSearchDto.getMetaDataChunk() != null) {
			Map<String, String> metaData = gson.fromJson(donationSearchDto.getMetaDataChunk(), Map.class);
			if (addAnd) {
				sql += " AND ";
			}
			addAnd = true;
			boolean firstOne = true;
			for (String val : metaData.keySet()) {
				if (!firstOne) {
					sql += " AND ";
				}
				firstOne = false;
				sql += "d.metaData->>'" + val + "' = '" + metaData.get(val) + "'";
			}
		}
		sql += " ORDER BY dueDate DESC,r.description";
		
		return entityManager.createNativeQuery(sql, Donation.class).getResultList();
	}

	// Private fields

	// An EntityManager will be automatically injected from entityManagerFactory
	// setup on DatabaseConfig class.
	@PersistenceContext
	private EntityManager entityManager;

}
