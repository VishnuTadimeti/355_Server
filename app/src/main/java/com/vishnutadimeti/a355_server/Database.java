package com.vishnutadimeti.a355_server;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by vishnutadimeti on 12/14/16.
 */

public class Database extends SQLiteOpenHelper {

    private static final int databaseVersion = 1;
    private static final String databaseName = "database";
    private static final String taskTable = "taskTable";
    private static final String calTable = "calTable";
    private static final String chatTable = "chatTable";
    private static final String task = "";
    private static final String cal = "";
    private static final String chat = "";

    Database(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqldb) {
        String createTaskTable = "create table " + taskTable + "(" + task +  " title" + ")";
        String createCalTable = "create table " + calTable + "(" + cal +  " event, " + " month, " + "day, " + " year" + ")";
        String createChatTable = "create table " + chatTable + "(" + chat +  " message" + ")";
        sqldb.execSQL(createTaskTable);
        sqldb.execSQL(createCalTable);
        sqldb.execSQL(createChatTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqldb, int i, int i1) {
        sqldb.execSQL("DROP TABLE IF EXISTS "+ taskTable);
        onCreate(sqldb);
    }

    void addTaskRecord(String value) {
        String a = "'" + value + "'";
        this.getWritableDatabase().execSQL("insert into " + taskTable + " values (" + a + ")");
    }

    void addCalRecord(String value, int month, int day, int year) {
        String a = "'" + value + "'";
        this.getWritableDatabase().execSQL("insert into " + calTable + " values (" + a + ", " + month + ", " + day + ", " + year + ")");
    }

    void addChatRecord(String value) {
        String a = "'" + value + "'";
        this.getWritableDatabase().execSQL("insert into " + chatTable + " values (" + a + ")");
    }

//
//    public String getTaskRecord() {
//        Cursor cur = this.getWritableDatabase().rawQuery("select * from taskTable", new String [] {});
//        String text = "";
//        if (cur.moveToFirst()) {
//            while (!cur.isAfterLast()) {
//                int count = cur.getColumnCount();
//                for (int i = 0; i < count; i++) {
//                    text = text + cur.getString(i);
//                }
//                Log.d("Tasks Database: ", text);
//                cur.moveToNext();
//            }
//        }
//        cur.close();
//        return text;
//    }
//
//    public String getCalRecord() {
//        Cursor cur = this.getWritableDatabase().rawQuery("select * from calTable", new String [] {});
//        String text = "";
//        if (cur.moveToFirst()) {
//            while (!cur.isAfterLast()) {
//                int count = cur.getColumnCount();
//                for (int i = 0; i < count; i++) {
//                    text = text + cur.getString(i);
//                }
//                Log.d("Database: ", text);
//                cur.moveToNext();
//            }
//        }
//        cur.close();
//        return text;
//    }
//
//    public String getChatRecord() {
//        Cursor cur = this.getWritableDatabase().rawQuery("select * from chatTable", new String [] {});
//        String text = "";
//        if (cur.moveToFirst()) {
//            while (!cur.isAfterLast()) {
//                int count = cur.getColumnCount();
//                for (int i = 0; i < count; i++) {
//                    text = text + cur.getString(i);
//                }
//                Log.d("Database: ", text);
//                cur.moveToNext();
//            }
//        }
//        cur.close();
//        return text;
//    }

    JSONArray tasksResults() {
        Cursor cursor = this.getReadableDatabase().rawQuery("select * from taskTable", new String [] {});
        JSONArray resultSet = new JSONArray();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0 ; i < totalColumn ; i++) {
                if( cursor.getColumnName(i) != null ) {
                    try
                    {
                        if ( cursor.getString(i) != null ) {
                            Log.d("JSON_Tasks: ", cursor.getString(i) );
                            rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                        }
                        else {
                            rowObject.put( cursor.getColumnName(i) ,  "" );
                        }
                    }
                    catch (Exception e)
                    {
                        Log.d("Wrong: ", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("Task", resultSet.toString());
        return resultSet;
    }

    JSONArray calResults() {
        Cursor cursor = this.getReadableDatabase().rawQuery("select * from calTable", new String [] {});
        JSONArray resultSet = new JSONArray();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0 ; i < totalColumn ; i++) {
                if( cursor.getColumnName(i) != null ) {
                    try
                    {
                        if ( cursor.getString(i) != null ) {
                            Log.d("Getting String: ", cursor.getString(i) );
                            rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                        }
                        else {
                            rowObject.put( cursor.getColumnName(i) ,  "" );
                        }
                    }
                    catch (Exception e)
                    {
                        Log.d("Wrong: ", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("Cal", resultSet.toString());
        return resultSet;
    }

    JSONArray chatResults() {
        Cursor cursor = this.getReadableDatabase().rawQuery("select * from chatTable", new String [] {});
        JSONArray resultSet = new JSONArray();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0 ; i < totalColumn ; i++) {
                if( cursor.getColumnName(i) != null ) {
                    try
                    {
                        if ( cursor.getString(i) != null ) {
                            Log.d("Getting String: ", cursor.getString(i) );
                            rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                        }
                        else {
                            rowObject.put( cursor.getColumnName(i) ,  "" );
                        }
                    }
                    catch (Exception e)
                    {
                        Log.d("Wrong: ", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("Chat", resultSet.toString());
        return resultSet;
    }
}
