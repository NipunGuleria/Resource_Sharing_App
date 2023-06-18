package com.hp.resv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hp.resv2.model.Fileinmodel;

public class RetreivePDFActivity extends AppCompatActivity {

    RecyclerView pdfRecyclerView;
    private DatabaseReference pRef;
    Query query;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retreive_pdfactivity);
        displayPdfs();
    }

    private void displayPdfs() {
        pRef= FirebaseDatabase.getInstance().getReference().child("pdfs");
        pdfRecyclerView=findViewById(R.id.recyclerview);
        pdfRecyclerView.setHasFixedSize((true));
        pdfRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        progressBar=findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        query=pRef.orderByChild("filename");

        //we will request the files with file name,if file name exists it shows in recycler view

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    progressBar.setVisibility(View.VISIBLE);
                    showPdf();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(RetreivePDFActivity.this,":",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showPdf() {
        FirebaseRecyclerOptions<Fileinmodel> options=new FirebaseRecyclerOptions.Builder<Fileinmodel>()
                .setQuery(query,Fileinmodel.class)
                .build();
        FirebaseRecyclerAdapter<Fileinmodel, Adapter>adapter=new FirebaseRecyclerAdapter<Fileinmodel, Adapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Adapter holder, int position, @NonNull Fileinmodel model) {
                progressBar.setVisibility(View.GONE);
                holder.pdfTitle.setText(model.getFilename());
                holder.itemView.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(Intent.ACTION_VIEW);
                        intent.setType("application/pdf");
                        intent.setData(Uri.parse(model.getFileurl()));
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public Adapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_item,parent,false);
                Adapter holder=new Adapter(view);
                return holder;
            }

        };
        pdfRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}