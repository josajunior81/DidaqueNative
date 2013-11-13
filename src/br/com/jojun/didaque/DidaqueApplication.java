package br.com.jojun.didaque;

import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import br.com.jojun.didaque.util.Constantes;
import br.com.jojun.didaque.util.DBHelper;

public class DidaqueApplication  extends Application {

	private static final String TAG = DidaqueApplication.class.getSimpleName();
	@SuppressLint("DefaultLocale")
	public static String LANG;
	private long inicio;
	private long fim;
	

	private static Context context;
	
	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		Locale locale = new Locale(sharedPrefs.getString("prefIdioma", "PT"));
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config, 
		getBaseContext().getResources().getDisplayMetrics());
		
		LANG = context.getResources().getConfiguration().locale.getLanguage().toUpperCase();
		if(!LANG.equalsIgnoreCase("PT") && !LANG.equalsIgnoreCase("ES") )
			LANG = "PT";
		
		Log.i(TAG, "Fabricante: " + Build.MANUFACTURER);
		Log.i(TAG, "Aparelho: " + Build.MODEL);
		Log.i(TAG, "Versão: " + Build.VERSION.RELEASE);

		inicio = Calendar.getInstance().getTimeInMillis();
		fim = 0;
		SQLiteDatabase database = DBHelper.getInstance().getReadableDatabase();
		
		long timeDelay = 1000;
		if (fim == 0) {
			fim = Calendar.getInstance().getTimeInMillis();
			long diff = fim - inicio;
			timeDelay = (diff < timeDelay) ? timeDelay - diff : 0;
		}

		Cursor cursor = database.rawQuery("SELECT COUNT() as total FROM sqlite_master WHERE name ='Apostila'", null);
		cursor.moveToNext();
		if(cursor.getInt(0) <= 0) {
			DBHelper.copyDatabase();
		}
		cursor.close();
		database.close();
		
		database = DBHelper.getBibliaInstance(getContext(), Constantes.ALMEIDA, Constantes.ALMEIDA_VERSAO).getReadableDatabase();

		cursor = database.rawQuery("SELECT COUNT() as total FROM sqlite_master WHERE name ='versiculos'", null);
		cursor.moveToNext();
		if(cursor.getInt(0) <= 0) {
			DBHelper.copyBibliaDatabase(Constantes.ALMEIDA, Constantes.ALMEIDA_VERSAO);
		}
		cursor.close();
		database.close();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	public static Context getContext() {
		return context;
	}
}