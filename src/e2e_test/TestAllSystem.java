package e2e_test;

import java.time.LocalDate;
import java.util.List;

import dailyJob.CouponExpirationDailyJob;
import dataBase.ConnectionPool;
import exceptions.CouponSystemException;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;
import javaBeans.Category;
import javaBeans.Company;
import javaBeans.Coupon;
import javaBeans.Customer;
import loginManager.ClientType;
import loginManager.LoginManager;
import util.DaoDB_Util;

public class TestAllSystem {

	public static void testAll() throws CouponSystemException {

//		CouponExpirationDailyJob dailyJob = new CouponExpirationDailyJob();
//		Thread dailyJobThread = new Thread(dailyJob, "dailyJobThread");

		try {
			// start the system ==========
			ConnectionPool.getInstance(); // will start the connection pool

//			dailyJobThread.start(); // start the dailyJob thread

			// ======== Before starting we prepare DB with some Data =============
			DaoDB_Util clientDB = new DaoDB_Util();
			clientDB.deleteAllTableRows();
			clientDB.insertAllTableRows();

//			 -=-=-=-=-=- use all facade methods to display its available operations -=-=-=-=-=-
			// LoginManager.getInstance().login(null, null, null); // need to give the data:
			// email, password, clientType

// ========================== Test Admin Facade =========================================
			// Login as administrator -> get a facade using the LoginManager:
			AdminFacade adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admincom", "admin",
					ClientType.ADMINISTRATOR);
			if (adminFacade != null) {
				System.out.println("Login with wrong credentials should not work!!!!");
			}

			adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin",
					ClientType.ADMINISTRATOR);

			// 1. Test Get All Companies in the system
			List<Company> allComp = adminFacade.getAllCompanies();
			if (allComp.size() != 2) {
				System.out.println("Test getAllCompanies Failed!!!!!");
			}
			if (allComp.get(0).getId() != 1 || !allComp.get(0).getName().equals("Walla")) {
				System.out.println("Test getAllCompanies Pos 0 Failed!!!!!");
			}
			if (allComp.get(1).getId() != 2 || !allComp.get(1).getName().equals("Dikla")) {
				System.out.println("Test getAllCompanies Pos 1 Failed!!!!!");
			}
			// ---------------------------------------------------------------------

			// 2. Test Add Company
			Company companyToAdd = new Company(0, "Fatal", "order@fatal", "3443");
			adminFacade.addCompany(companyToAdd);
			List<Company> allCompAfterAdd = adminFacade.getAllCompanies();
			if (allCompAfterAdd.size() != 3) {
				System.out.println("Test addCompany Failed!!!!!");
			}

			{
				// 3. Add Same Company again - should raise Error
				try {
					adminFacade.addCompany(companyToAdd);
				} catch (CouponSystemException e) {
					if (!e.getMessage().equals(
							"adding company with name: Fatal with email: order@fatal failed - company already exist in database")) {
						System.out.println("Test addCompany With Same values not raised expected Error!!!!!");
					}
					allCompAfterAdd = adminFacade.getAllCompanies();
					if (allCompAfterAdd.size() != 3) {
						System.out.println("Test addCompany with same values Not Failed as expected!!!!!");
					}
				}
			}

			{
				// 4. Add Company with same name and different mail - should raise Error
				try {
					companyToAdd.setEmail("newmail@mail");
					adminFacade.addCompany(companyToAdd);
				} catch (CouponSystemException e) {
					if (!e.getMessage().equals(
							"adding company with name: Fatal with email: newmail@mail failed - company already exist in database")) {
						System.out.println("Test addCompany With Same values not raised expected Error!!!!!");
					}
					allCompAfterAdd = adminFacade.getAllCompanies();
					if (allCompAfterAdd.size() != 3) {
						System.out.println("Test addCompany with same values Not Failed as expected!!!!!");
					}
				}
			}

