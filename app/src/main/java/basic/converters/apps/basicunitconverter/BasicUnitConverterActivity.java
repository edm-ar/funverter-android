package basic.converters.apps.basicunitconverter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class BasicUnitConverterActivity extends Activity {

    private static final String TAG = BasicUnitConverterActivity.class.getSimpleName(); // tag to be used when logging
    private static final String ACTIVITYSUFFIX = "ConverterActivity"; //TODO "ConverterActivity" suffix needs to be set in a different way to avoid hard coding
    private static final String ACTIVITYPACKAGE = "basic.converters.apps.basicunitconverter."; //TODO find better way of getting package path to support refactoring
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_unit_converter);

        // create touch handler
        View.OnTouchListener touchHandler = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                View childView = ((ViewGroup)v).getChildAt(0); // get first item since there's only one
                Intent intent;
                res = getResources();

                // TODO create background color resources to replace hardcoded colors
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundColor(res.getColor(R.color.btnClickColor));
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setBackgroundColor(res.getColor(R.color.btnColor));
                        break;
                }

                String activityName = ((TextView)childView).getText().toString().concat(ACTIVITYSUFFIX);
                String activityFullPath = ACTIVITYPACKAGE.concat(activityName);
                try {
                    // dynamically load activity with full package path and class name
                    intent = new Intent(BasicUnitConverterActivity.this, Class.forName(activityFullPath));
                    Log.i(TAG,"Starting " + activityFullPath + " activity.");
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                    showToast("Oops...there has been an error :(");
                }

                return true;
            }
        };

        // iterate over parent layout's children and add click handler to each of them
        RelativeLayout parentLayout = (RelativeLayout)findViewById(R.id.parentLayout);
        for(int i = 0; i < parentLayout.getChildCount(); i++) {
            View child = parentLayout.getChildAt(i);
            child.setOnTouchListener(touchHandler);
        }
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
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            try {
                intent = new Intent(BasicUnitConverterActivity.this, Class.forName("basic.converters.preferences.SettingsActivity"));
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
}
