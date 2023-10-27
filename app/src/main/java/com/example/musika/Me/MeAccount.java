package com.example.musika.Me;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.musika.GlobalClass;
import com.example.musika.Me.Setting.activity_me_setting;
import com.example.musika.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MeAccount extends Fragment {
    ImageView me_toSetting, avatar;
    Drawable drawable;
    Button upload;
    TextView fullname_pf, country_pf, describe_pf, followby_pf, follow_pf;
    GlobalClass globalClass = new GlobalClass();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public MeAccount() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_me_account, container, false);

        avatar = rootView.findViewById(R.id.avatar_xml);
        fullname_pf = rootView.findViewById(R.id.fullname_pf_xml);
        country_pf = rootView.findViewById(R.id.country_pf_xml);
        describe_pf = rootView.findViewById(R.id.describe_pf_xml);
        followby_pf = rootView.findViewById(R.id.followby_pf_xml);
        follow_pf = rootView.findViewById(R.id.follow_pf_xml);

        databaseReference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String getFullname = snapshot.child(globalClass.getNickname()).child("fullname").getValue().toString();
                final String getCountry = snapshot.child(globalClass.getNickname()).child("country").getValue().toString();
                final String getDescribe = snapshot.child(globalClass.getNickname()).child("describe").getValue().toString();
                final String getFollow = snapshot.child(globalClass.getNickname()).child("follow").getValue().toString();
                final String getFollowby = snapshot.child(globalClass.getNickname()).child("followby").getValue().toString();
                final String getSong = snapshot.child(globalClass.getNickname()).child("song").getValue().toString();
                final String getAvatar = snapshot.child(globalClass.getNickname()).child("avatar").getValue().toString();
                final String getisArtist = snapshot.child(globalClass.getNickname()).child("isArtist").getValue().toString();

                fullname_pf.setText(getFullname);
                country_pf.setText(getCountry);
                describe_pf.setText(getDescribe);
                followby_pf.setText(getFollowby);
                follow_pf.setText(getFollow);

                if (getAvatar == null) {
                    drawable = getResources().getDrawable(R.drawable.account_ava);
                    avatar.setImageDrawable(drawable);
                } else {
                    Picasso.get().load(getAvatar).into(avatar);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        me_toSetting = rootView.findViewById(R.id.me_toSetting_xml);
        me_toSetting.setOnClickListener((v) -> {
            Intent intent = new Intent(getActivity(), activity_me_setting.class);
            startActivity(intent);
        }); //======================================================================================

        upload = rootView.findViewById(R.id.upload_xml);
        upload.setOnClickListener((v) -> {
            Intent intent = new Intent(getActivity(), UploadSong.class);
            startActivity(intent);
        }); //======================================================================================
        return rootView;
    }
}