package basic.converters.helper;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import basic.converters.apps.basicunitconverter.R;
import basic.converters.util.ConversionEntriesDataSource;
import basic.converters.util.ConversionEntry;
import basic.converters.util.UnitSymbols;

/**
 * Created by Edmar on 6/29/2015.
 */
public abstract class AbstractConverter extends Activity implements Converter {

    private String TAG;
    private String TABLE_NAME;

    private AutoCompleteTextView textInput;
    private Spinner toSpinner;
    private Spinner fromSpinner;
    private ImageButton calculateBtn;
    private boolean isFirstSelection = true;
    private TextView textOutput;
    private Resources res;
    private Context context;
    private Class unitClazz;
    private int unitsArrayId;

    private ConversionEntriesDataSource dataSource;
    private List<ConversionEntry> entries;
    private InputMethodManager imm;

    protected void onCreate(Bundle savedInstanceState, Context ctx,
                            String converterName, int unitsArray, int layout,
                            Class activityClass, Class unitClass) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.animator.enter, R.animator.exit);
        setContentView(layout);

        imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);

        context = ctx;
        TABLE_NAME = converterName;
        unitClazz = unitClass;
        TAG = activityClass.getName();
        unitsArrayId = unitsArray;

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);

        dataSource = new ConversionEntriesDataSource(ctx);
        dataSource.open();

        res = getResources();

        textInput = (AutoCompleteTextView) findViewById(R.id.textInput);
        fromSpinner = (Spinner)findViewById(R.id.fromSpinner);
        calculateBtn = (ImageButton) findViewById(R.id.calculateBtn);
        textOutput = (TextView) findViewById(R.id.textOutput);

        Log.i(TAG, "Adding " + TABLE_NAME + " to autocomplete view");
        // set adapter for autocomplete
        setAutocompleteAdapter();

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

    public void itemSelectedHandler(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "Select from: " + parent.getSelectedItem());
        ArrayList<String> units = new ArrayList<>();
        units.add(res.getString(R.string.to_prompt));

        for(String unit : res.getStringArray(unitsArrayId)) {
            //TODO find a better way of avoiding the addition of non-unit values
            if(!unit.equals(parent.getSelectedItem().toString())
                    && !unit.contains(res.getString(R.string.convert))) {
                units.add(unit);
            }
        }

        LinearLayout spinnersContainer = (LinearLayout)findViewById(R.id.spinnersContainer);
        if(spinnersContainer.findViewById(R.id.toSpinner) != null) {
            spinnersContainer.removeView(findViewById(R.id.toSpinner));
        }
        toSpinner = new Spinner(this);
        toSpinner.setId(R.id.toSpinner);
        ArrayAdapter<String> spinnerArrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units);
        toSpinner.setAdapter(spinnerArrayAdapter);
        spinnersContainer.addView(toSpinner);
    }

    public void buttonClickHandler() {
        imm.hideSoftInputFromWindow(textInput.getWindowToken(), 0); // hide keyboard once calculation is executed

        String inputText = textInput.getText().toString();
        if(inputText.isEmpty()) {
            showToast("Please enter a valid number");
            return;
        }

        Float input = Float.parseFloat(inputText);
        StringBuilder result = new StringBuilder();

        if(!String.valueOf(fromSpinner.getSelectedItem()).contains(res.getString(R.string.convert))
                && !String.valueOf(toSpinner.getSelectedItem()).contains(res.getString(R.string.convert))) {
            // convert all to upper case so that the unit class can find them in enum
            String fromUnit = fromSpinner.getSelectedItem().toString().toUpperCase().replace(" ","");
            // split on space so that enum type methods are matched accurately
            String[] temp = toSpinner.getSelectedItem().toString().split(" ");
            StringBuilder toUnitSb = new StringBuilder();
            for(String u : temp) {
                toUnitSb.append(StringUtils.capitalize(u.toLowerCase()));
            }
            String toUnit = toUnitSb.toString();

            try {
                Log.d(TAG, "Converting from " + fromUnit + " to " + toUnit);
                Object[] constants = unitClazz.getEnumConstants();
                Object constant = null;

                //TODO try to use a valueOf type of solution instead of iterating over constants
                for(int i = 0; i < constants.length; i++) {
                    if(fromUnit.equals(constants[i].toString())) {
                        constant = constants[i];
                        break;
                    }
                }

                Method mth = constant.getClass()
                        .getDeclaredMethod("to"
                                .concat(StringUtils.capitalize(toUnit)), double.class);
                Object returnValue = mth.invoke(constant, (Object) (Double.parseDouble(inputText)));
                double out = ((Number)returnValue).doubleValue();

                DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                symbols.setGroupingSeparator(',');
                symbols.setDecimalSeparator('.');
                df.setDecimalFormatSymbols(symbols);
                result.append(df.format(out));

                if(UnitSymbols.symbols.get(toUnit.toLowerCase()) != null) {
                    result.append(" ").append(UnitSymbols.symbols.get(toUnit.toLowerCase()));
                }
            } catch(NoSuchMethodException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch(IllegalAccessException e) {
                Log.e(TAG, e.getMessage());
            } catch(InvocationTargetException e) {
                Log.e(TAG, e.getMessage());
            } catch(NullPointerException e) {
                Log.e(TAG, e.getMessage());
            }
        } else {
            showToast("Please pick units to convert from and to");
            return;
        }

        if(StringUtils.isNotBlank(result)) {
            try {
                dataSource.createConversionEntry(String.valueOf(input), TABLE_NAME);
                // get updated list of entries
                setAutocompleteAdapter();
            } catch(SQLiteConstraintException e) {
                Log.e(TAG, e.getMessage(), e);
            }
            textOutput.setText(result.toString());
        } else {
            showToast("Oops...something went wrong :(");
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

    public void showToast(String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void setAutocompleteAdapter() {
        entries = dataSource.getAllTableConversionEntries(TABLE_NAME);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, entries);
        textInput.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.left_to_right, R.animator.right_to_left);
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