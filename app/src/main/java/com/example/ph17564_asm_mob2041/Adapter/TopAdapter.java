package com.example.ph17564_asm_mob2041.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ph17564_asm_mob2041.Entity.Top;
import com.example.ph17564_asm_mob2041.R;
import com.example.ph17564_asm_mob2041.ui.TopFragment;

import java.util.ArrayList;

public class TopAdapter extends ArrayAdapter<Top> {
    private Context context;
    TopFragment topFragment;
    private ArrayList<Top> lists;
    TextView tvTopSach,tvTopSL;
    public TopAdapter(@NonNull Context context, TopFragment topFragment,ArrayList<Top> lists) {
        super(context, 0,lists);
        this.context = context;
        this.lists = lists;
        this.topFragment = topFragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.top_item,null);
        }
        final Top item = lists.get(position);
        if (item!=null){
            tvTopSach = v.findViewById(R.id.tvTopSach);
            tvTopSach.setText("Sách: "+item.tenSach);
            tvTopSL  = v.findViewById(R.id.tvTopSL);
            tvTopSL.setText("Số lượng: "+item.soLuong);

        }
        return v;
    }
}
