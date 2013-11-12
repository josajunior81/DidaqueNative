package br.com.jojun.didaque.activity;

import java.util.Locale;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import br.com.jojun.didaque.R;

public class PreferenciasActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	
	private ListPreference myPreference;
	
	@SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		Log.i("PREF", key);
		myPreference = (ListPreference) findPreference(key);
		if(myPreference != null) {
			Locale locale = new Locale(sharedPreferences.getString("prefIdioma", "PT"));
			Locale.setDefault(locale);
			Configuration config = new Configuration();
			config.locale = locale;
			getBaseContext().getResources().updateConfiguration(config, 
			getBaseContext().getResources().getDisplayMetrics());
			myPreference.setEntries(R.array.idiomas);
			myPreference.setEntryValues(R.array.idiomasValores);
			myPreference.setTitle(R.string.prefTituloIdiomas);
		}
//		finish();
	}
}
