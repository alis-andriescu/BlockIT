package com.example.alis.proiect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "block.db";
    public static final String TABLE_NUMBERS = "numbers";
    public static final String COLUMN_IDNUMBER = "_idNumber";
    public static final String COLUMN_PHONENUMBER = "phoneNumber";
    public static final String TABLE_WORDS = "words";
    public static final String COLUMN_IDWORD = "_idWord";
    public static final String COLUMN_WORD = "word";
    public static final String TABLE_DATA = "dates";
    public static final String COLUMN_IDDATA = "_idData";
    public static final String COLUMN_DATA = "data";
    public static final String TABLE_CONTACTS = "contacts";
    public static final String COLUMN_IDCONTACT = "_idContact";
    public static final String COLUMN_CONTACT = "contact";


    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NUMBERS + "(" +
               COLUMN_IDNUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
               COLUMN_PHONENUMBER + " TEXT " +
                ");";

        String queryW = "CREATE TABLE " + TABLE_WORDS + "(" +
                COLUMN_IDWORD + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_WORD + " TEXT " +
                ");";
        String queryD = "CREATE TABLE " + TABLE_DATA + "(" +
                COLUMN_IDDATA + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_DATA + " TEXT " +
                ");";
        String queryC = "CREATE TABLE " + TABLE_CONTACTS + "(" +
                COLUMN_IDCONTACT + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_CONTACT + " TEXT " +
                ");";
        db.execSQL(query);
        db.execSQL(queryW);
        db.execSQL(queryD);
        db.execSQL(queryC);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NUMBERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }


    public void addNumber(PhoneNumber phoneNumber){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHONENUMBER, phoneNumber.get_phoneNumber());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NUMBERS, null, values);
        db.close();
    }

    public void addWord(Word word){
        ContentValues valuesW = new ContentValues();
        valuesW.put(COLUMN_WORD, word.get_word());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_WORDS, null, valuesW);
        db.close();
    }

    public void addData(Data data){
        ContentValues valuesD = new ContentValues();
        valuesD.put(COLUMN_DATA, data.get_data());
       SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_DATA, null, valuesD);
         db.close();

    }
public void addContact(String contact){
    ContentValues valuesD = new ContentValues();
    valuesD.put(COLUMN_CONTACT, contact);
    SQLiteDatabase db = getWritableDatabase();
    db.insert(TABLE_CONTACTS, null, valuesD);
    db.close();

}
    public void deleteNumber(String phoneNumber){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NUMBERS + " WHERE " + COLUMN_PHONENUMBER + "=\"" + phoneNumber + "\";");
    }

    public void deleteWord(String word){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_WORDS + " WHERE " + COLUMN_WORD + "=\"" + word + "\";");
    }

    public void deleteHistory(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_DATA + " WHERE 1;");
    }


    public String databaseNumbersToString(){
        String dbString = "";
        SQLiteDatabase db =  getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NUMBERS + " WHERE 1;";


        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();


        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("phoneNumber")) != null) {
                dbString += c.getString(c.getColumnIndex("phoneNumber"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

    public String databaseWordsToString(){
        String dbStringW = "";
        SQLiteDatabase dbW =  getWritableDatabase();
        String queryW = "SELECT * FROM " + TABLE_WORDS + " WHERE 1";


        Cursor cW = dbW.rawQuery(queryW, null);
        cW.moveToFirst();

        while (!cW.isAfterLast()) {
            if (cW.getString(cW.getColumnIndex("word")) != null) {
                dbStringW += cW.getString(cW.getColumnIndex("word"));
                dbStringW += "\n";
            }
            cW.moveToNext();
        }
        dbW.close();
        return dbStringW;

    }

    public String databaseDataToString(){
        String dbStringW = "";
        SQLiteDatabase dbW =  getWritableDatabase();
        String queryW = "SELECT * FROM " + TABLE_DATA + " WHERE 1";


        Cursor cW = dbW.rawQuery(queryW, null);
        cW.moveToFirst();

        while (!cW.isAfterLast()) {
            if (cW.getString(cW.getColumnIndex("data")) != null) {
                dbStringW += cW.getString(cW.getColumnIndex("data"));
                dbStringW += "\n";
            }
            cW.moveToNext();
        }
        dbW.close();
        return dbStringW;

    }
    public String databaseContactsToString(){
        String dbStringW = "";
        SQLiteDatabase dbW =  getWritableDatabase();
        String queryW = "SELECT * FROM " + TABLE_CONTACTS + " WHERE 1";


        Cursor cW = dbW.rawQuery(queryW, null);
        cW.moveToFirst();

        while (!cW.isAfterLast()) {
            if (cW.getString(cW.getColumnIndex("contact")) != null) {
                dbStringW += cW.getString(cW.getColumnIndex("contact"));
                dbStringW += "\n";
            }
            cW.moveToNext();
        }
        dbW.close();
        return dbStringW;

    }
}
