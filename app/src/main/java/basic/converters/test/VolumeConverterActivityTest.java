package basic.converters.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import basic.converters.apps.basicunitconverter.VolumeConverterActivity;
import basic.converters.apps.basicunitconverter.R;
import basic.converters.util.UnitSymbols;

/**
 * Created by Edmar on 6/28/2015.
 */
public class VolumeConverterActivityTest
        extends ActivityInstrumentationTestCase2<VolumeConverterActivity> {

    private VolumeConverterActivity mActivity;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    private AutoCompleteTextView inputText;
    private ImageButton calculateBtn;
    private TextView outputText;
    private String[] units;

    public VolumeConverterActivityTest() {
        super(VolumeConverterActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);

        mActivity = getActivity();
        units = mActivity.getResources().getStringArray(R.array.volume_units);
        fromSpinner = (Spinner)mActivity.findViewById(R.id.fromSpinner);
        inputText = (AutoCompleteTextView)mActivity.findViewById(R.id.textInput);
        calculateBtn = (ImageButton)mActivity.findViewById(R.id.calculateBtn);

        // run on main sync to wait for UI interactions to complete
        getInstrumentation().runOnMainSync(
                new Runnable() {
                    @Override
                    public void run() {
                        inputText.requestFocus();
                        inputText.setText("0.35");

                        fromSpinner.requestFocus();
                        fromSpinner.setSelection(1); // selects cubic meter
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
                        toSpinner.setSelection(1); // selects cubic inch from the generated list
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

        String unitSymbol = UnitSymbols.symbols
                .get(toSpinner.getSelectedItem().toString().toLowerCase());
        outputText = (TextView) mActivity.findViewById(R.id.textOutput);
        assertNotNull(outputText);
        assertEquals("21,358.29".concat(" " + unitSymbol), outputText.getText().toString());
    }
}
