package dailyJob;

import java.util.concurrent.TimeUnit;

import dao.CouponsDao;
import dao.CouponsDaoDB;
import exceptions.CouponSystemException;

public class CouponExpirationDailyJob implements Runnable {

	// ---- ATTRIBUTES ----
	private CouponsDao couponsDao;
	private boolean quit;
	private Thread thread;

	// ---- CTORs ----
	public CouponExpirationDailyJob() {
		super();
		this.couponsDao = new CouponsDaoDB();
	}

	public CouponExpirationDailyJob(CouponsDao couponsDao) {
		super();
		this.couponsDao = couponsDao;
	}

	// ---- SETTER ----
	public void setThread(Thread thread) {
		this.thread = thread;
	}

	// ---- METHODS ----
	@Override
	public void run() {
		setThread(Thread.currentThread());
		while (!quit) {
			System.out.println("run daily job is started");
			// here job- logic of delete expired coupons - using DAOs
			// every 24 hours this runs
			try {
				couponsDao.deleteCouponPurchaseByCouponExpirationDate();
				couponsDao.deleteCouponByCouponExpirationDate();
				//Thread.sleep(TimeUnit.HOURS.toMillis(24)); // runtime
				Thread.sleep(TimeUnit.SECONDS.toMillis(10)); // for testing
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("job sleep was interrupted");
			} catch (CouponSystemException e) {
				e.printStackTrace();
			}
		}

		System.out.println("daily job is ended");
	}

	public void stop() {
		this.quit = true;
		thread.interrupt(); // if the thread is during work, the thread will change its interrupted status
							// but will stop only when it in sleep
	}

}
