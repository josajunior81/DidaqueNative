package br.com.jojun.didaque.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.ShareActionProvider.OnShareTargetSelectedListener;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.jojun.didaque.R;
import br.com.jojun.didaque.adapter.LicaoPagerAdapter;
import br.com.jojun.didaque.bean.Apostila;
import br.com.jojun.didaque.fragment.LicaoFragment;
import br.com.jojun.didaque.fragment.TextosFragment;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdView;
import com.google.analytics.tracking.android.EasyTracker;

@SuppressWarnings("deprecation")
public class DefaultActivity extends ActionBarActivity {
	protected String[] apostilas;
    protected DrawerLayout mDrawerLayout;
    protected ListView mDrawerList;
    protected CharSequence mTitle;
    protected CharSequence mDrawerTitle;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected boolean telaInicial= true;
    private int apostila;
    private int licao;
    protected ViewPager mViewPager;
    protected List<LicaoFragment> fragments;
    protected LicaoFragment fragmentAtual;
    protected FragmentManager fragmentManager = getSupportFragmentManager();
    protected FragmentTransaction transaction;
    private LicaoPagerAdapter pagerAdapter;
    private AdView mAdView;
    protected TextView mAdStatus;
    private ShareActionProvider mShareActionProvider;
	private ClipboardManager clipboard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		mDrawerTitle = "Apostilas";
		mTitle = "Didaque";
		
		clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		
		if(savedInstanceState != null){
			telaInicial = savedInstanceState.getBoolean("tela");
		} 
		
	    pagerAdapter = new LicaoPagerAdapter(fragmentManager, new ArrayList<LicaoFragment>());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		pagerAdapter.notifyDataSetChanged();
		mViewPager.setAdapter(pagerAdapter);
	    mViewPager.setOnPageChangeListener(
	            new ViewPager.SimpleOnPageChangeListener() {
	                @Override
	                public void onPageSelected(final int position) {
	                	licao = position;
	                	getSupportActionBar().setSelectedNavigationItem(position);
	                }
	            });
	    
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
		
        if(!telaInicial) {
        	apostila = savedInstanceState.getInt("apostila");
        	licao = savedInstanceState.getInt("licao");
        	selectItem(apostila);
        }
        
	    mAdStatus = (TextView) findViewById(R.id.status);
	    mAdView = (AdView)findViewById(R.id.ad);
	    mAdView.setAdListener(new MyAdListener());

	    AdRequest adRequest = new AdRequest();
	    adRequest.addKeyword("sporting goods");
	    mAdView.loadAd(adRequest);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this); // Add this method.
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
//		super.onSaveInstanceState(outState);
		
		outState.putBoolean("tela", telaInicial);
		outState.putInt("apostila", apostila);
		outState.putInt("licao", licao);
//		Log.i("DA", "TelaInicial: "+telaInicial);
		
	}
	
	protected void isShowTabBar(){
		if(!telaInicial) {
			// Specify that tabs should be displayed in the action bar.
			if( getSupportActionBar().getNavigationMode() != ActionBar.NAVIGATION_MODE_TABS)
				getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		   
		    fragments = new ArrayList<LicaoFragment>();

		    for (int i = 0; i < Apostila.getQuantidadeLicoes(apostila); i++) {
	        	LicaoFragment fragment = (LicaoFragment) LicaoFragment.instantiate(this, LicaoFragment.class.getName());
	    		fragment.setNumeroApostila(apostila);
	    		fragment.setNumeroLicao((i+1));
//	    		fragment.initLicao();
	        	fragments.add(fragment);
		    }

		    pagerAdapter.setListFragments(fragments);
		    pagerAdapter.notifyDataSetChanged();
			mViewPager.setAdapter(pagerAdapter);

		 // Create a tab listener that is called when the user changes tabs.
		    ActionBar.TabListener tabListener = new ActionBar.TabListener() {
		        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
		        	mViewPager.setCurrentItem(tab.getPosition());
		        	licao = tab.getPosition();
		        	
					fragmentAtual = new LicaoFragment();
		    		ft.replace(R.id.content_frame, fragmentAtual);
		    		supportInvalidateOptionsMenu();
		    	}
	
		        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
		            // hide the given tab
		        }
	
		        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
		            // probably ignore this event
		        }
		    };
		    
		    getSupportActionBar().removeAllTabs();
		    
		    // Add 3 tabs, specifying the tab's text and TabListener
		    for (int i = 0; i < Apostila.getQuantidadeLicoes(apostila); i++) {
		    	
		    	getSupportActionBar().addTab(getSupportActionBar().newTab()
		                        .setText(" - " + (i + 1) + " - ")
		                        .setTabListener(tabListener), i, ((i)==licao?true : false));
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
    	Log.i("DA", "No menu!");
        if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
        
//    	 // Handle presses on the action bar items
//        switch (item.getItemId()) {
//            case R.id.action_compartilhar:
//                compartilhar();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
        return super.onOptionsItemSelected(item);
    }
    
    private void compartilhar() {
    	Intent shareIntent = new Intent(Intent.ACTION_SEND);
    	shareIntent.setType("text/plain");
    	String texto = "";
    	if(telaInicial){
    		texto = ((TextView)findViewById(R.id.tv_textos_licao)).getText()+"\n"+
    				((TextView)findViewById(R.id.tv_textos_texto)).getText();
    	}else{
    		LicaoFragment lf = (LicaoFragment)pagerAdapter.getItem(licao);
    		texto = lf.tvTitulo.getText()+"\n\n"+
    				lf.tvCatequese.getText();   		 
    	}
    	shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Compartilhamento do APP Didaquê");
    	shareIntent.putExtra(Intent.EXTRA_TEXT, texto);
    	mShareActionProvider.setOnShareTargetSelectedListener(new OnShareTargetSelectedListener() {
			
			@Override
			public boolean onShareTargetSelected(ShareActionProvider shareActionProvider, Intent intent) {
				if ("com.facebook.katana".equals(intent.getComponent().getPackageName())) {
					clipboard.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
					Toast toast = Toast.makeText(DefaultActivity.this, "TEXTO COPIADO, COLE AQUI!",  Toast.LENGTH_LONG);
					toast.setGravity(Gravity.TOP|Gravity.CENTER_VERTICAL, 0, 100);
					toast.show();
					return true;
				}
				else
					return false;
			}
		});
    	mShareActionProvider.setShareIntent(shareIntent);
	}

	/* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }
	
	protected class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @SuppressWarnings("rawtypes")
		@Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	        selectItem(position);
	    }
	}
	
	/** Swaps fragments in the main content view */
	protected void selectItem(int position) {
		
		if(position > 0) {
			if(apostila != position)
				licao = 0;
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
		
		// Locate MenuItem with ShareActionProvider
	    MenuItem item = menu.findItem(R.id.action_compartilhar);

	    // Fetch and store ShareActionProvider
	    mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
	    compartilhar();
		return true;
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
	
	// Receives callbacks on various events related to fetching ads.  In this sample,
    // the application displays a message on the screen.  A real application may,
    // for example, fill the ad with a banner promoting a feature.
    private class MyAdListener implements AdListener {

        @Override
        public void onDismissScreen(Ad ad) {}

        @Override
        public void onFailedToReceiveAd(Ad ad, ErrorCode errorCode) {
            mAdStatus.setText(R.string.error_receive_ad);
        }

        @Override
        public void onLeaveApplication(Ad ad) {}

        @Override
        public void onPresentScreen(Ad ad) {}

        @Override
        public void onReceiveAd(Ad ad) { mAdStatus.setText(""); }
    }
}