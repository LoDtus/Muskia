package com.example.musika.Me.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.musika.GlobalClass;
import com.example.musika.Me.MeFragment;
import com.example.musika.Me.Setting.Style.setting_style;
import com.example.musika.R;

public class activity_me_setting extends AppCompatActivity {

    ImageView setting_toProfile, setting_toStyle, setting_toFeedback, setting_toDelete;
    Button logout;

    GlobalClass globalClass = new GlobalClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_setting);

        setting_toProfile = (ImageView) findViewById(R.id.setting_toProfile_xml);
        setting_toProfile.setOnClickListener((v) -> {
            Intent intent = new Intent(activity_me_setting.this, EditProfile.class);
            startActivity(intent);
        });

        setting_toStyle = (ImageView) findViewById(R.id.setting_toStyle_xml);
        setting_toStyle.setOnClickListener((v) -> {
            Intent intent = new Intent(activity_me_setting.this, setting_style.class);
            startActivity(intent);
        });

        setting_toFeedback = (ImageView) findViewById(R.id.setting_toFeedback_xml);
        setting_toFeedback.setOnClickListener((v) -> {
            Intent intent = new Intent(activity_me_setting.this, Feedback.class);
            startActivity(intent);
        });

        setting_toDelete = (ImageView) findViewById(R.id.setting_toDelete_xml);
        setting_toDelete.setOnClickListener((v) -> {
            Intent intent = new Intent(activity_me_setting.this, DeleteAccount.class);
            startActivity(intent);
        });

        logout = (Button) findViewById(R.id.logout_xml);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalClass.setNickname(null);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_me_setting_xml, new MeFragment());
                transaction.commit();
            }
        });
    }
}