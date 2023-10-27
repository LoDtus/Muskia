package com.example.musika.Me.Setting.Style;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musika.R;

import java.util.ArrayList;
import java.util.List;

public class styleAdapter extends RecyclerView.Adapter<styleAdapter.itemStyleViewHolder> {
    private List<itemStyle> itemStyles;
    private styleListener styleListener;

    public styleAdapter(List<itemStyle> itemStyles, com.example.musika.Me.Setting.Style.styleListener styleListener) {
        this.itemStyles = itemStyles;
        this.styleListener = styleListener;
    } //============================================================================================

    @NonNull
    @Override
    public itemStyleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new itemStyleViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_setting_itemstyle, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull itemStyleViewHolder holder, int position) {
        holder.binditemStyle(itemStyles.get(position));
    }

    @Override
    public int getItemCount() {
        return itemStyles.size();
    } //============================================================================================

    public List<itemStyle> getSelectitemStyle() {
        List<itemStyle> selecteditemStyles = new ArrayList<>();
        for (itemStyle itemStyle : itemStyles) {
            if (itemStyle.isSelect) {
                selecteditemStyles.add(itemStyle);
            }
        }
        return selecteditemStyles;
    } //============================================================================================

    class itemStyleViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutStyle;
        private ImageView imgUser;
        private TextView nameUser;

        itemStyleViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutStyle = itemView.findViewById(R.id.layout_itemStyle_xml);
            imgUser = itemView.findViewById(R.id.imgStyle_xml);
            nameUser = itemView.findViewById(R.id.style_xml);
        }

        void binditemStyle (final itemStyle itemStyle) {
            imgUser.findViewById(R.id.imgStyle_xml);
            nameUser.findViewById(R.id.style_xml);

            layoutStyle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemStyle.isSelect) {
                        layoutStyle.setBackgroundResource(R.drawable.show_item_style);
                        itemStyle.isSelect = false;
                        if (getSelectitemStyle().size() == 0) {
                            styleListener.onTvShowAction(false);
                        }
                    }
                    else {
                        layoutStyle.setBackgroundResource(R.drawable.select_item_style);
                        itemStyle.isSelect = true;
                        styleListener.onTvShowAction(true);
                    }
                } //================================================================================
            });
        } // binditemStyle =========================================================================
    } // itemStyleViewHolder =======================================================================
}