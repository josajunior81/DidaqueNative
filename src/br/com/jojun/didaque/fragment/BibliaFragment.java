package br.com.jojun.didaque.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.ActionMode;
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
	
	private class ActionModeCallback implements ActionMode.Callback {

	    @Override
	    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	        // Inflate a menu resource providing context menu items
	        MenuInflater inflater = mode.getMenuInflater();
	        inflater.inflate(R.menu.context_menu, menu);
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
	            case R.id.action_compartilhar:
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
