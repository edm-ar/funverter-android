package basic.converters.helper;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by edmar on 12/21/14.
 */
public interface Converter {
    void itemSelectedHandler(AdapterView<?> parent, View view, int position, long id);
    void buttonClickHandler();
    void showToast(String msg);
}
