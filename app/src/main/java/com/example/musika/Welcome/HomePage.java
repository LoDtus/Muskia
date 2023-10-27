package com.example.musika.Welcome;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.musika.GlobalClass;
import com.example.musika.Home.HomeFragment;
import com.example.musika.Me.MeAccount;
import com.example.musika.Me.MeFragment;
import com.example.musika.R;
import com.example.musika.Search.SearchFragment;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class HomePage extends AppCompatActivity {

    // Khai báo biến Bottom Navigation:
    MeowBottomNavigation toolBar;
    RelativeLayout main_layout;
    GlobalClass globalClass = new GlobalClass();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        /* Set Bottom Navigaton: */ //**************************************************************
        main_layout = findViewById(R.id.main_layout_xml);
        toolBar = findViewById(R.id.toolBar_xml);

        // Set Layout mặc định khi mở:
        replace(new HomeFragment());
        toolBar.show(1, true);

        // Gán icon:
        toolBar.add(new MeowBottomNavigation.Model(1, R.drawable.home));
        toolBar.add(new MeowBottomNavigation.Model(2, R.drawable.magnify));
        toolBar.add(new MeowBottomNavigation.Model(3, R.drawable.account));

        meow_toolBar();
        toolBar.setSelectedIconColor(Color.parseColor("#303136"));
        main_layout.setBackgroundColor(Color.parseColor("#303136"));
    } //============================================================================================

    /* Set Bottom Navigation: */ //*****************************************************************
    // 1. Set click cho Bottom Navigation:
    private void meow_toolBar () {
        toolBar.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()) {
                    case 1: {
                        replace(new HomeFragment());
                        main_layout.setBackgroundColor(Color.parseColor("#303136"));
                        toolBar.setSelectedIconColor(Color.parseColor("#303136"));
                        break;
                    }
                    case 2: {
                        replace(new SearchFragment());
                        main_layout.setBackgroundColor(Color.parseColor("#303136"));
                        toolBar.setSelectedIconColor(Color.parseColor("#303136"));
                        break;
                    }
                    case 3: {
                        if (globalClass.getNickname() == null) {
                            replace(new MeFragment());
                            main_layout.setBackgroundColor(Color.parseColor("#303136"));
                            toolBar.setSelectedIconColor(Color.parseColor("#303136"));
                            break;
                        } else {
                            replace(new MeAccount());
                            main_layout.setBackgroundColor(Color.parseColor("#303136"));
                            toolBar.setSelectedIconColor(Color.parseColor("#303136"));
                            break;
                        }
                    }
                }
                return null;
            }
        });
    }
    // 2. Set Fragment cho Bottom Navigation:
    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.linearLayout, fragment);
        transaction.commit();
    } //============================================================================================
}