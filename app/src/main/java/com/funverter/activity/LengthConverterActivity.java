package com.funverter.activity;

import android.os.Bundle;

import com.funverter.converter.apps.converters.R;
import com.funverter.helper.AbstractConverter;
import com.funverter.converter.LengthUnit;

public class LengthConverterActivity extends AbstractConverter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this,
                "length", R.layout.activity_length_converter,
                this.getClass(), LengthUnit.class);
    }
}
