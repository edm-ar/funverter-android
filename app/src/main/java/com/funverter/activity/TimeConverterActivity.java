package com.funverter.activity;


import android.os.Bundle;

import com.funverter.converter.apps.converters.R;
import com.funverter.helper.AbstractConverter;
import com.funverter.converter.TimeUnitExtension;

//TODO update timeunit converter
public class TimeConverterActivity extends AbstractConverter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this,
                "time", R.layout.activity_time_converter,
                this.getClass(), TimeUnitExtension.class);
    }
}