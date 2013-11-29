package br.com.jojun.didaque.fragment;

import java.util.List;
import java.util.Set;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.ShareActionProvider.OnShareTargetSelectedListener;
import android.text.ClipboardManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.jojun.didaque.R;
import br.com.jojun.didaque.activity.BibliaActivity;
import br.com.jojun.didaque.adapter.VersiculoAdapter;
import br.com.jojun.didaque.bean.Biblia;

public class BibliaFragment extends Fragment {
	public TextView tvCapitulo;
	List<Biblia> alBiblia;
	ListView versiculos;
	VersiculoAdapter adapter;
	private String livro;
	private int capitulo;
	private ActionMode mActionMode;
	private int goToVersiculo = -1;
    private ShareActionProvider mShareActionProvider;
	private ClipboardManager clipboard;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_biblia, container, false);
	}
	
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onViewCreated(view, savedInstanceState);
		
    	if(alBiblia == null)
			setVersiculos(livro, capitulo);
		
    	versiculos = (ListView)view.findViewById(R.id.list_versiculos);
    	adapter = new VersiculoAdapter(getActivity(), alBiblia);
    	versiculos.setAdapter(adapter);
    	adapter.notifyDataSetChanged();
    	
    	if(goToVersiculo > -1) {
	    	versiculos.setSelection(goToVersiculo);
	    	versiculos.setItemChecked(goToVersiculo, true);
	    	versiculos.setSelected(true);
	    	goToVersiculo = -1;
    	}
    	
    	
    	final BibliaActivity ba = (BibliaActivity)getActivity();
    	
    	versiculos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				adapter.toggleChecked(position);
				boolean hasCheckedItems = adapter.getCheckedItemCount() > 0;
				if (hasCheckedItems && mActionMode == null) {
					mActionMode = ba.startSupportActionMode(new ActionModeCallback());
				}
				else if (!hasCheckedItems && mActionMode!= null){
					mActionMode.finish();
					mActionMode = null;
				}
				
				if (mActionMode != null)
					mActionMode.setTitle(String.valueOf(adapter.getCheckedItemCount()) + "");
			}
    		
		});
    }

    public void goToVersiculo(int versiculo){
    	goToVersiculo = versiculo;
    	if(versiculos != null) {
	    	versiculos.setSelection(versiculo);
	    	versiculos.setItemChecked(versiculo, true);
	    	versiculos.setSelected(true);
    	}
    }
    
	public void setVersiculos(String livro, int capitulo) {
		alBiblia = Biblia.getVersiculos(livro, capitulo);
	}

	public String getLivro() {
		return livro;
	}

	public void setLivro(String livro) {
		this.livro = livro;
	}

	public int getCapitulo() {
		return capitulo;
	}

	public void setCapitulo(int capitulo) {
		this.capitulo = capitulo;
	}
	
    private Intent compartilhar() {
    	if(mShareActionProvider == null)
    		return null;
    	Intent shareIntent = new Intent(Intent.ACTION_SEND);
    	shareIntent.setType("text/plain");
    	String texto = null;
    	
    	Set<Integer> positions = adapter.getCheckedItems();
    	for(int pos : positions){
    		Biblia b = adapter.getItem(pos);
    		if(texto == null)
    			texto = b.livro+" - "+b.capitulo+"\n";
    		texto+= b.versiculo+". "+b.texto+" ";
    	}

    	shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Compartilhamento do APP Didaquê");
    	shareIntent.putExtra(Intent.EXTRA_TEXT, texto);
    	
    	mShareActionProvider.setOnShareTargetSelectedListener(new OnShareTargetSelectedListener() {
			
			@Override
			public boolean onShareTargetSelected(ShareActionProvider shareActionProvider, Intent intent) {
				if ("com.facebook.katana".equals(intent.getComponent().getPackageName())) {
					clipboard.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
					Toast toast = Toast.makeText(getActivity(), "TEXTO COPIADO, COLE AQUI!",  Toast.LENGTH_LONG);
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
	
	private class ActionModeCallback implements ActionMode.Callback {

	    @Override
	    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	        // Inflate a menu resource providing context menu items
	        MenuInflater inflater = mode.getMenuInflater();
	        inflater.inflate(R.menu.context_menu, menu);
			// Locate MenuItem with ShareActionProvider
		    MenuItem item = menu.findItem(R.id.action_context_compartilhar);

		    // Fetch and store ShareActionProvider
		    mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
		    mShareActionProvider.setShareIntent(compartilhar());
	        
	        return true;
	    }

	    // Called each time the action mode is shown. Always called after onCreateActionMode, but
	    // may be called multiple times if the mode is invalidated.
	    @Override
	    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	        return false; // Return false if nothing is done
	    }

	    // Called when the user selects a contextual menu item
	    @Override
	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	        switch (item.getItemId()) {
	            case R.id.action_context_compartilhar:
	            	adapter.exitMultiMode();
	            	mode.finish(); // Action picked, so close the CAB
	                return true;
	            case R.id.action_context_copiar:
	            	adapter.exitMultiMode();
	            	mode.finish(); // Action picked, so close the CAB
	            	return true;
	            case R.id.action_context_favarito:
	            	adapter.exitMultiMode();
	            	mode.finish(); // Action picked, so close the CAB
	            	return true;	            	
	            default:
	                return false;
	        }
	    }

	    // Called when the user exits the action mode
	    @Override
	    public void onDestroyActionMode(ActionMode mode) {
	    	adapter.exitMultiMode();
	    	mActionMode = null;
	    }
	}; 
}
