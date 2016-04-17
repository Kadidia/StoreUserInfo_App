package com.example.kadi.storeuserinfo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Kadi on 12/03/2016.
 */
public class myDBHandler extends SQLiteOpenHelper {
    private HashMap<String,Integer> link;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ConnectionDB";

    private static final String USER_INFO_KEY = "id";
    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String WEBSITE_link = "link";
    private static final String VISIT_number = "number";
    private static final String USER_INFO_TABLE_NAME = "UserInfo";

    private static final String TABLE_USER_INFO_CREATE =
            "CREATE TABLE " + USER_INFO_TABLE_NAME + " (" +
                    USER_INFO_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    WEBSITE_link + TYPE_TEXT +"," +
                    VISIT_number + TYPE_INTEGER + ");";

    private static final  String TABLE_USER_INFO_DELETE =
            "DROP TABLE IF EXISTS " + USER_INFO_TABLE_NAME + ";";
    private static final  String UPDATE_TABLE =
            "UPDATE "+ USER_INFO_TABLE_NAME + " SET " + VISIT_number +" = " + VISIT_number + " + 1";

    public myDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USER_INFO_CREATE);
        String count = "SELECT count(*) FROM " + USER_INFO_TABLE_NAME;
        Cursor cursor = db.rawQuery(count, null);
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        if(icount <= 0) initializeUsersDataBase(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_USER_INFO_DELETE);


    }
    public void addlink(String key){

        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("dnd,dd", "linkkkkk");
        String script = UPDATE_TABLE + " WHERE "+ WEBSITE_link +" LIKE '" + key + "';";
        db.execSQL(script);
        db.close();
    }
    public  void add(String key, Integer visit, SQLiteDatabase db){

            // SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(WEBSITE_link, key);
            values.put(VISIT_number, visit);


            db.insert(USER_INFO_TABLE_NAME, null, values);
          //  db.close();

    }
    public HashMap<String,Integer> getAllUserInfos(){
       HashMap<String, Integer> data = new HashMap<>();
        String selectQuery = "SELECT * FROM " + USER_INFO_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{

                data.put(cursor.getString(1), Integer.parseInt(cursor.getString(2)));

            }while (cursor.moveToNext());
        }
        return data;

    }
    public void initializeUsersDataBase(SQLiteDatabase db){
        link = new HashMap<String, Integer>();
        link.put("en.wikipedia.org/wiki/Mexico_City",0);
        link.put("en.wikipedia.org/wiki/Beijing",0);
        link.put("en.wikipedia.org/wiki/Manila",0);
        link.put("en.wikipedia.org/wiki/Dhaka",0);
        link.put("en.wikipedia.org/wiki/Seoul",0);
        link.put("en.wikipedia.org/wiki/Jakarta",0);
        link.put("de.wikipedia.org/wiki/Tokyo",0);
        link.put("de.wikipedia.org/wiki/Taipei",0);
        link.put("en.wikipedia.org/wiki/Bogot%C3%A1",0);
        link.put("en.wikipedia.org/wiki/Hong_Kong", 0);

        for(String key : link.keySet()){

            add(key, link.get(key), db);
        }

    }

}
