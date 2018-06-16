package foodshop.dto;

import java.util.Date;

public class SaleDto {
	private Long id;
	private Date date;
	private Integer salesDetailsAmount;
	private Double total;

	public SaleDto() {
	}

	public SaleDto(Long id, Date date, Integer salesDetailsAmount, Double total) {
		this.id = id;
		this.date = date;
		this.salesDetailsAmount = salesDetailsAmount;
		this.total = total;
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

	public Integer getSalesDetailsAmount() {
		return salesDetailsAmount;
	}

	public void setSalesDetailsAmount(Integer salesDetailsAmount) {
		this.salesDetailsAmount = salesDetailsAmount;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}
