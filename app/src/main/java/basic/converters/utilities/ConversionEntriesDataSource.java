package basic.converters.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import basic.converters.helpers.MySQLiteHelper;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by edmar on 2/15/15.
 * This class is our DAO. It maintains the database connection and supports adding new values and fetching all values.
 */
public class ConversionEntriesDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_VALUES };

    public ConversionEntriesDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ConversionEntry createConversionEntry(String conversionEntry, String tableName) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_VALUES, conversionEntry);
        long insertId;
        Cursor cursor;
        ConversionEntry entry = null;

        try{
            insertId = database.insert(MySQLiteHelper.tables.get(tableName), null, values);
            cursor = database.query(MySQLiteHelper.tables.get(tableName),
                    allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            entry = cursorToConversionEntry(cursor);
            cursor.close();
        } catch(Exception e) {
            Log.w(this.getClass().getName(), "Error inserting " + values + " into " + tableName + ". Probably because the value is not unique.", e);
        }

        return entry;
    }

    public void deleteTableConversionEntry(ConversionEntry conversionEntry, String tableName) {
        long id = conversionEntry.getId();
        System.out.println("Value deleted with id: " + id);
        database.delete(MySQLiteHelper.tables.get(tableName), MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public void deleteAllTableConversionEntries(String tableName) {
        System.out.println("Deleted all values from table: " + tableName);
        database.delete(MySQLiteHelper.tables.get(tableName), null, null);
    }

    public void deleteAllConversonEntries() {
        System.out.println("Deleted all conversion values from all tables");
        database.delete(null, null, null);
    }

    public List<ConversionEntry> getAllTableConversionEntries(String tableName) {
        List<ConversionEntry> conversionEntries = new ArrayList<ConversionEntry>();

        Cursor cursor = database.query(MySQLiteHelper.tables.get(tableName),
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ConversionEntry comment = cursorToConversionEntry(cursor);
            conversionEntries.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return conversionEntries;
    }

    public List<ConversionEntry> getAllConversionEntries() {
        List<ConversionEntry> conversionEntries = new ArrayList<ConversionEntry>();

        Cursor cursor = database.query(null,
                new String[] {MySQLiteHelper.COLUMN_VALUES}, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ConversionEntry entry = cursorToConversionEntry(cursor);
            conversionEntries.add(entry);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return conversionEntries;
    }

    private ConversionEntry cursorToConversionEntry(Cursor cursor) {
        ConversionEntry conversionEntry = new ConversionEntry();
        conversionEntry.setId(cursor.getLong(0));
        conversionEntry.setValue(Double.parseDouble(cursor.getString(1)));
        return conversionEntry;
    }
}
