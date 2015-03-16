package basic.converters.apps.basicunitconverter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.EnumUtils;

import basic.converters.utilities.ConversionEntriesDataSource;
import basic.converters.utilities.TimeUnitExtension;

public class TimeConverterActivity extends Activity {
    private static final String TAG = TimeConverterActivity.class.getSimpleName(); // tag to be used when logging

    private EditText textInput;
    private Spinner toSpinner;
    private Spinner fromSpinner;
    private Button calculateBtn;
    private TextView textOutput;
    private boolean isFirstSelection = true;
    private Resources res;

    private ConversionEntriesDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_converter);

        dataSource = new ConversionEntriesDataSource(this);
        dataSource.open();

        res = getResources();

        textInput = (EditText)findViewById(R.id.textInput);
        fromSpinner = (Spinner)findViewById(R.id.fromSpinner);
        calculateBtn = (Button) findViewById(R.id.calculateBtn);
        textOutput = (TextView) findViewById(R.id.textOutput);

        // add item click listener to spinner
        Spinner.OnItemSelectedListener spinListener = new ItemListener();
        fromSpinner.setOnItemSelectedListener(spinListener);

        // create button listener
        View.OnClickListener listener = new ButtonListener();
        calculateBtn.setOnClickListener(listener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time_converter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            if(!unit.equals(parent.getSelectedItem().toString()) && !unit.contains(res.getString(R.string.convert))) { //TODO find a better way of avoiding the addition of non-unit values
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

        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
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

            if(EnumUtils.isValidEnum(TimeUnit.class, fromUnit) && EnumUtils.isValidEnum(TimeUnit.class, toUnit)) { // handles conversions between days, hours, seconds and minutes
                result = String.valueOf(TimeUnit.valueOf(toUnit).convert(Long.parseLong(inputText),TimeUnit.valueOf(fromUnit)));
            } else if(!EnumUtils.isValidEnum(TimeUnit.class, fromUnit) && !EnumUtils.isValidEnum(TimeUnit.class, toUnit)) {
                result = String.valueOf(TimeUnitExtension.valueOf(toUnit).convert(Long.parseLong(inputText),TimeUnitExtension.valueOf(fromUnit)));
            } else if(EnumUtils.isValidEnum(TimeUnit.class, fromUnit) && !EnumUtils.isValidEnum(TimeUnit.class, toUnit)) {
                result = String.valueOf(TimeUnitExtension.valueOf(toUnit).convert((long)(Double.parseDouble(inputText) * TimeUnit.valueOf(fromUnit).toMillis(1)))); // handles decimal inputs
            } else {
                long d = (long)(Double.parseDouble(inputText) * TimeUnitExtension.valueOf(fromUnit).getMillis());// handles decimal inputs
                result = String.valueOf(TimeUnit.valueOf(toUnit).convert(d, TimeUnit.MILLISECONDS)); // use milliseconds since anything can be converted from milliseconds
            }
        }
        else {
            showToast("Please pick units to convert from and to");
            return;
        }

        if(!result.isEmpty()) {
            dataSource.createConversionEntry(String.valueOf(input), "time");
            textOutput.setText(result);
        }
    }

    private void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}