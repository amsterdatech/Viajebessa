package com.bemobi.viajebessa.response;

import java.util.List;

import com.bemobi.viajebessa.vo.TravelPackage;
import com.google.gson.annotations.SerializedName;

public class TravelPackagesData {
	@SerializedName("total")
	private int total;

	@SerializedName("count")
	private int count;

	@SerializedName("offset")
	private int offset;

	@SerializedName("results")
	private List<TravelPackage> results;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public List<TravelPackage> getResults() {
		return results;
	}

	public void setResults(List<TravelPackage> results) {
		this.results = results;
	}

}
