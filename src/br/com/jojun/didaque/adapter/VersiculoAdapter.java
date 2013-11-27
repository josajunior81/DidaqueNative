package br.com.jojun.didaque.adapter;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.jojun.didaque.bean.Biblia;
import br.com.jojun.didaque.R;

public class VersiculoAdapter extends BaseAdapter {
	
	private List<Biblia> list = Collections.emptyList();
	private LayoutInflater inflater;
	private HashSet<Integer> checkedItems;
	private boolean multiMode;
	
	public VersiculoAdapter(final Context context, final List<Biblia> list) {
		this.inflater = LayoutInflater.from(context);
		this.list = list;
		this.checkedItems = new HashSet<Integer>();
	}
	
	public void enterMultiMode(){
        this.multiMode = true;
        this.notifyDataSetChanged();
    }
	
	public void exitMultiMode() {
        this.checkedItems.clear();
        this.multiMode = false;
        this.notifyDataSetChanged();
    }
	
	public void setChecked(int pos, boolean checked) {
        if (checked) {
            this.checkedItems.add(Integer.valueOf(pos));
        } else {
            this.checkedItems.remove(Integer.valueOf(pos));
        }
        if (this.multiMode) {
            this.notifyDataSetChanged();
        }
    }
	
	public void toggleChecked(int pos) {
        final Integer v = Integer.valueOf(pos);
        if (this.checkedItems.contains(v)) {
            this.checkedItems.remove(v);
        } else {
            this.checkedItems.add(v);
        }
        this.notifyDataSetChanged();
    }

    public int getCheckedItemCount() {
        return this.checkedItems.size();
    }
	
	public boolean isChecked(int pos) {
        return this.checkedItems.contains(Integer.valueOf(pos));
    }

	public Biblia getFirstCheckedItem() {
        for (Integer i : this.checkedItems) {
            return this.list.get(i.intValue());
        }
        return null;
    }

    public Set<Integer> getCheckedItems() {
        return this.checkedItems;
    }
	
    public void clear() {
        this.list.clear();
    }
    
    public void updateData(Biblia[] data) {
        for (int i = 0; i < data.length; i++) {
            this.list.add(data[i]);
        }
        this.checkedItems.clear();
        this.notifyDataSetChanged();
    }
    
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Biblia getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
    public boolean hasStableIds() {
        return true;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listview_versiculos, null);
		}
		TextView tvNumero = (TextView) convertView.findViewById(R.id.versiculo_numero);
		TextView tvTexto = (TextView) convertView.findViewById(R.id.versiculo_texto);
		
		Biblia b = getItem(position);
		
		tvNumero.setText(b.versiculo+".");
		tvTexto.setText(b.texto);
		
		convertView.setBackgroundResource(this.multiMode ? R.drawable.selector_list_multimode : R.drawable.selector_list);
		
		if (checkedItems.contains(Integer.valueOf(position))) {
            convertView.getBackground().setState(new int[] { android.R.attr.state_checked });
        } else {
            convertView.getBackground().setState(new int[] { -android.R.attr.state_checked });
        }
		
		return convertView;
	}

}
