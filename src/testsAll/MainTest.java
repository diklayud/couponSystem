package testsAll;

import java.util.List;

import dailyJob.CouponExpirationDailyJob;
import dataBase.ConnectionPool;
import exceptions.CouponSystemException;
import facade.AdminFacade;
import javaBeans.Company;
import javaBeans.Customer;
import loginManager.ClientType;
import loginManager.LoginManager;
import myTest.DaoDB_Util;

public class MainTest {

	public static void testAll() throws CouponSystemException {
		ConnectionPool.getInstance(); // will start the connection pool
		CouponExpirationDailyJob dailyJob = new CouponExpirationDailyJob();
		Thread dailyJobThread = new Thread(dailyJob, "dailyJobThread");
		
		//Thread dailyJobThread = new Thread(new CouponExpirationDailyJob());
		dailyJobThread.start();

		try {
			// Delete all rows from all tables in system's DB
			initAllDBTables();
			
			AdminFacade adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin",
					ClientType.ADMINISTRATOR);
			
			addCompanyToDB(adminFacade,"Fatal", "order@fatal", "3443");
			addCompanyToDB(adminFacade,"Zer4U", "order@zer4u", "1234");
			addCustomerToDB(adminFacade, "Rani", "Berkovich", "ranb@mail", "1777");
			addCustomerToDB(adminFacade, "Dan", "Cohen", "danc@mail", "777");
			
		} catch (CouponSystemException e) {
			System.err.println(e.getMessage());
		} finally
		{
			try {
				dailyJob.stop();
				dailyJobThread.join();
				ConnectionPool.getInstance().closeAllConnection(); // will stop the connection pool
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void addCompanyToDB(AdminFacade adminFacade,String comp_name, String comp_mail, String comp_pass) throws CouponSystemException {
		List<Company> allCompaniesBeforeAdd = adminFacade.getAllCompanies();
		Company companyToAdd = new Company(0, comp_name, comp_mail, comp_pass);
		adminFacade.addCompany(companyToAdd);
		List<Company> allCompAfterAdd = adminFacade.getAllCompanies();
		if (allCompAfterAdd.size() != allCompaniesBeforeAdd.size() + 1) {
			System.out.println("add Company Failed!!!!!");
		}
	}
	
	private static void addCustomerToDB(AdminFacade adminFacade,String customer_fname, String customer_lname, String customer_mail, String customer_pass) throws CouponSystemException {
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
	

