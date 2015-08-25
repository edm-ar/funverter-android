package basic.converters.apps.basicunitconverter;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.EnumUtils;

import basic.converters.util.ConversionEntriesDataSource;
import basic.converters.util.ConversionEntry;
import basic.converters.util.TimeUnitExtension;

//TODO update timeunit converter
public class TimeConverterActivity extends Activity {
    private static final String TAG = TimeConverterActivity.class.getSimpleName(); // tag to be used when logging

    private AutoCompleteTextView textInput;
    private Spinner toSpinner;
    private Spinner fromSpinner;
    private ImageButton calculateBtn;
    private TextView textOutput;
    private boolean isFirstSelection = true;
    private Resources res;

    private ConversionEntriesDataSource dataSource;

    private InputMethodManager imm;

    private static final String TABLE_NAME = "time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.animator.enter, R.animator.exit);
        setContentView(R.layout.activity_time_converter);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);

        dataSource = new ConversionEntriesDataSource(this);
        dataSource.open();

        imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);

        res = getResources();

        textInput = (AutoCompleteTextView)findViewById(R.id.textInput);
        fromSpinner = (Spinner)findViewById(R.id.fromSpinner);
        calculateBtn = (ImageButton) findViewById(R.id.calculateBtn);
        textOutput = (TextView) findViewById(R.id.textOutput);

        Log.i(TAG, "Adding " + TABLE_NAME + " to autocomplete view");
        // set adapter for autocomplete
        List<ConversionEntry> entries = dataSource.getAllTableConversionEntries(TABLE_NAME);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, entries);
        textInput.setAdapter(adapter);

        // add item click listener to spinner
        Spinner.OnItemSelectedListener spinListener = new ItemListener();
        fromSpinner.setOnItemSelectedListener(spinListener);

        // create button listener
        View.OnClickListener listener = new ButtonListener();
        calculateBtn.setOnClickListener(listener);

        textInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imm.showSoftInput(v, 0); // show keyboard when input field is tapped
                return true;
            }
        });
    }

    private class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            buttonClickHandler();
        }
    }

    private class ItemListener implements Spinner.OnItemSelectedListener {

        public void onNothingSelected(AdapterView<?> parent) {
            // do nothing
        }

        public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
            if(isFirstSelection) {
                isFirstSelection = false;
            } else {
                itemSelectedHandler(parent, view, position, id);
            }
        }
    }

    private void itemSelectedHandler(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "Select from: " + parent.getSelectedItem());
        ArrayList<String> units = new ArrayList<String>();
        units.add(res.getString(R.string.to_prompt));

        for(String unit : res.getStringArray(R.array.time_units)) {
            //TODO find a better way of avoiding the addition of non-unit values
            if(!unit.equals(parent.getSelectedItem().toString()) && !unit.contains(res.getString(R.string.convert))) {
                units.add(unit);
            }
        }

        LinearLayout spinnersContainer = (LinearLayout)findViewById(R.id.spinnersContainer);
        if(spinnersContainer.findViewById(R.id.toSpinner) != null) {
            spinnersContainer.removeView(findViewById(R.id.toSpinner));
        }

        toSpinner = new Spinner(this);
        toSpinner.setId(R.id.toSpinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, units);
        toSpinner.setAdapter(spinnerArrayAdapter);
        spinnersContainer.addView(toSpinner);
    }

    private void buttonClickHandler() {
        imm.hideSoftInputFromWindow(textInput.getWindowToken(), 0); // hide keyboard once calculation is executed

        String inputText = textInput.getText().toString();
        if(inputText.isEmpty()) {
            showToast("Please enter a valid number");
            return;
        }

        Float input = Float.parseFloat(inputText);
        Float x;
        String result = "";

        if(!String.valueOf(fromSpinner.getSelectedItem()).contains(res.getString(R.string.convert))
                && !String.valueOf(toSpinner.getSelectedItem()).contains(res.getString(R.string.convert))) {
            // convert all to upper case so that TimeUnit can find them in enum
            String fromUnit = fromSpinner.getSelectedItem().toString().toUpperCase();
            String toUnit = toSpinner.getSelectedItem().toString().toUpperCase();

            try {
                if (EnumUtils.isValidEnum(TimeUnit.class, fromUnit)
                        && EnumUtils.isValidEnum(TimeUnit.class, toUnit)) { // handles conversions between days, hours, seconds and minutes
                    result = String.valueOf(TimeUnit.valueOf(toUnit)
                            .convert(Long.parseLong(inputText), TimeUnit.valueOf(fromUnit)));
                } else if (!EnumUtils.isValidEnum(TimeUnit.class, fromUnit)
                        && !EnumUtils.isValidEnum(TimeUnit.class, toUnit)) {
                    result = String.valueOf(TimeUnitExtension.valueOf(toUnit)
                            .convert(Long.parseLong(inputText), TimeUnitExtension.valueOf(fromUnit)));
                } else if (EnumUtils.isValidEnum(TimeUnit.class, fromUnit)
                        && !EnumUtils.isValidEnum(TimeUnit.class, toUnit)) {
                    result = String.valueOf(TimeUnitExtension.valueOf(toUnit)
                            .convert((long) (Double.parseDouble(inputText)
                                    * TimeUnit.valueOf(fromUnit).toMillis(1)))); // handles decimal inputs
                } else {
                    long d = (long) (Double.parseDouble(inputText)
                            * TimeUnitExtension.valueOf(fromUnit).getMillis());// handles decimal inputs
                    result = String.valueOf(TimeUnit.valueOf(toUnit).convert(d, TimeUnit.MILLISECONDS)); // use milliseconds since anything can be converted from milliseconds
                }
            } catch (NumberFormatException nfe) {
                Log.e(TAG, nfe.getMessage(), nfe);
                showToast("Decimal numbers not supported for this conversion");
            }
        }
        else {
            showToast("Please pick units to convert from and to");
            return;
        }

        if(!result.isEmpty()) {
            dataSource.createConversionEntry(String.valueOf(input), TABLE_NAME);
            textOutput.setText(result);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        dataSource.open();
    }

    @Override
    public void onPause(){
        super.onPause();
        dataSource.close();
    }

    @Override
    public void onStop() {
        super.onStop();
        dataSource.close();
    }

    private void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                finish();
                overridePendingTransition(R.animator.left_to_right, R.animator.right_to_left);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}