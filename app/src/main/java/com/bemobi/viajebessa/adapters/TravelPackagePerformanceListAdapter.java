package com.bemobi.viajebessa.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bemobi.viajebessa.R;
import com.bemobi.viajebessa.infrastructure.ViajeBessaApp;
import com.bemobi.viajebessa.vo.TravelPackage;
import com.squareup.picasso.Picasso;

public class TravelPackagePerformanceListAdapter extends BaseAdapter {
	private List<TravelPackage> mTravelPackages;

	public TravelPackagePerformanceListAdapter() {
		this.mTravelPackages = new ArrayList<TravelPackage>();
	}

	@Override
	public int getCount() {
		return this.mTravelPackages.size();
	}

	@Override
	public Object getItem(int position) {
		return this.mTravelPackages.get(position);
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void removeAll() {
		mTravelPackages.removeAll(mTravelPackages);
	}

	public void addAll(List<TravelPackage> packages) {
		if (mTravelPackages != null) {
			mTravelPackages.addAll(packages);
			notifyDataSetChanged();
		}
	}

	public List<TravelPackage> getTravelPackages() {
		return mTravelPackages;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		ViewHolder holder = null;

		if (rowView == null) {

			LayoutInflater inflater = (LayoutInflater) ViajeBessaApp
					.getInstance().getSystemService(
							Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.travel_package_list_item,
					parent, false);

			holder = new ViewHolder();

			holder.mPictureView = (ImageView) rowView
					.findViewById(R.id.item_picture);

			holder.mLabelView = (TextView) rowView
					.findViewById(R.id.item_title);

			holder.mSubtitleView = (TextView) rowView
					.findViewById(R.id.item_price);

			rowView.setTag(holder);

		} else {
			holder = (ViewHolder) rowView.getTag();
		}

		TravelPackage travelPackage = mTravelPackages.get(position);

		// Use ImageRequest by simplicity, once on a big image quantity kinda
		// app, use UIL or even Picasso is quite recommended instead of
		// NetworkImageView.

		holder.mLabelView.setText(travelPackage.getTitle());
		// TODO This formattin should use string and decimal format notation
		holder.mSubtitleView.setText("R$ " + travelPackage.getCost() + ",00");

		// holder.mPictureView

		// Trigger the download of the URL asynchronously into the image view.
		Picasso.with(ViajeBessaApp.getInstance())
				.load(travelPackage.getImageUrl())
				.config(Config.ARGB_8888)
				.placeholder(R.color.default_image_background)
				.error(R.color.default_image_background)
				.resizeDimen(R.dimen.list_item_image_width,
						R.dimen.list_item_image_height).centerCrop()
				.into(holder.mPictureView);

		return rowView;
	}

	public static class ViewHolder {
		public ImageView mPictureView;
		public TextView mLabelView;
		public TextView mSubtitleView;
	}

}
