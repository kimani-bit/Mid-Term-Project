package com.example.testing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table UserDetails (username TEXT primary key, password TEXT)");
        DB.execSQL("create Table WaterDetails (water_hse_number TEXT primary key, water_previous_reading INTEGER, " +
                "water_current_reading INTEGER, usage_water INTEGER, water_amount_paid INTEGER, water_balance INTEGER, " +
                "water_month INTEGER, phone_number INTEGER)");
        DB.execSQL("create Table ElectricityDetails (electricity_hse_number TEXT primary key, " +
                "electricity_previous_reading INTEGER, electricity_current_reading INTEGER, usage_electricity REAL, " +
                "electricity_amount_paid INTEGER, electricity_balance REAL, electricity_month INTEGER, " +
                "phone_number INTEGER)");
        DB.execSQL("create Table Rates(type TEXT primary key, value DOUBLE(5, 2))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {

        DB.execSQL("drop Table if exists UserDetails");

    }

    //insert user credentials
    public Boolean insertuserdata(String username, String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //Sign up and log in
        contentValues.put("username", username);
        contentValues.put("password", password);

        long result = DB.insert("UserDetails", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //insert water data
    public Boolean insertwaterdata(String water_hse_number,
                                   Integer water_previous_reading, Integer water_current_reading,
                                   Integer usage_water, Integer water_amount_paid, Integer water_balance,
                                   Integer water_month, Integer phone_number) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //Water DB
        contentValues.put("water_hse_number", water_hse_number);
        contentValues.put("water_previous_reading", water_previous_reading);
        contentValues.put("water_current_reading", water_current_reading);
        contentValues.put("usage_water", usage_water);
        contentValues.put("water_amount_paid", water_amount_paid);
        contentValues.put("water_balance", water_balance);
        contentValues.put("water_month", water_month);
        contentValues.put("phone_number", phone_number);

        long result = DB.insert("WaterDetails", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //insert electricity data
    public Boolean insertelectricitydata(String electricity_hse_number, double electricity_previous_reading,
                                         double electricity_current_reading, double usage_electricity,
                                         double electricity_amount_paid, double electricity_balance,
                                         Integer electricity_month, Integer phone_number) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("electricity_hse_number", electricity_hse_number);
        contentValues.put("electricity_previous_reading", electricity_previous_reading);
        contentValues.put("electricity_current_reading", electricity_current_reading);
        contentValues.put("usage_electricity", usage_electricity);
        contentValues.put("electricity_amount_paid", electricity_amount_paid);
        contentValues.put("electricity_balance", electricity_balance);
        contentValues.put("electricity_month", electricity_month);
        contentValues.put("phone_number", phone_number);

        long result = DB.insert("ElectricityDetails", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //rates update
    public Boolean insertrates(String type, double value) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //Sign up and log in
        contentValues.put("type", type);
        contentValues.put("value", value);

        long result = DB.insert("Rates", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public Boolean checkusername(String username){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from UserDetails where username=?", new String[] {username});
        if (cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean checkpassword(String username, String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from UserDetails where username=? and password=?",
                new String[] {username,password});
        if (cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }


    public Boolean updateuserdata(String username, String password)
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        Cursor cursor=DB.rawQuery("Select * from UserDetails where username=?", new String[] {username});
        if(cursor.getCount()>0) {
            long result = DB.update("UserDetails", contentValues, "name=?", new String[]{username});

            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }

    //update water records
    public Boolean updatewater(String water_hse_number, Integer water_previous_reading, Integer water_current_reading,
                               Integer usage_water, Integer water_amount_paid, Integer water_balance,
                               Integer water_month, Integer phone_number)
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("water_previous_reading",water_previous_reading);
        contentValues.put("water_current_reading",water_current_reading);
        contentValues.put("usage_water",usage_water);
        contentValues.put("water_amount_paid",water_amount_paid);
        contentValues.put("water_balance",water_balance);
        contentValues.put("water_month",water_month);
        contentValues.put("phone_number", phone_number);
        Cursor cursor=DB.rawQuery("Select * from WaterDetails where water_hse_number=?",
                new String[] {water_hse_number});
        if(cursor.getCount()>0) {
            long result = DB.update("WaterDetails", contentValues, "water_hse_number=?",
                    new String[]{water_hse_number});

            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }


    //update electricity records
    public Boolean updateelectricity(String electricity_hse_number, Double electricity_previous_reading,
                                     Double electricity_current_reading,
                                     Double usage_electricity, Double electricity_amount_paid, Double electricity_balance,
                                     Integer electricity_month, Integer phone_number)
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("electricity_previous_reading",electricity_previous_reading);
        contentValues.put("electricity_current_reading",electricity_current_reading);
        contentValues.put("usage_electricity",usage_electricity);
        contentValues.put("electricity_amount_paid",electricity_amount_paid);
        contentValues.put("electricity_balance",electricity_balance);
        contentValues.put("electricity_month",electricity_month);
        contentValues.put("phone_number", phone_number);
        Cursor cursor=DB.rawQuery("Select * from ElectricityDetails where electricity_hse_number=?",
                new String[] {electricity_hse_number});
        if(cursor.getCount()>0) {
            long result = DB.update("ElectricityDetails", contentValues, "electricity_hse_number=?",
                    new String[]{electricity_hse_number});

            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }

    //delete data
    public Boolean deletedata(String username) {
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor=DB.rawQuery("Select * from UserDetails where username=?", new String[] {username});
        if(cursor.getCount()>0) {
            long result = DB.delete("UserDetails", "name=?", new String[]{username});

            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }

    //obtain rates for water or electricity from admin side
    public Cursor getdata(String type)
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor=DB.rawQuery("Select * from Rates where type=?",new String[] {type});
        return cursor;
    }

    //obtain previous readings for electricity
    public Cursor obtainelectricity(String electricity_hse_number)
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor=DB.rawQuery("Select * from ElectricityDetails where electricity_previous_reading=?",new String[]{electricity_hse_number});
        return cursor;
    }

    //obtain previous reading for water
    public Cursor obtainwater(String water_hse_number)
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor=DB.rawQuery("Select * from WaterDetails where water_previous_reading=?",new String[]{water_hse_number});
        return cursor;
    }

    //printing the data
    public Cursor gethousedata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Waterdetails", null);
        return cursor;
    }
}
