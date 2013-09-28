package br.com.jojun.didaque.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import br.com.jojun.didaque.fragment.LicaoFragment;

public class LicaoFragmentAdapter extends FragmentStatePagerAdapter {

	public LicaoFragmentAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
        Fragment fragment = new LicaoFragment();
        Bundle args = new Bundle();
//        args.putInt(ApostilasFragment.ARG_OBJECT, i + 1);
        fragment.setArguments(args);
        return fragment;
	}

	@Override
	public int getCount() {
		return 0;
	}
	
    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}
