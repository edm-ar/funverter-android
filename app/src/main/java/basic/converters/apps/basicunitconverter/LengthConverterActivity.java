package basic.converters.apps.basicunitconverter;

import android.os.Bundle;
import basic.converters.helper.AbstractConverter;
import basic.converters.util.LengthUnit;

public class LengthConverterActivity extends AbstractConverter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this,
                "length", R.array.length_units,
                R.layout.activity_length_converter,
                this.getClass(), LengthUnit.class);
    }
}
