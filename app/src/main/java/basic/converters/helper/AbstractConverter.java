package basic.converters.helper;

import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import basic.converters.apps.basicunitconverter.BasicUnitConverterActivity;
import basic.converters.apps.basicunitconverter.R;
import basic.converters.util.ConversionEntriesDataSource;
import basic.converters.util.ConversionEntry;
import basic.converters.util.UnitSymbols;

/**
 * Created by Edmar on 6/29/2015.
 */
public abstract class AbstractConverter extends AppCompatActivity implements Converter {

    private String TAG;
    private String tableName;

    private AutoCompleteTextView textInput;
    private Spinner toSpinner;
    private Spinner fromSpinner;
    private ImageButton calculateBtn;
    private TextView textOutput;
    private Context context;
    private Class unitClazz;
    private ListView drawerView;
    private MyAdapter myAdapter;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private String activityTitle;
    private ActionBar actionBar;
    private Map<String,String> funFacts;
    private TextView factText;
    private String converterFunFact;

    private ConversionEntriesDataSource dataSource;
    private List<ConversionEntry> entries;
    private InputMethodManager imm;

    protected void onCreate(Bundle savedInstanceState, Context ctx,
                            String converterName, int layout,
                            final Class activityClass, Class unitClass) {

        super.onCreate(savedInstanceState);
        overridePendingTransition(R.animator.enter, R.animator.exit);
        setContentView(layout);

        /* initialize fun facts map */
        funFacts = new HashMap<>();
        String factsArrayName = converterName.toLowerCase().concat("_facts");
        converterFunFact = converterName.toLowerCase().concat("_fun_fact");
        int factsArrayId = getResources().getIdentifier(factsArrayName, "array", getPackageName());
        String[] factsArray = getResources().getStringArray(factsArrayId);

        for(String fact : factsArray) {
            String[] keyValue = fact.split("\\|");
            if(keyValue.length > 1) {
                funFacts.put(keyValue[0],keyValue[1]);
            }
        }

        /* initialize global variables*/
        factText = (TextView) findViewById(R.id.factText);
        factText.setMovementMethod(new ScrollingMovementMethod());
        factText.setText(getResources().getString(getResources().
                getIdentifier(converterFunFact, "string", getPackageName())));

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

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

        setUpDrawer();

        dataSource = new ConversionEntriesDataSource(ctx);
        dataSource.open();

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
        // hide keyboard once calculation is executed
        imm.hideSoftInputFromWindow(textInput.getWindowToken(), 0);

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

            String topFactKey = fromUnit.toUpperCase();
            String bottomFactKey = toUnit.toUpperCase();
            StringBuilder funFactsSb = new StringBuilder();

            // clear facts
            factText.setText("");
            if(funFacts.containsKey(topFactKey)) {
                funFactsSb.append(funFacts.get(topFactKey));
                funFactsSb.append(System.getProperty("line.separator"));
                funFactsSb.append(System.getProperty("line.separator"));
            }
            if(funFacts.containsKey(bottomFactKey)) {
                funFactsSb.append(funFacts.get(bottomFactKey));
            }

            if(StringUtils.isNotBlank(funFactsSb.toString())) {
                factText.setText(funFactsSb.toString());
            }
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
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpDrawer() {
        // set drawerView adapter
        drawerView = (ListView) findViewById(R.id.navlist);
        myAdapter = new MyAdapter(this);
        drawerView.setAdapter(myAdapter);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        activityTitle = getTitle().toString();

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(activityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);

        // add onItemClickListener to drawer items
        drawerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                String itemSelected = (String) parent.getAdapter().getItem(position);
                String activityName =
                        itemSelected.concat(BasicUnitConverterActivity.ACTIVITYSUFFIX);
                String activityFullPath =
                        BasicUnitConverterActivity.ACTIVITYPACKAGE.concat(activityName);
                try {
                    // dynamically load activity with full package path and class name
                    intent = new Intent(context, Class.forName(activityFullPath));
                    Log.i(TAG, "Starting " + activityFullPath + " activity.");
                    startActivity(intent);
                    overridePendingTransition(R.animator.enter, R.animator.exit);
                    drawerLayout.closeDrawer(drawerView);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                    showToast("Oops...there has been an error :(");
                }
            }
        });
        // to use drawable as item background
        drawerView.setDrawSelectorOnTop(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}

// used to crunch the data from unit_converters string-array
class MyAdapter extends BaseAdapter {
    private Context context;
    String[] unit_converters;
    // used to ensure same order as unit_converters list
    int[] images = {R.drawable.ic_temperature, R.drawable.ic_weight, R.drawable.ic_length,
                    R.drawable.ic_volume, R.drawable.ic_area, R.drawable.ic_time};
    private Resources.Theme theme;

    public MyAdapter(Context context) {
        unit_converters = context.getResources().getStringArray(R.array.unit_converters);
        this.context = context;
        theme = context.getTheme();
    }

    @Override
    public int getCount() {
        return unit_converters.length;
    }

    @Override
    public Object getItem(int position) {
        return unit_converters[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /* called each time a single row needs to be drawn */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = null;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // convert textview row to a java object
            item = inflater.inflate(R.layout.drawer_item_layout, parent, false);
        } else {
            item = convertView;
        }
        // set appropriate image and text for each item
        TextView unitTextView = (TextView) item.findViewById(R.id.unitText);
        ImageView unitIconView = (ImageView) item.findViewById(R.id.unitIcon);

        int color = Color.parseColor("#000000");

        unitIconView.setColorFilter(color);
        unitTextView.setText(unit_converters[position]);
        unitTextView.setTextColor(color);
        unitIconView.setImageResource(images[position]);

        return item;
    }
}