package basic.converters.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by edmar on 2/15/15.
 * This class is responsible for creating the database. The onUpgrade() method will simply delete all existing data and re-create the table.
 * It also defines a list of table names and the table columns.
 */
public class MySQLiteHelper extends SQLiteOpenHelper{

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_VALUES = "conversion_entries";

    private static final String DATABASE_NAME = "conversionentries.db";
    private static final int DATABASE_VERSION = 1;

    private static final String[] tableNames = {"distance","speed","temperature","time","volume","weight"};
    public static HashMap<String, String> tables = new HashMap<String, String>();

    {
        for(String tableName : tableNames) {
            tables.put(tableName, tableName);
        }
    }

    private String DATABASE_CREATE = "CREATE TABLE `%s` (\n" +
            "\t`%s`\tINTEGER,\n" +
            "\t`%s`\tREAL NOT NULL unique,\n" +
            "\tPRIMARY KEY(%s)\n" +
            ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        for(String tableName : tableNames) {
            // Database creation sql statement
            // TODO create table to only accept unique values
            database.execSQL(String.format(DATABASE_CREATE, tableName, COLUMN_ID, COLUMN_VALUES, COLUMN_ID));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        for(String tableName : tableNames) {
            db.execSQL("DROP TABLE IF EXISTS " + tableName);
        }
        onCreate(db);
    }
}
