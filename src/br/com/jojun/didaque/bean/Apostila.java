package br.com.jojun.didaque.bean;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.jojun.didaque.DidaqueApplication;
import br.com.jojun.didaque.util.DBHelper;

public class Apostila {
	public int numeroLicao;
	public String tituloApostila;
	public String tituloLicao;
	public String catequese;
	public int numeroApostila;
	public String idioma;
	
	public static Apostila getLicao(int numeroLicao, int numeroApostila) {
		SQLiteDatabase db = DBHelper.getInstance().getReadableDatabase();
		Cursor cursor = db.query("Apostila ",
                new String[] { "tituloLicao","catequese" }, "numeroLicao = "+numeroLicao+" AND numeroApostila = "+numeroApostila+" AND idioma = '"+DidaqueApplication.LANG+"'", null, null, null, null);
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
                new String[] { "tituloLicao","catequese" }, " numeroApostila = "+apostila+" AND idioma = '"+DidaqueApplication.LANG+"'", null, null, null, null);
		int quantidade = cursor.getCount();
		cursor.close();
		db.close();
		return quantidade;
	}
}