			{
				// 5. Add Company with same (old) mail and different name - should raise Error
				try {
					companyToAdd.setName("Isrotel");
					companyToAdd.setEmail("order@fatal");
					adminFacade.addCompany(companyToAdd);
				} catch (CouponSystemException e) {
					if (!e.getMessage().equals(
							"adding company with name: Isrotel with email: order@fatal failed - company already exist in database")) {
						System.out.println("Test addCompany With Same values not raised expected Error!!!!!");
					}
					allCompAfterAdd = adminFacade.getAllCompanies();
					if (allCompAfterAdd.size() != 3) {
						System.out.println("Test addCompany with same values Not Failed as expected!!!!!");
					}
				}
			}

			// 6. Test Get CompanyById and Update Company Password
			Company companyToUpdate = adminFacade.getOneCompany(1);
			companyToUpdate.setPassword("newPassword");
			adminFacade.updateCompany(companyToUpdate);
			Company companyAfterUpdate = adminFacade.getOneCompany(1);
			if (!companyAfterUpdate.getPassword().equals("newPassword")) {
				System.out.println("Test GetOne & Upadate password Failed!!!!!");
			}

			// 7. Test Get CompanyById and Update Company mail
			Company companyToUpdateMail = adminFacade.getOneCompany(1);
			companyToUpdateMail.setEmail("mail@new");
			adminFacade.updateCompany(companyToUpdateMail);
			Company companyAfterUpdateMail = adminFacade.getOneCompany(1);
			if (!companyAfterUpdateMail.getEmail().equals("mail@new")) {
				System.out.println("Test GetOne & Upadate E-mail Failed!!!!!");
			}

			{
				// 8. Test Get CompanyById and Update Company mail to the same mail
				companyToUpdateMail = adminFacade.getOneCompany(1);
				adminFacade.updateCompany(companyToUpdateMail);
				if (!companyAfterUpdateMail.getEmail().equals("mail@new")) {
					System.out.println("Test GetOne & Upadate E-mail Failed!!!!!");
				}
			}

			{
				// 9. Test Get CompanyById and Update Company mail to existing mail - should
				// raise Error
				try {
					companyToUpdateMail = adminFacade.getOneCompany(1);
					companyToUpdateMail.setEmail("dikla@mail");
					adminFacade.updateCompany(companyToUpdateMail);
				} catch (CouponSystemException e) {
					if (!e.getMessage().equals("update company with id: " + companyToUpdateMail.getId()
							+ " failed - company email already exist")) {
						System.out.println("Test updateCompany With Same E-mail not raised expected Error!!!!!");
					}
				}
			}

			// 10. Test Get CompanyById and Update Company name to existing name - expecting
			// to no changes
			Company companyToUpdateName = adminFacade.getOneCompany(2);
			companyToUpdateName.setName("Walla");
			adminFacade.updateCompany(companyToUpdateName);
			Company companyAfterUpdateName = adminFacade.getOneCompany(2);
			if (companyAfterUpdateName.getName().equals("Walla")) {
				System.out.println("Test GetOne & Upadate Name Failed!!!!!");
			}

			// 11. Test Get CompanyById and Update Company name to new name - expecting to
			// no changes
			companyToUpdateName = adminFacade.getOneCompany(2);
			companyToUpdateName.setName("New Name");
			adminFacade.updateCompany(companyToUpdateName);
			companyAfterUpdateName = adminFacade.getOneCompany(2);
			if (companyAfterUpdateName.getName().equals("New Name")) {
				System.out.println("Test GetOne & Upadate Name Failed!!!!!");
			}

			{
				// 12. Test Get CompanyById and Update Company id to a new id - should raise
				// Error
				Company companyToUpdateID = adminFacade.getOneCompany(1);
				try {
					companyToUpdateID.setId(1000);
					adminFacade.updateCompany(companyToUpdateID);
				} catch (CouponSystemException e) {
					if (!e.getMessage()
							.equals("get company with id: " + companyToUpdateID.getId() + " failed - was not found")) {
						System.out.println("Test updateCompanyID not raised expected Error!!!!!");
					}
				}
			}

