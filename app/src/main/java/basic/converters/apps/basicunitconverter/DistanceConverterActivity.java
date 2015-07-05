package basic.converters.apps.basicunitconverter;

import android.os.Bundle;
import basic.converters.helper.AbstractConverter;
import basic.converters.util.DistanceUnit;

public class DistanceConverterActivity extends AbstractConverter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this,
                "distance", R.array.distance_units,
                R.layout.activity_distance_converter,
                this.getClass(), DistanceUnit.class);
    }
}
