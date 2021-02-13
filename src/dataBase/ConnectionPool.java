package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import exceptions.CouponSystemException;

public class ConnectionPool {

	/**
	 * put here the url to connect to the database
	 */	
	private String url = "jdbc:mysql://localhost:3306/coupon_system?serverTimezone=Israel";

	/**
	 * put here the user name to connect to the database
	 */
	private String user = "dikla";
	
	/**
	 * put here the password to connect to the database
	 */
	private String password = "1994";

	/**
	 * this set will contain the connections
	 */
	private Set<Connection> connections = new HashSet<>();
	
	/**
	 * max size of the pool
	 */
	public static final int MAX_CONNECTIONS = 5;

	private boolean poolOpen;

	// ===== singleton =====
	private static ConnectionPool instance;

	private ConnectionPool() throws CouponSystemException {
		try {
			// fill the pool with connections
			for (int i = 0; i < MAX_CONNECTIONS; i++) {
				Connection con = DriverManager.getConnection(url, user, password);
				connections.add(con);
			}
			poolOpen = true;
			System.out.println("connection pool up");
		} catch (SQLException e) {
			throw new CouponSystemException("ConnectionPool initialization failed", e);
		}
	}

	/**
	 * if the ConnectionPool instance is null, will initialize the instance.
	 * 
	 * @return the instance of the connection pool
	 * @throws CouponSystemException
	 */
	public static ConnectionPool getInstance() throws CouponSystemException {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}
	// =====================

	/**
	 * Remove a connection from the connection pool set.
	 * waits if the set is empty.
	 * 
	 * @return the removed connection
	 * @throws CouponSystemException
	 */
	public synchronized Connection getConnection() throws CouponSystemException {
		if (!poolOpen) {
			throw new CouponSystemException("get connection failed - the pool is closed");
		} else {
			while (this.connections.isEmpty()) {
				try {
					wait();
				} catch (InterruptedException e) {
					throw new CouponSystemException("get connection failed - wait was interruped", e);
				}
			}
			Iterator<Connection> it = this.connections.iterator();
			Connection con = it.next();
			it.remove();
			return con;
		}
	}

	/**
	 * Restores the used connection back to the collection pool set.
	 * notifies when a connection restored.
	 * 
	 * @param connection
	 */
	public synchronized void restoreConnection(Connection connection) {
		connections.add(connection);
		notify();
	}

	/**
	 * Close all the open connections.
	 * waits until all the connections are in the set.
	 */
	public synchronized void closeAllConnection() throws CouponSystemException {
		poolOpen = false;
		while (connections.size() < MAX_CONNECTIONS) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new CouponSystemException("close all connections failed - wait was interruped", e);
			}
		}
		Iterator<Connection> it = this.connections.iterator();
		while (it.hasNext()) {
			Connection con = it.next();
			try {
				con.close();
				it.remove();
			} catch (SQLException e) {
				throw new CouponSystemException("close all connections failed - couldn't close the connection", e);
			}
		}
		System.out.println("connection pool down");
	}
}
