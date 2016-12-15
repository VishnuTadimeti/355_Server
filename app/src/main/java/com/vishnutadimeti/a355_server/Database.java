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


    // Create Tables

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


    // Add "Task" data to the table
    void addTaskRecord(String value) {
        String a = "'" + value + "'";
        this.getWritableDatabase().execSQL("insert into " + taskTable + " values (" + a + ")");
    }

    // Add "Calendar" event to the table
    void addCalRecord(String value, int month, int day, int year) {
        String a = "'" + value + "'";
        this.getWritableDatabase().execSQL("insert into " + calTable + " values (" + a + ", " + month + ", " + day + ", " + year + ")");
    }

    // Add "Chat" message to the table
    void addChatRecord(String value) {
        String a = "'" + value + "'";
        this.getWritableDatabase().execSQL("insert into " + chatTable + " values (" + a + ")");
    }

    // Read through the "Tasks" Table and return the data in terms of JSON
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

    // Read through the "Cal" Table and return the data in terms of JSON
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

    // Read through the "Chat" Table and return the data in terms of JSON
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
