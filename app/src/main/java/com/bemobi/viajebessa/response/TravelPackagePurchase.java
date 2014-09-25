package com.bemobi.viajebessa.response;

import com.bemobi.viajebessa.vo.TravelPackage;
import com.google.gson.annotations.SerializedName;

public class TravelPackagePurchase {
	@SerializedName("total")
	private int id;

	@SerializedName("status")
	private String status;

	@SerializedName("item")
	private TravelPackage item;

	public int getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public TravelPackage getItem() {
		return item;
	}

}
