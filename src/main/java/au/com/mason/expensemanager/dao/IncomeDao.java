package au.com.mason.expensemanager.dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import au.com.mason.expensemanager.domain.Expense;
import au.com.mason.expensemanager.domain.Income;

@Repository
@Transactional
public class IncomeDao implements TransactionDao<Income> {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public Income create(Income income) {
		entityManager.persist(income);

		return income;
	}
	
	public void delete(Income income) {
		if (entityManager.contains(income))
			entityManager.remove(income);
		else
			entityManager.remove(entityManager.merge(income));
		return;
	}
	
	public void deleteById(Long id) {
		Income income = getById(id);
		if (entityManager.contains(income))
			entityManager.remove(income);
		else
			entityManager.remove(entityManager.merge(income));
		return;
	}
	
	@SuppressWarnings("unchecked")
	public List<Income> getAllRecurring() {
		
		return entityManager.createQuery(
				"from Income where recurringType IS NOT NULL AND deleted = false").getResultList();
	}	
	
	public Income getById(long id) {
		return entityManager.find(Income.class, id);
	}
	
	public Income update(Income income) {
		return entityManager.merge(income);
	}

	public List<Income> getForWeek(LocalDate weekStartDate) {
		String sql = "from Income where recurringType IS NULL "
				+ "AND dueDate >= to_date('" + weekStartDate.format(FORMATTER) + "', 'yyyy-mm-dd') "
				+ "AND dueDate <= to_date('" + weekStartDate.plusDays(6).format(FORMATTER) + "', 'yyyy-mm-dd')";

		return entityManager.createQuery(sql).getResultList();
	}

	public List<Income> getPastDate(LocalDate date) {
		String sql = "from Income where recurringType IS NULL"
				+ " AND dueDate > to_date('" + date.format(FORMATTER) + "', 'yyyy-mm-dd')";

		return entityManager.createQuery(sql).getResultList();
	}

	public List<Income> getPastDate(LocalDate date, Income recurringIncome) {
		String sql = "from Income where dueDate > to_date('" + date.format(FORMATTER)
				+ "', 'yyyy-mm-dd') and recurringTransaction = :recurringTransaction";
		Query query = entityManager.createQuery(sql);
		query.setParameter("recurringTransaction", recurringIncome);

		return query.getResultList();
	}
	
	public List<Income> getForRecurring(Income recurringIncome) {
		String sql = "from Income where recurringTransaction = :recurringTransaction";
		Query query = entityManager.createQuery(sql);
		query.setParameter("recurringTransaction", recurringIncome);

		return query.getResultList();
	}

	public void deleteTransactions(Long recurringTransactionId) {
		entityManager.createQuery("delete from Income where recurringTransaction.id = " + recurringTransactionId
				+ " AND dueDate > to_date('" + LocalDate.now().format(FORMATTER) + "', 'yyyy-mm-dd')")
				.executeUpdate();
	}
	
	// Private fields

	// An EntityManager will be automatically injected from entityManagerFactory
	// setup on DatabaseConfig class.
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Expense> getUnpaidBeforeWeek(LocalDate weekStartDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
