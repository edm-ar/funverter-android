package basic.converters.apps.basicunitconverter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import basic.converters.utilities.ConversionEntriesDataSource;


public class DistanceConverterActivity extends Activity {

    private static final String TAG = DistanceConverterActivity.class.getSimpleName(); // tag to be used when logging

    private EditText textInput;
    private RadioButton kilometersRadioBtn;
    private RadioButton milesRadioBtn;
    private Button calculateBtn;
    private TextView textOutput;

    private ConversionEntriesDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_converter);

        dataSource = new ConversionEntriesDataSource(this);
        dataSource.open();

        textInput = (EditText) findViewById(R.id.textInput);
        kilometersRadioBtn = (RadioButton) findViewById(R.id.kilometersRadioBtn);
        milesRadioBtn = (RadioButton) findViewById(R.id.milesRadioBtn);
        calculateBtn = (Button) findViewById(R.id.calculateBtn);
        textOutput = (TextView) findViewById(R.id.textOutput);

        // create button listener
        View.OnClickListener listener = new ButtonListener();
        calculateBtn.setOnClickListener(listener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_distance_converter, menu);
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
            dataSource.createConversionEntry(String.valueOf(input), "distance");
            textOutput.setText(result);
        }
    }

    private void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
