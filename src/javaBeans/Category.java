package javaBeans;

import java.util.HashMap;
import java.util.Map;

import exceptions.CouponSystemException;

public enum Category {

	FOOD(1), RESTAURANT(2), ELECTRICITY(3), VACATION(4);

	public final int categoryId;

	/**
	 * Constructor for each ENUM we initialize categoryId by ENUM-value
	 * (are written in the brackets of declaration)
	 * 
	 * @param val
	 */
	private Category(int val) {
		this.categoryId = val;
	}

	
	/**
	 * Getter 
	 * @return the category id of ENUM
	 */
	public int getCategoryId() {
		return this.categoryId;
	}
	
	/**
	 * Map of each categoryId to belonging Category ENUM  
	 */
	private static final Map<Integer, Category> intToCategoryMap = new HashMap<Integer, Category>();
	
	/**
	 * initialize the map
	 */
	static {
	    for (Category category : Category.values()) {
	        intToCategoryMap.put(category.categoryId, category);
	    }
	}

	/**
	 * Convert categoryId (int) to Category ENUM
	 * @param categoryId
	 * @return Category ENUM
	 * @throws CouponSystemException
	 */
	public static Category fromInt(int categoryId) throws CouponSystemException {
		Category type = intToCategoryMap.get(Integer.valueOf(categoryId));
	    if (type == null) 
	        throw new CouponSystemException("the categoryId: " + categoryId + " not exist");
	    return type;
	}
	
	
}