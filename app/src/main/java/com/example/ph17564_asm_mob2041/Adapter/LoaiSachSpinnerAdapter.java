package com.example.ph17564_asm_mob2041.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ph17564_asm_mob2041.Entity.LoaiSach;
import com.example.ph17564_asm_mob2041.R;

import java.util.ArrayList;

public class LoaiSachSpinnerAdapter extends ArrayAdapter<LoaiSach> {
    private Context context;
    private ArrayList<LoaiSach> lists;
    TextView tvMaLoaiSach,tvTenLoaiSach;

    public LoaiSachSpinnerAdapter(@NonNull Context context, ArrayList<LoaiSach> lists) {
        super(context, 0,lists);
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v==null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.loaisach_item_spinner,null);
        }
        final LoaiSach item = lists.get(position);
        if (item!=null){
            tvMaLoaiSach = v.findViewById(R.id.tvMaLoaiSachSp);
            tvMaLoaiSach.setText(item.maLoai+".");
            tvTenLoaiSach = v.findViewById(R.id.tvTenLoaiSachSp);
            tvTenLoaiSach.setText(item.tenLoai);
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v==null){
            LayoutInflater inflater=(LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.loaisach_item_spinner,null);
        }
        final LoaiSach item = lists.get(position);
        if (item!=null){
            tvMaLoaiSach = v.findViewById(R.id.tvMaLoaiSachSp);
            tvMaLoaiSach.setText(item.maLoai+".");
            tvTenLoaiSach = v.findViewById(R.id.tvTenLoaiSachSp);
            tvTenLoaiSach.setText(item.tenLoai);
        }
        return v;
    }
}
