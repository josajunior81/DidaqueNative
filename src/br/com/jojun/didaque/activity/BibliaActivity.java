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
import android.support.v7.view.ActionMode;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.ShareActionProvider.OnShareTargetSelectedListener;
import android.text.ClipboardManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.com.jojun.didaque.R;
import br.com.jojun.didaque.adapter.BibliaPagerAdapter;
import br.com.jojun.didaque.bean.Biblia;
import br.com.jojun.didaque.fragment.BibliaFragment;
import br.com.jojun.didaque.util.MyAdListener;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.analytics.tracking.android.EasyTracker;

@SuppressWarnings("deprecation")
public class BibliaActivity extends ActionBarActivity {
	private String[] livros;
    protected DrawerLayout mDrawerLayout;
    protected ListView mDrawerList;
    protected CharSequence mTitle;
    protected CharSequence mDrawerTitle;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected ViewPager mViewPager;
    protected List<BibliaFragment> fragments;
    protected BibliaFragment fragmentAtual;
    protected FragmentManager fragmentManager = getSupportFragmentManager();
    protected FragmentTransaction transaction;
    private BibliaPagerAdapter pagerAdapter;
    private AdView mAdView;
    private ShareActionProvider mShareActionProvider;
	private ClipboardManager clipboard;
	private ArrayAdapter<String> drawerAdapter;
	private static final int RESULT_SETTINGS = 1;
	private int capitulo;
	private String nomeLivro;
	private int qtdCapitulos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_biblia);
		
		initActivity(savedInstanceState);
        
	    mAdView = (AdView)findViewById(R.id.ad_biblia);
	    mAdView.setAdListener(new MyAdListener());

	    AdRequest adRequest = new AdRequest();
	    adRequest.addKeyword("sporting goods");
	    mAdView.loadAd(adRequest);
	}

	private void initActivity(Bundle savedInstanceState) {
		mDrawerTitle = "Almeida Recebida";
		mTitle = getResources().getString(R.string.app_name);
		
		clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		
		if(savedInstanceState != null){
//			telaInicial = savedInstanceState.getBoolean("tela");
		} 
		
	    pagerAdapter = new BibliaPagerAdapter(fragmentManager, new ArrayList<BibliaFragment>());
		mViewPager = (ViewPager) findViewById(R.id.pager_biblia);
		pagerAdapter.notifyDataSetChanged();
		mViewPager.setAdapter(pagerAdapter);
	    mViewPager.setOnPageChangeListener(
	            new ViewPager.SimpleOnPageChangeListener() {
	                @Override
	                public void onPageSelected(final int position) {
	                	capitulo = position;
	                	getSupportActionBar().setSelectedNavigationItem(position);
	                }
	            });
	    List<String> alLivros = Biblia.getLivros();
	    livros = alLivros.toArray(new String[alLivros.size()]);
	    
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_biblia);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_biblia);

        // Set the adapter for the list view
        drawerAdapter = new ArrayAdapter<String>(this, R.layout.adapter_list_apostila, livros);
        mDrawerList.setAdapter(drawerAdapter);
        drawerAdapter.notifyDataSetChanged();
        
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
//            	if(telaInicial)
            	mTitle = nomeLivro;
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
		
