package br.com.jojun.didaque.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.jojun.didaque.DidaqueApplication;
import br.com.jojun.didaque.util.Constantes;
import br.com.jojun.didaque.util.DBHelper;

@SuppressWarnings("serial")
public class Biblia implements Serializable{
	public String livro;
	public int quantidadeCapitulos;
	public int capitulo;
	public int versiculo;
	public String texto;
	
	public static List<String> getLivros(){
		List<String> livros = new ArrayList<String>();
		SQLiteDatabase db = DBHelper.getBibliaInstance(DidaqueApplication.getContext(), Constantes.ALMEIDA, Constantes.ALMEIDA_VERSAO).getReadableDatabase();
		Cursor cursor = db.query("versiculos group by livro order by ROWID", new String[] { "livro"}, null, null, null, null, null);
		while(cursor.moveToNext()) {
			livros.add(cursor.getString(0));
		}	  
		cursor.close();
		db.close();
		return livros;
	}
	
	public static int getQuantidadeCapitulos(String livro){

		SQLiteDatabase db = DBHelper.getBibliaInstance(DidaqueApplication.getContext(), Constantes.ALMEIDA, Constantes.ALMEIDA_VERSAO).getReadableDatabase();
		Cursor cursor = db.query("versiculos ", new String[] { "capitulo"}, "livro = '"+livro+"' GROUP BY capitulo", null, null, null, null);
		int qtd = cursor.getCount();
		cursor.close();
		db.close();
		return qtd;
	}

	public static List<Biblia> getVersiculos(String livro, int capitulo) {
		List<Biblia> versiculos = new ArrayList<Biblia>();
		SQLiteDatabase db = DBHelper.getBibliaInstance(DidaqueApplication.getContext(), Constantes.ALMEIDA, Constantes.ALMEIDA_VERSAO).getReadableDatabase();
		Cursor cursor = db.query("versiculos ",
                new String[] { "livro, capitulo, versiculo, texto"}, "livro = '"+livro+"' AND capitulo = "+capitulo+" ORDER BY versiculo", null, null, null, null);
		while(cursor.moveToNext()) {
			Biblia b = new Biblia();
			b.livro = cursor.getString(0);
			b.capitulo = cursor.getInt(1);
			b.versiculo = cursor.getInt(2);
			b.texto = cursor.getString(3);
			versiculos.add(b);
		}	  
		cursor.close();
		db.close();
		return versiculos;
	}
	
	public static List<Biblia> getVersiculos(String livro) {
		List<Biblia> versiculos = new ArrayList<Biblia>();
		SQLiteDatabase db = DBHelper.getBibliaInstance(DidaqueApplication.getContext(), Constantes.ALMEIDA, Constantes.ALMEIDA_VERSAO).getReadableDatabase();
		Cursor cursor = db.query("versiculos ",
                new String[] { "livro, capitulo, versiculo, texto"}, "livro = '"+livro+"' ORDER BY versiculo", null, null, null, null);
		while(cursor.moveToNext()) {
			Biblia b = new Biblia();
			b.livro = cursor.getString(0);
			b.capitulo = cursor.getInt(1);
			b.versiculo = cursor.getInt(2);
			b.texto = cursor.getString(3);
			versiculos.add(b);
		}	  
		cursor.close();
		db.close();
		return versiculos;
	}
	
	public static List<Biblia> pesquisar(String pesquisa){
		List<Biblia> versiculos = new ArrayList<Biblia>();
		SQLiteDatabase db = DBHelper.getBibliaInstance(DidaqueApplication.getContext(), Constantes.ALMEIDA, Constantes.ALMEIDA_VERSAO).getReadableDatabase();
		Cursor cursor = db.query("versiculos ",
                new String[] { "livro, capitulo, versiculo, texto"}, " texto MATCH '"+pesquisa+"' ", null, null, null, null);
		while(cursor.moveToNext()) {
			Biblia b = new Biblia();
			b.livro = cursor.getString(0);
			b.capitulo = cursor.getInt(1);
			b.versiculo = cursor.getInt(2);
			b.texto = cursor.getString(3);
			versiculos.add(b);
		}	  
		cursor.close();
		db.close();
		return versiculos;
	}
}
