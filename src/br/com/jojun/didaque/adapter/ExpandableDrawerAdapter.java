package br.com.jojun.didaque.adapter;

import android.app.Activity;
import android.content.Context;
import android.provider.SyncStateContract.Constants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import br.com.jojun.didaque.R;

public class ExpandableDrawerAdapter extends BaseExpandableListAdapter {

	private static final int BIBLIAS = 0;
	private static final int APOSTILAS = 1;

	private LayoutInflater mInflater;
	private String[] mainNavItems, biblias, apostilas;

	public ExpandableDrawerAdapter (Activity context) {
		this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mainNavItems = context.getResources().getStringArray(R.array.main_nav_items);
		this.biblias = context.getResources().getStringArray(R.array.array_biblias);
		this.apostilas = context.getResources().getStringArray(R.array.array_apostilas);
	}

	public Object getChild(int groupPosition, int childPosition) {

		switch (groupPosition) {
		case BIBLIAS:
			return biblias[childPosition];
		case APOSTILAS:
			return apostilas[childPosition];
		default:
			return "";
		}
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.adapter_list_apostila, null);
		}

//		TextView childText = (TextView) convertView.findViewById(R.id.nav_text);
//		childText.setText((String) getChild(groupPosition, childPosition));

		return convertView;
	}

	public int getChildrenCount(int groupPosition) {
		switch (groupPosition) {
		case BIBLIAS:
			return biblias.length;
		case APOSTILAS:
			return apostilas.length;
		default:
			return 0;
		}
	}

	public Object getGroup(int groupPosition) {
		return mainNavItems[groupPosition];
	}

	public int getGroupCount() {
		return mainNavItems.length;
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.adapter_list_apostila,	null);
		}

//		TextView groupText = (TextView) convertView.findViewById(R.id.nav_text);
//		groupText.setText((String) getGroup(groupPosition));
		return convertView;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}