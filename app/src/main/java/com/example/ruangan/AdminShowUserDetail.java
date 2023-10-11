package com.example.ruangan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdminShowUserDetail extends AppCompatActivity {
    TextView showIdTv, showName, showEmail, showUserTipe;
    FloatingActionButton deleteButton, editButton;
    String key ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_show_user_detail);
        showIdTv = findViewById(R.id.showIdTv);
        showName = findViewById(R.id.showName);
        showEmail = findViewById(R.id.showEmail);
        showUserTipe = findViewById(R.id.showUserTipe);
        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            showIdTv.setText(bundle.getString("uid"));
            showName.setText(bundle.getString("name"));
            showEmail.setText(bundle.getString("email"));
            showUserTipe.setText(bundle.getString("userType"));
            key = bundle.getString("Key");
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

                reference.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(AdminShowUserDetail.this,"Data Pemohon berhasil terhapus",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),AdminShowUser.class));
                        finish();
                    }
                });

            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminShowUserDetail.this, AdminUpdateUser.class)

                        .putExtra("uid",showIdTv.getText().toString())
                        .putExtra("name",showName.getText().toString())
                        .putExtra("email",showEmail.getText().toString())
                        .putExtra("userType",showUserTipe.getText().toString())
                        .putExtra("Key",key);
                startActivity(intent);
                finish();
            }
        });


    }

}