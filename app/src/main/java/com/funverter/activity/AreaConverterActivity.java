package com.funverter.activity;

import android.os.Bundle;

import com.funverter.converter.apps.converters.R;
import com.funverter.helper.AbstractConverter;
import com.funverter.converter.AreaUnit;


public class AreaConverterActivity extends AbstractConverter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this,
                "area", R.layout.activity_area_converter,
                this.getClass(), AreaUnit.class);
    }
}
