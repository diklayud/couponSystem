package javaBeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// create a class based on a database table - java beans
public class Company {

	// fields
	private int id;
	private String name;
	private String email;
	private String password;
	List<Coupon> coupons = new ArrayList<>(); // create without initialize at this point, will use it later

	// CTORs
	public Company() {
	}

	public Company(int id) {
		super();
		this.id = id;
	}

	public Company(int id, String name, String email, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	// getters / setters
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// Override methods
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Company)) {
			return false;
		}
		Company other = (Company) obj;
		return id == other.id;
	}

}
