package br.com.jojun.didaque.adapter;

import java.util.List;

import br.com.jojun.didaque.fragment.LicaoFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class LicaoPagerAdapter extends FragmentPagerAdapter {

	private List<LicaoFragment> listFragments;
	
	public LicaoPagerAdapter(FragmentManager fm, List<LicaoFragment> fragments) {
		super(fm);
		listFragments = fragments;
	}

	@Override
	public LicaoFragment getItem(int position) {
		Log.i("PF", "veio no get item");
		return listFragments.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listFragments.size();
	}

}
