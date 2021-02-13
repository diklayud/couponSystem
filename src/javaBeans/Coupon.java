package javaBeans;

import java.time.LocalDate;
import java.util.Objects;

//create a class based on a database table - java beans
public class Coupon {

	// fields
	private int id;
	private int companyId;
	private Category category;
	private String couponTitle;
	private String couponDescription;
	private LocalDate couponCreationDate;
	private LocalDate couponExpirationDate;
	private int couponsAmount;
	private double couponPrice;
	private String couponImage;

	// CTORs
	public Coupon() {
	}

	public Coupon(int id) {
		super();
		this.id = id;
	}

	public Coupon(int id, int companyId, Category category, String couponTitle, String couponDescription,
			LocalDate couponCreationDate, LocalDate couponExpirationDate, int couponsAmount, double couponPrice,
			String couponImage) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.category = category;
		this.couponTitle = couponTitle;
		this.couponDescription = couponDescription;
		this.couponCreationDate = couponCreationDate;
		this.couponExpirationDate = couponExpirationDate;
		this.couponsAmount = couponsAmount;
		this.couponPrice = couponPrice;
		this.couponImage = couponImage;
	}

	// getters / setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getCouponTitle() {
		return couponTitle;
	}

	public void setCouponTitle(String couponTitle) {
		this.couponTitle = couponTitle;
	}

	public String getCouponDescription() {
		return couponDescription;
	}

	public void setCouponDescription(String couponDescription) {
		this.couponDescription = couponDescription;
	}

	public LocalDate getCouponCreationDate() {
		return couponCreationDate;
	}

	public void setCouponCreationDate(LocalDate couponCreationDate) {
		this.couponCreationDate = couponCreationDate;
	}

	public LocalDate getCouponExpirationDate() {
		return couponExpirationDate;
	}

	public void setCouponExpirationDate(LocalDate couponExpirationDate) {
		this.couponExpirationDate = couponExpirationDate;
	}

	public int getCouponsAmount() {
		return couponsAmount;
	}

	public void setCouponsAmount(int couponsAmount) {
		this.couponsAmount = couponsAmount;
	}

	public double getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(double couponPrice) {
		this.couponPrice = couponPrice;
	}

	public String getCouponImage() {
		return couponImage;
	}

	public void setCouponImage(String couponImage) {
		this.couponImage = couponImage;
	}

	// Override methods
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", companyId=" + companyId + ", category=" + category + ", couponTitle="
				+ couponTitle + ", couponDescription=" + couponDescription + ", couponCreationDate="
				+ couponCreationDate + ", couponExpirationDate=" + couponExpirationDate + ", couponsAmount="
				+ couponsAmount + ", couponPrice=" + couponPrice + ", couponImage=" + couponImage + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Coupon)) {
			return false;
		}
		Coupon other = (Coupon) obj;
		return id == other.id;
	}

}
