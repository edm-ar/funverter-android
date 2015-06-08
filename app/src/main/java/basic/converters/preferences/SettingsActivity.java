package basic.converters.preferences;

import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;

/**
 * Created by Edmar on 6/7/2015.
 */
public class SettingsActivity extends PreferenceActivity{

    private static final String TAG = SettingsActivity.class.getSimpleName(); // tag to be used when logging

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
