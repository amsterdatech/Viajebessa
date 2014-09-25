package com.bemobi.viajebessa.interfaces;


public interface ITravelPackagesDetailView<T> extends BaseView {
	public void updateDetail(T item);

	public void showErrorDialog(String error);

	public void showPurchaseDialog(String message);

	public void showProgressDialog(String msg);

	public void updateProgressDialog(String msg);

	public void hideProgressDialog();

}
