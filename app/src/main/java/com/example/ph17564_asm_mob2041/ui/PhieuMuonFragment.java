package com.example.ph17564_asm_mob2041.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.ph17564_asm_mob2041.Adapter.LoaiSachSpinnerAdapter;
import com.example.ph17564_asm_mob2041.Adapter.PhieuMuonAdapter;
import com.example.ph17564_asm_mob2041.Adapter.SachAdapter;
import com.example.ph17564_asm_mob2041.Adapter.SachSpinnerAdapter;
import com.example.ph17564_asm_mob2041.Adapter.ThanhVienSpinnerAdapter;
import com.example.ph17564_asm_mob2041.DAO.LoaiSachDAO;
import com.example.ph17564_asm_mob2041.DAO.PhieuMuonDAO;
import com.example.ph17564_asm_mob2041.DAO.SachDAO;
import com.example.ph17564_asm_mob2041.DAO.ThanhVienDAO;
import com.example.ph17564_asm_mob2041.Entity.LoaiSach;
import com.example.ph17564_asm_mob2041.Entity.PhieuMuon;
import com.example.ph17564_asm_mob2041.Entity.Sach;
import com.example.ph17564_asm_mob2041.Entity.ThanhVien;
import com.example.ph17564_asm_mob2041.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PhieuMuonFragment extends Fragment {

    ListView lv;
    ArrayList<PhieuMuon> list;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaPM;
    Spinner spTV,spSach;
    TextView tvNgay,tvTienThue;
    CheckBox chkTraSach;
    Button btnSave,btnCancel;
    static PhieuMuonDAO dao;
    PhieuMuonAdapter adapter;
    PhieuMuon item;
    ThanhVienSpinnerAdapter thanhVienSpinnerAdapter;
    ArrayList<ThanhVien> listThanhVien;
    ThanhVienDAO thanhVienDAO;
    ThanhVien thanhVien;
    int maThanhVien;
    SachSpinnerAdapter sachSpinnerAdapter;
    ArrayList<Sach> listSach;
    SachDAO sachDAO;
    Sach sach;
    int maSach,tienThue;
    int positionTV,positionSach;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_phieu_muon,container,false);
        lv = v.findViewById(R.id.lvPhieuMuon);
        fab = v.findViewById(R.id.fab);
        dao = new PhieuMuonDAO(getActivity());
        capNhatLv();
        fab.setOnClickListener(v1 -> {
            if (dao.checkTV()>0){
                Toast.makeText(getContext(),"Không tìm thấy thành viên",Toast.LENGTH_SHORT).show();
            }
            else if (dao.checkSach()>0){
                Toast.makeText(getContext(),"Không tìm thấy sách",Toast.LENGTH_SHORT).show();
            }
            else {
                openDialog(getActivity(),0);
            }
        });
        lv.setOnItemLongClickListener((parent, view, position, id) -> {
            item = list.get(position);
            openDialog(getActivity(),1);//update
            return false;
        });
        return v;
    }
    protected void openDialog(final Context context, final int type){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.phieumuon_dialog);
        edMaPM = dialog.findViewById(R.id.edMaPM);
        spTV = dialog.findViewById(R.id.spHoTen);
        spSach = dialog.findViewById(R.id.spTenSach);
        tvNgay = dialog.findViewById(R.id.tvNgay);
        tvTienThue = dialog.findViewById(R.id.tvTienThue);
        chkTraSach = dialog.findViewById(R.id.chkTraSach);
        btnSave = dialog.findViewById(R.id.btnSave);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        //lay ma thanh vien
        thanhVienDAO = new ThanhVienDAO(context);
        listThanhVien = new ArrayList<ThanhVien>();
        listThanhVien = (ArrayList<ThanhVien>)thanhVienDAO.getAll();
        thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(context,listThanhVien);
        spTV.setAdapter(thanhVienSpinnerAdapter);
        spTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maThanhVien = listThanhVien.get(position).maTV;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //lay ma sach
        sachDAO = new SachDAO(context);
        listSach = new ArrayList<Sach>();
        listSach = (ArrayList<Sach>)sachDAO.getAll();
        sachSpinnerAdapter = new SachSpinnerAdapter(context,listSach);
        spSach.setAdapter(sachSpinnerAdapter);
        spSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = listSach.get(position).maSach;
                tienThue = listSach.get(position).giaThue;
                tvTienThue.setText("Tiền thuê: "+tienThue);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tvNgay.setText("Ngày thuê: "+now());

        //ktra type 0 or 1
        edMaPM.setEnabled(false);
        if (type!=0){
            edMaPM.setText(String.valueOf(item.maPM));
            for (int i =0;i<listThanhVien.size();i++)
                if (item.maTV==(listThanhVien.get(i).maTV)){
                    positionTV = i;
                }
            spTV.setSelection(positionTV);
            for (int i =0;i<listSach.size();i++)
                if (item.maSach==(listSach.get(i).maSach)){
                    positionSach = i;
                }
            spSach.setSelection(positionSach);
            tvNgay.setText("Ngày thuê: "+item.ngay);
            tvTienThue.setText("Tiền thuê:" +item.tienThue);
            if (item.traSach==1){
                chkTraSach.setChecked(true);
            }
            else {
                chkTraSach.setChecked(false);
            }
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new PhieuMuon();
                item.maSach = maSach;
                item.maTV = maThanhVien;
                item.ngay = java.sql.Date.valueOf(now());
                item.tienThue = tienThue;
                if (chkTraSach.isChecked()){
                    item.traSach = 1;
                }
                else {
                    item.traSach =0;
                }
                if (validate()>0){
                    if (type==0){
                        if (dao.insert(item)>0){
                            Toast.makeText(context,"Thêm thành công",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context,"Thêm thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        item.maPM = Integer.parseInt(edMaPM.getText().toString());
                        if (dao.update(item)>0){
                            Toast.makeText(context,"Sửa thành công",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context,"Sửa thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhatLv();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    private String now(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(Calendar.getInstance().getTime());
        return date;
    }

    public void xoa(final String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.delete(id);
                capNhatLv();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert  = builder.create();
        builder.show();
    }
    java.util.Date date;
    void capNhatLv(){
        list = (ArrayList<PhieuMuon>)dao.getAll();
        adapter = new PhieuMuonAdapter(getActivity(),this,list);
        lv.setAdapter(adapter);
    }
    private int validate(){
        int check = 1;
        return check;
    }
}