package com.example.ph17564_asm_mob2041;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ph17564_asm_mob2041.DAO.ThuThuDAO;
import com.example.ph17564_asm_mob2041.Entity.ThuThu;

//import com.example.ph17564_asm_mob2041.DAO.ThuThuDAO;

public class LoginActivity extends AppCompatActivity {
    ThuThuDAO dao ;
    EditText edUser,edPass;
    TextView tvNew;
    Button btnLogin;
    CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUser = findViewById(R.id.edUserName);
        edPass = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        checkBox = findViewById(R.id.checkBox);
        tvNew = findViewById(R.id.tvNew);
        dao = new ThuThuDAO(this);
        //doc user pass
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        edUser.setText(pref.getString("USERNAME",""));
        edPass.setText(pref.getString("PASSWORD",""));
        checkBox.setChecked(pref.getBoolean("REMEMBER",false));

        tvNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkThuThu = dao.checkThuThu();
                if (checkThuThu) {
                    ThuThu thuThu = new ThuThu("admin", "admin", "admin");
                    dao.insert(thuThu);
                    Toast.makeText(getApplicationContext(),"Đã tạo tài khoản admin",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Tài khoản admin đã tồn tại",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }
    String strUser,strPass;
    public void checkLogin() {
        strUser = edUser.getText().toString();
        strPass = edPass.getText().toString();
        if (strUser.isEmpty() || strPass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Tên đăng nhập và mật khẩu không đươc để trống", Toast.LENGTH_SHORT).show();
        } else {
            if (dao.checkAcc(strUser,strPass)==false) {
                Toast.makeText(getApplicationContext(), "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                rememberUser(strUser, strPass, checkBox.isChecked());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("user", strUser);
                startActivity(intent);
                finish();
            }
        }
    }

    private void rememberUser(String u, String p, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if(!status){
            edit.clear();
        }
        else {
            edit.putString("USERNAME",u);
            edit.putString("PASSWORD",p);
            edit.putBoolean("REMEMBER",status);
        }
        edit.commit();
    }
}