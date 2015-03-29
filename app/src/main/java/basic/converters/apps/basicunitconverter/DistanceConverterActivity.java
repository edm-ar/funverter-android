package basic.converters.apps.basicunitconverter;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import basic.converters.utilities.ConversionEntriesDataSource;
import basic.converters.utilities.ConversionEntry;


public class DistanceConverterActivity extends Activity {

    private static final String TAG = DistanceConverterActivity.class.getSimpleName(); // tag to be used when logging

    private AutoCompleteTextView textInput;
    private RadioButton kilometersRadioBtn;
    private RadioButton milesRadioBtn;
    private Button calculateBtn;
    private TextView textOutput;

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

        textInput = (AutoCompleteTextView) findViewById(R.id.textInput);
        kilometersRadioBtn = (RadioButton) findViewById(R.id.kilometersRadioBtn);
        milesRadioBtn = (RadioButton) findViewById(R.id.milesRadioBtn);
        calculateBtn = (Button) findViewById(R.id.calculateBtn);
        textOutput = (TextView) findViewById(R.id.textOutput);

        Log.i(TAG, "Adding " + TABLE_NAME + " to autocomplete view");
        // set adapter for autocomplete
        List<ConversionEntry> entries = dataSource.getAllTableConversionEntries(TABLE_NAME);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, entries);
        textInput.setAdapter(adapter);

        // create button listener
        View.OnClickListener listener = new ButtonListener();
        calculateBtn.setOnClickListener(listener);
    }

    private class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            buttonClickHandler();
        }
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
        String result;

        if(kilometersRadioBtn.isChecked()) {
            Log.i(TAG, "Converting" + input + " to Kilometers");
            x = input/0.62137F;
            result = x + " Kilometers";
        }
        else if(milesRadioBtn.isChecked()) {
            Log.i(TAG, "Converting" + input + " to Miles");
            x = input * 0.62137F;
            result = x + " Miles";
        }
        else {
            showToast("Please pick unit to convert to");
            return;
        }

        if(!result.isEmpty()) {
            dataSource.createConversionEntry(String.valueOf(input), TABLE_NAME);
            textOutput.setText(result);
        }
    }

    private void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
