package dao;

import java.util.List;

import exceptions.CouponSystemException;
import javaBeans.Category;
import javaBeans.Coupon;

/**
 * contains CRUD methods to communicate to the DB
 */
public interface CouponsDao {
	
	/**
	 * add a coupon to the DB
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 */
	void addCoupon(Coupon coupon) throws CouponSystemException;

	/**
	 * updates coupon's details by coupon instance in storage or throws an exception
	 * if the coupon is not in the DB
	 * 
	 * @param coupon
	 * @throws CouponSystemException if the specified coupon not found
	 */
	void updateCoupon(Coupon coupon) throws CouponSystemException;

	/**
	 * delete coupon from the DB by couponID
	 * 
	 * @param couponID
	 * @throws CouponSystemException if the coupon of the specified id not found
	 */
	void deleteCoupon(int couponID) throws CouponSystemException;

	/**
	 * delete coupon from the DB by companyID
	 * 
	 * @param companyID
	 * @throws CouponSystemException
	 */
	void deleteCouponByCompany(int companyID) throws CouponSystemException;

	/**
	 * get all coupons of certain company from DB
	 * 
	 * @return list of coupons of all companies
	 * @throws CouponSystemException
	 */
	List<Coupon> getAllCoupons() throws CouponSystemException;

	/**
	 * get all coupons of specified company from DB
	 * 
	 * @param companyID
	 * @return list of coupons of certain company
	 * @throws CouponSystemException
	 */
	List<Coupon> getAllCompanyCoupons(int companyID) throws CouponSystemException;

	/**
	 * get all coupons of specified customer from DB
	 * 
	 * @param customerID
	 * @return list of coupons of certain customer
	 * @throws CouponSystemException
	 */
	List<Coupon> getAllCustomerCoupons(int customerID) throws CouponSystemException;
	
	/**
	 * get all coupons of certain company and of certain coupon's category from DB
	 * 
	 * @param companyID
	 * @param categoryID
	 * @return list of coupons of certain company and coupon's category
	 * @throws CouponSystemException
	 */
	List<Coupon> getAllCompanyCouponsByCategory(int companyID, Category categoryID) throws CouponSystemException;

	/**
	 * get all coupons of certain customer and of certain coupon's category from DB
	 * 
	 * @param customerID
	 * @param categoryID
	 * @return list of coupons of certain customer and coupon's category
	 * @throws CouponSystemException
	 */
	List<Coupon> getAllCustomerCouponsByCategory(int customerID, Category categoryID) throws CouponSystemException;
	
	/**
	 * get all coupons of certain company below or equals to maximum price
	 * 
	 * @param companyID
	 * @param couponPrice
	 * @return list of coupons of certain company below or equals to maximum price
	 * @throws CouponSystemException
	 */
	List<Coupon> getAllCompanyCouponsByPrice(int companyID, double couponPrice) throws CouponSystemException;

	/**
	 * get all coupons of certain customer below to maximum price
	 * 
	 * @param customerID
	 * @param couponPrice
	 * @return list of coupons of certain customer below to maximum price
	 * @throws CouponSystemException
	 */
	List<Coupon> getAllCustomerCouponsByPrice(int customerID, double couponPrice) throws CouponSystemException;
	
	/**
	 * get all coupons IDs of certain company from DB by companyID
	 * 
	 * @param companyID
	 * @return list of coupons IDs of certain company
	 * @throws CouponSystemException
	 */
	List<Integer> getAllCompanyCouponsIds(int companyID) throws CouponSystemException;

	/**
	 * get a coupon from DB
	 * 
	 * @param couponID
	 * @return coupon instance
	 * @throws CouponSystemException if the coupon of the specified id not found
	 */
	Coupon getOneCoupon(int couponID) throws CouponSystemException;

	/**
	 * add entry/record of purchasing. purchasing is unique combination between
	 * customer and coupon
	 * 
	 * @param customerID
	 * @param couponID
	 * @throws CouponSystemException
	 */
	void addCouponPurchase(int customerID, int couponID) throws CouponSystemException;

	/**
	 * delete entry/record of purchasing. purchasing is unique combination between
	 * customer and coupon
	 * 
	 * @param customerID
	 * @param couponID
	 * @throws CouponSystemException
	 */
	void deleteCouponPurchase(int customerID, int couponID) throws CouponSystemException;

	/**
	 * delete entry/record of purchasing by couponID using companyID
	 * 
	 * @param companyID
	 * @throws CouponSystemException
	 */
	void deleteCouponPurchaseByCompanyID(int companyID) throws CouponSystemException;

	/**
	 * delete entry/record of purchasing by customerID
	 * 
	 * @param customerID
	 * @throws CouponSystemException
	 */
	void deleteCouponPurchaseByCustomerID(int customerID) throws CouponSystemException;

	/**
	 * delete entry/record of purchasing by couponID
	 * 
	 * @param couponID
	 * @throws CouponSystemException
	 */
	void deleteCouponPurchaseByCouponID(int couponID) throws CouponSystemException;

	/**
	 * checks if coupon exists in the DB by parameters:
	 * 
	 * @param companyId
	 * @param couponTitle
	 * @return true if coupon's title is already exist under a specified company,
	 *         false if not
	 * @throws CouponSystemException
	 */
	boolean isCouponTitleExist(int companyId, String couponTitle) throws CouponSystemException;

	/**
	 * checks in the DB if coupon was already bought by the specified customer
	 * 
	 * @param customerID
	 * @param couponID
	 * @return true if coupon already bought by the customer, false if not
	 * @throws CouponSystemException
	 */
	boolean isCouponPurchase(int customerID, int couponID) throws CouponSystemException;

	/**
	 * check the amount and the expiration date of the specified coupon
	 * 
	 * @param couponID
	 * @return true if the amount of the coupon is more than ZERO and the expiration
	 *         date didn't pass yet. false if one of them doesn't exist
	 * @throws CouponSystemException
	 */
	boolean couponAmountAndDate(int couponID) throws CouponSystemException;
	
	/**
	 * update the amount of the coupon in the DB
	 * 
	 * @param couponID
	 * @throws CouponSystemException
	 */
	void updateCouponAmount(int couponID) throws CouponSystemException;
	
	/**
	 * delete entry/record of purchasing by coupon expiration date
	 * 
	 * @throws CouponSystemException
	 */
	void deleteCouponPurchaseByCouponExpirationDate() throws CouponSystemException;
	
	/**
	 * delete entry/record of coupon by coupon expiration date
	 * 
	 * @throws CouponSystemException
	 */
	void deleteCouponByCouponExpirationDate() throws CouponSystemException;
	
}