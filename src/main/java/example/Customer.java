package example;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlType(namespace = "http://www.example.org/type")
public class Customer {

	private String name;
	private int id;

	private String address;

	// @XmlTransient
	@XmlElement(required = true, nillable = false)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(namespace = "http://www.example.org/property")
	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
