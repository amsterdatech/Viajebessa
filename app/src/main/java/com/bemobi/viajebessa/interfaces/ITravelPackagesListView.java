package com.bemobi.viajebessa.interfaces;

import java.util.List;

public interface ITravelPackagesListView<E> extends BaseView {
	public void updateList(List<E> packages);

	public void showErrorDialog(String error);

}
