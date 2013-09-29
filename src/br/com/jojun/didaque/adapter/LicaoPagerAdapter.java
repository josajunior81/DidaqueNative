package br.com.jojun.didaque.adapter;

import java.util.List;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import br.com.jojun.didaque.fragment.LicaoFragment;

public class LicaoPagerAdapter extends FragmentPagerAdapter {

	private List<LicaoFragment> listFragments;
	
	public LicaoPagerAdapter(FragmentManager fm, List<LicaoFragment> fragments) {
		super(fm);
		setListFragments(fragments);
	}

	@Override
	public LicaoFragment getItem(int position) {
		Log.i("PF", "veio no get item");
		return getListFragments().get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return getListFragments().size();
	}

	public List<LicaoFragment> getListFragments() {
		return listFragments;
	}

	public void setListFragments(List<LicaoFragment> listFragments) {
		this.listFragments = listFragments;
	}

}
