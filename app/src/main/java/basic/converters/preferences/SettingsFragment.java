package basic.converters.preferences;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import basic.converters.apps.basicunitconverter.R;

/**
 * Created by Edmar on 6/7/2015.
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private static final int SEND_FEEDBACK_REQUEST = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from XML resource
        addPreferencesFromResource(R.xml.preferences);

        Preference sendFeedbackPref = findPreference("pref_key_send_feedback");
        sendFeedbackPref.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ejsgon@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");

        try {
            startActivityForResult(Intent.createChooser(emailIntent,
                    "Send feedback using: "), SEND_FEEDBACK_REQUEST);
        } catch(ActivityNotFoundException e) {
            Toast toast = Toast.makeText(getActivity(),
                    "Oops...there doesn't seem to be any email clients available.",
                    Toast.LENGTH_SHORT);
            toast.show();
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SEND_FEEDBACK_REQUEST) {
            if(resultCode == 0) {
                Toast toast = Toast.makeText(getActivity(),
                        "Feedback sent :)",
                        Toast.LENGTH_SHORT);
                toast.show();
            } else if(resultCode == -1) {
                Toast toast = Toast.makeText(getActivity(),
                        "Oops...something went wrong :(",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
