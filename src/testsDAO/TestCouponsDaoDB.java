package testsDAO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import dao.CompaniesDao;
import dao.CompaniesDaoDB;
import dao.CouponsDao;
import dao.CouponsDaoDB;
import exceptions.CouponSystemException;
import javaBeans.Category;
import javaBeans.Company;
import javaBeans.Coupon;

public class TestCouponsDaoDB {

	public static void main(String[] args) {

		CompaniesDao companiesDao = new CompaniesDaoDB();
		CouponsDao couponsDao = new CouponsDaoDB();

//		{ // saving coupons using DAO
//			try {
//				// Company company1 = new Company(0, "Walla", "walla@DY", "111");
//				// companiesDao.addCompany(company1);
////				Company company1fromDB = companiesDao.getCompanyByEmailandPassword("walla@DY", "111");
//				Company company2fromDB = companiesDao.getCompanyByEmailandPassword("dikla@DY", "222");
//				System.out.println("found company in DB");
//				LocalDate localDateExpiration = LocalDate.now().plusMonths(1);
//				LocalDate localDateCreation = LocalDate.now();
//
////				Coupon coupon = new Coupon(0, company1fromDB.getId(), Category.FOOD, "new title", 
////						"new description", localDateCreation, localDateExpiration, 10, 59.6, "path.to.c.image");
//
//				Coupon coupon2 = new Coupon(0, company2fromDB.getId(), Category.FOOD, "title", "description",
//						localDateCreation, localDateExpiration, 5, 9.7, "path.to.c2.image");
//
////				couponsDao.addCoupon(coupon);
//				couponsDao.addCoupon(coupon2);
//				System.out.println("coupon added to DB");
//			} catch (CouponSystemException e) {
//				e.printStackTrace();
//			}
//		}

//			{ // getting all coupons
//				try {
//					List<Coupon> allCoupons = couponsDao.getAllCoupons();
//					System.out.println(allCoupons);
//				} catch (CouponSystemException e) {
//					e.printStackTrace();
//				}
//				
//			}

//			{ // getting a coupon
//				try {
//					Coupon coupon = couponsDao.getOneCoupon(2);
//					System.out.println(coupon);
//				} catch (CouponSystemException e) {
//					e.printStackTrace();
//				}
//						
//			}

//		{ // updating an existing coupon
//			try {
//			// get a coupon
//				Coupon coupon = couponsDao.getOneCoupon(3);
//				System.out.println(coupon);
//				if(coupon != null) {
//					// update the categoryId in the object
//					coupon.setCategory(Category.RESTAURANT);
//					// update the categoryId in the DB
//					couponsDao.updateCoupon(coupon);
//					System.out.println(coupon);
//				}else {
//					System.out.println("coupon with the id: 3 not found");
//				}
//			} catch (CouponSystemException e) {
//				e.printStackTrace();
//			}
//			
//		}

//		{ // updating a non-existing coupon - will throw an exception
//			try {
//			// create a coupon instance. its details are NOT in the database
//			Coupon coupon = new Coupon(0, 5, Category.ELECTRICITY, "TITLE", "DESCRIPTION", LocalDate.now(), LocalDate.of(2021, 3, 15), 1, 5.5, "pathToCouponPic");
//			// try to update the amount in the database, will fail and throw an exception
//				couponsDao.updateCoupon(coupon);
//				System.out.println(coupon);
//			} catch (CouponSystemException e) {
//				e.printStackTrace();
//				System.err.println(e.getMessage());
//			}
//		
//		}

//		{ // delete a coupon
//			try {
//				couponsDao.deleteCoupon(3);
//				System.out.println("coupon with id specified deleted");
//			} catch (CouponSystemException e) {
//				e.printStackTrace();
//			}
//			
//		}

//		{ // add entry/record of purchasing.
//			try {
//				couponsDao.addCouponPurchase(2, 2);
//				System.out.println("record of purchasing added");
//			} catch (CouponSystemException e) {
//				e.printStackTrace();
//			}
//		
//		}

//		{ // delete entry/record of purchasing.
//			try {
//				couponsDao.deleteCouponPurchase(2, 2);
//				System.out.println("record of purchasing deleted");
//			} catch (CouponSystemException e) {
//				e.printStackTrace();
//			}
//			
//		}
		
		{ // get company coupons ids
			int id = 1;
			try {
				List<Integer> ans = couponsDao.getAllCompanyCouponsIds(id);
				System.out.println("coupons of company #" + id + " : " + ans.toString());
				couponsDao.deleteCouponPurchaseByCompanyID(id);
			} catch (CouponSystemException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
			}	
		}
		
	}

}
