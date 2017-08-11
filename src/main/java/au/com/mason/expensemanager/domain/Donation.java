package au.com.mason.expensemanager.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="donations")
public class Donation {
	
	public Donation() {}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne
	@JoinColumn(name = "causeId")
	private RefData cause;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public RefData getCause() {
		return cause;
	}

	public void setCause(RefData cause) {
		this.cause = cause;
	}

}
