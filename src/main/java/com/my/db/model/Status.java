package com.my.db.model;

import java.util.Objects;

/**
 * Role model.
 * 
 * @author Tenisheva N.
 * 
 */

public class Status {

	private static final long serialVersionUID = 8466257860808346236L;
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Status status = (Status) o;
		return id == status.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return name;
	}
}
