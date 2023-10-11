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

public class PemohonShowOrder extends AppCompatActivity {
private RecyclerView orderRv;
 private ArrayList<ModelOrderUser> ordersList;
    private FirebaseAuth firebaseAuth;
    private AdapterOrderUser adapterOrderUser;

    private TextView filteredOrdersTv;
    private ImageButton filteredordersButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemohon_show_order);
        orderRv = findViewById(R.id.orderRv);
        firebaseAuth = FirebaseAuth.getInstance();
        ordersList = new ArrayList<>();
        filteredOrdersTv = findViewById(R.id.filteredOrdersTv);
        filteredordersButton = findViewById(R.id.filteredordersButton);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(PemohonShowOrder.this,1);
        orderRv.setLayoutManager(gridLayoutManager);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.laporan);

        bottomNavigationView.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                    finish();
                    return true;
                case R.id.laporan:

                    return true;
                case R.id.pengaturan:
                    startActivity(new Intent(getApplicationContext(),HomeSettingActivity.class));
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
                AlertDialog.Builder builder = new AlertDialog.Builder(PemohonShowOrder.this);
                builder.setTitle("Filter Status:")
                        .setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which == 0){
                                    filteredOrdersTv.setText("Tampilkan Semua");
                                    adapterOrderUser.getFilter().filter("");
                                }else{
                                    String optionClicked = options[which];
                                    filteredOrdersTv.setText("Tampilkan "+optionClicked+"");
                                    adapterOrderUser.getFilter().filter(optionClicked);
                                }
                            }
                        }).show();
            }
        });

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders")
                            ;
                    ref.orderByChild("orderUserId").equalTo(firebaseAuth.getUid())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    ordersList.clear();
                                    if(dataSnapshot.exists()){
                                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                                            ModelOrderUser modelOrderUser = ds.getValue(ModelOrderUser.class);
                                            ordersList.add(modelOrderUser);
                                        }
                                      adapterOrderUser = new AdapterOrderUser(PemohonShowOrder.this,ordersList);
                                        orderRv.setAdapter(adapterOrderUser);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
    }
}