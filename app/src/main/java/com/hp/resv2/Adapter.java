package com.hp.resv2;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class Adapter extends RecyclerView.ViewHolder implements View.OnClickListener {
    public itemClickListener listener;
    private final Context context;
    public TextView pdfTitle;

    public Adapter(@NonNull View itemView) {
        super(itemView);
        context=itemView.getContext();
        pdfTitle=itemView.findViewById(R.id.pdf_name);
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v,getAdapterPosition(),false);

    }
}
