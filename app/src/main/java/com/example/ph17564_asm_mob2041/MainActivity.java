package com.example.ph17564_asm_mob2041;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ph17564_asm_mob2041.DAO.ThuThuDAO;
import com.example.ph17564_asm_mob2041.Entity.ThuThu;
import com.example.ph17564_asm_mob2041.ui.DoanhThuFragment;
import com.example.ph17564_asm_mob2041.ui.DoiMatKhauFragment;
import com.example.ph17564_asm_mob2041.ui.LoaiSachFragment;
import com.example.ph17564_asm_mob2041.ui.PhieuMuonFragment;
import com.example.ph17564_asm_mob2041.ui.SachFragment;
import com.example.ph17564_asm_mob2041.ui.ThanhVienFragment;
import com.example.ph17564_asm_mob2041.ui.ThemMoiFragment;
import com.example.ph17564_asm_mob2041.ui.TopFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    View mheaderView;
    TextView eduser;
    ThuThuDAO thuThuDAO;
    FragmentManager manager;
    ThuThu thuThu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);

        //set toolbar
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //PhieuMuonFragment lam home
        setTitle("Phiếu mượn ");
        manager = getSupportFragmentManager();
        PhieuMuonFragment phieuMuonFragment = new PhieuMuonFragment();
        manager.beginTransaction()
                .replace(R.id.flContent,phieuMuonFragment)
                .commit();
        NavigationView nv = findViewById(R.id.nav_view);

        //lay user
        mheaderView = nv.getHeaderView(0);
        eduser = mheaderView.findViewById(R.id.tvUser);
        Intent i = getIntent();
        String user = i.getStringExtra("user");
        thuThuDAO = new ThuThuDAO(this);
        thuThu = thuThuDAO.getID(user);
        String userName = thuThu.hoTen;
        eduser.setText("Welcome "+userName+"!");
        //admin co quyen truy cap

          if(user.equalsIgnoreCase("admin")){
            nv.getMenu().findItem(R.id.sub_addUser).setVisible(true);
          }

        nv.setNavigationItemSelectedListener(item -> {
             manager = getSupportFragmentManager();
            switch (item.getItemId()){
                case R.id.nav_PhieuMuon:
                    setTitle("Phiếu mượn ");
                    PhieuMuonFragment phieuMuonFragment1 = new PhieuMuonFragment();
                    manager.beginTransaction()
                            .replace(R.id.flContent,phieuMuonFragment1)
                            .commit();
                    break;
                case R.id.nav_LoaiSach:
                    setTitle("Loại sách ");
                    LoaiSachFragment loaiSachFragment = new LoaiSachFragment();
                    manager.beginTransaction()
                            .replace(R.id.flContent,loaiSachFragment)
                            .commit();
                    break;
                case R.id.nav_Sach:
                    setTitle("Sách ");
                    SachFragment sachFragment = new SachFragment();
                    manager.beginTransaction()
                            .replace(R.id.flContent,sachFragment)
                            .commit();
                    break;
                case R.id.nav_ThanhVien:
                    setTitle("Thành viên ");
                    ThanhVienFragment thanhVienFragment = new ThanhVienFragment();
                    manager.beginTransaction()
                            .replace(R.id.flContent,thanhVienFragment)
                            .commit();
                    break;
                case R.id.sub_Top:
                    setTitle("Top 10 đầu sách");
                    TopFragment topFragment = new TopFragment();
                    manager.beginTransaction()
                            .replace(R.id.flContent,topFragment)
                            .commit();
                    break;
                case R.id.sub_DoanhThu:
                    setTitle("Thống kê doanh thu");
                    DoanhThuFragment doanhThuFragment = new DoanhThuFragment();
                    manager.beginTransaction()
                            .replace(R.id.flContent,doanhThuFragment)
                            .commit();
                    break;
                case R.id.sub_addUser:
                    setTitle("Tạo tài khoản ");
                    ThemMoiFragment themMoiFragment = new ThemMoiFragment();
                    manager.beginTransaction()
                            .replace(R.id.flContent,themMoiFragment)
                            .commit();
                    break;
                case R.id.sub_Pass:
                    setTitle("Đổi mật khẩu ");
                    DoiMatKhauFragment doiMatKhauFragment = new DoiMatKhauFragment();
                    manager.beginTransaction()
                            .replace(R.id.flContent,doiMatKhauFragment)
                            .commit();
                    break;
                case R.id.sub_LogOut:
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
                    break;
            }
            drawerLayout.closeDrawers();
            return false;
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}