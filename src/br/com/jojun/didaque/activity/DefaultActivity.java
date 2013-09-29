package br.com.jojun.didaque.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.jojun.didaque.R;
import br.com.jojun.didaque.adapter.LicaoPagerAdapter;
import br.com.jojun.didaque.bean.Apostila;
import br.com.jojun.didaque.fragment.LicaoFragment;
import br.com.jojun.didaque.fragment.TextosFragment;

public class DefaultActivity extends ActionBarActivity {
	protected String[] apostilas;
    protected DrawerLayout mDrawerLayout;
    protected ListView mDrawerList;
    protected CharSequence mTitle;
    protected CharSequence mDrawerTitle;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected boolean telaInicial;
    private int apostila;
    protected ViewPager mViewPager;
    protected List<LicaoFragment> fragments;
    protected LicaoFragment fragmentAtual;
    protected FragmentManager fragmentManager = getSupportFragmentManager();
    protected FragmentTransaction transaction;
    private LicaoPagerAdapter pagerAdapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);

		mDrawerTitle = "Apostilas";
		mTitle = "Didaque";
		
		telaInicial = true;
		
		apostilas = getResources().getStringArray(R.array.array_apostilas);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.adapter_list_apostila, apostilas));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
            	getSupportActionBar().setTitle(mDrawerTitle);
            	supportInvalidateOptionsMenu();
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
	}
	
	protected void isShowTabBar(){
		if(!telaInicial) {
		    
			// Specify that tabs should be displayed in the action bar.
		    getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		    
		    fragments = new ArrayList<LicaoFragment>();

		    for (int i = 0; i < Apostila.getQuantidadeLicoes(apostila); i++) {
	        	LicaoFragment fragment = new LicaoFragment();
	        	fragment.setRetainInstance(true);
	    		fragment.setNumeroApostila(apostila);
	    		fragment.setNumeroLicao((i+1));
	        	fragments.add(fragment);
		    }
		    
		    pagerAdapter = new LicaoPagerAdapter(fragmentManager, fragments);
		    pagerAdapter.notifyDataSetChanged();
			mViewPager = (ViewPager) findViewById(R.id.pager);
			mViewPager.setAdapter(pagerAdapter);
		    mViewPager.setOnPageChangeListener(
		            new ViewPager.SimpleOnPageChangeListener() {
		                @Override
		                public void onPageSelected(final int position) {
		                	getSupportActionBar().setSelectedNavigationItem(position);
		                }
		            });

		 // Create a tab listener that is called when the user changes tabs.
		    ActionBar.TabListener tabListener = new ActionBar.TabListener() {
		        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
		        	mViewPager.setCurrentItem(tab.getPosition());
//		        	
					fragmentAtual = new LicaoFragment();
		    		ft.replace(R.id.content_frame, fragmentAtual);
		    	}
	
		        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
		            // hide the given tab
		        }
	
		        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
		            // probably ignore this event
		        }
		    };
		    
		    // Add 3 tabs, specifying the tab's text and TabListener
		    for (int i = 0; i < Apostila.getQuantidadeLicoes(apostila); i++) {
		    	getSupportActionBar().addTab(
		    			getSupportActionBar().newTab()
		                        .setText(" - " + (i + 1) + " - ")
		                        .setTabListener(tabListener));
		    }
		}
		else{
			getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		}

	}
	
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }
	
	protected class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	        selectItem(position);
	    }
	}
	
	/** Swaps fragments in the main content view */
	protected void selectItem(int position) {
		
		if(position > 0) {
			telaInicial = false;
			apostila = position;
			
			transaction = fragmentManager.beginTransaction();
			TextosFragment frag = (TextosFragment)fragmentManager.findFragmentById(R.id.fragment_texto);
			transaction.addToBackStack(null);
			transaction.hide(frag);
			transaction.commit();

		}
		else if(!telaInicial) {
			telaInicial = true;
			pagerAdapter.setListFragments(new ArrayList<LicaoFragment>());
			pagerAdapter.notifyDataSetChanged();
			mViewPager.setAdapter(pagerAdapter);
			transaction = fragmentManager.beginTransaction();
			TextosFragment frag = (TextosFragment)fragmentManager.findFragmentById(R.id.fragment_texto);
			frag.setTextoAleatorio();
			transaction.addToBackStack(null);
			transaction.show(frag);
			transaction.commit();
		}
		isShowTabBar();

		mDrawerList.setItemChecked(position, true);
	    setTitle(apostilas[position]);
	    mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
	    mTitle = title;
	    getSupportActionBar().setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dashboard, menu);
		return false;
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if(telaInicial)
			finish();
		else {
			pagerAdapter.setListFragments(new ArrayList<LicaoFragment>());
			pagerAdapter.notifyDataSetChanged();
			mViewPager.setAdapter(pagerAdapter);
			telaInicial = true;
			isShowTabBar();
		}
	}

}
