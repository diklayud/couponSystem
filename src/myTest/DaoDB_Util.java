package myTest;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import dataBase.ConnectionPool;
import exceptions.CouponSystemException;

public class DaoDB_Util implements DaoUtil {

	@Override
	public void deleteAllTableRows() throws CouponSystemException {		
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			
			String sql1 = "delete from COMPANIES";
			String sql2 = "delete from COUPONS";
			String sql3 = "delete from CUSTOMERS";
			String sql4 = "delete from CUSTOMERS_VS_COUPONS";
			
			Statement stmt = con.createStatement();			
			stmt.addBatch(sql1);
			stmt.addBatch(sql2);
			stmt.addBatch(sql3);
			stmt.addBatch(sql4);
			stmt.executeBatch();

		} catch (SQLException e) {
			throw new CouponSystemException("delete all rows from tables failed. " + e.getMessage(), e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}
	}

	

	@Override
	public void insertAllTableRows() throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			
			String sql1 = "INSERT INTO COMPANIES VALUES (2,'Dikla','dikla@mail','157'),"
				                                     + "(1,'Walla','walla@DY','111')";
			
			String sql2 = "INSERT INTO COUPONS VALUES (1,1,1,'Dominus','Buy 1 get 2 pizza','2021-01-22','2021-02-22',10,9.6,'path.to.dominus.image'),"
			                           + "(2,1,2,'BigMac','Big America for Free','2021-01-23','2021-02-23',0,17.7,'path.to.mac.image'),"
			                           + "(3,2,3,'iPhone','Get charger for free','2021-01-23','2021-02-23',5,259.7,'path.to.apple.image'),"			
			                           + "(4,2,4,'Vietnam','flight 20% off','2021-01-23','2021-02-23',8,299.99,'path.to.vietnam.image');";			
			
			String sql3  = "INSERT INTO CUSTOMERS VALUES (2,'Israel','Israeli','iloveisrael@mail','123'),"
													  + "(3,'Bar','Kokuvski','bark@mail','333');";
			
			String sql4  = "INSERT INTO CUSTOMERS_VS_COUPONS VALUES (2,3),(2,1),(3,2),(3,1)";					  
						
			Statement stmt = con.createStatement();			
			stmt.addBatch(sql1);
			stmt.addBatch(sql2);
			stmt.addBatch(sql3);
			stmt.addBatch(sql4);
			stmt.executeBatch();

		} catch (SQLException e) {
			throw new CouponSystemException("Error In Insert data: " + e.getMessage() , e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}	
	}

}
