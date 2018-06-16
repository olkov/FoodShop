package foodshop.dto;

import java.util.List;

import foodshop.entity.Role;
import foodshop.entity.User;

public class UserDto {
	private Long id;
	private String userName;
	private String name;
	private List<Role> roles;
	private Integer amountOfSales;
	private Double soldSum;

	public UserDto(User user, Integer amountOfSales, Double soldSum) {
		this.id = user.getId();
		this.userName = user.getUserName();
		this.name = user.getFirstName() + " " + user.getLastName();
		this.roles = user.getRoles();
		this.amountOfSales = amountOfSales;
		this.soldSum = soldSum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Integer getAmountOfSales() {
		return amountOfSales;
	}

	public void setAmountOfSales(Integer amountOfSales) {
		this.amountOfSales = amountOfSales;
	}

	public Double getSoldSum() {
		return soldSum;
	}

	public void setSoldSum(Double soldSum) {
		this.soldSum = soldSum;
	}
}
