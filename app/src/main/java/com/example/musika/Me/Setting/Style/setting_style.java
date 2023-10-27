package com.example.musika.Me.Setting.Style;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musika.R;

import java.util.ArrayList;
import java.util.List;

public class setting_style extends AppCompatActivity implements styleListener {

    RecyclerView listStyle_layout;
    styleAdapter styleAdapter;
    private List<itemStyle> list = new ArrayList<>();
    Button saveStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_style);

        listStyle_layout = findViewById(R.id.listStyle_xml);
        saveStyle = (Button) findViewById(R.id.saveStyle_xml);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        listStyle_layout.setLayoutManager(gridLayoutManager);

        getListUser();
        styleAdapter = new styleAdapter(list, this);

        listStyle_layout.setAdapter(styleAdapter);
        saveStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<itemStyle> selectTvShow = styleAdapter.getSelectitemStyle();
                StringBuilder tvShowName = new StringBuilder();
                for(int i=0; i<selectTvShow.size(); i++) {
                    if (i==0) {
                        tvShowName.append(selectTvShow.get(i).style);
                    } else {
                        tvShowName.append("\n").append(selectTvShow.get(i).style);
                    }
                }
                Toast.makeText(setting_style.this, tvShowName.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private List<itemStyle> getListUser() {
        list.add(new itemStyle(R.drawable.ic_launcher_background, "1"));
        list.add(new itemStyle(R.drawable.ic_launcher_background, "2"));
        list.add(new itemStyle(R.drawable.ic_launcher_background, "3"));
        list.add(new itemStyle(R.drawable.ic_launcher_background, "4"));
        list.add(new itemStyle(R.drawable.ic_launcher_background, "5"));
        list.add(new itemStyle(R.drawable.ic_launcher_background, "6"));
        list.add(new itemStyle(R.drawable.ic_launcher_background, "7"));
        list.add(new itemStyle(R.drawable.ic_launcher_background, "8"));
        list.add(new itemStyle(R.drawable.ic_launcher_background, "9"));
        list.add(new itemStyle(R.drawable.ic_launcher_background, "10"));
        return list;
    }

    @Override
    public void onTvShowAction(Boolean isSelected) {

    }
}