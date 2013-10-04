package br.com.jojun.didaque.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.com.jojun.didaque.R;
import br.com.jojun.didaque.bean.TextoAleatorio;

public class TextosFragment extends Fragment {
	private TextView tvTexto;
	private TextView tvLicao;
	private TextoAleatorio textoAleatorio;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_textos, container, false);
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onViewCreated(view, savedInstanceState);
    	setTextoAleatorio();
    }
    
    public void setTextoAleatorio(){
		textoAleatorio = TextoAleatorio.getTexoAleatoriamente();
		tvTexto = (TextView)getView().findViewById(R.id.tv_textos_texto);
		tvLicao = (TextView)getView().findViewById(R.id.tv_textos_licao);
		
		tvTexto.setText(Html.fromHtml(textoAleatorio.texto));
		tvLicao.setText(textoAleatorio.licao);
    }
}
