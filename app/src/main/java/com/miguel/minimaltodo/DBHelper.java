package com.miguel.minimaltodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private Context mContext;

    public static final String LOG_TAG = "DBHelper";

    private static OnDatabaseChanged onDatabaseChangedListener;

    public static String DATABASE_NAME = "task_list.db";
    private static final int DATABASE_VERSION = 1;

    public static abstract class DBHelperItem implements BaseColumns {
        public static final String TABLE_NAME = "saved_tasks";

        public static final String COLUMN_NAME_TASK_NAME = "task_name";
        public static final String COLUMN_NAME_TASK_REMINDER_DATE = "reminder_date";
        public static final String COLUMN_NAME_IMAGE = "image";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBHelperItem.TABLE_NAME + " (" +
                    DBHelperItem._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    DBHelperItem.COLUMN_NAME_TASK_NAME + TEXT_TYPE + COMMA_SEP +
                    DBHelperItem.COLUMN_NAME_TASK_REMINDER_DATE + TEXT_TYPE + COMMA_SEP +
                    DBHelperItem.COLUMN_NAME_IMAGE + " INTEGER " + ")";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBHelperItem.TABLE_NAME;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
           sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }


    // Bad Design
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
         sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
         sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }
    public static void setOnDatabaseListener(OnDatabaseChanged listener){
        onDatabaseChangedListener = listener;
    }

    public int getCount(){
        SQLiteDatabase db = getReadableDatabase();
        String[] projection ={DBHelperItem._ID};
        Cursor c = db.query(DBHelperItem.TABLE_NAME, projection, null, null, null, null, null);
        int count = c.getCount();
        c.close();
        return count;
    }
    public Task getItemAtPosition(int position){
        SQLiteDatabase db = getReadableDatabase();
        String[] projection ={
                DBHelperItem._ID,
                DBHelperItem.COLUMN_NAME_TASK_NAME,
                DBHelperItem.COLUMN_NAME_TASK_REMINDER_DATE,
                DBHelperItem.COLUMN_NAME_IMAGE
        };
        Cursor c = db.query(DBHelperItem.TABLE_NAME, projection, null, null, null, null, null);
        if(c.moveToPosition(position)){
            Task task = new Task();
            task.setId(c.getInt(c.getColumnIndex(DBHelperItem._ID)));
            task.setTitle(c.getString(c.getColumnIndex(DBHelperItem.COLUMN_NAME_TASK_NAME)));
            task.setReminderDate(c.getString(c.getColumnIndex(DBHelperItem.COLUMN_NAME_TASK_REMINDER_DATE)));
            task.setImage(c.getInt(c.getColumnIndex(DBHelperItem.COLUMN_NAME_IMAGE)));
            c.close();
            return task;
        }
        return null;
    }

    public long addTask(String title, String dateReminder, int imageResource){
          SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelperItem.TABLE_NAME, title);
        cv.put(DBHelperItem.COLUMN_NAME_TASK_REMINDER_DATE, dateReminder);
        cv.put(DBHelperItem.COLUMN_NAME_IMAGE, imageResource);

        long rowId = db.insert(DBHelperItem.TABLE_NAME, null, cv);

        if(onDatabaseChangedListener!=null){
            onDatabaseChangedListener.OnTaskInserted();
        }
        return rowId;
    }
    //rename task
    public void renameTask(Task task, String newTitle){
           SQLiteDatabase db = getWritableDatabase();
           ContentValues cv = new ContentValues();
           cv.put(DBHelperItem.COLUMN_NAME_TASK_NAME, newTitle);

           db.update(DBHelperItem.TABLE_NAME,cv,DBHelperItem._ID + "=" + task.getId(),null);

           if(onDatabaseChangedListener!=null){
               onDatabaseChangedListener.OnTaskRenamed();
           }
    }
    public void changeTaskReminderDate(Task task, String newDate){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelperItem.COLUMN_NAME_TASK_REMINDER_DATE, newDate);

        db.update(DBHelperItem.TABLE_NAME,cv,DBHelperItem._ID + "=" + task.getId(),null);

        if(onDatabaseChangedListener!=null){
            onDatabaseChangedListener.OnTaskReminderDateChanged();
        }
    }
    //remove task
    public void removeTaskAtPosition(int id){
        SQLiteDatabase db = getWritableDatabase();
        String[] whereArgs = { String.valueOf(id) };
        db.delete(DBHelperItem.TABLE_NAME, "_ID=?", whereArgs);

        if(onDatabaseChangedListener!=null){
            onDatabaseChangedListener.OnTaskRemoved();
        }
    }
}
