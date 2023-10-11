package com.example.ruangan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ruangan.databinding.ActivityHomeBinding;
import com.example.ruangan.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private TextView namep, homep;
    private ImageButton logoutBtn;
    private ProgressDialog progressDialog;

    private Button ruangan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Tunggu Sebentar");
        homep = findViewById(R.id.homep);
//        logoutBtn = findViewById(R.id.logoutBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);
        checkUser();
        bottomNavigationView.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.home:
                    return true;
                case R.id.laporan:
                    startActivity(new Intent(getApplicationContext(),PemohonShowOrder.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                    finish();
                    return true;
                case R.id.pengaturan:
                    startActivity(new Intent(getApplicationContext(),HomeSettingActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                    finish();
                    return true;
            }
            return false;
        });
        ruangan = findViewById(R.id.btn_ruangan);

        ruangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,PemohonRuanganActivity.class);
                startActivity(intent);

            }
        });
//        logoutBtn.setOnClickListener((v -> {
//            progressDialog.setMessage("Sedang Logout...");
//            progressDialog.show();
//            firebaseAuth.signOut();
//            checkUser();
//        }));
    }

    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        else{
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            ref.orderByChild("uid").equalTo(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot DataSnapshot) {
                    for(DataSnapshot ds: DataSnapshot.getChildren()){
                        String name = "Home (" + ds.child("name").getValue() + " - " + ds.child("userType").getValue() + ")";

                        homep.setText(name);
                    }
                }

                @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
            });
        }
    }
}