package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dataBase.ConnectionPool;
import exceptions.CouponSystemException;
import javaBeans.Customer;

/**
 * a DAO Access Object class - encapsulates all JDBC CRUD (Create, Read, Update,
 * Delete) actions
 */
public class CustomerDaoDB implements CustomerDao {

	@Override
	public boolean isCustomerExists(String email, String password) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from CUSTOMERS where email=? and password=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) { // if rs.next() returns false then there are no rows.
				return false;
			} else {
				return true;
			}

		} catch (SQLException e) {
			throw new CouponSystemException("check if customer exists failed" + e.getMessage(), e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public boolean isCustomerEmailExists(String email) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from CUSTOMERS where email=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) { // if rs.next() returns false then there are no rows.
				return false;
			} else {
				return true;
			}

		} catch (SQLException e) {
			throw new CouponSystemException("check if customer exists failed" + e.getMessage(), e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public void addCustomer(Customer customer) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "insert into CUSTOMERS values(0, ?, ?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getPassword());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("saving customer: " + customer + " failed" + e.getMessage(), e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	public void addCustomer2(String name, String lastName, String email, String password) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "insert into CUSTOMERS values(0, ?, ?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, lastName);
			pstmt.setString(3, email);
			pstmt.setString(4, password);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("saving customer failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "update CUSTOMERS set first_name=?, last_name=?, email=?, password=? where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getPassword());
			pstmt.setInt(5, customer.getId());
			int rowCount = pstmt.executeUpdate();
			if (rowCount == 0) {
				throw new CouponSystemException(
						"update customer " + customer + " failed because it is not in the database");
			}

		} catch (SQLException e) {
			throw new CouponSystemException("update customer: " + customer + " failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public void deleteCustomer(int customerID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "delete from CUSTOMERS where id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			int rowCount = pstmt.executeUpdate();
			if (rowCount == 0) {
				throw new CouponSystemException(
						"delete customer with id: " + customerID + " failed because it is not in the database");
			}

		} catch (SQLException e) {
			throw new CouponSystemException("delete customer with id: " + customerID + " failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public List<Customer> getAllCustomers() throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from CUSTOMERS";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			List<Customer> customersList = new ArrayList<>();
			while (rs.next()) {
				Customer customer = new Customer(rs.getInt("id"));
				customer.setFirstName(rs.getString("first_name"));
				customer.setLastName(rs.getString("last_name"));
				customer.setEmail(rs.getString("email"));
				customer.setPassword(rs.getString("password"));
				customersList.add(customer);
			}
			return customersList;

		} catch (SQLException e) {
			throw new CouponSystemException("get all customers failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public Customer getOneCustomer(int customerID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from CUSTOMERS where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Customer customer = new Customer(customerID);
				customer.setFirstName(rs.getString("first_name"));
				customer.setLastName(rs.getString("last_name"));
				customer.setEmail(rs.getString("email"));
				customer.setPassword(rs.getString("password"));
				return customer;
			} else {
				throw new CouponSystemException("get customer with id: " + customerID + " faild - was not found");
			}

		} catch (SQLException e) {
			throw new CouponSystemException("get customer with id: " + customerID + " faild", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public int getIdOfCustomerByEmailPassword(String email, String password) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from CUSTOMERS where email=? and password=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				System.out.println("No customer found");
				return -1;
			} else {
				return rs.getInt("id");
			}

		} catch (SQLException e) {
			throw new CouponSystemException("check if customer exists failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}
	}

}
