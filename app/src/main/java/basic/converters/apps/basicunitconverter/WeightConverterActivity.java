package basic.converters.apps.basicunitconverter;

import android.os.Bundle;
import basic.converters.helper.AbstractConverter;
import basic.converters.util.WeightUnit;


public class WeightConverterActivity extends AbstractConverter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this,
                "weight", R.layout.activity_weight_converter,
                this.getClass(), WeightUnit.class);
    }
}
