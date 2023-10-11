package com.example.ruangan;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AdminUpdateRuangan extends AppCompatActivity {

    Button editButton;
    EditText updateDesc, updateTitle, updateKap, updateFas;
    String title,desc,kap, fas;
    String key;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_ruangan);

        updateTitle = findViewById(R.id.updateRuangan);
        updateDesc = findViewById(R.id.updateDesc);
        updateKap = findViewById(R.id.updateKapasitas);
        updateFas = findViewById(R.id.updateFasilitas);
        editButton = findViewById(R.id.btn_update_r);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            updateDesc.setText(bundle.getString("Description"));
            updateTitle.setText(bundle.getString("Title"));
            updateKap.setText(bundle.getString("Kapasitas"));
            updateFas.setText(bundle.getString("Fasilitas"));
            key = bundle.getString("Key");
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("ruangan").child(key);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
                Intent intent = new Intent(AdminUpdateRuangan.this, AdminRuanganActivity.class);
                startActivity(intent);
            }
        });

    }


    public void updateData(){
        title = updateTitle.getText().toString().trim();
        desc = updateDesc.getText().toString().trim();
        kap = updateKap.getText().toString();
        fas = updateFas.getText().toString();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("nama",""+title);
        hashMap.put("deksripsi",""+desc);
        hashMap.put("kapasitas",""+kap);
        hashMap.put("fasilitas",""+fas);

        databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){


                    Toast.makeText(AdminUpdateRuangan.this, "Data Ruangan Berhasil Di Update", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminUpdateRuangan.this, "Data ruangan gagal di update", Toast.LENGTH_SHORT).show();
            }
        });
    }
}