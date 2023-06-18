package com.hp.resv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class sublist extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter myadapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<person> subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sublist);

        recyclerView=findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        subject=new ArrayList<person>();
        subject.add(new person("PYTHON","VIEW RESOURCES"));
        subject.add(new person("OOPS","VIEW RESOURCES"));
        subject.add(new person("FML","VIEW RESOURCES"));
        subject.add(new person("SE","VIEW RESOURCES"));
        subject.add(new person("MATHEMATICS","VIEW RESOURCES"));
        subject.add(new person("PSLA","VIEW RESOURCES"));
        subject.add(new person("DLD","VIEW RESOURCES"));
        subject.add(new person("PAI","VIEW RESOURCES"));
        subject.add(new person("CN","VIEW RESOURCES"));

        myadapter=new personadapter(this, subject);

        recyclerView.setAdapter(myadapter);


    }


}