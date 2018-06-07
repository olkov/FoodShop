package foodshop.dto;

import java.util.List;

import foodshop.entity.Balance;
import foodshop.entity.Good;
import foodshop.entity.Group;
import foodshop.entity.Invoice;
import foodshop.entity.Produser;

public class GoodDto {
	private Long id;
	private String code;
	private String name;
	private String unit;
	private List<Balance> balances;
	private Group group;
	private Produser produser;
	private List<Invoice> invoices;

	public GoodDto() {
	}

	public GoodDto(Good good) {
		this.id = good.getId();
		this.code = good.getCode();
		this.name = good.getName();
		this.unit = good.getUnit();
		this.group = good.getGroup();
		this.produser = good.getProduser();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public List<Balance> getBalances() {
		return balances;
	}

	public void setBalances(List<Balance> balances) {
		this.balances = balances;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Produser getProduser() {
		return produser;
	}

	public void setProduser(Produser produser) {
		this.produser = produser;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	@Override
	public String toString() {
		return "GoodDto [id=" + id + ", code=" + code + ", name=" + name + ", unit=" + unit + ", balances=" + balances
				+ ", group=" + group + ", produser=" + produser + ", invoices=" + invoices + "]";
	}
}
