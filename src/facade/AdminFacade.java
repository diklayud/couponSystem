package facade;

import java.util.List;

import dao.CompaniesDaoDB;
import dao.CouponsDaoDB;
import dao.CustomerDaoDB;
import exceptions.CouponSystemException;
import javaBeans.Company;
import javaBeans.Customer;

public class AdminFacade extends ClientFacade {

	// CTOR
	public AdminFacade() {
		this.companiesDao = new CompaniesDaoDB();
		this.customerDao = new CustomerDaoDB();
		this.couponsDao = new CouponsDaoDB();
	}

	// METHOD FROM SUPERCLASS
	@Override
	public boolean login(String email, String password) {
		if (email.equals("admin@admin.com") && password.equals("admin")) {
			return true;
		} else {
			return false;
		}
	}

	// METHODS

	/**
	 * add Company if not exist company with same name or email in DB
	 * 
	 * @param company
	 * @throws CouponSystemException
	 */
	public void addCompany(Company company) throws CouponSystemException {

		boolean exist = this.companiesDao.isCompanyExistsByNameEmail(company.getName(), company.getEmail());
		if (exist == false) {
			this.companiesDao.addCompany(company);
		} else {
			throw new CouponSystemException("adding company with name: " + company.getName() + " with email: "
					+ company.getEmail() + " failed - company already exist in database");
		}

	}

	/**
	 * update company details except to company id or company name
	 * 
	 * @param company
	 * @throws CouponSystemException
	 */
	public void updateCompany(Company company) throws CouponSystemException {
		try {
			// get the company from the database
			Company companyFromDbToUpdate = companiesDao.getOneCompany(company.getId());
			
			// check if email not exist in DB
			boolean exist = this.companiesDao.isCompanyEmailExists(company.getEmail());
			
			if (companyFromDbToUpdate.getEmail().equals(company.getEmail()) || exist == false) {
				
				// update the permitted fields
				companyFromDbToUpdate.setEmail(company.getEmail());
				companyFromDbToUpdate.setPassword(company.getPassword());
				// send the updated instance to the db
				companiesDao.updateCompany(companyFromDbToUpdate);
			} else {
				throw new CouponSystemException(
						"update company with id: " + company.getId() + " failed - company email already exist");
			}

		} catch (CouponSystemException e) {
			throw new CouponSystemException(e.getMessage());
		}
	}

	/**
	 * delete company, it's coupons and it's coupons purchases
	 * 
	 * @param companyID
	 * @throws CouponSystemException
	 */
	public void deleteCompanyAndCompanyCoupons(int companyID) throws CouponSystemException {
		this.couponsDao.deleteCouponPurchaseByCompanyID(companyID);
		this.couponsDao.deleteCouponByCompany(companyID);
		this.companiesDao.deleteCompany(companyID);

	}

	/**
	 * @return list of companies
	 * @throws CouponSystemException
	 */
	public List<Company> getAllCompanies() throws CouponSystemException {
		return companiesDao.getAllCompanies();
	}

	/**
	 * @param companyID
	 * @return company by its ID
	 * @throws CouponSystemException
	 */
	public Company getOneCompany(int companyID) throws CouponSystemException {
		return companiesDao.getOneCompany(companyID);
	}
	
	/**
	 * add customer if not exist customer with same email in DB
	 * 
	 * @param customer
	 * @throws CouponSystemException
	 */
	public void addCustomer(Customer customer) throws CouponSystemException {
		boolean exist = this.customerDao.isCustomerEmailExists(customer.getEmail());
		if (exist == false) {
			this.customerDao.addCustomer(customer);
		} else {
			throw new CouponSystemException(
					"adding customer with email: " + customer.getEmail() + " failed - email already exist in DB");
		}
	}

	/**
	 * update customer details except to customer id
	 * 
	 * @param company
	 * @throws CouponSystemException
	 */
	public void updateCustomer(Customer customer) throws CouponSystemException {
		try {
			// check if email not exist in DB
			boolean exist = this.customerDao.isCustomerEmailExists(customer.getEmail());
			if (exist == false) {
				// get the customer from the database
				Customer customerFromDb = customerDao.getOneCustomer(customer.getId());
				// update the permitted fields
				customerFromDb.setFirstName(customer.getFirstName());
				customerFromDb.setLastName(customer.getLastName());
				customerFromDb.setEmail(customer.getEmail());
				customerFromDb.setPassword(customer.getPassword());
				// send the updated instance to the db
				customerDao.updateCustomer(customerFromDb);
			} else {
				throw new CouponSystemException(
						"update customer with id: " + customer.getId() + " failed - customer email already exist");
			}

		} catch (CouponSystemException e) {
			throw new CouponSystemException(e.getMessage());
		}
	}
	
	/**
	 * delete customer and it's coupons purchases
	 * 
	 * @param customerID
	 * @throws CouponSystemException
	 */
	public void deleteCustomer(int customerID) throws CouponSystemException {
		couponsDao.deleteCouponPurchaseByCustomerID(customerID);
		customerDao.deleteCustomer(customerID);
	}
	
	/**
	 * @return list of Customers
	 * @throws CouponSystemException
	 */
	public List<Customer> getAllCustomers() throws CouponSystemException{
		return customerDao.getAllCustomers();
	}
	
	
	/**
	 * @param customerID
	 * @return customer by its ID
	 * @throws CouponSystemException
	 */
	public Customer getOneCustomer(int customerID) throws CouponSystemException {
		return customerDao.getOneCustomer(customerID);
	}
	
	
}
