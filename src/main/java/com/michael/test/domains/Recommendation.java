package com.michael.test.domains;

import java.util.List;

/**
 * @author michaelwang on 2021-03-14
 */
@SuppressWarnings("unused")
public class Recommendation {
	private List<Product> myOrders;
	private List<Product> wishList;
	private List<Product> hotDeals;

	public Recommendation() {
	}

	public Recommendation(List<Product> myOrders, List<Product> wishList, List<Product> hotDeals) {
		this.myOrders = myOrders;
		this.wishList = wishList;
		this.hotDeals = hotDeals;
	}

	public List<Product> getMyOrders() {
		return myOrders;
	}

	public void setMyOrders(List<Product> myOrders) {
		this.myOrders = myOrders;
	}

	public List<Product> getWishList() {
		return wishList;
	}

	public void setWishList(List<Product> wishList) {
		this.wishList = wishList;
	}

	public List<Product> getHotDeals() {
		return hotDeals;
	}

	public void setHotDeals(List<Product> hotDeals) {
		this.hotDeals = hotDeals;
	}

	@Override
	public String toString() {
		return "Recommendation{" + "myOrders=" + myOrders + ", wishList=" + wishList + ", hotDeals=" + hotDeals + '}';
	}
}
