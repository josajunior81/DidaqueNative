package br.com.jojun.didaque.bean;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.jojun.didaque.DidaqueApplication;
import br.com.jojun.didaque.util.DBHelper;

public class TextoAleatorio {
	public String texto;
	public String licao;
	public String idioma;
	
	public static TextoAleatorio getTexoAleatoriamente() {
		SQLiteDatabase db = DBHelper.getInstance().getReadableDatabase();
		Cursor cursor = db.query("TextoAleatorio ",
                new String[] { "licao","texto" }, " idioma = '"+DidaqueApplication.LANG+"' Order BY RANDOM() LIMIT 1", null, null, null, null);
		if(!cursor.moveToFirst())
			return null;
		else {
			TextoAleatorio ta = new TextoAleatorio();
			ta.licao = cursor.getString(0);
			ta.texto = cursor.getString(1);
			return ta;
		}
	}
}
