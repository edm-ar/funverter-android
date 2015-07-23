package basic.converters.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import basic.converters.apps.basicunitconverter.AreaConverterActivity;
import basic.converters.apps.basicunitconverter.R;

/**
 * Created by Edmar on 6/28/2015.
 */
public class AreaConverterActivityTest
        extends ActivityInstrumentationTestCase2<AreaConverterActivity> {

    private AreaConverterActivity mActivity;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    private AutoCompleteTextView inputText;
    private Button calculateBtn;
    private TextView outputText;
    private String[] units;

    public AreaConverterActivityTest() {
        super(AreaConverterActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);

        mActivity = getActivity();
        units = mActivity.getResources().getStringArray(R.array.area_units);
        fromSpinner = (Spinner)mActivity.findViewById(R.id.fromSpinner);
        inputText = (AutoCompleteTextView)mActivity.findViewById(R.id.textInput);
        calculateBtn = (Button)mActivity.findViewById(R.id.calculateBtn);

        // run on main sync to wait for UI interactions to complete
        getInstrumentation().runOnMainSync(
                new Runnable() {
                    @Override
                    public void run() {
                        inputText.requestFocus();
                        inputText.setText("5");

                        fromSpinner.requestFocus();
                        fromSpinner.setSelection(1); // selects inches
                    }
                }
        );
        getInstrumentation().waitForIdleSync(); // wait for UI interactions to finish so that
        // toSpinner created

        getInstrumentation().runOnMainSync(
                new Runnable() {
                    @Override
                    public void run() {
                        toSpinner = (Spinner) mActivity.findViewById(R.id.toSpinner);
                        toSpinner.requestFocus();
                        toSpinner.setSelection(1); // selects are from the generated list
                    }
                }
        );
    }

    public void testPreConditions() {
        assertNotNull(mActivity);
        assertNotNull(fromSpinner);
        assertNotNull(inputText);
        assertNotNull(calculateBtn);
        assertNotNull(units);
        assertTrue(units.length > 0);
        assertNotNull(toSpinner);
    }

    public void runTest() {
        getInstrumentation().runOnMainSync(
                new Runnable() {
                    @Override
                    public void run() {
                        calculateBtn.performClick();
                    }
                }
        );

        outputText = (TextView) mActivity.findViewById(R.id.textOutput);
        assertNotNull(outputText);
        assertEquals("202.34", outputText.getText().toString());
    }
}
