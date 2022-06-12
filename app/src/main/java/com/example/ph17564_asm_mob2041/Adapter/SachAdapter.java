package com.example.ph17564_asm_mob2041.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.ph17564_asm_mob2041.DAO.LoaiSachDAO;
import com.example.ph17564_asm_mob2041.Entity.LoaiSach;
import com.example.ph17564_asm_mob2041.Entity.Sach;
import com.example.ph17564_asm_mob2041.R;
import com.example.ph17564_asm_mob2041.ui.SachFragment;

import java.util.ArrayList;

public class SachAdapter extends ArrayAdapter<Sach> {
    private Context context;
    SachFragment fragment;
    private ArrayList<Sach> lists;
    TextView tvMaS,tvTenS,tvGiaThue,tvLoaiSach;
    ImageView imgXoaS;

    public SachAdapter(@NonNull Context context, SachFragment fragment, ArrayList<Sach> lists) {
        super(context, 0,lists);
        this.context = context;
        this.lists = lists;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.sach_item,null);
        }
        final  Sach item = lists.get(position);
        if (item != null){
            LoaiSachDAO loaiSachDAO =  new LoaiSachDAO(context);
            LoaiSach loaiSach = loaiSachDAO.getID(String.valueOf(item.maLoai));
            tvMaS = v.findViewById(R.id.tvMaS);
            tvMaS.setText("Mã sách: "+item.maSach);
            tvTenS = v.findViewById(R.id.tvTenS);
            tvTenS.setText("Tên sách: "+item.tenSach);
            tvGiaThue = v.findViewById(R.id.tvGiaThue);
            tvGiaThue.setText("Giá thuê: "+item.giaThue);
            tvLoaiSach = v.findViewById(R.id.tvLoaiSach);
            tvLoaiSach.setText("Loại sách: "+item.maLoai);

            imgXoaS = v.findViewById(R.id.imgXoaS);
        }
        imgXoaS.setOnClickListener(v1 -> {
            fragment.xoa(String.valueOf(item.maSach));
        });
        return v;
    }
}

