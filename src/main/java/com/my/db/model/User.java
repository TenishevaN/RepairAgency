package com.my.db.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class User  implements Serializable {

	private static final long serialVersionUID = 8466257860808346236L;
	private int id;
	private String login;
	private String name;
	private String email;
	private transient String password;
	private int roleId;
	private int invoiceId;
	private BigDecimal invoiceAmmount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleId() {
		return roleId;
	}

	public String getRoleName(int id){
		return Role.getRole(this).getName();
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public BigDecimal getInvoiceAmmount() {
		return invoiceAmmount;
	}

	public void setInvoiceAmmount(BigDecimal invoiceAmmount) {
		this.invoiceAmmount = invoiceAmmount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id == user.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", login='" + login + '\'' +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", roleId=" + roleId +
				", invoiceId=" + invoiceId +
				", invoiceAmmount=" + invoiceAmmount +
				'}';
	}
}
