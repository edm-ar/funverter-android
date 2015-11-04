package basic.converters.apps.funverter;

import android.os.Bundle;
import basic.converters.helper.AbstractConverter;
import basic.converters.util.LengthUnit;

public class LengthConverterActivity extends AbstractConverter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this,
                "length", R.layout.activity_length_converter,
                this.getClass(), LengthUnit.class);
    }
}
