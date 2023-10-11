package com.example.ruangan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AdminRuanganDetailActivity extends AppCompatActivity {
    TextView detailDesc, detailTitle, detailKap, detailFas;
    ImageView detailImage;
    FloatingActionButton deleteButton, editButton;
    String key;
    String imageUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ruangan_detail);

        detailDesc = findViewById(R.id.detailDesc);
        detailTitle = findViewById(R.id.detailTitle);
        detailKap = findViewById(R.id.detailKap);
        detailFas = findViewById(R.id.detailFas);
        detailImage = findViewById(R.id.detailImg);
        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            detailDesc.setText(bundle.getString("Description"));
            detailTitle.setText(bundle.getString("Title"));
            detailKap.setText(bundle.getString("Kapasitas"));
            detailFas.setText(bundle.getString("Fasilitas"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ruangan");
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        Toast.makeText(AdminRuanganDetailActivity.this,"Data Ruangan berhasil terhapus",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),AdminRuanganActivity.class));
                        finish();
                    }
                });

            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminRuanganDetailActivity.this, AdminUpdateRuangan.class)

                .putExtra("Description",detailDesc.getText().toString())
                .putExtra("Title",detailTitle.getText().toString())
                .putExtra("Kapasitas",detailKap.getText().toString())
                .putExtra("Fasilitas",detailFas.getText().toString())
                .putExtra("Image",imageUrl)
                .putExtra("Key",key);

                startActivity(intent);

            }
        });
    }
}