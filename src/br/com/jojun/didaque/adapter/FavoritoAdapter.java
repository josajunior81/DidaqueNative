package br.com.jojun.didaque.adapter;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;
import br.com.jojun.didaque.R;
import br.com.jojun.didaque.bean.Biblia;

public class FavoritoAdapter extends BaseAdapter implements SectionIndexer {
	private List<Biblia> list = Collections.emptyList();
	private LayoutInflater inflater;
	
	public FavoritoAdapter(final Context context, final List<Biblia> list) {
		this.inflater = LayoutInflater.from(context);
		this.list = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Biblia getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		if (view == null) {
			view = inflater.inflate(R.layout.listview_favorito_biblia, null);
		}
		
		TextView tvLivro = (TextView) view.findViewById(R.id.tv_favorito_livro);
		TextView tvTexto = (TextView) view.findViewById(R.id.tv_favorito_texto);
		
		Biblia b = getItem(position);
		
		tvLivro.setText(b.livro);
		tvTexto.setText(b.texto);
		
		return view;
	}
	@Override
	public int getPositionForSection(int sectionIndex) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getSectionForPosition(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return null;
	}

}
