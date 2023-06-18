package com.hp.resv2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class personadapter extends RecyclerView.Adapter<personadapter.ViewHolder> {

    private ArrayList<person> subject;

    public personadapter(Context context ,ArrayList<person>list){
        subject=list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivpic;
        TextView name;
        Button desc;
        final int sublist=1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            desc=itemView.findViewById(R.id.desc);
            ivpic=itemView.findViewById(R.id.ivpic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            desc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(v.getContext(), com.hp.resv2.folderpage.class);
                    v.getContext().startActivity(intent);
                }
            });


        }




        }


    @NonNull
    @Override
    public personadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull personadapter.ViewHolder holder, int position) {

       holder.itemView.setTag(subject.get(position));
       holder.name.setText(subject.get(position).getName());
       holder.desc.setText(subject.get(position).getDesc());
       holder.ivpic.setImageResource(R.drawable.ic_baseline_arrow_forward_24);





    }

    @Override
    public int getItemCount() {
        return subject.size();
    }


}
