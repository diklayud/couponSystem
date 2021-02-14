package tests_main;

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

public class MainTest {

	public static void testAll() throws CouponSystemException {
		ConnectionPool.getInstance(); // will start the connection pool
		CouponExpirationDailyJob dailyJob = new CouponExpirationDailyJob();
		Thread dailyJobThread = new Thread(dailyJob, "dailyJobThread");

		dailyJobThread.start();

		try {
			// Delete all rows from all tables in coupon system's DB
			initAllDBTables();

			// Login as Admin
			AdminFacade adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin",
					ClientType.ADMINISTRATOR);

			addCompanyToDB(adminFacade, "Fatal", "order@fatal", "3443");
			addCompanyToDB(adminFacade, "Zer4U", "order@zer4u", "1234");
			addCustomerToDB(adminFacade, "Rani", "Berkovich", "ranb@mail", "1777");
			addCustomerToDB(adminFacade, "Dan", "Cohen", "danc@mail", "777");

			// Login as company -> get a facade using the LoginManager:
			CompanyFacade companyFacade = (CompanyFacade) LoginManager.getInstance().login("order@fatal", "3443",
					ClientType.COMPANY);

			// add coupons
			addCoupon(companyFacade, Category.VACATION, "Eilat", "get one night for free", LocalDate.of(2021, 01, 01),
					LocalDate.of(2021, 02, 10), 5, 200, "Path.eilat");
			addCoupon(companyFacade, Category.VACATION, "Jerusalem", "second night 50% off", LocalDate.now(),
					LocalDate.now().plusDays(2), 3, 400, "Path.jerusalem");
			Thread.sleep(25000);
			List<Coupon> coupons = companyFacade.getAllCompanyCoupons();

			// Login as customer -> get a facade using the LoginManager:
			CustomerFacade customerFacade = (CustomerFacade) LoginManager.getInstance().login("ranb@mail", "1777",
					ClientType.CUSTOMER);

			// purchase coupon
			Coupon couponToBuy = coupons.get(0);
			CustomerBuyCoupon(customerFacade, couponToBuy, Category.VACATION);

			Thread.sleep(10000);

		} catch (CouponSystemException e) {
			System.err.println(e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				dailyJob.stop();
				dailyJobThread.join();
				ConnectionPool.getInstance().closeAllConnection(); // will stop the connection pool
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static void CustomerBuyCoupon(CustomerFacade customerFacade, Coupon couponToBuy, Category category) throws CouponSystemException {
		List<Coupon> customerCouponsBefore = customerFacade.getAllCustomerCouponsByCategory(category);
		customerFacade.purchaseCoupon(couponToBuy);
		List<Coupon> customerCoupons = customerFacade.getAllCustomerCouponsByCategory(category);
		if (customerCoupons.size() != customerCouponsBefore.size() + 1) {
			System.out.println("Test purchase coupon Failed!!!!!");
		}
	}

	private static void addCoupon(CompanyFacade companyFacade, Category category, String couponTitle,
			String couponDescription, LocalDate creationDate, LocalDate expirationDate, int amount, int price,
			String imagePath) throws CouponSystemException {
		List<Coupon> allCouponsBeforeAdd = companyFacade.getAllCompanyCoupons();
		Coupon couponToAdd = new Coupon(0, companyFacade.getCompanyId(), category, couponTitle, couponDescription,
				creationDate, expirationDate, amount, price, imagePath);
		companyFacade.addCoupon(couponToAdd);
		List<Coupon> allCouponsAfterAdd = companyFacade.getAllCompanyCoupons();
		if (allCouponsAfterAdd.size() != allCouponsBeforeAdd.size() + 1) {
			System.out.println("Test addCoupon Failed!!!!!");
		}
	}

	private static void addCompanyToDB(AdminFacade adminFacade, String comp_name, String comp_mail, String comp_pass)
			throws CouponSystemException {
		List<Company> allCompaniesBeforeAdd = adminFacade.getAllCompanies();
		Company companyToAdd = new Company(0, comp_name, comp_mail, comp_pass);
		adminFacade.addCompany(companyToAdd);
		List<Company> allCompAfterAdd = adminFacade.getAllCompanies();
		if (allCompAfterAdd.size() != allCompaniesBeforeAdd.size() + 1) {
			System.out.println("add Company Failed!!!!!");
		}
	}

	private static void addCustomerToDB(AdminFacade adminFacade, String customer_fname, String customer_lname,
			String customer_mail, String customer_pass) throws CouponSystemException {
		List<Customer> allCustBeforeAdd = adminFacade.getAllCustomers();
		Customer customerToAdd = new Customer(0, customer_fname, customer_lname, customer_mail, customer_pass);
		adminFacade.addCustomer(customerToAdd);
		List<Customer> allCustAfterAdd = adminFacade.getAllCustomers();
		if (allCustAfterAdd.size() != allCustBeforeAdd.size() + 1) {
			System.out.println("add addCustomer Failed!!!!!");
		}
	}

	private static void initAllDBTables() throws CouponSystemException {
		DaoDB_Util clientDB = new DaoDB_Util();
		clientDB.deleteAllTableRows();
	}
}
