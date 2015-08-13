package basic.converters.apps.basicunitconverter;

import android.os.Bundle;

import basic.converters.helper.AbstractConverter;
import basic.converters.util.TemperatureUnit;

public class TemperatureConverterActivity extends AbstractConverter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this,
            "temperature", R.array.temperature_units,
                R.layout.activity_temperature_converter,
                this.getClass(), TemperatureUnit.class);
    }
}
