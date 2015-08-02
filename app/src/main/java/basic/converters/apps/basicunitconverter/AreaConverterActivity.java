package basic.converters.apps.basicunitconverter;

import android.os.Bundle;

import basic.converters.helper.AbstractConverter;
import basic.converters.util.AreaUnit;


public class AreaConverterActivity extends AbstractConverter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this,
                "area", R.array.area_units,
                R.layout.activity_area_converter,
                this.getClass(), AreaUnit.class);
    }
}
