package au.com.mason.expensemanager.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import au.com.mason.expensemanager.domain.RecurringExpense;

@Repository
@Transactional
public class RecurringExpenseDao {
  
  /**
   * Save the expense in the database.
   */
  public RecurringExpense create(RecurringExpense recurringExpense) {
    entityManager.persist(recurringExpense);
    
    return recurringExpense;
  }
  
  /**
   * Delete the expense from the database.
   */
  public void delete(RecurringExpense recurringExpense) {
    if (entityManager.contains(recurringExpense))
      entityManager.remove(recurringExpense);
    else
      entityManager.remove(entityManager.merge(recurringExpense));
    return;
  }
  
  /**
   * Delete the expense from the database.
   */
  public void deleteById(Long id) {
	RecurringExpense recurringExpense = getById(id);
    if (entityManager.contains(recurringExpense))
      entityManager.remove(recurringExpense);
    else
      entityManager.remove(entityManager.merge(recurringExpense));
    return;
  }  
  
  /**
   * Return all the expenses stored in the database.
   */
  @SuppressWarnings("unchecked")
  public List getAll() {
    return entityManager.createQuery("from RecurringExpense").getResultList();
  }
  
  /**
   * Return the expense having the passed id.
   */
  public RecurringExpense getById(long id) {
    return entityManager.find(RecurringExpense.class, id);
  }

  /**
   * Update the passed expense in the database.
   */
  public RecurringExpense update(RecurringExpense recurringExpense) {
    return entityManager.merge(recurringExpense);
  }

  // Private fields
  
  // An EntityManager will be automatically injected from entityManagerFactory
  // setup on DatabaseConfig class.
  @PersistenceContext
  private EntityManager entityManager;
  
}
