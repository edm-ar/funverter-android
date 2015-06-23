package basic.converters.apps.basicunitconverter;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import basic.converters.util.ConversionEntriesDataSource;
import basic.converters.util.ConversionEntry;
import basic.converters.util.DistanceUnit;


public class DistanceConverterActivity extends Activity {

    private static final String TAG = DistanceConverterActivity.class.getSimpleName(); // tag to be used when logging

    private AutoCompleteTextView textInput;
    private Spinner toSpinner;
    private Spinner fromSpinner;
    private Button calculateBtn;
    private boolean isFirstSelection = true;
    private TextView textOutput;
    private Resources res;

    private ConversionEntriesDataSource dataSource;

    private static final String TABLE_NAME = "distance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_converter);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);

        dataSource = new ConversionEntriesDataSource(this);
        dataSource.open();

        res = getResources();

        textInput = (AutoCompleteTextView) findViewById(R.id.textInput);
        fromSpinner = (Spinner)findViewById(R.id.fromSpinner);
        calculateBtn = (Button) findViewById(R.id.calculateBtn);
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

    private class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            buttonClickHandler();
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        if(isFirstSelection) {
            isFirstSelection = false;
        } else {
            itemSelectedHandler(parent, view, position, id);
        }
    }

    private void itemSelectedHandler(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "Select from: " + parent.getSelectedItem());
        ArrayList<String> units = new ArrayList<String>();
        units.add(res.getString(R.string.to_prompt));

        for(String unit : res.getStringArray(R.array.distance_units)) {
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

        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(textInput.getWindowToken(), 0); // hide keyboard once calculation is executed

        String inputText = textInput.getText().toString();
        if(inputText.isEmpty()) {
            showToast("Please enter a valid number");
            return;
        }

        Float input = Float.parseFloat(inputText);
        Float x = null;
        String result = null;

       if(!String.valueOf(fromSpinner.getSelectedItem()).contains(res.getString(R.string.convert))
               && !String.valueOf(toSpinner.getSelectedItem()).contains(res.getString(R.string.convert))) {

           // convert all to upper case so that TimeUnit can find them in enum
           String fromUnit = fromSpinner.getSelectedItem().toString().toUpperCase();
           String toUnit = toSpinner.getSelectedItem().toString().toUpperCase();

           try {
               //Class enumClazz = Class.forName("app.basic.converters.util.DistanceUnit");
               Log.d(TAG, "Converting from " + fromUnit + " to " + toUnit);
               //Method mth = enumClazz.getDeclaredMethod("to".concat(StringUtils.capitalize(toUnit.toLowerCase())), double.class);
               Method mth = DistanceUnit.valueOf(fromUnit).getDeclaringClass()
                       .getDeclaredMethod("to".concat(StringUtils
                               .capitalize(toUnit.toLowerCase())), double.class);
               Object returnValue = mth.invoke(DistanceUnit.valueOf(fromUnit),
                       (Object)(Double.parseDouble(inputText)));
               result = String.valueOf(((Number)returnValue).doubleValue());
           } catch(NoSuchMethodException e) {
               Log.e(TAG, e.getMessage(), e);
           } catch(IllegalAccessException e) {
               Log.e(TAG, e.getMessage());
           } catch(InvocationTargetException e) {
               Log.e(TAG, e.getMessage());
           }
       } else {
           showToast("Please pick units to convert from and to");
           return;
       }

        if(StringUtils.isNotBlank(result)) {
            dataSource.createConversionEntry(String.valueOf(input), TABLE_NAME);
            textOutput.setText(result);
        } else {
            showToast("Oops...something went wrong :(");
        }
    }

    private void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
