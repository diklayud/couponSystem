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
import javaBeans.Company;

/**
 * a DAO Access Object class - encapsulates all JDBC CRUD (Create, Read, Update,
 * Delete) actions
 */
public class CompaniesDaoDB implements CompaniesDao {

	@Override
	public boolean isCompanyExists(String email, String password) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COMPANIES where email=? and password=?";
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
			throw new CouponSystemException("check if company exists failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public boolean isCompanyExistsByNameEmail(String name, String email) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COMPANIES where name=? or email=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				return false;
			} else {
				return true;
			}

		} catch (SQLException e) {
			throw new CouponSystemException("check if company exists failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}
	
	@Override
	public boolean isCompanyEmailExists(String email) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COMPANIES where email=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				return false;
			} else {
				return true;
			}

		} catch (SQLException e) {
			throw new CouponSystemException("check if company exists failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public boolean isCompanyExistsByIdName(int id, String name) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COMPANIES where id=? and name=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				return false;
			} else {
				return true;
			}

		} catch (SQLException e) {
			throw new CouponSystemException("check if company exists failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}
	
	@Override
	public int getIdOfCompanyByEmailPassword(String email, String password) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COMPANIES where email=? and password=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				return -1;
			} else {
				return rs.getInt("id");
			}

		} catch (SQLException e) {
			throw new CouponSystemException("check if company exists failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}
	}

	@Override
	public void addCompany(Company company) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "insert into COMPANIES values(0, ?, ?, ?)";
			// initialize the '?' with real values
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getEmail());
			pstmt.setString(3, company.getPassword());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("saving company: " + company + " failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public void updateCompany(Company company) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "update COMPANIES set name=?, email=?, password=? where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getEmail());
			pstmt.setString(3, company.getPassword());
			pstmt.setInt(4, company.getId());
			int rowCount = pstmt.executeUpdate();
			if (rowCount == 0) {
				throw new CouponSystemException(
						"update company " + company + " failed because it is not in the database");
			}

		} catch (SQLException e) {
			throw new CouponSystemException("update company: " + company + " failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public void deleteCompany(int companyID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "delete from COMPANIES where id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			int rowCount = pstmt.executeUpdate();
			if (rowCount == 0) {
				throw new CouponSystemException(
						"delete company with id: " + companyID + " failed because it is not in the database");
			}

		} catch (SQLException e) {
			throw new CouponSystemException("delete company with id: " + companyID + " failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public List<Company> getAllCompanies() throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COMPANIES";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			List<Company> companiesList = new ArrayList<>();
			while (rs.next()) {
				Company company = new Company(rs.getInt("id"));
				company.setName(rs.getString("name"));
				company.setEmail(rs.getString("email"));
				company.setPassword(rs.getString("password"));
				companiesList.add(company);
			}
			return companiesList;

		} catch (SQLException e) {
			throw new CouponSystemException("get all companies failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public Company getOneCompany(int companyID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COMPANIES where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Company company = new Company(companyID);
				company.setName(rs.getString("name"));
				company.setEmail(rs.getString("email"));
				company.setPassword(rs.getString("password"));
				return company;
			} else {
				throw new CouponSystemException("get company with id: " + companyID + " failed - was not found");
			}

		} catch (SQLException e) {
			throw new CouponSystemException("get company with id: " + companyID + " failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public Company getCompanyByEmailAndPassword(String email, String password) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COMPANIES where email=? AND password=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Company company = new Company(rs.getInt("id"));
				company.setName(rs.getString("name"));
				company.setEmail(rs.getString("email"));
				company.setPassword(rs.getString("password"));
				return company;
			} else {
				throw new CouponSystemException(
						"company with email:" + email + ", pass: " + password + " - was not found");
			}

		} catch (SQLException e) {
			throw new CouponSystemException("company with email:" + email + ", pass: " + password + " - was not found", e);

		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);

			}
		}
	}
	
	
}
