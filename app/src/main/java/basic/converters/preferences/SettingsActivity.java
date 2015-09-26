package basic.converters.preferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Edmar on 6/7/2015.
 */
public class SettingsActivity extends AppCompatActivity{

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
