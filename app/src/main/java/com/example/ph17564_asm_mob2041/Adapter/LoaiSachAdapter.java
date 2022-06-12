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

import com.example.ph17564_asm_mob2041.Entity.LoaiSach;
import com.example.ph17564_asm_mob2041.R;
import com.example.ph17564_asm_mob2041.ui.LoaiSachFragment;


import java.util.ArrayList;

public class LoaiSachAdapter extends ArrayAdapter<LoaiSach> {
    private Context context;
    LoaiSachFragment fragment;
    private ArrayList<LoaiSach> lists;
    TextView tvMaLS,tvTenLS;
    ImageView imgXoaLS;

    public LoaiSachAdapter(@NonNull Context context, LoaiSachFragment fragment, ArrayList<LoaiSach> lists) {
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
            v=inflater.inflate(R.layout.loaisach_item,null);
        }
        final  LoaiSach item = lists.get(position);
        if (item != null){

            tvMaLS = v.findViewById(R.id.tvMaLS);
            tvMaLS.setText("Mã loại sách: "+item.maLoai);
            tvTenLS = v.findViewById(R.id.tvTenLS);
            tvTenLS.setText("Tên loại sách: "+item.tenLoai);

            imgXoaLS = v.findViewById(R.id.imgXoaLS);
        }
        imgXoaLS.setOnClickListener(v1 -> {
            fragment.xoa(String.valueOf(item.maLoai));
        });
        return v;
    }
}
