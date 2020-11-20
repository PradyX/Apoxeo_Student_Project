package com.prady.apoxeostudentproject.db;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prady.apoxeostudentproject.R;

public class DBViewHolder extends RecyclerView.ViewHolder {

    View view;
    private DBViewHolder.clickListener mClickListener;

    public DBViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(view, getAdapterPosition());
                return false;
            }
        });
    }

    public void setData(Context context, String name, String country) {
        TextView nameTxt = view.findViewById(R.id.name_txt);
        TextView countryTxt = view.findViewById(R.id.country_txt);

        nameTxt.setText(name);
        countryTxt.setText(country);
    }

    public void setOnClickListener(DBViewHolder.clickListener clickListener) {
        mClickListener = clickListener;
    }

    public interface clickListener {
        void onItemLongClick(View view, int pos);
    }
}

