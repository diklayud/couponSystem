package testsDAO;

import javax.swing.JOptionPane;

import dao.CustomerDao;
import dao.CustomerDaoDB;
import exceptions.CouponSystemException;
import javaBeans.Company;
import javaBeans.Customer;

public class TestCustomersDaoDB {

	public static void main(String[] args) {

		CustomerDao customerDao = new CustomerDaoDB();

//		{ // saving customers using DAO
//			Customer customer = new Customer(0, "cus", "tomer", "dikla@DY", "111");
//			Customer customer2 = new Customer(0, "customer", "c1", "d@DY", "123");
//			Customer customer3 = new Customer(0, "custom", "c2", "dikla@mail", "157");
//
//			try {
//				customerDao.addCustomer(customer);
//				customerDao.addCustomer(customer2);
//				customerDao.addCustomer(customer3);
//				System.out.println("customers saved");
//			} catch (CouponSystemException e) {
//				e.printStackTrace();
//			}
//		}

//		{ // getting all customers
//			try {
//				List<Customer> allCustomers = customerDao.getAllCustomers();
//				System.out.println(allCustomers);
//			} catch (CouponSystemException e) {
//				e.printStackTrace();
//			}
//		}

//		{ // getting a customer
//			try {
//				Customer customer = customerDao.getOneCustomer(1);
//				System.out.println(customer);
//			} catch (CouponSystemException e) {
//				e.printStackTrace();
//			}			
//		}

//		{ // updating an existing customer
//			try {
//				// get a company
//				Customer customer = customerDao.getOneCustomer(1);
//				System.out.println(customer);
//				if (customer != null) {
//					// update the password in the object
//					customer.setPassword("222");
//					// update the password in the database
//					customerDao.updateCustomer(customer);
//					System.out.println(customer);
//				} else {
//					System.out.println("customer with id: 1 not found");
//				}
//			} catch (CouponSystemException e) {
//				e.printStackTrace();
//			}
//		}

//		{ // updating a non-existing customer - will throw an exception
//			try {
//				// create a customer instance. its details are NOT in the database
//				Customer customer = new Customer(0, "evg", "yud", "evg@mail", "1212");
//				// try to update the name in the database, will fail and throw an exception
//				customerDao.updateCustomer(customer);
//				System.out.println(customer);
//			} catch (CouponSystemException e) {
////				e.printStackTrace();
//				System.err.println(e.getMessage());
//			}
//
//		}
		
//		{ // delete a customer
//			int id = Integer.parseInt(JOptionPane.showInputDialog("enter customer id to delete"));
//			try {
//				customerDao.deleteCustomer(id);
//				System.out.println("customer with id " + id + " deleted");
//			} catch (CouponSystemException e) {
//				e.printStackTrace();
//				JOptionPane.showMessageDialog(null, e.getMessage());
//			}	
//		}
			
		{ // check if customer exists
			try {
				String email = JOptionPane.showInputDialog("enter customer's email");
				String password = JOptionPane.showInputDialog("enter customer's password");
				Customer customer = new Customer();
				customer.setEmail(email);
				customer.setPassword(password);
				boolean isExsit = customerDao.isCustomerExists(customer.getEmail(), customer.getPassword());
				if (isExsit) {
					System.out.println("customer exist");
				}else {
					System.out.println("customer is not exist, please register");
				}
			} catch (CouponSystemException e) {
				e.printStackTrace();
				JOptionPane.showInputDialog(e.getMessage());
			}

		}
		
	}
}
