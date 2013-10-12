package br.com.jojun.didaque;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;
import br.com.jojun.didaque.util.DBHelper;

public class DidaqueApplication  extends Application {

	private static final String TAG = DidaqueApplication.class.getSimpleName();
	private final String DB_NAME = "didaque-db.sqlite";
	private final String DB_NAME_ASSETS = "didaque-db.sqlite";
	private long inicio;
	private long fim;
	

	private static Context context;
	
	@Override
	public void onCreate() {
		super.onCreate();
		context = this;

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

		final Cursor cursor = database.rawQuery("SELECT COUNT() as total FROM sqlite_master WHERE name ='Apostila'", null);
		cursor.moveToNext();
		if(cursor.getInt(0) <= 0) {
			File dbFile = DidaqueApplication.getContext().getDatabasePath(DB_NAME);
			InputStream is;
			try {
				is = DidaqueApplication.getContext().getAssets().open(DB_NAME_ASSETS);

				OutputStream os = new FileOutputStream(dbFile);

				byte[] buffer = new byte[1024];
				while (is.read(buffer) > 0) {
					os.write(buffer);
				}

				os.flush();
				os.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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