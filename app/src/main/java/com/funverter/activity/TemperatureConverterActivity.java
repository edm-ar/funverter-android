package com.funverter.activity;

import android.os.Bundle;

import com.funverter.converter.apps.converters.R;
import com.funverter.helper.AbstractConverter;
import com.funverter.converter.TemperatureUnit;

public class TemperatureConverterActivity extends AbstractConverter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this,
            "temperature", R.layout.activity_temperature_converter,
                this.getClass(), TemperatureUnit.class);
    }
}
