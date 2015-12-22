package com.funverter.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.funverter.util.ConversionEntriesDataSource;
import com.funverter.util.ConversionEntry;

import java.util.List;

/**
 * Created by Edmar on 12/22/2015.
 */
public class AutoCompleteAdapter extends ArrayAdapter {

    private ConversionEntriesDataSource dataSource;
    private List<ConversionEntry> entries;
    private String tableName;

    private Context ctx;
    private int layout;

    public AutoCompleteAdapter(ConversionEntriesDataSource dataSource,
                               List<ConversionEntry> entries,
                               String tableName,
                               Context ctx,
                               int layout) {
        super(ctx, layout, entries);

        this.ctx = ctx;
        this.layout = layout;

        this.dataSource = dataSource;
        this.entries = entries;
        this.tableName = tableName;
    }

    public ArrayAdapter createAutoCompleteAdapter() {
        entries = dataSource.getAllTableConversionEntries(tableName);
        ArrayAdapter adapter =
                new ArrayAdapter(ctx, layout, entries);
        return adapter;
    }
}