			// 13. Test Get All Customers in the system
			List<Customer> allCust = adminFacade.getAllCustomers();
			if (allCust.size() != 2) {
				System.out.println("Test getAllCustomers Failed!!!!!");
			}
			if (allCust.get(0).getId() != 2 || !allCust.get(0).getFirstName().equals("Israel")) {
				System.out.println("Test getAllCustomers Pos 0 Failed!!!!!");
			}
			if (allCust.get(1).getId() != 3 || !allCust.get(1).getFirstName().equals("Bar")) {
				System.out.println("Test getAllCustomers Pos 1 Failed!!!!!");
			}

			// 14. Test Add Customer
			Customer customerToAdd = new Customer(0, "Rani", "Fatal", "raniz@mail", "777");
			adminFacade.addCustomer(customerToAdd);
			List<Customer> allCustAfterAdd = adminFacade.getAllCustomers();
			if (allCustAfterAdd.size() != 3) {
				System.out.println("Test addCustomer Failed!!!!!");
			}

			{
				// 15. Add Customer with Existing E-mail - should raise Error
				try {
					Customer customerToAddExistingMail = new Customer(0, "Ran", "Cohen", "raniz@mail", "789");
					adminFacade.addCustomer(customerToAddExistingMail);
				} catch (CouponSystemException e) {
					if (!e.getMessage()
							.equals("adding customer with email: raniz@mail failed - email already exist in DB")) {

						System.out.println("Test addCustomer With Existing E-mail not raised expected Error!!!!!");
					}
					allCustAfterAdd = adminFacade.getAllCustomers();
					if (allCompAfterAdd.size() != 3) {
						System.out.println("Test addCustomer with Existing E-mail Not Failed as expected!!!!!");
					}
				}
			}

			{
				// 16. Test Get CustomerById and Update Customer mail to existing mail - should
				// raise Error
				Customer customerToUpdateMail = adminFacade.getOneCustomer(3);
				try {
					customerToUpdateMail.setEmail("raniz@mail");
					adminFacade.updateCustomer(customerToUpdateMail);
				} catch (CouponSystemException e) {
					if (!e.getMessage().equals("update customer with id: " + customerToUpdateMail.getId()
							+ " failed - customer email already exist")) {

						System.out.println("Test updateCustomer With Existing E-mail not raised expected Error!!!!!");
					}
				}
			}

			// 17. Test Get CustomerById and Update Customer mail to new mail
			Customer customerToUpdateMail = adminFacade.getOneCustomer(3);
			customerToUpdateMail.setEmail("new@mail");
			adminFacade.updateCustomer(customerToUpdateMail);
			Customer customerAfterUpdateMail = adminFacade.getOneCustomer(3);
			if (!customerAfterUpdateMail.getEmail().equals("new@mail")) {
				System.out.println("Test GetOne & Upadate mail Failed!!!!!");
			}

// =================================== Test Company Facade =========================================

			// Login as company -> get a facade using the LoginManager:
			CompanyFacade companyFacade = (CompanyFacade) LoginManager.getInstance().login("mail@neww", "newPassword",
					ClientType.COMPANY);
			if (companyFacade != null) {
				System.out.println("Login with wrong credentials should not work!!!!");
			}

			companyFacade = (CompanyFacade) LoginManager.getInstance().login("mail@new", "newPassword",
					ClientType.COMPANY);

			// 1. Test Get All Coupons of the logged in company
			List<Coupon> allCoupons = companyFacade.getAllCompanyCoupons();
			if (allCoupons.size() != 2) {
				System.out.println("Test getAllCompanyCoupons Failed!!!!!");
			}
			if (allCoupons.get(0).getId() != 1 || allCoupons.get(0).getCompanyId() != 1) {
				System.out.println("Test getAllCompanies Pos 0 Failed!!!!!");
			}
			if (allCoupons.get(1).getId() != 2 || !allCoupons.get(1).getCouponTitle().equals("BigMac")) {
				System.out.println("Test getAllCompanies Pos 1 Failed!!!!!");
			}

