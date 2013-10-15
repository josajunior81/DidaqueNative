package br.com.jojun.didaque.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.com.jojun.didaque.R;
import br.com.jojun.didaque.bean.Apostila;

public class LicaoFragment extends Fragment {
	public TextView tvTitulo;
	public TextView tvCatequese;
	public Apostila apostila;
	public int numeroApostila;
	public int numeroLicao;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_licao, container, false);
	}
	
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onViewCreated(view, savedInstanceState);
		apostila = Apostila.getLicao(numeroLicao, numeroApostila);
		
		tvTitulo = (TextView)view.findViewById(R.id.tv_licao_titulo);
		tvCatequese = (TextView)view.findViewById(R.id.tv_licao_catequese);
		if(apostila != null) {
			tvTitulo.setText(apostila.tituloLicao);
			tvCatequese.setText(Html.fromHtml(apostila.catequese));
		}	
    }

	public int getNumeroApostila() {
		return numeroApostila;
	}

	public void setNumeroApostila(int numeroApostila) {
		this.numeroApostila = numeroApostila;
	}

	public int getNumeroLicao() {
		return numeroLicao;
	}

	public void setNumeroLicao(int numeroLicao) {
		this.numeroLicao = numeroLicao;
	}
}
