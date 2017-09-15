package au.com.mason.expensemanager.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import au.com.mason.expensemanager.domain.Document;
import au.com.mason.expensemanager.domain.Statics;

@Repository
@Transactional
public class DocumentDao {
	
	private Gson gson = new GsonBuilder().serializeNulls().create();
	
	public List<Document> getAll(String folder) {
		Query query = entityManager.createQuery("FROM Document WHERE folder = :folder");
		query.setParameter("folder", folder);
		query.setMaxResults(Statics.MAX_RESULTS.getIntValue());
		return query.getResultList();
	}
	
	public Document create(Document document) {
		entityManager.persist(document);

		return document;
	}
	
	public void delete(Document document) {
		if (entityManager.contains(document))
			entityManager.remove(document);
		else
			entityManager.remove(entityManager.merge(document));
		return;
	}
	
	public void deleteById(Long id) {
		Document document = entityManager.find(Document.class, id);
		entityManager.remove(document);
		return;
	}
	
	public Document getById(long id) {
		return entityManager.find(Document.class, id);
	}
	
	public Document update(Document document) {
		return entityManager.merge(document);
	}
	
	// Private fields

	// An EntityManager will be automatically injected from entityManagerFactory
	// setup on DatabaseConfig class.
	@PersistenceContext
	private EntityManager entityManager;

}
