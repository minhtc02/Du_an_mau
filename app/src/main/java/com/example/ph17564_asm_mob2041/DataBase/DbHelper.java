package com.example.ph17564_asm_mob2041.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    static final String dbname="PNLIB";
    static final int dvVersion=1;
    public DbHelper(Context context) {
        super(context,dbname,null,dvVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Tao bang Thu Thu
        String createTableThuThu=
                "create table ThuThu (" +
                        "maTT TEXT PRIMARY KEY, " +
                        "hoTen TEXT NOT NULL, " +
                        "matKhau TEXT NOT NULL )";
        db.execSQL(createTableThuThu);

        //Tao bang Thanh Vien
        String createTableThanhVien=
                "create table ThanhVien (" +
                        "maTV INTEGER PRIMARY KEY , " +
                        "hoTen TEXT NOT NULL, " +
                        "namSinh TEXT NOT NULL )";
        db.execSQL(createTableThanhVien);

        //Tao bang Loai Sach
        String createTableLoaiSach=
                "create table LoaiSach (" +
                        "maLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tenLoai TEXT NOT NULL )";
        db.execSQL(createTableLoaiSach);

        //Tao bang Sach
        String createTableSach=
                "create table Sach (" +
                        "maSach INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tenSach TEXT NOT NULL, " +
                        "giaThue INTEGER NOT NULL, "+
                        "maLoai INTEGER REFERENCES LoaiSach(maLoai))";
        db.execSQL(createTableSach);

        //Tao bang PhieuMuon
        String createTablePhieuMuon=
                "create table PhieuMuon (" +
                        "maPM INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tienThue INTEGER NOT NULL, " +
                        "ngay DATE NOT NULL, " +
                        "traSach INTEGER NOT NULL, " +
                        "maTT TEXT REFERENCES ThuThu(maTT), " +
                        "maTV INTEGER REFERENCES ThanhVien(maTV), " +
                        "maSach INTEGER REFERENCES Sach(maSach))" ;
        db.execSQL(createTablePhieuMuon);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String dropTableThuThu = "drop table if exists ThuThu";
        db.execSQL(dropTableThuThu);
        String dropTableThanhVien = "drop table if exists ThanhVien";
        db.execSQL(dropTableThanhVien);
        String dropTableLoaiSach = "drop table if exists LoaiSach";
        db.execSQL(dropTableLoaiSach);
        String dropTableSach = "drop table if exists Sach";
        db.execSQL(dropTableSach);
        String dropTablePhieuMuon = "drop table if exists PhieuMuon";
        db.execSQL(dropTablePhieuMuon);
    }
}