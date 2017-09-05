package au.com.mason.expensemanager.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import au.com.mason.expensemanager.domain.Expense;
import au.com.mason.expensemanager.domain.Statics;
import au.com.mason.expensemanager.util.DateUtil;

@Repository
@Transactional
public class ExpenseDao implements TransactionDao<Expense> {
	
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
				"from Expense where recurringType IS NOT NULL AND deleted = false"
				+ " ORDER BY dueDate DESC,entryType").getResultList();
	}	
	
	@SuppressWarnings("unchecked")
	public List<Expense> getAll() {
		Query query = entityManager.createQuery(
				"from Expense ORDER BY dueDate DESC,entryType");
		query.setMaxResults(Statics.MAX_RESULTS.getIntValue());

		return query.getResultList();
	}	
	
	public Expense getById(long id) {
		return entityManager.find(Expense.class, id);
	}
	
	public Expense update(Expense expense) {
		return entityManager.merge(expense);
	}

	public List<Expense> getForWeek(LocalDate weekStartDate) {
		String sql = "from Expense where recurringType IS NULL "
				+ "AND dueDate >= to_date('" + DateUtil.getFormattedDbDate(weekStartDate) + "', 'yyyy-mm-dd') "
				+ "AND dueDate <= to_date('" + DateUtil.getFormattedDbDate(weekStartDate.plusDays(6)) + "', 'yyyy-mm-dd')"
				+ " ORDER BY dueDate,entryType";

		return entityManager.createQuery(sql).getResultList();
	}
	
	public List<Expense> getUnpaidBeforeWeek(LocalDate weekStartDate) {
		String sql = "from Expense where recurringType IS NULL "
				+ "AND dueDate < to_date('" + DateUtil.getFormattedDbDate(weekStartDate) + "', 'yyyy-mm-dd') "
				+ "AND paid = false ORDER BY dueDate,entryType";

		return entityManager.createQuery(sql).getResultList();
	}	
	
	public List<Expense> getPastDate(LocalDate date) {
		String sql = "from Expense where recurringType IS NULL"
				+ " AND dueDate > to_date('" + DateUtil.getFormattedDbDate(date) + "', 'yyyy-mm-dd')";

		return entityManager.createQuery(sql).getResultList();
	}

	public List<Expense> getPastDate(LocalDate date, Expense recurringExpense) {
		String sql = "from Expense where dueDate > to_date('" + DateUtil.getFormattedDbDate(date)
				+ "', 'yyyy-mm-dd') and recurringTransaction = :recurringTransaction";
		Query query = entityManager.createQuery(sql);
		query.setParameter("recurringTransaction", recurringExpense);

		return query.getResultList();
	}
	
	public List<Expense> findExpenses(Expense expense) {
		String sql = "SELECT * FROM transactions e LEFT JOIN refdata r on e.entrytypeId = r.id "
				+ "where transactiontype = 'EXPENSE' AND e.recurringtypeid IS NULL AND e.paid = true ";
		if (expense.getEntryType() != null) {
			sql += "AND r.description = '" + expense.getEntryType().getDescription() + "' ";
		}
		if (expense.getStartDate() != null) {
			sql += "AND e.dueDate >= to_date('" + DateUtil.getFormattedDbDate(expense.getStartDate()) + "', 'yyyy-mm-dd') ";
		}
		if (expense.getEndDate() != null) {
			sql += "AND e.dueDate <= to_date('" + DateUtil.getFormattedDbDate(expense.getEndDate()) + "', 'yyyy-mm-dd') ";
		}
		if (expense.getMetaData() != null) {
			for (String val : expense.getMetaData().keySet()) {
				sql += "AND e.metaData->>'" + val + "' = '" + expense.getMetaData().get(val) + "' ";
			}
		}
		sql += "ORDER BY e.dueDate DESC,r.description";

		Query query = entityManager.createNativeQuery(sql, Expense.class);
		query.setMaxResults(Statics.MAX_RESULTS.getIntValue());
		return query.getResultList();
	}
	
	public List<Expense> getForRecurring(Expense recurringExpense) {
		String sql = "from Expense where recurringTransaction = :recurringTransaction";
		Query query = entityManager.createQuery(sql);
		query.setParameter("recurringTransaction", recurringExpense);

		return query.getResultList();
	}

	public void deleteTransactions(Long recurringTransactionId) {
		entityManager.createQuery("delete from Expense where recurringTransaction.id = " + recurringTransactionId
				+ " AND dueDate > to_date('" + DateUtil.getFormattedDbDate(LocalDate.now()) + "', 'yyyy-mm-dd')")
				.executeUpdate();
	}
	
	// Private fields

	// An EntityManager will be automatically injected from entityManagerFactory
	// setup on DatabaseConfig class.
	@PersistenceContext
	private EntityManager entityManager;

}
