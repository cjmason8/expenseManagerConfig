package au.com.mason.expensemanager.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import au.com.mason.expensemanager.domain.RefData;
import au.com.mason.expensemanager.domain.RefDataType;

@Repository
@Transactional
public class RefDataDao {
	
	@SuppressWarnings("unchecked")
	public List<RefData> getAll(String type) {
		Query query = entityManager.createQuery("FROM RefData WHERE type = :type ORDER BY type, description");
		query.setParameter("type", RefDataType.valueOf(type));
		return query.getResultList();
	}
	
	public List<RefData> getAll() {
		return entityManager.createQuery("FROM RefData ORDER BY type, description").getResultList();
	}	
	
	public RefData getById(long id) {
		return entityManager.find(RefData.class, id);
	}
	
	public RefData update(RefData refData) {
		return entityManager.merge(refData);
	}
	
	public RefData create(RefData refData) {
		entityManager.persist(refData);

		return refData;
	}
	
	public void delete(RefData refData) {
		if (entityManager.contains(refData))
			entityManager.remove(refData);
		else
			entityManager.remove(entityManager.merge(refData));
		return;
	}
	
	public void deleteById(Long id) {
		RefData refData = entityManager.find(RefData.class, id);
		entityManager.remove(refData);
		return;
	}
	
	// Private fields

	// An EntityManager will be automatically injected from entityManagerFactory
	// setup on DatabaseConfig class.
	@PersistenceContext
	private EntityManager entityManager;

}
