package dao;

import java.util.List;

import exceptions.CouponSystemException;
import javaBeans.Customer;

/**
 * contains CRUD methods to communicate to the DB
 */
public interface CustomerDao {

	/**
	 * checks if customer exists in the DB by parameters:
	 * 
	 * @param email
	 * @param password
	 * @return true if exist, false if not
	 */
	boolean isCustomerExists(String email, String password) throws CouponSystemException;

	/**
	 * checks if customer exists in the DB by parameters:
	 * 
	 * @param email
	 * @return true if exist, false if not
	 */
	boolean isCustomerEmailExists(String email) throws CouponSystemException;
	
	/**
	 * add a customer to the DB
	 * 
	 * @param customer
	 * @throws CouponSystemException
	 */
	void addCustomer(Customer customer) throws CouponSystemException;

	/**
	 * updates customer's details by customer instance in storage or throws an
	 * exception if the customer is not in the DB
	 * 
	 * @param customer
	 * @throws CouponSystemException if the specified customer not found
	 */
	void updateCustomer(Customer customer) throws CouponSystemException;

	/**
	 * delete customer from the DB by customerID
	 * 
	 * @param customerID
	 * @throws CouponSystemException if the customer of the specified id not found
	 */
	void deleteCustomer(int customerID) throws CouponSystemException;

	/**
	 * get all customers from DB
	 * 
	 * @return list of customers
	 * @throws CouponSystemException
	 */
	List<Customer> getAllCustomers() throws CouponSystemException;

	/**
	 * get a customer from DB
	 * 
	 * @param customerID
	 * @return customer instance
	 * @throws CouponSystemException if the customer of the specified id not found
	 */
	Customer getOneCustomer(int customerID) throws CouponSystemException;

	/**
	 * get customer id if customer exists in the DB by parameters below, otherwise return -1
	 * 
	 * @param email
	 * @param password
	 * @return customer id
	 * @throws CouponSystemException
	 */
	int getIdOfCustomerByEmailPassword(String email, String password) throws CouponSystemException;
}
