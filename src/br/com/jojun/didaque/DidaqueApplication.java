package br.com.jojun.didaque;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

public class DidaqueApplication  extends Application {

	private static final String TAG = DidaqueApplication.class.getSimpleName();

	private static Context context;
	
	@Override
	public void onCreate() {
		super.onCreate();
		context = this;

		Log.i(TAG, "Fabricante: " + Build.MANUFACTURER);
		Log.i(TAG, "Aparelho: " + Build.MODEL);
		Log.i(TAG, "Versão: " + Build.VERSION.RELEASE);
	}

	public static Context getContext() {
		return context;
	}
}