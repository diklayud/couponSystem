package testsDAO;

import java.sql.Connection;

import dataBase.ConnectionPool;
import exceptions.CouponSystemException;

public class TestPool {

	public static void main(String[] args) throws InterruptedException {
		
		try {
			System.out.println("get connection");
			Connection con1 = ConnectionPool.getInstance().getConnection();
			Connection con2 = ConnectionPool.getInstance().getConnection();
			Thread.sleep(3000);
			System.out.println("restore connection");
			ConnectionPool.getInstance().restoreConnection(con1);
			ConnectionPool.getInstance().restoreConnection(con2);
			System.out.println("Connectios restored");
			
		} catch (CouponSystemException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}finally {
			try {
				System.out.println("close all connection");
				ConnectionPool.getInstance().closeAllConnection();
			} catch (CouponSystemException e) {
				e.printStackTrace();
			}
		}

	}

}
