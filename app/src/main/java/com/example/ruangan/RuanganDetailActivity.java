package com.example.ruangan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class RuanganDetailActivity extends AppCompatActivity {
    TextView detailDesc, detailTitle, detailKap, detailFas,idRuangan;
    ImageView detailImage;
    Button orderButton;
    String key ="";
    String imageUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruangan_detail);
        detailDesc = findViewById(R.id.detailDesc);
        detailTitle = findViewById(R.id.detailTitle);
        detailKap = findViewById(R.id.detailKap);
        detailFas = findViewById(R.id.detailFas);
        detailImage = findViewById(R.id.detailImg);
        orderButton = findViewById(R.id.recOrder);
        idRuangan = findViewById(R.id.idRuangan);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            detailDesc.setText(bundle.getString("Description"));
            detailTitle.setText(bundle.getString("Title"));
            detailKap.setText(bundle.getString("Kapasitas"));
            detailFas.setText(bundle.getString("Fasilitas"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            idRuangan.setText(bundle.getString("idruangan"));
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RuanganDetailActivity.this, PemohonOrderActivity.class)

                        .putExtra("Description",detailDesc.getText().toString())
                        .putExtra("Title",detailTitle.getText().toString())
                        .putExtra("Kapasitas",detailKap.getText().toString())
                        .putExtra("Fasilitas",detailFas.getText().toString())
                        .putExtra("Key",key)
                        .putExtra("Image", imageUrl)
                        .putExtra("idRuangan", idRuangan.getText().toString());
                startActivity(intent);
                finish();
            }
        });

    }
}