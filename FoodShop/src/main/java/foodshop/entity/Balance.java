package foodshop.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Balance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date dateOfReceiving;
	private Double quantity;
	private Double pricePerUnit;
	private Long invoiceId;
	@JsonIgnore
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private Good good;

	public Balance() {}

	public Balance(Date dateOfReceiving, Double quantity, Double pricePerUnit) {
		this.dateOfReceiving = dateOfReceiving;
		this.quantity = quantity;
		this.pricePerUnit = pricePerUnit;
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

	public Double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public Good getGood() {
		return good;
	}

	public void setGood(Good good) {
		this.good = good;
	}
	
	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	@Override
	public String toString() {
//		return "{\"id\":" + id + ",\"dateOfReceiving\":\"" + dateOfReceiving + "\",\"quantity\":" + quantity + ",\"price\":" + price + "}";
		return "{id:" + id + ",dateOfReceiving:\"" + dateOfReceiving + "\",quantity:" + quantity + ",pricePerUnit:" + pricePerUnit + "}";
	}
}
