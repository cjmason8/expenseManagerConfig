package au.com.mason.expensemanager.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import au.com.mason.expensemanager.domain.Document;
import au.com.mason.expensemanager.domain.Statics;
import au.com.mason.expensemanager.dto.SearchParamsDto;

@Repository
@Transactional
public class DocumentDao {
	
	private Gson gson = new GsonBuilder().serializeNulls().create();
	
	public List<Document> getAll(String folderPath) {
		Query query = entityManager.createQuery("FROM Document WHERE folderPath = :folderPath");
		query.setParameter("folderPath", folderPath);
		return query.getResultList();
	}
	
	public Document getFolder(String folderPath, String folderName) {
		Query query = entityManager.createQuery("FROM Document WHERE folderPath = :folderPath AND fileName := folderName");
		query.setParameter("folderPath", folderPath);
		query.setParameter("fileName", folderName);
		return (Document) query.getSingleResult();
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
	
	public void updateDirectoryPaths(String oldPath, String newPath) {
		String sql = "UPDATE documents set folderPath = replace(folderPath, '" + oldPath + "', '" + newPath + "')";
		entityManager.createNativeQuery(sql).executeUpdate();
	}
	
	public void deleteDirectory(String folderPath) {
		String sql = "DELETE from documents where folderPath LIKE '" + folderPath + "%'";
		entityManager.createNativeQuery(sql).executeUpdate();
	}
	
	public List<Document> findDocuments(SearchParamsDto searchParamsDto) {
		if (StringUtils.isEmpty(searchParamsDto.getKeyWords()) && StringUtils.isEmpty(searchParamsDto.getMetaDataChunk())) {
			return new ArrayList<Document>();
		}

		String sql = "SELECT * FROM documents ";
		boolean hasWhere = false;
		if (!StringUtils.isEmpty(searchParamsDto.getKeyWords())) {
			if (!hasWhere) {
				hasWhere = true;
				sql += "WHERE ";
			}
			sql += "lower(fileName) LIKE '%" + searchParamsDto.getKeyWords().toLowerCase() + "%' ";			
		}
		if (!StringUtils.isEmpty(searchParamsDto.getMetaDataChunk())) {
			Map<String, String> metaData = (Map<String, String>) gson.fromJson(searchParamsDto.getMetaDataChunk(), Map.class);
			for (String val : metaData.keySet()) {
				if (!hasWhere) {
					hasWhere = true;
					sql += "WHERE ";
				}
				else {
					sql += "AND ";
				}
				sql += "(metaData->>'" + val + "' = '" + metaData.get(val) + "' ";
				if (!StringUtils.isEmpty(searchParamsDto.getKeyWords())) {
					sql += "OR lower(metaData->>'" + val + "') LIKE '%" + searchParamsDto.getKeyWords().toLowerCase() + "%'";
				}
				sql += ") ";
			}
		}

		Query query = entityManager.createNativeQuery(sql, Document.class);
		query.setMaxResults(Statics.MAX_RESULTS.getIntValue());
		return query.getResultList();
		
	}
	
	// Private fields

	// An EntityManager will be automatically injected from entityManagerFactory
	// setup on DatabaseConfig class.
	@PersistenceContext
	private EntityManager entityManager;

}
