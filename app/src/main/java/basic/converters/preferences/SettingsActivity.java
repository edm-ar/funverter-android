package basic.converters.preferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import basic.converters.apps.funverter.R;

/**
 * Created by Edmar on 6/7/2015.
 */
public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = SettingsActivity.class.getSimpleName(); // tag to be used when logging

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.animator.enter, R.animator.exit);
        // Display the fragment as the main content
        SettingsFragment fragment = new SettingsFragment();
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.left_to_right, R.animator.right_to_left);
    }
}
