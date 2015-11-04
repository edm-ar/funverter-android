package basic.converters.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import basic.converters.apps.funverter.LengthConverterActivity;
import basic.converters.apps.funverter.R;
import basic.converters.util.UnitSymbols;

/**
 * Created by Edmar on 6/28/2015.
 */
public class LengthConverterActivityTest
        extends ActivityInstrumentationTestCase2<LengthConverterActivity> {

    private LengthConverterActivity mActivity;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    private AutoCompleteTextView inputText;
    private ImageButton calculateBtn;
    private TextView outputText;
    private String[] units;

    public LengthConverterActivityTest() {
        super(LengthConverterActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);

        mActivity = getActivity();
        units = mActivity.getResources().getStringArray(R.array.length_units);
        fromSpinner = (Spinner)mActivity.findViewById(R.id.fromSpinner);
        toSpinner = (Spinner) mActivity.findViewById(R.id.toSpinner);
        inputText = (AutoCompleteTextView)mActivity.findViewById(R.id.textInput);
        calculateBtn = (ImageButton)mActivity.findViewById(R.id.calculateBtn);

        // run on main sync to wait for UI interactions to complete
        getInstrumentation().runOnMainSync(
                new Runnable() {
                    @Override
                    public void run() {
                        inputText.requestFocus();
                        inputText.setText("35");
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
                .get(toSpinner.getSelectedItem().toString().toLowerCase().replace(" ",""));
        outputText = (TextView) mActivity.findViewById(R.id.textOutput);
        assertNotNull(outputText);
        assertEquals("88.9".concat(" " + unitSymbol), outputText.getText().toString());
    }
}
