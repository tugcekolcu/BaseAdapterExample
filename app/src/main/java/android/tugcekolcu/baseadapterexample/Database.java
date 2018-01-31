package android.tugcekolcu.baseadapterexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tugcekolcu on 30.01.2018.
 */

public class Database extends SQLiteOpenHelper {


    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "AndroidTR";

    // Contacts table name
    private static final String TABLE_COUNTER = "Counter";

    // Contacts Table Columns names
    private static final String KEY_ID = "_id";
    private static final String KEY_CNAME = "counter_name";
    private static final String KEY_CVALUE = "counter_value";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String counterTable = "CREATE TABLE " + TABLE_COUNTER + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CNAME + " TEXT UNIQUE NOT NULL,"
                + KEY_CVALUE + " TEXT)";

        db.execSQL(counterTable);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTER);
        onCreate(db);
    }


    //CRUD OPERATIONS

    public void addCounter(CounterUIBean c) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CNAME, c.getCounterName());
        values.put(KEY_CVALUE, c.getCounterValue());
        db.insert(TABLE_COUNTER, null, values);
        db.close(); // Closing database connection
    }

    // Getting single person
    public CounterUIBean getCounter(String cname) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COUNTER, new String[]{KEY_CNAME,
                        KEY_CVALUE,}, KEY_CNAME + "=?",
                new String[]{cname}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        CounterUIBean counter = new CounterUIBean(cursor.getString(0),
                cursor.getString(1));
        return counter;

    }

    // Getting All Counters
    public ArrayList<CounterUIBean> getAllList() {
        ArrayList<CounterUIBean> cList = new ArrayList<CounterUIBean>();
        String selectQuery = "SELECT  * FROM " + TABLE_COUNTER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CounterUIBean counter = new CounterUIBean(cursor.getString(1),
                        cursor.getString(2));              // Adding counter to list
                cList.add(counter);
            } while (cursor.moveToNext());
        }

        return cList;

    }

    // Getting  list Count
    public int getListCount() {

        String countQuery = "SELECT  * FROM " + TABLE_COUNTER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        if(cursor.getCount()==0){
            return 0;
        }

        return cursor.getCount();
    }

    // Updating single counter
    public void updateCounterValues(ArrayList<CounterUIBean> cList) {

        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i < cList.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(KEY_CNAME, cList.get(i).getCounterName());
            values.put(KEY_CVALUE, cList.get(i).getCounterValue());
            // updating row
          int a = db.update(TABLE_COUNTER, values, KEY_CNAME + " = ?", new String[]{cList.get(i).getCounterName()});
            Log.d(" Update value" , String.valueOf(a));
        }
    }

    // Deleting single counter
    public void deleteCounter(CounterUIBean c) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COUNTER, KEY_CNAME + " = ?",
                new String[]{c.getCounterName()});
        db.close();

    }


}
