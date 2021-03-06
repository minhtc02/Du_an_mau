package com.example.ph17564_asm_mob2041.Adapter;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ph17564_asm_mob2041.DAO.SachDAO;
import com.example.ph17564_asm_mob2041.DAO.ThanhVienDAO;
import com.example.ph17564_asm_mob2041.Entity.PhieuMuon;
import com.example.ph17564_asm_mob2041.Entity.Sach;
import com.example.ph17564_asm_mob2041.Entity.ThanhVien;
import com.example.ph17564_asm_mob2041.R;
import com.example.ph17564_asm_mob2041.ui.PhieuMuonFragment;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
public class PhieuMuonAdapter extends ArrayAdapter<PhieuMuon> {
    private Context context;
    PhieuMuonFragment fragment;
    private ArrayList<PhieuMuon> lists;
    TextView tvMaPM,tvHoTen,tvTenSach,tvTienThue,tvTraSach,tvNgay;
    ImageView imgXoaPM;
    SachDAO sachDAO;
    ThanhVienDAO thanhVienDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public PhieuMuonAdapter(@NonNull Context context, PhieuMuonFragment fragment, ArrayList<PhieuMuon> lists) {
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
            v=inflater.inflate(R.layout.phieumuon_item,null);
        }
        final  PhieuMuon item = lists.get(position);
        if (item != null){

            tvMaPM = v.findViewById(R.id.tvMaPM);
            tvMaPM.setText("M?? phi???u: "+item.maPM);
            sachDAO  = new SachDAO(context);
            Sach sach = sachDAO.getID(String.valueOf(item.maSach));
            tvTenSach = v.findViewById(R.id.tvTenSach);
            tvTenSach.setText("T??n s??ch: "+sach.tenSach);
            thanhVienDAO  = new ThanhVienDAO(context);
            ThanhVien thanhVien = thanhVienDAO.getID(String.valueOf(item.maTV));
            tvHoTen = v.findViewById(R.id.tvTenPM);
            tvHoTen.setText("Th??nh vi??n: "+thanhVien.hoTen);
            tvTienThue = v.findViewById(R.id.tvTienThue);
            tvTienThue.setText("Ti???n thu??: "+item.tienThue);
            tvNgay = v.findViewById(R.id.tvNgay);
            tvNgay.setText("Ng??y thu??: "+item.ngay);
            //+sdf.format(item.ngay)
            tvTraSach = v.findViewById(R.id.tvTraSach);
            if (item.traSach==1){
                tvTraSach.setText("???? tr??? s??ch");
                tvTraSach.setTextColor(Color.BLUE);
            }else {
                tvTraSach.setTextColor(Color.RED);
                tvTraSach.setText("Ch??a tr??? s??ch");
            }
            imgXoaPM = v.findViewById(R.id.imgXoaPM);
        }
        imgXoaPM.setOnClickListener(v1 -> {
            fragment.xoa(String.valueOf(item.maPM));
        });
        return v;
    }
}

