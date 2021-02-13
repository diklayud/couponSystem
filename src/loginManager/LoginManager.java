package loginManager;

import exceptions.CouponSystemException;
import facade.AdminFacade;
import facade.ClientFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;

public class LoginManager {

	// ===== singleton =====

	private static LoginManager instance;

	private LoginManager() {
	}

	public static LoginManager getInstance() {
		if (instance == null) {
			instance = new LoginManager();
		}
		return instance;
	}

	// =====================

	/**
	 * enable each type of client to login to the system by verifying parameters:
	 * 
	 * @param email
	 * @param password
	 * @param clientType
	 * @return the valid ClientFacade or null if the parameters are wrong
	 * @throws CouponSystemException
	 */
	public ClientFacade login(String email, String password, ClientType clientType) throws CouponSystemException {
		ClientFacade clientFacade = null;

		switch (clientType) {
		case ADMINISTRATOR:
			ClientFacade adminFacade = new AdminFacade();
			boolean connect = adminFacade.login(email, password);
			if (connect) {
				return adminFacade;
			} else {
				return null;
			}

		case COMPANY:
			ClientFacade companyFacade = new CompanyFacade();
			connect = companyFacade.login(email, password);
			if (connect) {
				return companyFacade;
			} else {
				return null;
			}

		case CUSTOMER:
			ClientFacade customerFacade = new CustomerFacade();
			connect = customerFacade.login(email, password);
			if (connect) {
				return customerFacade;
			} else {
				return null;
			}

		default:
			return clientFacade;
		}
	}

}
