package com.example.musika.Home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musika.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment {
    ImageView appName, homeNotification;
    Drawable drawable;
    private RecyclerView rcv_rcm, rcv_thinhhanh, rcv_history, rcv_followsong;
    private SongAdapter songAdapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    Random random = new Random();
    private int idSong, i=0;
    private String SongID;
    TextView rcv_home;
    public HomeFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_home, container, false);

        // Set logo appName trên góc trái:
        appName = rootView.findViewById(R.id.appName_xml);
        drawable = getResources().getDrawable(R.drawable.musika_full);
        appName.setImageDrawable(drawable);

        // Set Notification:
        homeNotification = rootView.findViewById(R.id.homeNotification_xml);
        homeNotification.setOnClickListener((v) -> {
            Intent intent = new Intent(getActivity(), Notification.class);
            startActivity(intent);
        });

        /* Set Recycler View: */ //*****************************************************************
        rcv_rcm = (RecyclerView) rootView.findViewById(R.id.rcv_rcm_xml);
        rcv_thinhhanh = (RecyclerView) rootView.findViewById(R.id.rcv_thinhhanh_xml);
        rcv_history = (RecyclerView) rootView.findViewById(R.id.rcv_history_xml);
        rcv_followsong = (RecyclerView) rootView.findViewById(R.id.rcv_followsong_xml);

        // Cái này thì lại báo là vertical để trượt lên xuống
        LinearLayoutManager layout1 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        LinearLayoutManager layout2 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        LinearLayoutManager layout3 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        LinearLayoutManager layout4 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);

        rcv_home = (TextView) rootView.findViewById(R.id.rcm_home_xml);
        List<Song> list, list1;
        list = new ArrayList<>();
        i = 0;
        databaseReference.child("Song").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                while (i <= 8) {
                    idSong = random.nextInt(30);
                    SongID = "song" + Integer.toString(idSong);
                    if (snapshot.child(SongID).exists()) {
                        i++;
                        final String getPoster = snapshot.child(SongID).child("poster").getValue().toString();
                        final String getSong = snapshot.child(SongID).child("song").getValue().toString();
                        final String getArtist = snapshot.child(SongID).child("artist").getValue().toString();
                        list.add(new Song(SongID, getPoster, getSong, getArtist));
                    } else {
                        continue;
                    }
                }
                songAdapter = new SongAdapter(list, getActivity());
                rcv_rcm.setLayoutManager(layout1);
                rcv_rcm.setAdapter(songAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        i = 0;
        databaseReference.child("Song").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                while (i <= 6) {
                    idSong = random.nextInt(30);
                    SongID = "song" + Integer.toString(idSong);
                    if (snapshot.child(SongID).exists()) {
                        i++;
                        final String getPoster = snapshot.child(SongID).child("poster").getValue().toString();
                        final String getSong = snapshot.child(SongID).child("song").getValue().toString();
                        final String getArtist = snapshot.child(SongID).child("artist").getValue().toString();
                        list.add(new Song(SongID, getPoster, getSong, getArtist));
                    } else {
                        continue;
                    }
                }
                songAdapter = new SongAdapter(list, getActivity());
                rcv_thinhhanh.setLayoutManager(layout2);
                rcv_thinhhanh.setAdapter(songAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        i = 0;
        databaseReference.child("Song").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                while (i <= 6) {
                    idSong = random.nextInt(30);
                    SongID = "song" + Integer.toString(idSong);
                    if (snapshot.child(SongID).exists()) {
                        i++;
                        final String getPoster = snapshot.child(SongID).child("poster").getValue().toString();
                        final String getSong = snapshot.child(SongID).child("song").getValue().toString();
                        final String getArtist = snapshot.child(SongID).child("artist").getValue().toString();
                        list.add(new Song(SongID, getPoster, getSong, getArtist));
                    } else {
                        continue;
                    }
                }
                songAdapter = new SongAdapter(list, getActivity());
                rcv_history.setLayoutManager(layout3);
                rcv_history.setAdapter(songAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        i = 0;
        databaseReference.child("Song").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                while (i <= 6) {
                    idSong = random.nextInt(30);
                    SongID = "song" + Integer.toString(idSong);
                    if (snapshot.child(SongID).exists()) {
                        i++;
                        final String getPoster = snapshot.child(SongID).child("poster").getValue().toString();
                        final String getSong = snapshot.child(SongID).child("song").getValue().toString();
                        final String getArtist = snapshot.child(SongID).child("artist").getValue().toString();
                        list.add(new Song(SongID, getPoster, getSong, getArtist));
                    } else {
                        continue;
                    }
                }
                songAdapter = new SongAdapter(list, getActivity());
                rcv_followsong.setLayoutManager(layout4);
                rcv_followsong.setAdapter(songAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //==========================================================================================
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (songAdapter != null) {
            songAdapter.release();
        }
    }
}