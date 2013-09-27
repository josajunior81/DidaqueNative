package br.com.jojun.didaque.util;

import br.com.jojun.didaque.DidaqueApplication;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
//	private static final String TAG = DBHelper.class.getSimpleName();
	private static DBHelper instance;
	private static String dbName;
	
    public DBHelper(Context context) {
        super(context, dbName, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersoin, int newVersion) {
	}
	
	public static DBHelper getInstance(Context context){
		if(instance == null)
			instance = new DBHelper(context);
		
		return instance;
	}
	
	public static DBHelper getInstance(){
		if(instance == null) {
	        ApplicationInfo ai;
			try {
				ai = DidaqueApplication.getContext().getPackageManager().getApplicationInfo(DidaqueApplication.getContext().getPackageName(), PackageManager.GET_META_DATA);
		        Bundle bundle = ai.metaData;
		        dbName = bundle.getString("DATABASE_NAME"); 
			} catch (NameNotFoundException e) {
				dbName = "database.sqlite";
			}
			instance = new DBHelper(DidaqueApplication.getContext());
		}
		
		return instance;
	}

}
