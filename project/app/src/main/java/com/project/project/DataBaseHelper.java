package com.project.project;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseHelper extends android.database.sqlite.SQLiteOpenHelper {


    public static final String TABLE_NAME = "USER";
    private static final String DB_NAME = "TravelDestination";



    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int
            version) {
        super(context, name, null, version);

    }

    public DataBaseHelper(Context context) {
        super(context,DB_NAME ,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE USER(EMAIL TEXT PRIMARY KEY  NOT NULL,FIRST_NAME TEXT,LAST_NAME TEXT ,PASSWORD TEXT, CONTINENT TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

// add user to database
    public void insert(String email, String FirstName, String LastName, String
            Password, String Continent ){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", email);
        contentValues.put("FIRST_NAME", FirstName);
        contentValues.put("LAST_NAME", LastName);
        contentValues.put("PASSWORD", Password);
        contentValues.put("CONTINENT", Continent);

        db.insert("USER", null, contentValues);
    }


  //to remember the email and password
    public boolean remember(String email, String password){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT EMAIL, PASSWORD FROM USER WHERE EMAIL = ?",new String[]{email});
        if(cursor.getCount()>0) {
            if (cursor.moveToFirst ()) {
                String data = cursor.getString(cursor.getColumnIndexOrThrow("PASSWORD"));
                if (password.equals (data))
                    return true;
            }
            return false;
        }
        else
            return  false;
    }

//update user
    public void update(String Email,String FirstName, String LastName, String
            Password,String Continent){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FIRST_NAME" ,FirstName);
        contentValues.put("LAST_NAME", LastName);
        contentValues.put("PASSWORD", Password);
        contentValues.put("CONTINENT", Continent);
        db.update("USER", contentValues, "EMAIL" + " = ?", new String[] {String.valueOf(Email)});
    }

    public String getEmail(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USER" , null);
        cursor.moveToLast();
        String email = cursor.getString(cursor.getColumnIndexOrThrow("EMAIL"));
        cursor.close();
        db.close();
        return email;
    }

    public Cursor getFullName(){

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT FIRST_NAME,LAST_NAME FROM USER  ";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
    public Cursor getPassword(){

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT PASSWORD FROM USER ";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public String getContinent(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT CONTINENT FROM USER WHERE EMAIL = ?", new String[] {email});
        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndexOrThrow("CONTINENT"));
        }
        cursor.close();
        return null;
    }
}


