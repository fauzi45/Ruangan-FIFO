package com.example.ruangan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SekjurRuanganDetailActivity extends AppCompatActivity {
    TextView detailDesc, detailTitle, detailKap, detailFas,idRuangan;
    ImageView detailImage;
    String key ="";
    String imageUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sekjur_ruangan_detail);
        detailDesc = findViewById(R.id.detailDesc);
        detailTitle = findViewById(R.id.detailTitle);
        detailKap = findViewById(R.id.detailKap);
        detailFas = findViewById(R.id.detailFas);
        detailImage = findViewById(R.id.detailImg);
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
    }
}