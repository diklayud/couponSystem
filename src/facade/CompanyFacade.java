package facade;

import java.util.List;

import dao.CompaniesDao;
import dao.CompaniesDaoDB;
import dao.CouponsDaoDB;
import dao.CustomerDaoDB;
import exceptions.CouponSystemException;
import javaBeans.Category;
import javaBeans.Company;
import javaBeans.Coupon;

public class CompanyFacade extends ClientFacade {

	// ATTRIBUTE
	private int companyId = -1;

	// CTOR
	public CompanyFacade() {
		this.companiesDao = new CompaniesDaoDB();
		this.customerDao = new CustomerDaoDB();
		this.couponsDao = new CouponsDaoDB();
	}

	// GETTER
	
	/**
	 * @return the ID of the logged-in company
	 */
	public int getCompanyId() {
		return companyId;
	}

	// METHOD FROM SUPERCLASS
	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		try {
			boolean exist = companiesDao.isCompanyExists(email, password);
			if (exist) {
				this.companyId = this.companiesDao.getIdOfCompanyByEmailPassword(email, password);
				return true;
			}
			return false;
		} catch (CouponSystemException e) {
			throw new CouponSystemException(e.getMessage());
		}

	}

	// METHODS

	/**
	 * add coupon if not exist coupon with same title under same company in DB
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void addCoupon(Coupon coupon) throws CouponSystemException {
		try {
			boolean exist = couponsDao.isCouponTitleExist(this.companyId, coupon.getCouponTitle());
			if (!exist) {
				couponsDao.addCoupon(coupon);
			} else {
				throw new CouponSystemException("add Coupon failed- coupon title: " + coupon.getCouponTitle()
						+ " already exist under company id: " + coupon.getCompanyId());
			}
		} catch (CouponSystemException e) {
			throw new CouponSystemException(e.getMessage());
		}
	}

	/**
	 * update Coupon details except to Coupon id or company id or creation date
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		try {
			// get the coupon from the database
			Coupon couponFromDb = couponsDao.getOneCoupon(coupon.getId());
			// check if company id not exist in DB - will throw exception
			companiesDao.getOneCompany(coupon.getCompanyId());
			// check if title not exist in DB
			boolean exist = this.couponsDao.isCouponTitleExist(coupon.getCompanyId(), coupon.getCouponTitle());
			if (exist == false) {

				// update the permitted fields
				couponFromDb.setCategory(coupon.getCategory());
				couponFromDb.setCouponTitle(coupon.getCouponTitle());
				couponFromDb.setCouponDescription(coupon.getCouponDescription());
				couponFromDb.setCouponExpirationDate(coupon.getCouponExpirationDate());
				couponFromDb.setCouponsAmount(coupon.getCouponsAmount());
				couponFromDb.setCouponPrice(coupon.getCouponPrice());
				couponFromDb.setCouponImage(coupon.getCouponImage());
				// send the updated instance to the db
				couponsDao.updateCoupon(couponFromDb);
			} else {
				throw new CouponSystemException(
						"update coupon with id: " + coupon.getId() + " failed - coupon title already exist");
			}

		} catch (CouponSystemException e) {
			throw new CouponSystemException(e.getMessage());
		}
	}

	/**
	 * delete coupon and it's purchases
	 * 
	 * @param couponID
	 * @throws CouponSystemException
	 */
	public void deleteCoupon(int couponID) throws CouponSystemException {
		this.couponsDao.deleteCouponPurchaseByCouponID(couponID);
		this.couponsDao.deleteCoupon(couponID);
	}

	/**
	 * @return list of company's coupons
	 * @throws CouponSystemException
	 */
	public List<Coupon> getAllCompanyCoupons() throws CouponSystemException {
		if (this.companyId == -1) {
			throw new CouponSystemException("You should Login first..");
		}
		return this.couponsDao.getAllCompanyCoupons(this.companyId);
	}

	/**
	 * @param categoryID
	 * @return list of company's coupons of certain category
	 * @throws CouponSystemException
	 */
	public List<Coupon> getAllCompanyCouponsByCategory(Category categoryID) throws CouponSystemException {
		if (this.companyId == -1) {
			throw new CouponSystemException("You should Login first..");
		}
		return this.couponsDao.getAllCompanyCouponsByCategory(this.companyId, categoryID);
	}

	/**
	 * @param couponMaxPrice
	 * @return list of company's coupons below maximum price
	 * @throws CouponSystemException
	 */
	public List<Coupon> getAllCompanyCouponsByPrice(double couponMaxPrice) throws CouponSystemException {
		if (this.companyId == -1) {
			throw new CouponSystemException("You should Login first..");
		}
		return this.couponsDao.getAllCompanyCouponsByPrice(this.companyId, couponMaxPrice);
	}

	/**
	 * @return company with it's details
	 * @throws CouponSystemException
	 */
	public Company getOneCompany() throws CouponSystemException {
		if (this.companyId == -1) {
			throw new CouponSystemException("You should Login first..");
		}
		return this.companiesDao.getOneCompany(this.companyId);
	}
}
