package basic.converters.preferences;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import basic.converters.apps.basicunitconverter.R;

/**
 * Created by Edmar on 6/7/2015.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
