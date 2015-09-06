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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import basic.converters.apps.basicunitconverter.R;
import basic.converters.util.ConversionEntriesDataSource;
import basic.converters.util.ConversionEntry;
import basic.converters.util.UnitSymbols;

/**
 * Created by Edmar on 6/29/2015.
 */
public abstract class AbstractConverter extends Activity implements Converter {

    private String TAG;
    private String tableName;

    private AutoCompleteTextView textInput;
    private Spinner toSpinner;
    private Spinner fromSpinner;
    private ImageButton calculateBtn;
    private TextView textOutput;
    private Resources res;
    private Context context;
    private Class unitClazz;

    private ConversionEntriesDataSource dataSource;
    private List<ConversionEntry> entries;
    private InputMethodManager imm;

    protected void onCreate(Bundle savedInstanceState, Context ctx,
                            String converterName, int layout,
                            Class activityClass, Class unitClass) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.animator.enter, R.animator.exit);
        setContentView(layout);

        res = getResources();
        imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);

        textInput = (AutoCompleteTextView) findViewById(R.id.textInput);
        fromSpinner = (Spinner)findViewById(R.id.fromSpinner);
        toSpinner = (Spinner)findViewById(R.id.toSpinner);
        toSpinner.setSelection(1);
        calculateBtn = (ImageButton) findViewById(R.id.calculateBtn);
        textOutput = (TextView) findViewById(R.id.textOutput);

        context = ctx;
        tableName = converterName;
        unitClazz = unitClass;
        TAG = activityClass.getName();

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);

        dataSource = new ConversionEntriesDataSource(ctx);
        dataSource.open();

        // restore saved instance if such exists
        if(savedInstanceState != null) {
            textInput.setText(savedInstanceState.getString("inputText"));
            fromSpinner.setSelection(savedInstanceState.getInt("convertFrom"));
            toSpinner.setSelection(savedInstanceState.getInt("convertTo"));
            textOutput.setText(savedInstanceState.getString("textOutput"));
        }

        Log.i(TAG, "Adding " + tableName + " to autocomplete view");

        // create button listener
        View.OnClickListener listener = new ButtonListener();
        calculateBtn.setOnClickListener(listener);

        textInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imm.showSoftInput(v, 0); // show keyboard when input field is tapped
                // set adapter for autocomplete
                setAutocompleteAdapter();
                return true;
            }
        });
    }

    private class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            buttonClickHandler();
        }
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

        if(StringUtils.isNotBlank(result)) {
            try {
                dataSource.createConversionEntry(String.valueOf(input), tableName);
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
        entries = dataSource.getAllTableConversionEntries(tableName);
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if(textInput != null)
            savedInstanceState.putString("inputText", textInput.getText().toString());
        if(fromSpinner != null)
            savedInstanceState.putInt("convertFrom", fromSpinner.getSelectedItemPosition());
        if(toSpinner != null)
            savedInstanceState.putInt("convertTo", toSpinner.getSelectedItemPosition());
        if(textOutput != null)
            savedInstanceState.putString("textOutput", textOutput.getText().toString());
        savedInstanceState.putBoolean("setAdapter", false);

        super.onSaveInstanceState(savedInstanceState);
    }
}