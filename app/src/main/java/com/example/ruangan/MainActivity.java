package com.example.ruangan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseDatabase fDatabase = FirebaseDatabase.getInstance();
    EditText email,password;
    Button btn_login,btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPassword);
        btn_login = findViewById(R.id.registernow);
        btn_register = findViewById(R.id.loginpage_button);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                }
                if(password.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                }
                if(!(email.getText().toString().isEmpty() && password.getText().toString().isEmpty())){
                    fAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(task -> {
                       if(task.isSuccessful()){
                           String uid = task.getResult().getUser().getUid();
                           fDatabase.getReference().child("Users").child(uid).child("usertype").addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                   int usertype = snapshot.getValue(Integer.class);
                                   if(usertype==0){
                                       Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                                       startActivity(intent);
                                       finish();
                                   }

                                   if(usertype==1){
                                       Intent intent = new Intent(MainActivity.this,SekjurActivity.class);
                                       startActivity(intent);
                                       finish();
                                   }

                                   if(usertype==2){
                                       Intent intent = new Intent(MainActivity.this,AdminActivity.class);
                                       startActivity(intent);
                                       finish();
                                   }
                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError error) {

                               }
                           });
                       }
                    });
                }
            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(in);
            }
        });



    }


}