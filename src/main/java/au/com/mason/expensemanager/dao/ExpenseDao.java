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

@Repository
@Transactional
public class ExpenseDao {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public Expense create(Expense expense) {
		entityManager.persist(expense);

		return expense;
	}
	
	public void delete(Expense expense) {
		if (entityManager.contains(expense))
			entityManager.remove(expense);
		else
			entityManager.remove(entityManager.merge(expense));
		return;
	}
	
	@SuppressWarnings("unchecked")
	public List<Expense> getAllRecurring() {
		return entityManager.createQuery(
				"from Expense where recurringType IS NOT NULL AND deleted = false").getResultList();
	}	
	
	public Expense getById(long id) {
		return entityManager.find(Expense.class, id);
	}
	
	public Expense update(Expense expense) {
		return entityManager.merge(expense);
	}

	public List<Expense> getForWeek(LocalDate weekStartDate) {
		String sql = "from Expense where recurringType IS NULL "
				+ "AND dueDate >= to_date('" + weekStartDate.format(FORMATTER) + "', 'yyyy-mm-dd') "
				+ "AND dueDate <= to_date('" + weekStartDate.plusDays(6).format(FORMATTER) + "', 'yyyy-mm-dd')";

		return entityManager.createQuery(sql).getResultList();
	}
	
	public List<Expense> getUnpaidBeforeWeek(LocalDate weekStartDate) {
		String sql = "from Expense where recurringType IS NULL "
				+ "AND dueDate <= to_date('" + weekStartDate.format(FORMATTER) + "', 'yyyy-mm-dd') "
				+ "AND paid = false";

		return entityManager.createQuery(sql).getResultList();
	}	
	
	public List<Expense> getPastDate(LocalDate date) {
		String sql = "from Expense where recurringType IS NULL"
				+ " AND dueDate > to_date('" + date.format(FORMATTER) + "', 'yyyy-mm-dd')";

		return entityManager.createQuery(sql).getResultList();
	}

	public List<Expense> getPastDate(LocalDate date, Expense recurringExpense) {
		String sql = "from Expense where dueDate > to_date('" + date.format(FORMATTER)
				+ "', 'yyyy-mm-dd') and recurringTransaction = :recurringTransaction";
		Query query = entityManager.createQuery(sql);
		query.setParameter("recurringTransaction", recurringExpense);

		return query.getResultList();
	}
	
	public List<Expense> getForRecurring(Expense recurringExpense) {
		String sql = "from Expense where recurringTransaction = :recurringTransaction";
		Query query = entityManager.createQuery(sql);
		query.setParameter("recurringTransaction", recurringExpense);

		return query.getResultList();
	}

	public void deleteExpenses(Long recurringTransactionId) {
		entityManager.createQuery("delete from Expense where recurringTransaction.id = " + recurringTransactionId
				+ " AND dueDate > to_date('" + LocalDate.now().format(FORMATTER) + "', 'yyyy-mm-dd')")
				.executeUpdate();
	}
	
	// Private fields

	// An EntityManager will be automatically injected from entityManagerFactory
	// setup on DatabaseConfig class.
	@PersistenceContext
	private EntityManager entityManager;

}
