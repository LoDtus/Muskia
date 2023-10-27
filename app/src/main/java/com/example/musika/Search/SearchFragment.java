package com.example.musika.Search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musika.Home.Song;
import com.example.musika.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    EditText searchBar;
    ImageView searchButton;
    private SearchAdapter searchAdapter;
    private RecyclerView searchList;

    private List<Search> listResult = new ArrayList<>();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    public SearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_search, container, false);

        searchBar = rootview.findViewById(R.id.searchBar_xml);
        searchList = rootview.findViewById(R.id.listSearch_xml);
        searchButton = rootview.findViewById(R.id.searchButton_xml);

        String searchKey = searchBar.getText().toString();
        List<Song> list = new ArrayList<>();
        LinearLayoutManager layout = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
/*
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                

                databaseReference.child("Song").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String songID;
                        for (int i=0; i<=30; i++) {
                            songID = "song" + Integer.toString(i);
                            if (snapshot.child(songID).exists()) {
                                if (snapshot.child(songID).child("song").getValue().toString().contains(searchKey)) {
                                    final String getPoster = snapshot.child(songID).child("poster").getValue().toString();
                                    final String getSong = snapshot.child(songID).child("song").getValue().toString();
                                    final String getArtist = snapshot.child(songID).child("artist").getValue().toString();
                                    list.add(new Song(songID, getPoster, getSong, getArtist));
                                } else {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                        }
                        searchAdapter = new SearchAdapter(list, getActivity());
                        searchList.setLayoutManager(layout);
                        searchList.setAdapter(searchAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
*/
        return rootview;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (searchAdapter != null) {
            searchAdapter.release();
        }
    }
}