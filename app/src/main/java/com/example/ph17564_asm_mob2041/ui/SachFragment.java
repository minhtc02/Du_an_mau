package com.example.ph17564_asm_mob2041.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.ph17564_asm_mob2041.Adapter.LoaiSachAdapter;
import com.example.ph17564_asm_mob2041.Adapter.LoaiSachSpinnerAdapter;
import com.example.ph17564_asm_mob2041.Adapter.SachAdapter;
import com.example.ph17564_asm_mob2041.DAO.LoaiSachDAO;
import com.example.ph17564_asm_mob2041.DAO.SachDAO;
import com.example.ph17564_asm_mob2041.Entity.LoaiSach;
import com.example.ph17564_asm_mob2041.Entity.Sach;
import com.example.ph17564_asm_mob2041.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SachFragment extends Fragment {
    ListView lv;
    ArrayList<Sach> list;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaS,edTenS,edGiaThue;
    Spinner spMaLoai;
    Button btnSave,btnCancel;
    static SachDAO dao;
    SachAdapter adapter;
    Sach item;
    LoaiSachSpinnerAdapter spinnerAdapter;
    ArrayList<LoaiSach> listLoaiSach;
    LoaiSachDAO loaiSachDAO;
    LoaiSach loaiSach;
    int maLoaiSach,position;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sach,container,false);
        lv = v.findViewById(R.id.lvSach);
        fab = v.findViewById(R.id.fab);
        dao = new SachDAO(getActivity());
        capNhatLv();
        fab.setOnClickListener(v1 -> {
            if (dao.checkLoaiSach()>0){
                Toast.makeText(getContext(),"Không tìm thấy loại sách",Toast.LENGTH_SHORT).show();
            }
            else {
                openDialog(getActivity(), 0);
            }
            });
        lv.setOnItemLongClickListener((parent, view, position, id) -> {
            item = list.get(position);
            openDialog(getActivity(),1);
            return false;
        });
        return v;
    }
    protected void openDialog(final Context context, final int type){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.sach_dialog);
        edMaS = dialog.findViewById(R.id.edMaS);
        edTenS = dialog.findViewById(R.id.edTenS);
        edGiaThue = dialog.findViewById(R.id.edGiaThue);
        edGiaThue.setInputType(InputType.TYPE_CLASS_NUMBER);
        spMaLoai = dialog.findViewById(R.id.spLoaiSach);
        btnSave = dialog.findViewById(R.id.btnSave);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        listLoaiSach = new ArrayList<>();
        loaiSachDAO = new LoaiSachDAO(context);
        listLoaiSach = (ArrayList<LoaiSach>)loaiSachDAO.getAll();
        spinnerAdapter = new LoaiSachSpinnerAdapter(context,listLoaiSach);
        spMaLoai.setAdapter(spinnerAdapter);
        //lay ma loai
        spMaLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaiSach = listLoaiSach.get(position).maLoai;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edMaS.setEnabled(false);
        if (type!=0){
            edMaS.setText(String.valueOf(item.maSach));
            edTenS.setText(item.tenSach);
            edGiaThue.setText(String.valueOf(item.giaThue));
            for (int i =0;i<listLoaiSach.size();i++)
                if (item.maLoai==(listLoaiSach.get(i).maLoai)){
                    position = i;
                }
            Log.i("demo","posSach"+position);
                spMaLoai.setSelection(position);
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
                if (validate()>0){
                item = new Sach();
                item.tenSach = edTenS.getText().toString();
                item.giaThue = Integer.parseInt(edGiaThue.getText().toString());
                item.maLoai = maLoaiSach;

                    if (type==0){
                        if (dao.insert(item)>0){
                            Toast.makeText(context,"Thêm thành công",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context,"Thêm thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        item.maSach = Integer.parseInt(edMaS.getText().toString());
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
    void capNhatLv(){
        list = (ArrayList<Sach>)dao.getAll();
        adapter = new SachAdapter(getActivity(),this,list);
        lv.setAdapter(adapter);
    }
    private int validate(){
        int check = 1;
        if(edTenS.getText().length()==0||edGiaThue.getText().length()==0){
            Toast.makeText(getContext(),"Bạn phải nhập đủ thông tin",Toast.LENGTH_SHORT).show();
            check=-1;
        }
        else if (edGiaThue.getText().length()!=0){
            int gia = Integer.parseInt(edGiaThue.getText().toString());
            if (gia<0||gia>500000){
                Toast.makeText(getContext(),"Bạn phải nhập đúng giá thuê",Toast.LENGTH_SHORT).show();
                check=-1;
            }
        }
        return check;
    }
}