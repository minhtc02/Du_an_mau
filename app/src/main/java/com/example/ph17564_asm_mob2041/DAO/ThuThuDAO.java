package com.example.ph17564_asm_mob2041.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ph17564_asm_mob2041.DataBase.DbHelper;
import com.example.ph17564_asm_mob2041.Entity.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    private SQLiteDatabase db;

    public ThuThuDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public long insert(ThuThu obj) {
        ContentValues values = new ContentValues();
        values.put("maTT", obj.maTT);
        values.put("hoTen", obj.hoTen);
        values.put("matKhau", obj.matKhau);
        return db.insert("ThuThu", null, values);
    }

    //update
    public int update(ThuThu obj) {
        ContentValues values = new ContentValues();
        values.put("maTT", obj.maTT);
        values.put("hoTen", obj.hoTen);
        values.put("matKhau", obj.matKhau);
        return db.update("ThuThu", values, "maTT=?", new String[]{String.valueOf(obj.maTT)});
    }

    //delete
    public int delete(String id) {
        return db.delete("ThuThu", "maTT=?", new String[]{id});
    }

    // get tat ca data
    public List<ThuThu> getAll() {
        String sql = "SELECT * FROM ThuThu";
        return getData(sql);
    }


    //getData theo id
    public ThuThu getID(String id) {
        String sql = "SELECT * FROM ThuThu WHERE maTT=?";
        List<ThuThu> list = getData(sql, id);
        return list.get(0);
    }

    private List<ThuThu> getData(String sql, String... selectionArgs) {
        List<ThuThu> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            ThuThu obj = new ThuThu();
            obj.maTT = c.getString(c.getColumnIndex("maTT"));
            obj.hoTen = c.getString(c.getColumnIndex("hoTen"));
            obj.matKhau = c.getString(c.getColumnIndex("matKhau"));
            list.add(obj);
        }
        return list;
    }
    public boolean checkThuThu(){
        String getThuThu = "SELECT * FROM ThuThu";
        Cursor cursor = db.rawQuery(getThuThu,null);
        if (cursor.getCount()==0){
            return true;
        }
        else {
            return false;

        }
    }
    public boolean checkAcc(String strUser, String strPass) {
        String getAccount = "SELECT * FROM ThuThu WHERE maTT = '"+strUser+"' " +
                "AND matKhau = '"+strPass+"'";
        Cursor cursor = db.rawQuery(getAccount,null);
        if (cursor.getCount()!=0){
            return true;
        }
        else {
            return false;

        }
    }
}

