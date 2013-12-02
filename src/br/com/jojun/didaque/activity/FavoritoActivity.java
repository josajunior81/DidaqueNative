package br.com.jojun.didaque.activity;

import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.jojun.didaque.R;
import br.com.jojun.didaque.adapter.FavoritoAdapter;
import br.com.jojun.didaque.bean.Biblia;

import com.google.analytics.tracking.android.EasyTracker;

public class FavoritoActivity  extends ActionBarActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_favorito_biblia);
	    
		final List<Biblia> list = Biblia.favoritos();
		ListView lv = (ListView)findViewById(android.R.id.list);
		
		final FavoritoAdapter adapter = new FavoritoAdapter(this, list);
		lv.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//				Intent data = new Intent();
//				Biblia b = adapter.getItem(position);
//				data.putExtra("biblia", b);
//				setResult(RESULT_OK, data);
//				finish();
			}			
		});
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

}
