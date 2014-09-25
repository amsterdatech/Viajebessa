package com.bemobi.viajebessa.vo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author William Gouvea
 * 
 * 
 *         Example of travel package data
 * 
 *         { "id":7, "title":"Antartica", "image":
 *         "https://lh4.googleusercontent.com/-ley-wPg00IA/UvvOEo-WvzI/AAAAAAAAAMM/ZytbySpJmWI/s369/06.png"
 *         , "description":"Antarctica - The Best Places to Travel in 2014",
 *         "cost":6300.00 }
 * 
 */

public class TravelPackage implements Parcelable {

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("image")
	private String imageUrl;

	@SerializedName("description")
	private String description;

	@SerializedName("cost")
	private int cost;

	public TravelPackage() {

	}

	public TravelPackage(Parcel in) {
		this();
		readFromParcel(in);
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getDescription() {
		return description;
	}

	public int getCost() {
		return cost;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(title);
		dest.writeString(imageUrl);
		dest.writeString(description);
		dest.writeInt(cost);

	}

	public void readFromParcel(Parcel in) {
		id = in.readInt();
		title = in.readString();
		imageUrl = in.readString();
		description = in.readString();
		cost = in.readInt();
	}

	public static final Parcelable.Creator<TravelPackage> CREATOR = new Parcelable.Creator<TravelPackage>() {

		public TravelPackage createFromParcel(Parcel in) {
			return new TravelPackage(in);
		}

		public TravelPackage[] newArray(int size) {
			return new TravelPackage[size];
		}
	};

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cost;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((imageUrl == null) ? 0 : imageUrl.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TravelPackage other = (TravelPackage) obj;
		if (cost != other.cost)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (imageUrl == null) {
			if (other.imageUrl != null)
				return false;
		} else if (!imageUrl.equals(other.imageUrl))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