			// 2. Test add coupon
			Coupon couponToAdd = new Coupon(0, 1, Category.VACATION, "Spain", "Canary Islands in half price!",
					LocalDate.now(), LocalDate.now().plusDays(3), 3, 300, "path.spain.canary");
			companyFacade.addCoupon(couponToAdd);
			List<Coupon> allCouponsAfterAdd = companyFacade.getAllCompanyCoupons();
			if (allCouponsAfterAdd.size() != 3) {
				System.out.println("Test addCoupon Failed!!!!!");
			}

			// 3. Test add coupon with title which exist under other company
			couponToAdd = new Coupon(0, 1, Category.ELECTRICITY, "iPhone", "50$ off!", LocalDate.now(),
					LocalDate.now().plusDays(3), 3, 500, "path.IPhone");
			companyFacade.addCoupon(couponToAdd);
			allCouponsAfterAdd = companyFacade.getAllCompanyCoupons();
			if (allCouponsAfterAdd.size() != 4) {
				System.out.println("Test addCoupon Failed!!!!!");
			}

			{
				// 4. Test add coupon with title which exist under this company - should raise
				// Error
				Coupon couponToAddSameTitle = new Coupon(0, 1, Category.VACATION, "Spain", "Barcelona - one day free",
						LocalDate.now(), LocalDate.now().plusDays(3), 5, 350, "path.spain.barcelona");
				try {
					companyFacade.addCoupon(couponToAddSameTitle);
				} catch (CouponSystemException e) {
					if (!e.getMessage()
							.equals("add Coupon failed- coupon title: " + couponToAddSameTitle.getCouponTitle()
									+ " already exist under company id: " + couponToAddSameTitle.getCompanyId())) {

						System.out.println("Test addCoupon With Same title not raised expected Error!!!!!");
					}
					allCouponsAfterAdd = companyFacade.getAllCompanyCoupons();
					if (allCouponsAfterAdd.size() != 4) {
						System.out.println("Test addCoupon with same title Not Failed as expected!!!!!");
					}
				}
			}

			// 5. Test Get All Coupons of the logged in company by specified category
			List<Coupon> allCouponsByCategory = companyFacade.getAllCompanyCouponsByCategory(Category.VACATION);
			if (allCouponsByCategory.size() != 1) {

				System.out.println("Test getAllCompanyCouponsByCategory Failed!!!!!");
			}
			if (allCouponsByCategory.get(0).getCompanyId() != 1
					|| !allCouponsByCategory.get(0).getCouponTitle().equals("Spain")) {

				System.out.println("Test getAllCompanyCouponsByCategory Pos 0 Failed!!!!!");
			}

			// 6. Test Get All Coupons of the logged in company by Max Price
			List<Coupon> allCouponsByMaxPrice = companyFacade.getAllCompanyCouponsByPrice(500);
			if (allCouponsByMaxPrice.size() != 4) {

				System.out.println("Test getAllCompanyCouponsByPrice Failed!!!!!");
			}
			if (allCouponsByMaxPrice.get(0).getCompanyId() != 1
					|| allCouponsByMaxPrice.get(0).getCouponPrice() != 9.6) {

				System.out.println("Test getAllCompanyCouponsByPrice Pos 0 Failed!!!!!");
			}
			if (allCouponsByMaxPrice.get(1).getCouponPrice() != 17.7) {

				System.out.println("Test getAllCompanyCouponsByPrice Pos 1 Failed!!!!!");
			}
			if (allCouponsByMaxPrice.get(2).getCouponPrice() != 300) {

				System.out.println("Test getAllCompanyCouponsByPrice Pos 2 Failed!!!!!");
			}
			if (allCouponsByMaxPrice.get(3).getCouponPrice() != 500) {

				System.out.println("Test getAllCompanyCouponsByPrice Pos 3 Failed!!!!!");
			}

