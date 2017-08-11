package au.com.mason.expensemanager.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import au.com.mason.expensemanager.domain.Donation;
import au.com.mason.expensemanager.domain.RefData;

@Repository
@Transactional
public class DonationDao {
	
	public List<Donation> getAll() {
		return entityManager.createQuery("FROM Donation").getResultList();
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

	// Private fields

	// An EntityManager will be automatically injected from entityManagerFactory
	// setup on DatabaseConfig class.
	@PersistenceContext
	private EntityManager entityManager;

}
