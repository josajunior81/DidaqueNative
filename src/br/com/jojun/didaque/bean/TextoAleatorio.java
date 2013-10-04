package br.com.jojun.didaque.bean;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.jojun.didaque.util.DBHelper;

public class TextoAleatorio {
	public String texto;
	public String licao;
	
	public static TextoAleatorio getTexoAleatoriamente() {
		SQLiteDatabase db = DBHelper.getInstance().getReadableDatabase();
		Cursor cursor = db.query("TextoAleatorio Order BY RANDOM() LIMIT 1",
                new String[] { "licao","texto" }, null, null, null, null, null);
		cursor.moveToFirst();
		TextoAleatorio ta = new TextoAleatorio();
		ta.licao = cursor.getString(0);
		ta.texto = cursor.getString(1);
		return ta;
	}
}
