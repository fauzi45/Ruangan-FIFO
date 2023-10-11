package com.example.ruangan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

public class PemohonOrderDetail extends AppCompatActivity {
    private String orderId, ruanganId,orderUserId;
    private ImageButton backbtn;
    private TextView descTv,phoneNumberTv,namePemohonTv,orderIdTv, orderDateTv, orderStatusTv, orderNamaRuanganTv, orderStartTv, orderFinishTv;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemohon_order_detail);
        Intent intent = getIntent();
        orderUserId = intent.getStringExtra("orderUserId");

        ruanganId = intent.getStringExtra("ruanganId");
        orderId = intent.getStringExtra("orderId");
        firebaseAuth = FirebaseAuth.getInstance();
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

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PemohonOrderDetail.this,PemohonShowOrder.class);
                startActivity(intent);

            }
        });
    }
    private void loadNameInfo(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid())
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
                orderStatusTv.setText(orderStatus);
                phoneNumberTv.setText(phone);
                descTv.setText(desc);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}