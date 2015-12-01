package com.funverter.helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.funverter.converter.apps.converters.R;

/**
 * Created by Edmar on 11/30/2015.
 *
 * Used to crunch the data from unit_converters string-array
 */

class UnitConvertersAdapter extends BaseAdapter {
    private Context context;
    String[] unit_converters;
    // used to ensure same order as unit_converters list
    int[] images = {R.drawable.ic_temperature, R.drawable.ic_weight, R.drawable.ic_length,
            R.drawable.ic_volume, R.drawable.ic_area, R.drawable.ic_time};
    private Resources.Theme theme;

    public UnitConvertersAdapter(Context context) {
        unit_converters = context.getResources().getStringArray(R.array.unit_converters);
        this.context = context;
        theme = context.getTheme();
    }

    @Override
    public int getCount() {
        return unit_converters.length;
    }

    @Override
    public Object getItem(int position) {
        return unit_converters[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /* called each time a single row needs to be drawn */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = null;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // convert textview row to a java object
            item = inflater.inflate(R.layout.drawer_item_layout, parent, false);
        } else {
            item = convertView;
        }
        // set appropriate image and text for each item
        TextView unitTextView = (TextView) item.findViewById(R.id.unitText);
        ImageView unitIconView = (ImageView) item.findViewById(R.id.unitIcon);

        int color = Color.parseColor("#000000");

        unitIconView.setColorFilter(color);
        unitTextView.setText(unit_converters[position]);
        unitTextView.setTextColor(color);
        unitIconView.setImageResource(images[position]);

        return item;
    }
}