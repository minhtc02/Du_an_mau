package com.example.ph17564_asm_mob2041.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ph17564_asm_mob2041.DataBase.DbHelper;
import com.example.ph17564_asm_mob2041.Entity.LoaiSach;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO {
    private SQLiteDatabase db;

    public LoaiSachDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public long insert(LoaiSach obj) {
        ContentValues values = new ContentValues();
        values.put("tenLoai", obj.tenLoai);
        return db.insert("LoaiSach", null, values);
    }

    //update
    public int update(LoaiSach obj) {
        ContentValues values = new ContentValues();
        values.put("tenLoai", obj.tenLoai);
        return db.update("LoaiSach", values, "maLoai=?", new String[]{String.valueOf(obj.maLoai)});
    }

    //delete
    public int delete(String id) {
        return db.delete("LoaiSach", "maLoai=?", new String[]{id});
    }

    // get tat ca data
    public List<LoaiSach> getAll() {
        String sql = "SELECT * FROM LoaiSach";
        return getData(sql);
    }

    //getData theo id
    public LoaiSach getID(String id) {
        String sql = "SELECT * FROM LoaiSach WHERE maLoai=?";
        List<LoaiSach> list = getData(sql, id);
        return list.get(0);
    }

    private List<LoaiSach> getData(String sql, String... selectionArgs) {
        List<LoaiSach> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            LoaiSach obj = new LoaiSach();
            obj.maLoai = Integer.parseInt(c.getString(c.getColumnIndex("maLoai")));
            obj.tenLoai = c.getString(c.getColumnIndex("tenLoai"));
            list.add(obj);
        }
        return list;
    }
}
