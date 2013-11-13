package br.com.jojun.didaque.adapter;

import java.util.List;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import br.com.jojun.didaque.fragment.BibliaFragment;

public class BibliaPagerAdapter extends FragmentStatePagerAdapter {

	private List<BibliaFragment> listFragments;
	
	public BibliaPagerAdapter(FragmentManager fm, List<BibliaFragment> fragments) {
		super(fm);
		setListFragments(fragments);
	}

	@Override
	public BibliaFragment getItem(int position) {
		if(getListFragments().size() > 0)
			return getListFragments().get(position);
		else
			return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return getListFragments().size();
	}

	public List<BibliaFragment> getListFragments() {
		return listFragments;
	}

	public void setListFragments(List<BibliaFragment> listFragments) {
		this.listFragments = listFragments;
	}

}
