package com.hp.resv2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    ImageView first;
    Button btncreate,button;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView textView;

    final int sublist=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btncreate=findViewById(R.id.btncreate);

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, com.hp.resv2.sublist.class);
                startActivity(intent);
            }
        });
        //programmed at end
        auth = FirebaseAuth.getInstance();
        button=findViewById(R.id.logout);
        user=auth.getCurrentUser();
        textView=findViewById(R.id.user_details);
        if (user==null){
            Intent intent=new Intent(getApplicationContext(), login.class);
            startActivity(intent);
            finish();
        }
        else {
            textView.setText(user.getEmail());

        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            }
        });




    }

}