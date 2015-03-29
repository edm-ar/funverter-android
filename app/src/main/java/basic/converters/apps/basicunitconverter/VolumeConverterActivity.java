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


public class VolumeConverterActivity extends Activity {

    private static final String TAG = VolumeConverterActivity.class.getSimpleName(); // tag to be used when logging

    private AutoCompleteTextView textInput;
    private RadioButton litersRadioBtn;
    private RadioButton cubicFeetRadioBtn;
    private Button calculateBtn;
    private TextView textOutput;

    private ConversionEntriesDataSource dataSource;

    private static final String TABLE_NAME = "volume";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_converter);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);

        dataSource = new ConversionEntriesDataSource(this);
        dataSource.open();

        textInput = (AutoCompleteTextView) findViewById(R.id.textInput);
        litersRadioBtn = (RadioButton) findViewById(R.id.litersRadioBtn);
        cubicFeetRadioBtn = (RadioButton) findViewById(R.id.cubicFeetRadioBtn);
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

        if(litersRadioBtn.isChecked()) {
            Log.i(TAG, "Converting" + input + " to Liters");
            x = input/0.035315F;
            result = x + " Liters";
        }
        else if(cubicFeetRadioBtn.isChecked()) {
            Log.i(TAG, "Converting" + input + " to Cubic Feet");
            x = input * 0.035315F;
            result = x + " Cubic Feet";
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
