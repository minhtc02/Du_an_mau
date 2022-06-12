package com.example.ph17564_asm_mob2041.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ph17564_asm_mob2041.DataBase.DbHelper;
import com.example.ph17564_asm_mob2041.Entity.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    private SQLiteDatabase db;

    public ThanhVienDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public long insert(ThanhVien obj){
        ContentValues values = new ContentValues();
        values.put("hoTen",obj.hoTen);
        values.put("namSinh",obj.namSinh);

        return db.insert("ThanhVien",null,values);
    }

    //update
    public int update(ThanhVien obj){
        ContentValues values = new ContentValues();
        values.put("hoTen",obj.hoTen);
        values.put("namSinh",obj.namSinh);

        return db.update("ThanhVien",values,"maTV=?",new String[]{String.valueOf(obj.maTV)});
    }

    //delete
    public int delete(String id){
        return db.delete("ThanhVien","maTV=?",new String[]{id});
    }

    // get tat ca data
    public List<ThanhVien> getAll(){
        String sql = "SELECT * FROM ThanhVien";
        return getData(sql);
    }



    //getData theo id
    public  ThanhVien getID(String id){
        String sql = "SELECT * FROM ThanhVien WHERE maTV=?";
        List<ThanhVien> thanhVienList = getData(sql,id);
        return thanhVienList.get(0);
    }
    private List<ThanhVien> getData(String sql,String...selectionArgs) {
        List<ThanhVien> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            ThanhVien obj = new ThanhVien();
            obj.maTV = Integer.parseInt(c.getString(c.getColumnIndex("maTV")));
            obj.hoTen = c.getString(c.getColumnIndex("hoTen"));
            obj.namSinh = c.getString(c.getColumnIndex("namSinh"));
            list.add(obj);
        }
        return list;
    }


}

