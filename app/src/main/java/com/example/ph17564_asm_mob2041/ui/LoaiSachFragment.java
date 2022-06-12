package com.example.ph17564_asm_mob2041.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.ph17564_asm_mob2041.Adapter.LoaiSachAdapter;
import com.example.ph17564_asm_mob2041.Adapter.ThanhVienAdapter;
import com.example.ph17564_asm_mob2041.DAO.LoaiSachDAO;
import com.example.ph17564_asm_mob2041.DAO.ThanhVienDAO;
import com.example.ph17564_asm_mob2041.Entity.LoaiSach;
import com.example.ph17564_asm_mob2041.Entity.ThanhVien;
import com.example.ph17564_asm_mob2041.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class LoaiSachFragment extends Fragment {
    ListView lv;
    ArrayList<LoaiSach> list;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaLS,edTenLS;
    Button btnSave,btnCancel;
    static LoaiSachDAO dao;
    LoaiSachAdapter adapter;
    LoaiSach item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_loai_sach,container,false);
        lv = v.findViewById(R.id.lvLoaiSach);
        fab = v.findViewById(R.id.fab);
        dao = new LoaiSachDAO(getActivity());
        capNhatLv();
        fab.setOnClickListener(v1 -> {openDialog(getActivity(),0);});
        lv.setOnItemLongClickListener((parent, view, position, id) -> {
            item = list.get(position);
            openDialog(getActivity(),1);
            return false;
        });
        return v;
    }
    protected void openDialog(final Context context, final int type){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.loaisach_dialog);
        edMaLS = dialog.findViewById(R.id.edMaLS);
        edTenLS = dialog.findViewById(R.id.edTenLS);
        btnSave = dialog.findViewById(R.id.btnSave);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        edMaLS.setEnabled(false);
        if (type!=0){
            edMaLS.setText(String.valueOf(item.maLoai));
            edTenLS.setText(item.tenLoai);
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
                item = new LoaiSach();
                item.tenLoai = edTenLS.getText().toString();
                if (validate()>0){
                    if (type==0){
                        if (dao.insert(item)>0){
                            Toast.makeText(context,"Thêm thành công",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context,"Thêm thành công",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        item.maLoai = Integer.parseInt(edMaLS.getText().toString());
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
        list = (ArrayList<LoaiSach>)dao.getAll();
        adapter = new LoaiSachAdapter(getActivity(),this,list);
        lv.setAdapter(adapter);
    }
    private int validate(){
        int check = 1;
        if(edTenLS.getText().length()==0){
            Toast.makeText(getContext(),"Bạn phải nhập đủ thông tin",Toast.LENGTH_SHORT).show();
            check=-1;
        }
        return check;
    }
}