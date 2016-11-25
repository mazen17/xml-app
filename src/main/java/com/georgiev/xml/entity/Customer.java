package com.georgiev.xml.entity;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlType(propOrder = { "ID", "lastName", "firstName","address" })
public class Customer {

	private String firstName;
	private String last_name;
	private int id;
	
	private String address;
	
	
	//@XmlTransient
	@XmlElement(required = true, nillable = false)
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String last_name) {
		this.last_name = last_name;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

}
