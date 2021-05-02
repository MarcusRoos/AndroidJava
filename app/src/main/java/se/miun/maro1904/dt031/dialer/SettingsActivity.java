package se.miun.maro1904.dt031.dialer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import java.io.File;
import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            Preference pref = findPreference(getString(R.string.deleteStored));
            pref.setOnPreferenceClickListener(preference -> clickMe());
            File directoryDefault = new File(getResources().getString(R.string.extractDirectory));
            File[] arrayFiles = directoryDefault.listFiles();
            String[] names = new String[arrayFiles.length];
            for (int i = 0; i < arrayFiles.length; i++) {
                names[i] = arrayFiles[i].getName();
            }

            ListPreference voicePref = findPreference("voiceList");
            voicePref.setEntries(names); // what is displayed
            voicePref.setEntryValues(names); // what is stored

        }


        private boolean clickMe() {
            SharedPreferences preferences = this.getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
            preferences.edit().clear().apply();
            return true;
        }




    }

}