			// 7. Test Get the logged in Company Details
			Company companyDetails = companyFacade.getOneCompany();
			if (companyDetails.getId() != 1 && !companyDetails.getName().equals("Walla")
					&& !companyDetails.getEmail().equals("mail@new")
					&& !companyDetails.getPassword().equals("newPassword")) {

				System.out.println("Test get companyDetails Failed!!!!!");
			}

			// 8. Test Update Coupon title to new title
			{
				Coupon couponToUpdate = companyFacade.getAllCompanyCoupons().get(0);
				couponToUpdate.setCouponTitle("new title");
				companyFacade.updateCoupon(couponToUpdate);
				List<Coupon> allCouponsAfterUpdate = companyFacade.getAllCompanyCoupons();
				if (!allCouponsAfterUpdate.get(0).getCouponTitle().equals("new title")) {
					System.out.println("Test Update Title Failed!!!!!");
				}
			}

			// 9. Test Update Coupon title to existing title under other company
			{
				Coupon couponToUpdate = companyFacade.getAllCompanyCoupons().get(0);
				couponToUpdate.setCouponTitle("Vietnam");
				companyFacade.updateCoupon(couponToUpdate);
				List<Coupon> allCouponsAfterUpdate = companyFacade.getAllCompanyCoupons();
				if (!allCouponsAfterUpdate.get(0).getCouponTitle().equals("Vietnam")) {
					System.out.println("Test Update Title Failed!!!!!");
				}
			}

			// 10. Test Update Coupon title to existing title - should raise Error
			{
				Coupon couponToUpdate = companyFacade.getAllCompanyCoupons().get(0);
				couponToUpdate.setCouponTitle("Spain");
				try {
					companyFacade.updateCoupon(couponToUpdate);
				} catch (CouponSystemException e) {
					if (!e.getMessage().equals("update coupon with id: " + couponToUpdate.getId()
							+ " failed - coupon title already exist")) {

						System.out.println("Test updateCoupon With Same title not raised expected Error!!!!!");
					}
				}
			}

			// 11. Test Update Coupon-id should raise error
			{
				Coupon couponToUpdate = companyFacade.getAllCompanyCoupons().get(0);
				couponToUpdate.setId(822);
				try {
					companyFacade.updateCoupon(couponToUpdate);
				} catch (CouponSystemException e) {
					if (!e.getMessage().equals("get coupon with id: 822 failed - was not found")) {

						System.out.println("Test update coupon id not raised expected Error!!!!!");
					}
				}
			}

			// 12. Test Update Coupon's company-id should raise error
			{
				Coupon couponToUpdate = companyFacade.getAllCompanyCoupons().get(0);
				couponToUpdate.setCompanyId(822);
				try {
					companyFacade.updateCoupon(couponToUpdate);
				} catch (CouponSystemException e) {
					if (!e.getMessage().equals("get company with id: " + 822 + " failed - was not found")) {

						System.out.println("Test update coupon's company-id not raised expected Error!!!!!");
					}
				}
			}

//	============================= Test Customer Facade =========================================

			// Login as customer -> get a facade using the LoginManager:
			CustomerFacade customerFacade = (CustomerFacade) LoginManager.getInstance().login("iloveisrael@mailll",
					"123", ClientType.CUSTOMER);
			if (customerFacade != null) {
				System.out.println("Login with wrong credentials should not work!!!!");
			}

			customerFacade = (CustomerFacade) LoginManager.getInstance().login("iloveisrael@mail", "123",
					ClientType.CUSTOMER);

