package foodshop.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sales")
public class Sale {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date date;
	private Boolean status;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private User user;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sale")
	private List<SalesDetail> salesDetails = new ArrayList<>();

	public Sale() {
	}

	public Sale(Date date, User user) {
		this.date = date;
		this.status = false;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<SalesDetail> getSalesDetails() {
		return salesDetails;
	}

	public void setSalesDetails(List<SalesDetail> salesDetails) {
		this.salesDetails = salesDetails;
	}

	public void addSalesDetails(SalesDetail detail) {
		this.salesDetails.add(detail);
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Sale [id=" + id + ", date=" + date + ", status=" + status + ", user=" + user + "]";
	}
}
