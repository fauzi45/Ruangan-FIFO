package com.example.ruangan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminPermohonanRuangan extends AppCompatActivity {
    private ArrayList<ModelOrderAdmin> orderAdminArrayList;
    private AdapterOrderAdmin adapterAdminOrder;
    private FirebaseAuth firebaseAuth;
    private TextView filteredOrdersTv;
    private ImageButton filteredordersButton;
    private RecyclerView orderAdminRv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_permohonan_ruangan);
        firebaseAuth = FirebaseAuth.getInstance();
        orderAdminRv = findViewById(R.id.orderAdminRv);
        filteredOrdersTv = findViewById(R.id.filteredOrdersTv);
        filteredordersButton = findViewById(R.id.filteredordersButton);
        orderAdminArrayList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(AdminPermohonanRuangan.this,1);
        orderAdminRv.setLayoutManager(gridLayoutManager);
//
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.laporan);

        bottomNavigationView.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(),AdminActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                    finish();
                    return true;
                case R.id.laporan:
                    return true;
                case R.id.pengaturan:
                    startActivity(new Intent(getApplicationContext(),AdminSettingActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                    finish();
                    return true;
            }
            return false;
        });
        filteredordersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String [] options = {"Semua","Menunggu Persetujuan","Disetujui","Ditolak"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminPermohonanRuangan.this);
                builder.setTitle("Filter Status:")
                        .setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which == 0){
                                    filteredOrdersTv.setText("Tampilkan Semua");
                                    adapterAdminOrder.getFilter().filter("");
                                }else{
                                    String optionClicked = options[which];
                                    filteredOrdersTv.setText("Tampilkan "+optionClicked+"");
                                    adapterAdminOrder.getFilter().filter(optionClicked);
                                }
                            }
                        }).show();
            }
        });

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders")
                ;
        ref.orderByChild("orderId")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        orderAdminArrayList.clear();
                        if(dataSnapshot.exists()){
                            for (DataSnapshot ds: dataSnapshot.getChildren()){
                                ModelOrderAdmin modelOrderAdmin = ds.getValue(ModelOrderAdmin.class);
                                orderAdminArrayList.add(modelOrderAdmin);
                            }
                            adapterAdminOrder = new AdapterOrderAdmin(AdminPermohonanRuangan.this, orderAdminArrayList);
                            orderAdminRv.setAdapter(adapterAdminOrder);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                }

            }




//    private void loadAllOrder() {
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                adminOrderArrayList.clear();
//                for(DataSnapshot ds: dataSnapshot.getChildren()){
//                    ModelAdminOrder modelAdminOrder = ds.getValue(ModelAdminOrder.class);
//                    adminOrderArrayList.add(modelAdminOrder);
//                }
//                adapterAdminOrder = new AdapterAdminOrder(AdminPermohonanRuangan.this,adminOrderArrayList);
//                orderAdminRv.setAdapter(adapterAdminOrder);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
