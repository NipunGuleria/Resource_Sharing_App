package com.hp.resv2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hp.resv2.model.Fileinmodel;

import java.io.File;

public class folderpage extends AppCompatActivity {

    EditText edit;
    Button uploadBTn;

    StorageReference storageReference;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folderpage);

        edit = findViewById(R.id.ediText);
        uploadBTn = findViewById(R.id.btn);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("pdfs");

        //set the upload btn disable first without selecting the pdf file
        uploadBTn.setEnabled(false);

        //select the pdf file
        edit.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectPDF();
            }
        }));
    }

    private void selectPDF(){
        Intent intent= new Intent();
        intent.setType(("application/pdf"));
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select pdf file"),101);
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==101 && resultCode==RESULT_OK && data!=null &&  data.getData()!= null){
            Uri uri=data.getData();

            //we need the file name of the pdf so extract the name of pdf file
            String uristring=uri.toString();
            File myfile=new File(uristring);
            String path=myfile.getAbsolutePath();
            String displayname=null;

            if (uristring.startsWith("content://")){
                Cursor cursor=null;
                try{
                    cursor=this.getContentResolver().query(uri,null,null,null,null);
                    if (cursor !=null && cursor.moveToFirst()){
                        displayname=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }

                }finally {
                    cursor.close();
                }

            }else if (uristring.startsWith("file://")){
                displayname=myfile.getName();
            }

            uploadBTn.setEnabled(true);
            edit.setText(displayname);

            uploadBTn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadPDF(data.getData());
                }
            });
        }
    }
    private void uploadPDF(Uri data ){
        final ProgressDialog pd= new ProgressDialog(this);
        pd.setTitle("File Uploading..");
        pd.show();

        final StorageReference reference=storageReference.child("uploads/"+System.currentTimeMillis()+"pdf");
        //store in upload folder of the firebase storage
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri =uriTask.getResult();

                        Fileinmodel fileinmodel=new Fileinmodel(edit.getText().toString(),uri.toString());
                        //get the views from model class
                        databaseReference.child(databaseReference.push().getKey()).setValue(fileinmodel);
                        //push the value into realtime database
                        Toast.makeText(folderpage.this,"uploaded successfully!!",Toast.LENGTH_SHORT).show();
                        pd.dismiss();


                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent=(100 * snapshot.getBytesTransferred())/ snapshot.getTotalByteCount();
                        pd.setMessage("uploaded : "+ (int) percent + "%");


                    }
                });

    }

    public void retrievepdfs(View view) {
        Intent intent=new Intent(getApplicationContext(),RetreivePDFActivity.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}