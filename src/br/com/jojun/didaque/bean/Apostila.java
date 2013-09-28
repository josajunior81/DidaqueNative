package br.com.jojun.didaque.bean;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.jojun.didaque.util.DBHelper;

public class Apostila {
	public int numeroLicao;
	public String tituloApostila;
	public String tituloLicao;
	public String catequese;
	public int numeroApostila;
	public static Apostila getLicao(int numeroLicao, int numeroApostila) {
		SQLiteDatabase db = DBHelper.getInstance().getReadableDatabase();
		Cursor cursor = db.query("Apostila ",
                new String[] { "tituloLicao","catequese" }, "numeroLicao = "+numeroLicao+" AND numeroApostila = "+numeroApostila, null, null, null, null);
		Log.i("A", ""+cursor.getCount());
		if(!cursor.moveToFirst())
			return null;
		else {
			Apostila a = new Apostila();
			a.tituloLicao = cursor.getString(0);
			a.catequese = cursor.getString(1);
			cursor.close();
			db.close();
			return a;
		}
	}
	
	public static int getQuantidadeLicoes(int apostila) {
		SQLiteDatabase db = DBHelper.getInstance().getReadableDatabase();
		Cursor cursor = db.query("Apostila ",
                new String[] { "tituloLicao","catequese" }, " numeroApostila = "+apostila, null, null, null, null);
		Log.i("A", ""+cursor.getCount());
		int quantidade = cursor.getCount();
		cursor.close();
		db.close();
		return quantidade;
	}
}
