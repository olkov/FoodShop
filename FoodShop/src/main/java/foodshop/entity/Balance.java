package foodshop.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Balance {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date dateOfReceiving;
	private Double quantity;
	private Double price;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private Good good;

	public Balance() {}

	public Balance(Date dateOfReceiving, Double quantity, Double price) {
		this.dateOfReceiving = dateOfReceiving;
		this.quantity = quantity;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateOfReceiving() {
		return dateOfReceiving;
	}

	public void setDateOfReceiving(Date dateOfReceiving) {
		this.dateOfReceiving = dateOfReceiving;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Good getGood() {
		return good;
	}

	public void setGood(Good good) {
		this.good = good;
	}

	@Override
	public String toString() {
		return "Balance [id=" + id + ", dateOfReceiving=" + dateOfReceiving + ", quantity=" + quantity + ", price="
				+ price + "]";
	}
}
