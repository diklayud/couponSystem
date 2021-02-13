package dao;

import java.util.List;

import exceptions.CouponSystemException;
import javaBeans.Company;

/**
 * contains CRUD methods to communicate to the DB
 */
public interface CompaniesDao {

	/**
	 * checks if company exists in the DB by parameters:
	 * 
	 * @param email
	 * @param password
	 * @return true if exist, false if not
	 */
	boolean isCompanyExists(String email, String password) throws CouponSystemException;

	/**
	 * checks if company exists in the DB by parameters:
	 * 
	 * @param email
	 * @param name
	 * @return true if exist, false if not
	 * @throws CouponSystemException
	 */
	boolean isCompanyExistsByNameEmail(String name, String email) throws CouponSystemException;
	
	/**
	 * checks if company exists in the DB by parameters:
	 * 
	 * @param id
	 * @param name
	 * @return true if exist, false if not
	 * @throws CouponSystemException
	 */
	boolean isCompanyExistsByIdName(int id, String name) throws CouponSystemException;

	/**
	 * checks if company exists in the DB by parameter:
	 * 
	 * @param email
	 * @return
	 * @throws CouponSystemException
	 */
	boolean isCompanyEmailExists(String email) throws CouponSystemException;
		
	/**
	 * add a company to the DB
	 * 
	 * @param company
	 * @throws CouponSystemException
	 */
	void addCompany(Company company) throws CouponSystemException;

	/**
	 * updates company's details by company instance in storage or throws an
	 * exception if the company is not in the DB
	 * 
	 * @param company
	 * @throws CouponSystemException if the specified company not found
	 */
	void updateCompany(Company company) throws CouponSystemException;

	/**
	 * delete company from the DB by companyID
	 * 
	 * @param companyID
	 * @throws CouponSystemException if the company of the specified id not found
	 */
	void deleteCompany(int companyID) throws CouponSystemException;

	/**
	 * get all companies from DB
	 * 
	 * @return list of companies
	 * @throws CouponSystemException
	 */
	List<Company> getAllCompanies() throws CouponSystemException;

	/**
	 * get a company from DB
	 * 
	 * @param companyID
	 * @return company instance
	 * @throws CouponSystemException if the company of the specified id not found
	 */
	Company getOneCompany(int companyID) throws CouponSystemException;

	/**
	 * get a company from DB
	 * 
	 * @param email
	 * @param password
	 * @return company instance
	 * @throws CouponSystemException
	 */
	Company getCompanyByEmailAndPassword(String email, String password) throws CouponSystemException;
	
	/**
	 * get company id if company exists in the DB by parameters below, otherwise return -1
	 * 
	 * @param email
	 * @param password
	 * @return company id 
	 * @throws CouponSystemException
	 */
	int getIdOfCompanyByEmailPassword(String email, String password) throws CouponSystemException;


	


	
}
