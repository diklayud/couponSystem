package facade;

import dao.CompaniesDao;
import dao.CouponsDao;
import dao.CustomerDao;
import exceptions.CouponSystemException;

public abstract class ClientFacade {

	protected CompaniesDao companiesDao;
	protected CustomerDao customerDao;
	protected CouponsDao couponsDao;
	
	public abstract boolean login(String email, String password) throws CouponSystemException;
	
}
