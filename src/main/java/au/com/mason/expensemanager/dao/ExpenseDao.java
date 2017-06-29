package au.com.mason.expensemanager.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import au.com.mason.expensemanager.domain.Expense;

@Repository
@Transactional
public class ExpenseDao {
  
  /**
   * Save the event in the database.
   */
  public Expense create(Expense expense) {
    entityManager.persist(expense);
    
    return expense;
  }
  
  /**
   * Delete the event from the database.
   */
  public void delete(Expense expense) {
    if (entityManager.contains(expense))
      entityManager.remove(expense);
    else
      entityManager.remove(entityManager.merge(expense));
    return;
  }
  
  /**
   * Delete the event from the database.
   */
  public void deleteById(Long id) {
	Expense expense = getById(id);
    if (entityManager.contains(expense))
      entityManager.remove(expense);
    else
      entityManager.remove(entityManager.merge(expense));
    return;
  }  
  
  /**
   * Return all the events stored in the database.
   */
  @SuppressWarnings("unchecked")
  public List getAll() {
    return entityManager.createQuery("from Expense").getResultList();
  }
  
  /**
   * Return the event having the passed id.
   */
  public Expense getById(long id) {
    return entityManager.find(Expense.class, id);
  }

  /**
   * Update the passed event in the database.
   */
  public Expense update(Expense expense) {
    return entityManager.merge(expense);
  }

  // Private fields
  
  // An EntityManager will be automatically injected from entityManagerFactory
  // setup on DatabaseConfig class.
  @PersistenceContext
  private EntityManager entityManager;
  
}
