package util;

import exceptions.CouponSystemException;

public interface DaoUtil {

	/**
	 * delete all rows in table
	 * @throws CouponSystemException
	 */
	void deleteAllTableRows() throws CouponSystemException;
	
	/**
	 * initialize DB with initial data
	 * @throws CouponSystemException
	 */
	void insertAllTableRows() throws CouponSystemException;
	
}
