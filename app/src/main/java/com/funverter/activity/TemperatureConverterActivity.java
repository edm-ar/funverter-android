package com.funverter.activity;

import android.os.Bundle;

import com.funverter.converter.apps.converters.R;
import com.funverter.helper.ConverterActivity;
import com.funverter.converter.TemperatureUnit;

public class TemperatureConverterActivity extends ConverterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this,
            "temperature", R.layout.activity_temperature_converter,
                this.getClass(), TemperatureUnit.class);
    }
}
