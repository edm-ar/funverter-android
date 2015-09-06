package basic.converters.apps.basicunitconverter;

import android.os.Bundle;

import basic.converters.helper.AbstractConverter;
import basic.converters.util.VolumeUnit;


public class VolumeConverterActivity extends AbstractConverter {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this,
                "volume", R.layout.activity_volume_converter,
                this.getClass(), VolumeUnit.class);
    }
}
