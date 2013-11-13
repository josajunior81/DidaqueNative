package br.com.jojun.didaque.fragment;

import java.util.List;

import com.google.ads.al;
import com.google.ads.l;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.jojun.didaque.DidaqueApplication;
import br.com.jojun.didaque.R;
import br.com.jojun.didaque.bean.Biblia;

public class BibliaFragment extends Fragment {
	public TextView tvCapitulo;
	List<Biblia> alBiblia;
	private LinearLayout layoutBiblia;
	private String livro;
	private int capitulo;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_biblia, container, false);
	}
	
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onViewCreated(view, savedInstanceState);
    	layoutBiblia = (LinearLayout)view.findViewById(R.id.layout_biblia);
    	if(capitulo > 0) {
    		
    		if(alBiblia == null)
    			setVersiculos(livro, capitulo);
    		
			for(Biblia b : alBiblia) {
				TextView tv = new TextView(getActivity());
				tv.setText(b.versiculo+". "+b.texto);
				layoutBiblia.addView(tv);
			}
    	}
//    	if(alVersiculos != null) {
//			for(int i=0; i< alVersiculos.size(); i++) {
//				TextView tv = new TextView(getActivity());
//				tv.setText((i+1)+". "+alVersiculos.get(i));
//				layoutBiblia.addView(tv);
//			}
//    	}
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
}
