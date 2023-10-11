package com.example.ruangan;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminUploadRuangan extends AppCompatActivity {
    ImageView uploadimage;
    Button simpan;
    EditText upnama,updeks,upfas,upkap;
    String imageurl;
    Uri uri;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_upload_ruangan);

        uploadimage = findViewById(R.id.uploadimage);
        upnama = findViewById(R.id.uploadruangan);
        updeks = findViewById(R.id.deksripsi);
        upfas = findViewById(R.id.fasilitas);
        upkap = findViewById(R.id.kapasruangan);
        simpan = findViewById(R.id.btn_simpan);


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadimage.setImageURI(uri);
                        }else{
                            Toast.makeText(AdminUploadRuangan.this, "Tidak ada gambar yang dipiih", Toast.LENGTH_SHORT).show();
                        }
                      }
                }
        );
        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedata();
            }
        });

    }

    private void savedata() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Gambar Ruangan")
                .child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(AdminUploadRuangan.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                    Uri urlImage = uriTask.getResult();
                    imageurl = urlImage.toString();
                    uploadData();
                    dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    private void uploadData() {
        String title = upnama.getText().toString();
        String desc = updeks.getText().toString();
        String kapasi = upkap.getText().toString();
        String fasi = upfas.getText().toString();

        String currentDate = ""+System.currentTimeMillis();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("idruangan",""+currentDate);
        hashMap.put("nama",""+title);
        hashMap.put("deksripsi",""+desc);
        hashMap.put("img",""+imageurl);
        hashMap.put("fasilitas",""+fasi);
        hashMap.put("kapasitas",""+kapasi);


        FirebaseDatabase.getInstance().getReference("ruangan").child(currentDate).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AdminUploadRuangan.this, "Data ruangan berhasil dibuat", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AdminUploadRuangan.this, AdminRuanganActivity.class));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminUploadRuangan.this, "Data ruangan gagal dibuat", Toast.LENGTH_SHORT).show();
            }
        });
    }
}