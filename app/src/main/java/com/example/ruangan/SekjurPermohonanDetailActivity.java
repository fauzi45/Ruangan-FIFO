package com.example.ruangan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SekjurPermohonanDetailActivity extends AppCompatActivity {
    private String orderId, ruanganId,orderUserId;
    private ImageButton backbtn,editBtn;
    private TextView descTv,phoneNumberTv,namePemohonTv,orderIdTv, orderDateTv, orderStatusTv, orderNamaRuanganTv, orderStartTv, orderFinishTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sekjur_permohonan_detail);
        Intent intent = getIntent();
        orderUserId = intent.getStringExtra("orderUserId");

        ruanganId = intent.getStringExtra("ruanganId");
        orderId = intent.getStringExtra("orderId");
        backbtn = findViewById(R.id.orderBckBtn);
        orderIdTv = findViewById(R.id.orderIdTv);
        orderDateTv = findViewById(R.id.orderDateTv);
        orderStatusTv = findViewById(R.id.orderStatusTv);
        orderNamaRuanganTv = findViewById(R.id.namaRuanganTv);
        orderStartTv = findViewById(R.id.orderStartTimeTv);
        orderFinishTv = findViewById(R.id.orderFinishTimeTv);
        namePemohonTv = findViewById(R.id.namePemohonTv);
        phoneNumberTv = findViewById(R.id.phoneNumberTv);
        descTv = findViewById(R.id.descTv);
        loadNameInfo();
        loadShopInfo();
        loadOrderInfo();
        editBtn = findViewById(R.id.editBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOrderStatusDialog();
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void editOrderStatusDialog() {
        final String [] options = {"Disetujui","Ditolak"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit status permohonan")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedOptions = options[which];
                        editOrderStatus(selectedOptions);
                    }
                }).show();
    }

    private void editOrderStatus(String selectedOptions) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("orderStatus",""+selectedOptions);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders");
        ref.child(orderId)
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(SekjurPermohonanDetailActivity.this, "Permohonan berhasil "+selectedOptions, Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SekjurPermohonanDetailActivity.this, "Gagal mengganti status", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void loadNameInfo(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(orderUserId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String pemohon = ""+dataSnapshot.child("name").getValue();
                        namePemohonTv.setText(pemohon);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void loadShopInfo(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ruangan");
        ref.child(ruanganId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String ruanganName = ""+dataSnapshot.child("nama").getValue();
                        orderNamaRuanganTv.setText(ruanganName);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void loadOrderInfo(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders");
        ref.child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String orderId = ""+dataSnapshot.child("orderId").getValue();
                String orderStatus = ""+dataSnapshot.child("orderStatus").getValue();
                String orderDate = ""+dataSnapshot.child("orderDate").getValue();
                String orderStartTime = ""+dataSnapshot.child("orderStartTime").getValue();
                String orderTimeEnd = ""+dataSnapshot.child("orderFinishTime").getValue();
                String desc = ""+dataSnapshot.child("orderDesc").getValue();
                String phone = ""+dataSnapshot.child("orderPhone").getValue();
                if(orderStatus.equals("Menunggu Persetujuan")){
                    orderStatusTv.setTextColor(getResources().getColor(R.color.grey));
                }else if(orderStatus.equals("Disetujui")){
                    orderStatusTv.setTextColor(getResources().getColor(R.color.greem));
                }else if(orderStatus.equals("Ditolak")){
                    orderStatusTv.setTextColor(getResources().getColor(R.color.red));
                }
                orderIdTv.setText(orderId);
                orderDateTv.setText(orderDate);
                orderStartTv.setText(orderStartTime);
                orderFinishTv.setText(orderTimeEnd);
                phoneNumberTv.setText(phone);
                descTv.setText(desc);
                orderStatusTv.setText(orderStatus);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}