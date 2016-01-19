package com.funverter.activity;

import android.os.Bundle;

import com.funverter.converter.apps.converters.R;
import com.funverter.helper.ConverterActivity;
import com.funverter.converter.VolumeUnit;


public class VolumeConverterActivity extends ConverterActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this,
                "volume", R.layout.activity_volume_converter,
                this.getClass(), VolumeUnit.class);
    }
}
