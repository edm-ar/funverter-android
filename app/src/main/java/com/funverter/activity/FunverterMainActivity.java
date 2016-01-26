package com.funverter.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.funverter.converter.apps.converters.R;
import com.tjeannin.apprate.AppRate;


public class FunverterMainActivity extends AppCompatActivity {
    // tag to be used when logging
    private static final String TAG = FunverterMainActivity.class.getSimpleName();
    //TODO "ConverterActivity" suffix needs to be set in a different way to avoid hard coding
    public static final String ACTIVITYSUFFIX = "ConverterActivity";
    //TODO find better way of getting package path to support refactoring
    public static final String ACTIVITYPACKAGE = "com.funverter.activity.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            // short sleep for presentation screen
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.ConverterHomeTheme);
        super.onCreate(savedInstanceState);
//        overridePendingTransition(R.animator.enter, R.animator.exit);
        setContentView(R.layout.activity_funverter_main);

        // adjust layout for specific devices
        LinearLayout funLayout = (LinearLayout)findViewById(R.id.funverver_menu);
        LinearLayout.LayoutParams funParams = (LinearLayout.LayoutParams)funLayout.getLayoutParams();

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        if(metrics.densityDpi > metrics.DENSITY_HIGH
                && metrics.densityDpi <= metrics.DENSITY_XHIGH) {
            funParams.weight = 1.5f;
            funLayout.setLayoutParams(funParams);
        } else if(metrics.densityDpi > metrics.DENSITY_XHIGH
                && metrics.densityDpi <= metrics.DENSITY_XXHIGH) {
            funParams.weight = 1f;
            funLayout.setLayoutParams(funParams);
        }

        new AppRate(this)
                .setMinDaysUntilPrompt(7)
                .setMinLaunchesUntilPrompt(10)
                .setShowIfAppHasCrashed(false)
                .init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.basic_unit_converter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so double
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            try {
                intent = new Intent(FunverterMainActivity.this,
                        Class.forName("com.funverter.preferences.SettingsActivity"));
                startActivity(intent);
            } catch(ClassNotFoundException e) {
                Log.e(TAG,e.getMessage(),e);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void onClick(View v) {
        Intent intent;
        String activityName = v.getContentDescription().toString().concat(ACTIVITYSUFFIX);
        String activityFullPath = ACTIVITYPACKAGE.concat(activityName);
        try {
            // dynamically load activity with full package path and class name
            intent = new Intent(FunverterMainActivity.this, Class.forName(activityFullPath));
            Log.i(TAG,"Starting " + activityFullPath + " activity.");
            startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            showToast("Oops...there has been an error :(");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.animator.left_to_right, R.animator.right_to_left);
    }
}
