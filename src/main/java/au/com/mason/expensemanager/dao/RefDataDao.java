package au.com.mason.expensemanager.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import au.com.mason.expensemanager.domain.RefData;
import au.com.mason.expensemanager.domain.RefDataType;
import au.com.mason.expensemanager.domain.Statics;
import au.com.mason.expensemanager.dto.RefDataDto;

@Repository
@Transactional
public class RefDataDao {
	
	private Gson gson = new GsonBuilder().serializeNulls().create();
	
	@SuppressWarnings("unchecked")
	public List<RefData> getAll(String type) {
		Query query = entityManager.createQuery("FROM RefData WHERE type = :type ORDER BY type, description");
		query.setParameter("type", RefDataType.valueOf(type));
		return query.getResultList();
	}
	
	public List<RefData> getAll() {
		Query query = entityManager.createQuery("FROM RefData ORDER BY type, description");
		query.setMaxResults(Statics.MAX_RESULTS.getIntValue());

		return query.getResultList();
	}	
	
	public List<RefData> getAllWithEmailKey() {
		Query query = entityManager.createQuery("FROM RefData WHERE emailKey IS NOT NULL");
		query.setMaxResults(Statics.MAX_RESULTS.getIntValue());

		return query.getResultList();
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
	
	public List<RefData> findRefDatas(RefDataDto refDataDto) {
		String sql = "SELECT * from refdata where ";
		boolean addAnd = false;
		if (refDataDto.getType() != null) {
			addAnd = true;
			sql += " type = '" + refDataDto.getType() + "' ";
		}
		if (refDataDto.getDescription() != null) {
			if (addAnd) {
				sql += "AND";
			}
			addAnd = true;
			sql += " LOWER(description) like '%" + refDataDto.getDescription().toLowerCase() + "%'";
		}
		if (refDataDto.getMetaDataChunk() != null) {
			Map<String, String> metaData = gson.fromJson(refDataDto.getMetaDataChunk(), Map.class);
			if (metaData != null) {
				for (String val : metaData.keySet()) {
					if (addAnd) {
						sql += "AND";
					}
					addAnd = true;
					sql += " metaData->>'" + val + "' = '" + metaData.get(val) + "' ";
				}
			}
		}
		sql += "ORDER BY type,description";

		Query query = entityManager.createNativeQuery(sql, RefData.class);
		query.setMaxResults(Statics.MAX_RESULTS.getIntValue());

		return query.getResultList();
	}
	
	// Private fields

	// An EntityManager will be automatically injected from entityManagerFactory
	// setup on DatabaseConfig class.
	@PersistenceContext
	private EntityManager entityManager;

}
