package basic.converters.apps.funverter;


import android.os.Bundle;
import basic.converters.helper.AbstractConverter;
import basic.converters.util.TimeUnitExtension;

//TODO update timeunit converter
public class TimeConverterActivity extends AbstractConverter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, this,
                "time", R.layout.activity_time_converter,
                this.getClass(), TimeUnitExtension.class);
    }
}