			// 1. Test Get the logged in Customer Details
			Customer customerDetails = customerFacade.getCustomerDetails();
			if (customerDetails.getId() != 2 && !customerDetails.getFirstName().equals("Israel")
					&& !customerDetails.getLastName().equals("Israeli")
					&& !customerDetails.getEmail().equals("iloveisrael@mail")
					&& !customerDetails.getPassword().equals("123")) {

				System.out.println("Test get CustomerDetails Failed!!!!!");
			}
			
			{
				// 2. Test purchase coupon
				Coupon coupon = companyFacade.getAllCompanyCoupons().get(2);
				customerFacade.purchaseCoupon(coupon);
				List<Coupon> coupons = customerFacade.getAllCustomerCouponsByCategory(Category.VACATION);
				if(coupons.size() != 1) {
					System.out.println("Test purchase coupon Failed!!!!!");
				}
			}
			
			{
				// 3. Test purchase same coupon again
				Coupon coupon = companyFacade.getAllCompanyCoupons().get(2);
				try {
					customerFacade.purchaseCoupon(coupon);

				} catch (CouponSystemException e) {
					if (!e.getMessage().equals("the specified coupon already was bought by customer: "+ customerDetails.getId())) {
						System.out.println("Test purchase same coupon again not raised expected Error!!!!!");
					}
				}
				
			}
			
			{
				// 4. Test Get the purchased coupons of the logged in Customer
				List<Coupon> couponsCustomerBuy = customerFacade.getAllCustomerCoupons();
				if(couponsCustomerBuy.size() != 3) {
					System.out.println("Test get all purchase coupon Failed!!!!!");
				}			
			}
			
			{			
				// 5. Test Get the purchased coupons to max price of the logged in Customer
				List<Coupon> couponsCustomerByPrice100 = customerFacade.getAllCustomerCouponsByPrice(100);
				if(couponsCustomerByPrice100.size() != 1) {
					System.out.println("Test get all coupons under 100 Failed!!!!!");
				}
				
				if(couponsCustomerByPrice100.get(0).getCouponPrice() > 100) {
					System.out.println("Test get all coupons under 100 Failed!!!!!");
				}
			}
			
			{			
				// 6. Test Try buy coupon with amount 0
				Coupon coupon = companyFacade.getAllCompanyCoupons().get(1);
				try {
					customerFacade.purchaseCoupon(coupon);
				} catch (CouponSystemException e) {
					if (!e.getMessage().equals("the specified coupon is not available anymore")) {
						System.out.println("Test purchase coupon with amount 0 not raised expected Error!!!!!");
					}
				}
			}

//			=======================================================
			{
				// Delete coupon
				Coupon coupon = companyFacade.getAllCompanyCoupons().get(1);
				companyFacade.deleteCoupon(coupon.getId());
				List<Coupon> coupons = companyFacade.getAllCompanyCoupons();
				if(coupons.size() != 3) {
					System.out.println("Test Delete coupon not working!!!!!");
				}
				
				// Delete customer
				adminFacade.deleteCustomer(3);
				List<Customer> allCustAfterDelete = adminFacade.getAllCustomers();
				if (allCustAfterDelete.size() != 2) {
					System.out.println("Test delete Customer Failed!!!!!");
				}
				
				// Delete company
				adminFacade.deleteCompanyAndCompanyCoupons(2);
				List<Company> allCompAfterDelete = adminFacade.getAllCompanies();
				if (allCompAfterDelete.size() != 2) {
					System.out.println("Test delete Company Failed!!!!!");
				}
			}
			
//			 ================================================================

		} catch (

		CouponSystemException e) {
			throw new CouponSystemException(e.getMessage());

		} finally {
			try {
				// stop the system ==========
				// stop the dailyJob thread - use join before proceed
//				dailyJob.stop();

//				dailyJobThread.join();

				ConnectionPool.getInstance().closeAllConnection(); // will stop the connection pool

//			} catch (InterruptedException e) {
//				throw new CouponSystemException(e.getMessage());

			} catch (CouponSystemException e) {
				e.printStackTrace();

				// ===========================

			}
		}
	}
}
