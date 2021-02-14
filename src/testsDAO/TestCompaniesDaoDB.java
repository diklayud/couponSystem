package testsDAO;

import java.net.MulticastSocket;
import java.util.List;

import javax.swing.JOptionPane;

import dao.CompaniesDao;
import dao.CompaniesDaoDB;
import dao.CouponsDaoDB;
import exceptions.CouponSystemException;
import javaBeans.Company;

public class TestCompaniesDaoDB {

	public static void main(String[] args) {

		CompaniesDao companiesDao = new CompaniesDaoDB();

//		{ // saving companies using DAO
//			Company company1 = new Company(0, "DY", "dikla@DY", "111");
//			Company company2 = new Company(0, "D.Y", "d@DY", "123");
//			Company company3 = new Company(0, "Dikla", "dikla@mail", "157");
//
//			try {
//				companiesDao.addCompany(company1);
//				companiesDao.addCompany(company2);
//				companiesDao.addCompany(company3);
//				System.out.println("companies saved");
//			} catch (CouponSystemException e) {
//				e.printStackTrace();
//			}
//		}

//		{ // getting all companies
//			try {
//				List<Company> allCompanies = companiesDao.getAllCompanies();
//				System.out.println(allCompanies);
//			} catch (CouponSystemException e) {
//				e.printStackTrace();
//			}
//		}

//		{ // getting a company
//			try {
//				Company company = companiesDao.getOneCompany(1);
//				System.out.println(company);
//			} catch (CouponSystemException e) {
//				e.printStackTrace();
//			}			
//		}

//		{ // updating an existing company
//			try {
//			// get a company
//				Company company = companiesDao.getOneCompany(1);
//				System.out.println(company);
//				if(company!=null) {
//					// update the password in the object
//					company.setPassword("222");
//					// update the password in the database
//					companiesDao.updateCompany(company);
//					System.out.println(company);
//				}else {
//					System.out.println("company with id: 1 not found");
//				}
//			} catch (CouponSystemException e) {
//				e.printStackTrace();
//			}
//		}

//		{ // updating a non-existing company - will throw an exception
//			try {
//			// create a company instance. its details are NOT in the database
//			Company company = new Company(0, "evg", "evg@mail", "1212");
//			// try to update the name in the database, will fail and throw an exception
//				companiesDao.updateCompany(company);
//				System.out.println(company);
//			} catch (CouponSystemException e) {
////				e.printStackTrace();
//				System.err.println(e.getMessage());
//			}

//		{ // delete a company
//			int id = Integer.parseInt(JOptionPane.showInputDialog("enter company id to delete"));
//			try {
//				companiesDao.deleteCompany(id);
//				System.out.println("company with id " + id + " deleted");
//			} catch (CouponSystemException e) {
//				e.printStackTrace();
//				JOptionPane.showMessageDialog(null, e.getMessage());
//			}	
//		}

//		{ // check if company exists
//			try {
//				String email = JOptionPane.showInputDialog("enter company's email");
//				String password = JOptionPane.showInputDialog("enter company's password");
//				Company company = new Company();
//				company.setEmail(email);
//				company.setPassword(password);
//				boolean isExsit = companiesDao.isCompanyExists(company.getEmail(), company.getPassword());
//				if (isExsit) {
//					System.out.println("company exist");
//				}else {
//					System.out.println("company is not exist, please register");
//				}
//			} catch (CouponSystemException e) {
//				e.printStackTrace();
//				JOptionPane.showInputDialog(e.getMessage());
//			}
//
//		}
		
	
	}

}