//        if(!telaInicial) {
//        	apostila = savedInstanceState.getInt("apostila");
//        	licao = savedInstanceState.getInt("licao");
//        	selectItem(apostila);
//        }
		
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
//		outState.putBoolean("tela", telaInicial);
//		outState.putInt("apostila", apostila);
//		outState.putInt("licao", licao);				
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
    	if (mDrawerToggle.onOptionsItemSelected(item)) {
    		return true;
    	}

    	switch (item.getItemId()) {
    	case R.id.action_setup:
    		Intent intent = new Intent(this, PreferenciasActivity.class);
    		startActivityForResult(intent, RESULT_SETTINGS);
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
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
		
		if(nomeLivro != null && !nomeLivro.equalsIgnoreCase(livros[position]))
			capitulo = 0;
		
		nomeLivro = livros[position];

		mDrawerList.setItemChecked(position, true);
	    setTitle(livros[position]);
	    mDrawerLayout.closeDrawer(mDrawerList);
	    
		if( getSupportActionBar().getNavigationMode() != ActionBar.NAVIGATION_MODE_TABS)
			getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	   
	    fragments = new ArrayList<BibliaFragment>();
	    
	    ActionBar.TabListener tabListener = new ActionBar.TabListener() {
	        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
	        	mViewPager.setCurrentItem(tab.getPosition());
	        	capitulo = tab.getPosition();
	        	
//				fragmentAtual = new BibliaFragment();
//	    		ft.replace(R.id.content_frame_biblia, fragmentAtual);

//	        	fragments = new ArrayList<BibliaFragment>();
//	    	    qtdCapitulos = Biblia.getQuantidadeCapitulos(nomeLivro);
//	    	    for (int i = 0; i < qtdCapitulos; i++) {
//	    	    	int ini = 0, fim = 3;
//	    	    	if(i > 0) {
//	    	    		ini = i-1;
//	    	    		fim = ini+2;
//	    	    	}
//	    	    	if(ini < fim) {
//	    		    	BibliaFragment fragment = (BibliaFragment) BibliaFragment.instantiate(BibliaActivity.this, BibliaFragment.class.getName());
//	    	    		fragment.setCapitulo((i+1));
//	    	    		fragment.setLivro(nomeLivro);
//	    	    		fragment.setVersiculos(nomeLivro, (i+1));
//	    	        	fragments.add(fragment);
//	    	    	}
//	    	    }
	        	
	    		if(mShareActionProvider != null)
	    			mShareActionProvider.setShareIntent(compartilhar());
	    	}

	        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
	            // hide the given tab
	        }

	        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
	            // probably ignore this event
	        }
	    };
	    
	    getSupportActionBar().removeAllTabs();
	    
	    qtdCapitulos = Biblia.getQuantidadeCapitulos(livros[position]);
	    for (int i = 0; i < qtdCapitulos; i++) {
	    	BibliaFragment fragment = (BibliaFragment) BibliaFragment.instantiate(BibliaActivity.this, BibliaFragment.class.getName());
    		fragment.setCapitulo((i+1));
    		fragment.setLivro(livros[position]);
//    		fragment.setVersiculos(livros[position], (i+1));
        	fragments.add(fragment);
	    	getSupportActionBar().addTab(getSupportActionBar().newTab()
                    .setText(" - " + (i + 1) + " - ")
                    .setTabListener(tabListener), i, ((i)==capitulo?true : false));
	    }

	    pagerAdapter = new BibliaPagerAdapter(fragmentManager, fragments);
		pagerAdapter.notifyDataSetChanged();
		mViewPager.setAdapter(pagerAdapter);
	}

	@Override
	public void setTitle(CharSequence title) {
	    mTitle = title;
	    getSupportActionBar().setTitle(mTitle);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.biblia_menu, menu);
		
		// Locate MenuItem with ShareActionProvider
	    MenuItem item = menu.findItem(R.id.action_compartilhar);

	    // Fetch and store ShareActionProvider
	    mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
	    mShareActionProvider.setShareIntent(compartilhar());
		return true;
	}
	
    private Intent compartilhar() {
    	if(mShareActionProvider == null)
    		return null;
    	Intent shareIntent = new Intent(Intent.ACTION_SEND);
    	shareIntent.setType("text/plain");
    	String texto = "";

    	shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Compartilhamento do APP Didaquê");
    	shareIntent.putExtra(Intent.EXTRA_TEXT, texto);
    	
    	mShareActionProvider.setOnShareTargetSelectedListener(new OnShareTargetSelectedListener() {
			
			@Override
			public boolean onShareTargetSelected(ShareActionProvider shareActionProvider, Intent intent) {
				if ("com.facebook.katana".equals(intent.getComponent().getPackageName())) {
					clipboard.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
					Toast toast = Toast.makeText(BibliaActivity.this, "TEXTO COPIADO, COLE AQUI!",  Toast.LENGTH_LONG);
					toast.setGravity(Gravity.TOP|Gravity.CENTER_VERTICAL, 0, 100);
					toast.show();
					return true;
				}
				else
					return false;
			}
		});
//    	mShareActionProvider.setShareIntent(shareIntent);
    	return shareIntent;
	}
}
