package com.example.musika.Me.Setting.isArtist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.musika.R;

import java.util.List;

public class ArtistAdapter extends ArrayAdapter<CategoryArtist> {
    public ArtistAdapter(@NonNull Context context, int resource, @NonNull List<CategoryArtist> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_artist, parent, false);
        TextView nullStatus = convertView.findViewById(R.id.nullStatus_xml);

        CategoryArtist cate = this.getItem(position);
        if (cate != null) {
            nullStatus.setText(cate.getNameCategory());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_artist, parent, false);
        TextView status = convertView.findViewById(R.id.status_xml);

        CategoryArtist cate = this.getItem(position);
        if (cate != null) {
            status.setText(cate.getNameCategory());
        }
        return convertView;
    }
}
