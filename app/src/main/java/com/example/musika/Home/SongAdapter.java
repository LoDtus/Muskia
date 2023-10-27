package com.example.musika.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musika.Home.song.SongPlayer;
import com.example.musika.R;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MusicViewHolder> {
    private Context context;
    private List<Song> listSong;

    public SongAdapter(List<Song> list, Context context) {
        this.listSong = list;
        this.context = context;
    } //============================================================================================

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        Song song = listSong.get(position);
        if (song == null) {
            return;
        }

        holder.title_ofSong.setText(String.valueOf(listSong.get(position).getNameSong()));
        holder.name_ofArtist.setText(String.valueOf(listSong.get(position).getArtist()));
        Glide.with(holder.poster.getContext()).load(listSong.get(position).getPoster()).into(holder.poster);
        holder.itemSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SongPlayer.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("SongID", song.getSongID());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent,false);
        return new MusicViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (listSong != null) {
            return listSong.size();
        }
        return 0;
    } //============================================================================================

    public class MusicViewHolder extends RecyclerView.ViewHolder {
        private String SongID;
        private LinearLayout itemSong;
        private ImageView poster;
        private TextView title_ofSong, name_ofArtist;
        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);

            itemSong = itemView.findViewById(R.id.itemSong_xml);
            poster = itemView.findViewById(R.id.poster_xml);
            title_ofSong = itemView.findViewById(R.id.title_ofSong_xml);
            name_ofArtist = itemView.findViewById(R.id.name_ofArtist_xml);
        }
    }

    public void release() {
        context = null;
    }
}
