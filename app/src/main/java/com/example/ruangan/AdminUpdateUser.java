package com.example.ruangan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AdminUpdateUser extends AppCompatActivity {
    Button editButton;
    EditText updateName, updateEmail, updateTipe;
    String name,email,tipe;
    String key;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_user);
        updateName = findViewById(R.id.updateName);
        updateEmail = findViewById(R.id.updateEmail);
        updateTipe = findViewById(R.id.updateTypeUser);

        editButton = findViewById(R.id.btn_update_user);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            updateName.setText(bundle.getString("name"));
            updateEmail.setText(bundle.getString("email"));
            updateTipe.setText(bundle.getString("userType"));
            key = bundle.getString("Key");
        }
 databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(key);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
                Intent intent = new Intent(AdminUpdateUser.this, AdminShowUser.class);
                startActivity(intent);
            }
        });

    }


    public void updateData(){

        name = updateName.getText().toString();
        email = updateEmail.getText().toString();
        tipe = updateTipe.getText().toString();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name",""+name);
        hashMap.put("email",""+email);
        hashMap.put("userType",""+tipe);

        databaseReference .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){


                    Toast.makeText(AdminUpdateUser.this, "Data Pemohon Berhasil Di Update", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminUpdateUser.this, "Data pemohon gagal di update", Toast.LENGTH_SHORT).show();
            }
        });
    }
}