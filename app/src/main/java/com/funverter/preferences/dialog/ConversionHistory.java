package com.funverter.preferences.dialog;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.widget.Toast;

import com.funverter.util.ConversionEntriesDataSource;

/**
 * Created by Edmar on 6/7/2015.
 */
public class ConversionHistory extends DialogPreference {

    Context context;

    public ConversionHistory(Context context, AttributeSet attrs) {
        super(context,attrs);
        this.context = context;
    }

    public void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if(positiveResult) {
            showToast("Clearing...");
            ConversionEntriesDataSource ds = new ConversionEntriesDataSource(context);
            ds.open();
            ds.deleteAllConversionEntries();
            showToast("Conversion history cleared successfully");
        } else {
            // do nothing
        }
    }

    private void showToast(String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
