package facade;

import java.util.List;

import dao.CompaniesDaoDB;
import dao.CouponsDaoDB;
import dao.CustomerDaoDB;
import exceptions.CouponSystemException;
import javaBeans.Category;
import javaBeans.Coupon;
import javaBeans.Customer;

public class CustomerFacade extends ClientFacade {

	// ATTRIBUTE
	private int customerId = -1;

	// CTOR
	public CustomerFacade() {
		this.companiesDao = new CompaniesDaoDB();
		this.customerDao = new CustomerDaoDB();
		this.couponsDao = new CouponsDaoDB();
	}

	// METHOD FROM SUPERCLASS
	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		try {
			boolean exist = this.customerDao.isCustomerExists(email, password);
			if (exist) {
				this.customerId = customerDao.getIdOfCustomerByEmailPassword(email, password);
				return true;
			}
			return false;
		} catch (CouponSystemException e) {
			throw new CouponSystemException(e.getMessage());
		}
	}

	// METHODS

	/**
	 * add coupon purchase by certain customer if the customer didn't buy this
	 * coupon already and if the total coupon's amount is more than ZERO and if it's
	 * due date isn't expired yet
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void purchaseCoupon(Coupon coupon) throws CouponSystemException {
		boolean alreadyBought = couponsDao.isCouponPurchase(this.customerId, coupon.getId());
		if (alreadyBought) {
			throw new CouponSystemException("the specified coupon already was bought by customer: " + this.customerId);
		} else {
			boolean couponAvailable = couponsDao.couponAmountAndDate(coupon.getId());
			if (couponAvailable) {
				couponsDao.addCouponPurchase(customerId, coupon.getId());
				couponsDao.updateCouponAmount(coupon.getId());
			} else {
				throw new CouponSystemException("the specified coupon is not available anymore");
			}
		}
	}

	/**
	 * @return list of customer's coupons
	 * @throws CouponSystemException
	 */
	public List<Coupon> getAllCustomerCoupons() throws CouponSystemException {
		if (this.customerId == -1) {
			throw new CouponSystemException("You should Login first..");
		}
		return this.couponsDao.getAllCustomerCoupons(this.customerId);
	}

	/**
	 * @param categoryID
	 * @return list of customer's coupons by specified coupon's category
	 * @throws CouponSystemException
	 */
	public List<Coupon> getAllCustomerCouponsByCategory(Category categoryID) throws CouponSystemException {
		if (this.customerId == -1) {
			throw new CouponSystemException("You should Login first..");
		}
		return couponsDao.getAllCustomerCouponsByCategory(this.customerId, categoryID);
	}

	/**
	 * @param couponMaxPrice
	 * @return list of customer's coupons below maximum price
	 * @throws CouponSystemException
	 */
	public List<Coupon> getAllCustomerCouponsByPrice(double couponMaxPrice) throws CouponSystemException {
		if (this.customerId == -1) {
			throw new CouponSystemException("You should Login first..");
		}
		return couponsDao.getAllCustomerCouponsByPrice(this.customerId, couponMaxPrice);
	}

	public Customer getCustomerDetails() throws CouponSystemException {
		if(this.customerId == -1) {
			throw new CouponSystemException("You should Login first..");
		}
		return customerDao.getOneCustomer(this.customerId);
	}

}
