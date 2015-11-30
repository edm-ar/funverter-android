package com.funverter.preferences;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Toast;

import com.funverter.converter.apps.converters.R;

/**
 * Created by Edmar on 6/7/2015.
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private static final String TAG = SettingsActivity.class.getSimpleName(); // tag to be used when logging

    private static final int SEND_FEEDBACK_REQUEST = 0;
    private PackageInfo packageInfo;
    private PackageManager packageManager;
    Preference sendFeedbackPref;
    Preference versionNamePref;
    Preference versionCodePref;
    Preference buildVersionPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from XML resource
        addPreferencesFromResource(R.xml.preferences);

        sendFeedbackPref = findPreference("pref_key_send_feedback");
        sendFeedbackPref.setOnPreferenceClickListener(this);

        versionNamePref = findPreference("pref_key_version_name");
        versionCodePref = findPreference("pref_key_version_code");
        buildVersionPref = findPreference("pref_key_build_version");

        setAboutPreferences();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch(preference.getKey()) {
            case "pref_key_send_feedback":
                sendFeedbackEmail();
                break;
            default:
                //do nothing
                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast toast = null;

        switch (resultCode) {
            case Activity.RESULT_OK:
                if(requestCode == SEND_FEEDBACK_REQUEST) {
                    toast = Toast.makeText(getActivity(),
                            "Feedback sent :)",
                            Toast.LENGTH_SHORT);
                }
                break;
            case Activity.RESULT_CANCELED:
                toast = Toast.makeText(getActivity(),
                        "Oops...something went wrong :(",
                        Toast.LENGTH_SHORT);
                break;
            default:
                //do nothing
                break;
        }

        if(toast != null) {
            toast.show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void sendFeedbackEmail() {
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
    }

    public void setAboutPreferences() {
        Activity activity = getActivity();
        packageManager = activity.getPackageManager();
        try {
            packageInfo = packageManager.getPackageInfo(activity.getPackageName(), 0);
            versionNamePref.setSummary(packageInfo.versionName);
            buildVersionPref.setSummary(Build.VERSION.RELEASE);
        } catch(PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
}
