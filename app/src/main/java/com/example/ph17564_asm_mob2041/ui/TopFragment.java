package com.example.ph17564_asm_mob2041.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.ph17564_asm_mob2041.Adapter.LoaiSachAdapter;
import com.example.ph17564_asm_mob2041.Adapter.TopAdapter;
import com.example.ph17564_asm_mob2041.DAO.ThongKeDAO;
import com.example.ph17564_asm_mob2041.Entity.LoaiSach;
import com.example.ph17564_asm_mob2041.Entity.Top;
import com.example.ph17564_asm_mob2041.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TopFragment extends Fragment {
    ListView listView;
    ArrayList<Top> list;
    TopAdapter adapter;
    ThongKeDAO thongKeDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_top, container, false);
        listView = v.findViewById(R.id.lvTop);
        thongKeDAO = new ThongKeDAO(getActivity());
        capNhatLv();
        return v;
    }
    void capNhatLv(){
        list = (ArrayList)thongKeDAO.getTop();
        adapter = new TopAdapter(getActivity(),this,list);
        listView.setAdapter(adapter);
    }
}