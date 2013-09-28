package br.com.jojun.didaque.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import br.com.jojun.didaque.DidaqueApplication;
import br.com.jojun.didaque.util.DBHelper;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity implements Runnable{

	public static final String DB_NAME = "didaque-db.sqlite";
	public static final String DB_NAME_ASSETS = "didaque-db.sqlite";
	private long inicio;
	private long fim;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		final Handler handler = new Handler();
		
		new AsyncTask<Void, Void, Void>() {

			protected void onPreExecute() {
				
			}

			@Override
			protected Void doInBackground(Void... params) {
				inicio = Calendar.getInstance().getTimeInMillis();
				fim = 0;
				SQLiteDatabase database = DBHelper.getInstance().getReadableDatabase();
				database.close();
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				long timeDelay = 1000;
				if (fim == 0) {
					fim = Calendar.getInstance().getTimeInMillis();
					long diff = fim - inicio;
					timeDelay = (diff < timeDelay) ? timeDelay - diff : 0;
				}
				
				SQLiteDatabase database = DBHelper.getInstance().getReadableDatabase();
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
				handler.postDelayed(SplashActivity.this, timeDelay);
			}
				

		}.execute();
	}
	
	@Override
	public void run() {
		 // This method will be executed once the timer is over
        // Start your app main activity
        Intent i = new Intent(SplashActivity.this, DefaultActivity.class);
        startActivity(i);

        // close this activity
        finish();		
	}

}
