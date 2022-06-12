package com.example.ph17564_asm_mob2041.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.ph17564_asm_mob2041.Adapter.ThanhVienAdapter;
import com.example.ph17564_asm_mob2041.DAO.ThanhVienDAO;
import com.example.ph17564_asm_mob2041.Entity.ThanhVien;
import com.example.ph17564_asm_mob2041.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ThanhVienFragment extends Fragment {
    ListView lv;
    ArrayList<ThanhVien> list;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaTV,edTenTV,edNamSinh;
    Button btnSave,btnCancel;
    ThanhVienDAO dao;
    ThanhVienAdapter adapter;
    ThanhVien item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_thanh_vien,container,false);
        lv = v.findViewById(R.id.lvThanhVien);
        fab = v.findViewById(R.id.fab);
        dao = new ThanhVienDAO(getActivity());
        capNhatLv();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(),0);
            }
        });
        lv.setOnItemLongClickListener((parent, view, position, id) -> {
            item = list.get(position);
            openDialog(getActivity(),1);
            return false;
        });
        return v;
    }
    protected void openDialog(final Context context,final int type){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.thanhvien_dialog);
        edMaTV = dialog.findViewById(R.id.edMaTV);
        edTenTV = dialog.findViewById(R.id.edTenTV);
        edNamSinh = dialog.findViewById(R.id.edNamSinh);
        edNamSinh.setInputType(InputType.TYPE_CLASS_NUMBER);
        btnSave = dialog.findViewById(R.id.btnSave);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        edMaTV.setEnabled(false);
        if (type!=0){
            edMaTV.setText(String.valueOf(item.maTV));
            edTenTV.setText(item.hoTen);
            edNamSinh.setText(item.namSinh);
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
                item = new ThanhVien();
                item.hoTen = edTenTV.getText().toString();
                item.namSinh = edNamSinh.getText().toString();
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
                        item.maTV = Integer.parseInt(edMaTV.getText().toString());
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
        list = (ArrayList<ThanhVien>) dao.getAll();
        adapter = new ThanhVienAdapter(getActivity(),this,list);
        lv.setAdapter(adapter);
    }
    private int validate(){
        int check = 1;
        if(edTenTV.getText().length()==0||edNamSinh.getText().length()==0){
            Toast.makeText(getContext(),"Bạn phải nhập đủ thông tin",Toast.LENGTH_SHORT).show();
            check=-1;
        }
        else if (edNamSinh.getText().length()!=0){
            int namSinh = Integer.parseInt(edNamSinh.getText().toString());
            if (namSinh<1900||namSinh>2021){
                Toast.makeText(getContext(),"Bạn phải nhập đúng năm sinh",Toast.LENGTH_SHORT).show();
                check=-1;
            }
        }
        return check;
    }
}