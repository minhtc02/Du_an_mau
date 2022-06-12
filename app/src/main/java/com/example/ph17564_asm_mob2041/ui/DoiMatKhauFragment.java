package com.example.ph17564_asm_mob2041.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.ph17564_asm_mob2041.DAO.ThuThuDAO;
import com.example.ph17564_asm_mob2041.Entity.ThuThu;
import com.example.ph17564_asm_mob2041.R;
import com.google.android.material.textfield.TextInputEditText;


public class DoiMatKhauFragment extends Fragment {
   ThuThuDAO dao;
   TextInputEditText edPassOld,edPassNew,edRePassNew;
   Button btnLuuPN,btnHuyPN;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
        edPassOld = v.findViewById(R.id.edPassOld);
        edPassNew = v.findViewById(R.id.edPassNew);
        edRePassNew = v.findViewById(R.id.edRePassNew);
        btnLuuPN = v.findViewById(R.id.btnLuuPN);
        btnHuyPN = v.findViewById(R.id.btnHuyPN);
        dao = new ThuThuDAO(getActivity());
        btnHuyPN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edPassOld.setText("");
                edPassNew.setText("");
                edRePassNew.setText("");
            }
        });
        btnLuuPN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_FILE",Context.MODE_PRIVATE);
                String user = sharedPreferences.getString("USERNAME","");
                if (validate()>0){
                    ThuThu thuThu = dao.getID(user);
                    thuThu.matKhau = edPassNew.getText().toString();
                    dao.update(thuThu);
                    if (dao.update(thuThu)>0){
                        Toast.makeText(getContext(),"Đổi mật khẩu thành công",Toast.LENGTH_SHORT).show();
                        edPassOld.setText("");
                        edPassNew.setText("");
                        edRePassNew.setText("");
                    }
                    else{
                        Toast.makeText(getContext(),"Đổi mật khẩu thất bại",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return v;
    }
    private int validate(){
        int check = 1;
        if (edPassOld.getText().length()==0||edPassNew.getText().length()==0||edRePassNew.getText().length()==0)
        {
            Toast.makeText(getContext(),"Bạn phải nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
            check = -1;
        }
        else
        {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passOld = sharedPreferences.getString("PASSWORD","");
            String pass= edPassNew.getText().toString();
            String rePass = edRePassNew.getText().toString();
            if (!passOld.equals(edPassOld.getText().toString())){
                Toast.makeText(getContext(),"Mật khẩu cũ sai",Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!pass.equals(rePass)){
                Toast.makeText(getContext(),"Mật khẩu không trùng khớp",Toast.LENGTH_SHORT).show();

            }
        }
        return check;
    }
}