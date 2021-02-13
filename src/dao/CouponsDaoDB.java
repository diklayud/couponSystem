package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dataBase.ConnectionPool;
import exceptions.CouponSystemException;
import javaBeans.Category;
import javaBeans.Coupon;

/**
 * a DAO Access Object class - encapsulates all JDBC CRUD (Create, Read, Update,
 * Delete) actions
 */
public class CouponsDaoDB implements CouponsDao {
	
	@Override
	public void addCoupon(Coupon coupon) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "insert into COUPONS values(0, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, coupon.getCompanyId());
			pstmt.setInt(2, coupon.getCategory().getCategoryId());
			pstmt.setString(3, coupon.getCouponTitle());
			pstmt.setString(4, coupon.getCouponDescription());
			pstmt.setDate(5, Date.valueOf(coupon.getCouponCreationDate()));
			pstmt.setDate(6, Date.valueOf(coupon.getCouponExpirationDate()));
			pstmt.setInt(7, coupon.getCouponsAmount());
			pstmt.setDouble(8, coupon.getCouponPrice());
			pstmt.setString(9, coupon.getCouponImage());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("saving coupon: " + coupon + " failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public boolean isCouponTitleExist(int companyId, String couponTitle) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COUPONS where company_id=? and coupon_title=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyId);
			pstmt.setString(2, couponTitle);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				return false;
			} else {
				return true;
			}

		} catch (SQLException e) {
			throw new CouponSystemException("check if Coupon Title exists failed", e);

		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "update COUPONS set company_id=?, category_id=?, coupon_title=?, coupon_description=?, coupon_creation_date=?, coupon_expiration_date=?, coupons_amount=?, coupon_price=?, coupon_image=? where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, coupon.getCompanyId());
			pstmt.setInt(2, coupon.getCategory().getCategoryId());
			pstmt.setString(3, coupon.getCouponTitle());
			pstmt.setString(4, coupon.getCouponDescription());
			pstmt.setDate(5, Date.valueOf(coupon.getCouponCreationDate()));
			pstmt.setDate(6, Date.valueOf(coupon.getCouponExpirationDate()));
			pstmt.setInt(7, coupon.getCouponsAmount());
			pstmt.setDouble(8, coupon.getCouponPrice());
			pstmt.setString(9, coupon.getCouponImage());
			pstmt.setInt(10, coupon.getId());
			pstmt.executeUpdate();
			int rowCount = pstmt.executeUpdate();
			if (rowCount == 0) {
				throw new CouponSystemException(
						"update coupon " + coupon + " failed because it is not in the database");
			}

		} catch (SQLException e) {
			throw new CouponSystemException("update coupon: " + coupon + " failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public void deleteCoupon(int couponID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "delete from COUPONS where id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, couponID);
			int rowCount = pstmt.executeUpdate();
			if (rowCount == 0) {
				throw new CouponSystemException(
						"delete coupon with id: " + couponID + " failed because it is not in the database");
			}

		} catch (SQLException e) {
			throw new CouponSystemException("delete coupon with id: " + couponID + " failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public void deleteCouponByCompany(int companyID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "delete from COUPONS where company_id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			int rowCount = pstmt.executeUpdate();
			if (rowCount == 0) {
				throw new CouponSystemException(
						"delete coupon with company id: " + companyID + " failed because it is not in the database");
			}

		} catch (SQLException e) {
			throw new CouponSystemException("delete coupon with company id: " + companyID + " failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public List<Coupon> getAllCoupons() throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COUPONS";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			List<Coupon> couponsList = new ArrayList<>();
			while (rs.next()) {
				Coupon coupon = new Coupon(rs.getInt("id"));
				coupon.setCompanyId(rs.getInt("company_id"));
				coupon.setCategory(Category.fromInt(rs.getInt("category_id")));
				coupon.setCouponTitle(rs.getString("coupon_title"));
				coupon.setCouponDescription(rs.getString("coupon_description"));

				// convert java.sql.date to java.time.LocalDate:
				Date dateSqlCreation = rs.getDate("coupon_creation_date"); // java.sql.Date
				LocalDate localDateCreation = dateSqlCreation.toLocalDate(); // conversion
				coupon.setCouponCreationDate(localDateCreation);

				// convert java.sql.date to java.time.LocalDate:
				Date dateSqlExpiration = rs.getDate("coupon_expiration_date"); // java.sql.Date
				LocalDate localDateExpiration = dateSqlExpiration.toLocalDate(); // conversion
				coupon.setCouponExpirationDate(localDateExpiration);

				coupon.setCouponsAmount(rs.getInt("coupons_amount"));
				coupon.setCouponPrice(rs.getDouble("coupon_price"));
				coupon.setCouponImage(rs.getString("coupon_image"));
				couponsList.add(coupon);
			}
			return couponsList;

		} catch (SQLException e) {
			throw new CouponSystemException("get all coupons failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public List<Coupon> getAllCompanyCoupons(int companyID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COUPONS where company_id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			ResultSet rs = pstmt.executeQuery();
			List<Coupon> couponsList = new ArrayList<>();
			while (rs.next()) {
				Coupon coupon = new Coupon(rs.getInt("id"));
				coupon.setCompanyId(rs.getInt("company_id"));
				coupon.setCategory(Category.fromInt(rs.getInt("category_id")));
				coupon.setCouponTitle(rs.getString("coupon_title"));
				coupon.setCouponDescription(rs.getString("coupon_description"));

				// convert java.sql.date to java.time.LocalDate:
				Date dateSqlCreation = rs.getDate("coupon_creation_date"); // java.sql.Date
				LocalDate localDateCreation = dateSqlCreation.toLocalDate(); // conversion
				coupon.setCouponCreationDate(localDateCreation);

				// convert java.sql.date to java.time.LocalDate:
				Date dateSqlExpiration = rs.getDate("coupon_expiration_date"); // java.sql.Date
				LocalDate localDateExpiration = dateSqlExpiration.toLocalDate(); // conversion
				coupon.setCouponExpirationDate(localDateExpiration);

				coupon.setCouponsAmount(rs.getInt("coupons_amount"));
				coupon.setCouponPrice(rs.getDouble("coupon_price"));
				coupon.setCouponImage(rs.getString("coupon_image"));
				couponsList.add(coupon);
			}
			return couponsList;

		} catch (SQLException e) {
			throw new CouponSystemException("get all company's coupons failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}
	}

	@Override
	public List<Coupon> getAllCustomerCoupons(int customerID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COUPONS where id IN (select coupon_id from CUSTOMERS_VS_COUPONS where customer_id=?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			ResultSet rs = pstmt.executeQuery();
			List<Coupon> couponsList = new ArrayList<>();
			while (rs.next()) {
				Coupon coupon = new Coupon(rs.getInt("id"));
				coupon.setCompanyId(rs.getInt("company_id"));
				coupon.setCategory(Category.fromInt(rs.getInt("category_id")));
				coupon.setCouponTitle(rs.getString("coupon_title"));
				coupon.setCouponDescription(rs.getString("coupon_description"));

				// convert java.sql.date to java.time.LocalDate:
				Date dateSqlCreation = rs.getDate("coupon_creation_date"); // java.sql.Date
				LocalDate localDateCreation = dateSqlCreation.toLocalDate(); // conversion
				coupon.setCouponCreationDate(localDateCreation);

				// convert java.sql.date to java.time.LocalDate:
				Date dateSqlExpiration = rs.getDate("coupon_expiration_date"); // java.sql.Date
				LocalDate localDateExpiration = dateSqlExpiration.toLocalDate(); // conversion
				coupon.setCouponExpirationDate(localDateExpiration);

				coupon.setCouponsAmount(rs.getInt("coupons_amount"));
				coupon.setCouponPrice(rs.getDouble("coupon_price"));
				coupon.setCouponImage(rs.getString("coupon_image"));
				couponsList.add(coupon);
			}
			return couponsList;

		} catch (SQLException e) {
			throw new CouponSystemException("get all customer's coupons failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}
	}

	@Override
	public List<Coupon> getAllCompanyCouponsByCategory(int companyID, Category categoryID)
			throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COUPONS where company_id=? and category_id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			pstmt.setInt(2, categoryID.getCategoryId());
			ResultSet rs = pstmt.executeQuery();
			List<Coupon> categoryCouponsList = new ArrayList<>();
			while (rs.next()) {
				Coupon coupon = new Coupon(rs.getInt("id"));
				coupon.setCompanyId(rs.getInt("company_id"));
				coupon.setCategory(Category.fromInt(rs.getInt("category_id")));
				coupon.setCouponTitle(rs.getString("coupon_title"));
				coupon.setCouponDescription(rs.getString("coupon_description"));

				// convert java.sql.date to java.time.LocalDate:
				Date dateSqlCreation = rs.getDate("coupon_creation_date"); // java.sql.Date
				LocalDate localDateCreation = dateSqlCreation.toLocalDate(); // conversion
				coupon.setCouponCreationDate(localDateCreation);

				// convert java.sql.date to java.time.LocalDate:
				Date dateSqlExpiration = rs.getDate("coupon_expiration_date"); // java.sql.Date
				LocalDate localDateExpiration = dateSqlExpiration.toLocalDate(); // conversion
				coupon.setCouponExpirationDate(localDateExpiration);

				coupon.setCouponsAmount(rs.getInt("coupons_amount"));
				coupon.setCouponPrice(rs.getDouble("coupon_price"));
				coupon.setCouponImage(rs.getString("coupon_image"));
				categoryCouponsList.add(coupon);
			}
			return categoryCouponsList;

		} catch (SQLException e) {
			throw new CouponSystemException("get all company's coupons by category: " + categoryID + " failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}
	}

	@Override
	public List<Coupon> getAllCustomerCouponsByCategory(int customerID, Category categoryID)
			throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COUPONS where category_id=? and id IN (select coupon_id from CUSTOMERS_VS_COUPONS where customer_id=?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, categoryID.getCategoryId());
			pstmt.setInt(2, customerID);
			ResultSet rs = pstmt.executeQuery();
			List<Coupon> categoryCouponsList = new ArrayList<>();
			while (rs.next()) {
				Coupon coupon = new Coupon(rs.getInt("id"));
				coupon.setCompanyId(rs.getInt("company_id"));
				coupon.setCategory(Category.fromInt(rs.getInt("category_id")));
				coupon.setCouponTitle(rs.getString("coupon_title"));
				coupon.setCouponDescription(rs.getString("coupon_description"));

				// convert java.sql.date to java.time.LocalDate:
				Date dateSqlCreation = rs.getDate("coupon_creation_date"); // java.sql.Date
				LocalDate localDateCreation = dateSqlCreation.toLocalDate(); // conversion
				coupon.setCouponCreationDate(localDateCreation);

				// convert java.sql.date to java.time.LocalDate:
				Date dateSqlExpiration = rs.getDate("coupon_expiration_date"); // java.sql.Date
				LocalDate localDateExpiration = dateSqlExpiration.toLocalDate(); // conversion
				coupon.setCouponExpirationDate(localDateExpiration);

				coupon.setCouponsAmount(rs.getInt("coupons_amount"));
				coupon.setCouponPrice(rs.getDouble("coupon_price"));
				coupon.setCouponImage(rs.getString("coupon_image"));
				categoryCouponsList.add(coupon);
			}
			return categoryCouponsList;

		} catch (SQLException e) {
			throw new CouponSystemException("get all customer's coupons by category: " + categoryID + " failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}
	}

	@Override
	public List<Coupon> getAllCompanyCouponsByPrice(int companyID, double couponPrice) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COUPONS where company_id=? and coupon_price <= ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			pstmt.setDouble(2, couponPrice);
			ResultSet rs = pstmt.executeQuery();
			List<Coupon> couponsByPriceList = new ArrayList<>();
			while (rs.next()) {
				Coupon coupon = new Coupon(rs.getInt("id"));
				coupon.setCompanyId(rs.getInt("company_id"));
				coupon.setCategory(Category.fromInt(rs.getInt("category_id")));
				coupon.setCouponTitle(rs.getString("coupon_title"));
				coupon.setCouponDescription(rs.getString("coupon_description"));

				// convert java.sql.date to java.time.LocalDate:
				Date dateSqlCreation = rs.getDate("coupon_creation_date"); // java.sql.Date
				LocalDate localDateCreation = dateSqlCreation.toLocalDate(); // conversion
				coupon.setCouponCreationDate(localDateCreation);

				// convert java.sql.date to java.time.LocalDate:
				Date dateSqlExpiration = rs.getDate("coupon_expiration_date"); // java.sql.Date
				LocalDate localDateExpiration = dateSqlExpiration.toLocalDate(); // conversion
				coupon.setCouponExpirationDate(localDateExpiration);

				coupon.setCouponsAmount(rs.getInt("coupons_amount"));
				coupon.setCouponPrice(rs.getDouble("coupon_price"));
				coupon.setCouponImage(rs.getString("coupon_image"));
				couponsByPriceList.add(coupon);
			}
			return couponsByPriceList;

		} catch (SQLException e) {
			throw new CouponSystemException("get all company's coupons by price failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}
	}

	@Override
	public List<Coupon> getAllCustomerCouponsByPrice(int customerID, double couponPrice) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COUPONS where coupon_price <= ? and id IN (select coupon_id from CUSTOMERS_VS_COUPONS where customer_id=?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setDouble(1, couponPrice);
			pstmt.setInt(2, customerID);
			ResultSet rs = pstmt.executeQuery();
			List<Coupon> couponsByPriceList = new ArrayList<>();
			while (rs.next()) {
				Coupon coupon = new Coupon(rs.getInt("id"));
				coupon.setCompanyId(rs.getInt("company_id"));
				coupon.setCategory(Category.fromInt(rs.getInt("category_id")));
				coupon.setCouponTitle(rs.getString("coupon_title"));
				coupon.setCouponDescription(rs.getString("coupon_description"));

				// convert java.sql.date to java.time.LocalDate:
				Date dateSqlCreation = rs.getDate("coupon_creation_date"); // java.sql.Date
				LocalDate localDateCreation = dateSqlCreation.toLocalDate(); // conversion
				coupon.setCouponCreationDate(localDateCreation);

				// convert java.sql.date to java.time.LocalDate:
				Date dateSqlExpiration = rs.getDate("coupon_expiration_date"); // java.sql.Date
				LocalDate localDateExpiration = dateSqlExpiration.toLocalDate(); // conversion
				coupon.setCouponExpirationDate(localDateExpiration);

				coupon.setCouponsAmount(rs.getInt("coupons_amount"));
				coupon.setCouponPrice(rs.getDouble("coupon_price"));
				coupon.setCouponImage(rs.getString("coupon_image"));
				couponsByPriceList.add(coupon);
			}
			return couponsByPriceList;

		} catch (SQLException e) {
			throw new CouponSystemException("get all customer's coupons by price failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}
	}

	@Override
	public List<Integer> getAllCompanyCouponsIds(int companyID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select id from COUPONS where company_id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			ResultSet rs = pstmt.executeQuery();
			List<Integer> couponsIdsList = new ArrayList<>();
			while (rs.next()) {
				couponsIdsList.add(rs.getInt("id"));
			}
			return couponsIdsList;

		} catch (SQLException e) {
			throw new CouponSystemException("get all company's coupons' IDs failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public Coupon getOneCoupon(int couponID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COUPONS where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, couponID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Coupon coupon = new Coupon(couponID);
				coupon.setCompanyId(rs.getInt("company_id"));
				coupon.setCategory(Category.fromInt(rs.getInt("category_id")));
				coupon.setCouponTitle(rs.getString("coupon_title"));
				coupon.setCouponDescription(rs.getString("coupon_description"));

				// convert java.sql.date to java.time.LocalDate:
				Date dateSqlCreation = rs.getDate("coupon_creation_date"); // java.sql.Date
				LocalDate localDateCreation = dateSqlCreation.toLocalDate(); // conversion
				coupon.setCouponCreationDate(localDateCreation);

				// convert java.sql.date to java.time.LocalDate:
				Date dateSqlExpiration = rs.getDate("coupon_expiration_date"); // java.sql.Date
				LocalDate localDateExpiration = dateSqlExpiration.toLocalDate(); // conversion
				coupon.setCouponExpirationDate(localDateExpiration);

				coupon.setCouponsAmount(rs.getInt("coupons_amount"));
				coupon.setCouponPrice(rs.getDouble("coupon_price"));
				coupon.setCouponImage(rs.getString("coupon_image"));
				return coupon;
			} else {
				throw new CouponSystemException("get coupon with id: " + couponID + " failed - was not found");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("get coupon with id: " + couponID + " failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}
	}

	@Override
	public void addCouponPurchase(int customerID, int couponID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "insert into CUSTOMERS_VS_COUPONS values(?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			pstmt.setInt(2, couponID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("saving purchase failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public boolean isCouponPurchase(int customerID, int couponID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from CUSTOMERS_VS_COUPONS where customer_id=? and coupon_id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			pstmt.setInt(2, couponID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			throw new CouponSystemException("find purchase failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public boolean couponAmountAndDate(int couponID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COUPONS where id=? and coupons_amount > 0 and coupon_expiration_date > now()";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, couponID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			throw new CouponSystemException("find coupon failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public void updateCouponAmount(int couponID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "update COUPONS set coupons_amount=coupons_amount-1 where id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, couponID);
			int rowCount = pstmt.executeUpdate();
			if (rowCount == 0) {
				throw new CouponSystemException(
						"update coupon " + couponID + " failed because it is not in the database");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("update coupon amount failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public void deleteCouponPurchase(int customerID, int couponID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "delete from CUSTOMERS_VS_COUPONS where customer_id = ? and coupon_id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			pstmt.setInt(2, couponID);
			int rowCount = pstmt.executeUpdate();
			if (rowCount == 0) {
				throw new CouponSystemException("delete customer with id: " + customerID + " and coupon with id: "
						+ couponID + " failed because it is not in the database");
			}

		} catch (SQLException e) {
			throw new CouponSystemException(
					"delete customer with id: " + customerID + " and coupon with id: " + couponID + " failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public void deleteCouponPurchaseByCompanyID(int companyID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "delete from CUSTOMERS_VS_COUPONS where coupon_id in (select id from COUPONS where company_id=?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			int rowCount = pstmt.executeUpdate();
			if (rowCount == 0) {
				throw new CouponSystemException("delete CouponPurchase belongs to company #: " + companyID
						+ " failed because no coupons found ");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("delete CouponPurchase of companyID #: " + companyID + " failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}
	}

	@Override
	public void deleteCouponPurchaseByCustomerID(int customerID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "delete from CUSTOMERS_VS_COUPONS where customer_id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			int rowCount = pstmt.executeUpdate();
			if (rowCount == 0) {
				throw new CouponSystemException("delete CouponPurchase belongs to customer with ID: " + customerID
						+ " failed because no coupons found ");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("delete CouponPurchase of customer with ID: " + customerID + " failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}
	}

	@Override
	public void deleteCouponPurchaseByCouponID(int couponID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "delete from CUSTOMERS_VS_COUPONS where coupon_id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, couponID);
			pstmt.executeUpdate();			
		} catch (SQLException e) {
			throw new CouponSystemException("delete CouponPurchase of coupon with ID: " + couponID + " failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}
	}

	@Override
	public void deleteCouponPurchaseByCouponExpirationDate() throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "delete from CUSTOMERS_VS_COUPONS where coupon_id in (select id from COUPONS where coupon_expiration_date < now())";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);		
		} catch (SQLException e) {
			throw new CouponSystemException("delete CouponPurchase failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}
	}

	@Override
	public void deleteCouponByCouponExpirationDate() throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "delete from COUPONS where coupon_expiration_date < now()";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new CouponSystemException("delete Coupon failed", e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}
	}

}
