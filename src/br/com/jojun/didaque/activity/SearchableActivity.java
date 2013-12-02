package br.com.jojun.didaque.activity;

import java.util.List;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.jojun.didaque.R;
import br.com.jojun.didaque.adapter.PesquisaAdapter;
import br.com.jojun.didaque.bean.Biblia;

import com.google.analytics.tracking.android.EasyTracker;

public class SearchableActivity extends ActionBarActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_pesquisa_biblia);
	    
	    getSupportActionBar().setTitle("Pesquisa");
	    getSupportActionBar().setHomeButtonEnabled(true);
	    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    
	    // Get the intent, verify the action and get the query
	    Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      pesquisar(query);
	    }
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

	private void pesquisar(String query) {
		final List<Biblia> list = Biblia.pesquisar(query);
		ListView lv = (ListView)findViewById(android.R.id.list);
		
		final PesquisaAdapter adapter = new PesquisaAdapter(this, list);
		lv.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				Intent data = new Intent();
				Biblia b = adapter.getItem(position);
				data.putExtra("biblia", b);
				setResult(RESULT_OK, data);
				finish();
			}			
		});
		
		Log.i("SEARCH", query+" "+list.size());
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	finish();
	            return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
}
