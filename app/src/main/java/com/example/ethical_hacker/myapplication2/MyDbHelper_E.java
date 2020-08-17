package com.example.ethical_hacker.myapplication2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyDbHelper_E extends SQLiteOpenHelper {


    SQLiteDatabase db;

    private static final String DB_NAME = "USER_RECORD";
    private static final int DB_VERSION = 11;

    public static final String I_D = "_id";

    public static final String User_Table = "User";
    public static final String USERNAME = "Username";
    public static final String MOBILE = "Mobile";
    public static final String PASSWORD = "Password";

    public static final String Category_Table = "Category";
    public static final String USER_ID = "User_id";
    public static final String CATEGORY_NAME = "Category_name";
    public static final String Exp_Table = "Expense";
    public static final String CATEGORY_ID = "Category_id";
    public static final String AMOUNT = "Amount";
    public static final String COMMENT = "Comment";
    public static final String Inc_Table = "Income";
    public static final String Exp_Date = "Exp_Date";


    private static final String CREATE_TABLE1 = "CREATE TABLE " + User_Table + "(" + I_D + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " " + USERNAME + " VARCHAR," + MOBILE + " VARCHAR UNIQUE , " + PASSWORD + " VARCHAR );";

    private static final String CREATE_TABLE2 = "CREATE TABLE " + Category_Table + "(" + I_D + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " " + USER_ID + " VARCHAR," + CATEGORY_NAME + " VARCHAR );";

    private static final String CREATE_TABLE3 = "CREATE TABLE " + Exp_Table + "(" + I_D + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " " + USER_ID + " VARCHAR," + CATEGORY_ID + " VARCHAR, " + AMOUNT + " INTEGER, " + COMMENT + " VARCHAR, " + Exp_Date + " VARCHAR );";

    private static final String CREATE_TABLE4 = "CREATE TABLE " + Inc_Table + "(" + I_D + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " " + USER_ID + " VARCHAR," + CATEGORY_ID + " VARCHAR, " + AMOUNT + " INTEGER, " + COMMENT + " VARCHAR," + Exp_Date + " VARCHAR  );";


    public MyDbHelper_E(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);
        db.execSQL(CREATE_TABLE3);
        db.execSQL(CREATE_TABLE4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean setUser(String username, String mobile, String password) {
//       String latLngs = latLng.toString();
        SQLiteDatabase db = getWritableDatabase();

        long status = 0;
        ContentValues cv = new ContentValues();
        cv.put(MyDbHelper_E.USERNAME, username);
        cv.put(MyDbHelper_E.MOBILE, mobile);
        cv.put(MyDbHelper_E.PASSWORD, password);

        status = db.insert(User_Table, null, cv);

        if (db.isOpen()) {
            db.close();
        }

        return status > 0 ? true : false;

    }

    public boolean setCategory(String userid, String category_name) {
//       String latLngs = latLng.toString();
        SQLiteDatabase db = getWritableDatabase();

        long status = 0;
        ContentValues cv = new ContentValues();
        cv.put(MyDbHelper_E.USER_ID, userid);
        cv.put(MyDbHelper_E.CATEGORY_NAME, category_name);

        status = db.insert(Category_Table, null, cv);

        if (db.isOpen()) {
            db.close();
        }

        return status > 0 ? true : false;

    }

    public boolean setExpense(String userid, String category_id, Integer amount, String comment, String expdate) {
//       String latLngs = latLng.toString();
        SQLiteDatabase db = getWritableDatabase();

        long status = 0;
        ContentValues cv = new ContentValues();
        cv.put(MyDbHelper_E.USER_ID, userid);
        cv.put(MyDbHelper_E.CATEGORY_ID, category_id);
        cv.put(MyDbHelper_E.AMOUNT, amount);
        cv.put(MyDbHelper_E.COMMENT, comment);
        cv.put(MyDbHelper_E.Exp_Date, expdate);

        status = db.insert(Exp_Table, null, cv);

        if (db.isOpen()) {
            db.close();
        }

        return status > 0 ? true : false;
    }

    public boolean setIncome(String userid, String category_id, Integer amount, String comment, String expdate) {
//       String latLngs = latLng.toString();
        SQLiteDatabase db = getWritableDatabase();

        long status = 0;
        ContentValues cv = new ContentValues();
        cv.put(MyDbHelper_E.USER_ID, userid);
        cv.put(MyDbHelper_E.CATEGORY_ID, category_id);
        cv.put(MyDbHelper_E.AMOUNT, amount);
        cv.put(MyDbHelper_E.COMMENT, comment);
        cv.put(MyDbHelper_E.Exp_Date, expdate);

        status = db.insert(Inc_Table, null, cv);

        if (db.isOpen()) {
            db.close();
        }

        return status > 0 ? true : false;
    }

    public boolean update(String table, String id, String category_id, Integer amount, String comment, String expdate) {
//       String latLngs = latLng.toString();
        SQLiteDatabase db = getWritableDatabase();

        long status = 0;
        ContentValues cv = new ContentValues();
        cv.put(MyDbHelper_E.CATEGORY_ID, category_id);
        cv.put(MyDbHelper_E.AMOUNT, amount);
        cv.put(MyDbHelper_E.COMMENT, comment);
        cv.put(MyDbHelper_E.Exp_Date, expdate);

        status = db.update(table, cv, I_D + " = " + id, null);

        if (db.isOpen()) {
            db.close();
        }

        return status > 0 ? true : false;

    }

    public boolean updateCategory(String table, String id, String category_name) {
//       String latLngs = latLng.toString();
        SQLiteDatabase db = getWritableDatabase();

        long status = 0;
        ContentValues cv = new ContentValues();
        cv.put(MyDbHelper_E.CATEGORY_NAME, category_name);

        status = db.update(table, cv, I_D + " = " + id, null);

        if (db.isOpen()) {
            db.close();
        }

        return status > 0 ? true : false;

    }

    // Getting All Contacts
    public ArrayList<Model> getLoginData(String mobileno, String password) {
        ArrayList<Model> locationList = new ArrayList<>();
        String query = "SELECT *  FROM " + User_Table + " WHERE " + MOBILE + " = '" + mobileno + "' AND " + PASSWORD + " = '" + password + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Model model;

        if (cursor.moveToFirst()) {
            do {
                model = new Model();

                model.setId(cursor.getString(0));
                model.setUsername(cursor.getString(1));

                model.setMobile(cursor.getString(2));


                locationList.add(model);
            }
            while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        if (db.isOpen()) {
            db.close();
        }
        // return contact list
        return locationList;
    }


    public ArrayList<Model> getCategoryData(String userid) {
        ArrayList<Model> locationList = new ArrayList<>();
        String query = "SELECT *  FROM " + Category_Table + " WHERE " + USER_ID + " = " + userid;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Model model;

        if (cursor.moveToFirst()) {
            do {
                model = new Model();

                model.setId(cursor.getString(0));
                model.setUserid(cursor.getString(1));
                model.setCategoryname(cursor.getString(2));

                locationList.add(model);
            }
            while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        if (db.isOpen()) {
            db.close();
        }

        // return contact list
        return locationList;
    }

    public ArrayList<Model> getExpenseData(String userid) {
        ArrayList<Model> locationList = new ArrayList<>();
        String query = "SELECT *  FROM " + Exp_Table + " WHERE " + USER_ID + " = " + userid;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Model model;

        if (cursor.moveToFirst()) {
            do {
                model = new Model();

                model.setId(cursor.getString(0));
                model.setUserid(cursor.getString(1));
                model.setCategoryname(cursor.getString(2));
                model.setAmount(cursor.getString(3));
                model.setComment(cursor.getString(4));
                model.setDate(cursor.getString(5));

                locationList.add(model);
            }
            while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        if (db.isOpen()) {
            db.close();
        }

        // return contact list
        return locationList;
    }

    public int getMonthExpense(String currdate, String userid) {
        String query = "SELECT sum(" + AMOUNT + ") FROM " + Exp_Table + " WHERE " + USER_ID + " = " + userid + " AND " + Exp_Date + " LIKE '%" + currdate + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        //Model model;
        int a = 0;
        if (cursor.moveToFirst()) {
            a = cursor.getInt(0);

        }
        return a;
    }

    public int getMonthInc(String currdate, String userid) {
        String query = "SELECT sum(" + AMOUNT + ") FROM " + Inc_Table + " WHERE " + USER_ID + " = " + userid + " AND " + Exp_Date + " LIKE '%" + currdate + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        //Model model;
        int a = 0;
        if (cursor.moveToFirst()) {
            a = cursor.getInt(0);

        }
        return a;
    }

    public ArrayList<Model> getIncomeData(String userid) {
        ArrayList<Model> locationList = new ArrayList<>();
        String query = "SELECT *  FROM " + Inc_Table + " WHERE " + USER_ID + " = " + userid;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Model model;

        if (cursor.moveToFirst()) {
            do {
                model = new Model();
                model.setId(cursor.getString(0));
                model.setUserid(cursor.getString(1));
                model.setCategoryname(cursor.getString(2));
                model.setAmount(cursor.getString(3));
                model.setComment(cursor.getString(4));
                model.setDate(cursor.getString(5));

                locationList.add(model);
            }
            while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        if (db.isOpen()) {
            db.close();
        }

        // return contact list
        return locationList;
    }
    // Getting All Contacts


    public void deleteData(String id, String table_name) {
        SQLiteDatabase db = getWritableDatabase();
        // Select All Query
        String selectQuery = "DELETE  FROM " + table_name + " WHERE " + I_D + "=" + id;

        db.execSQL(selectQuery);

        if (db.isOpen()) {
            db.close();
        }

    }


}

