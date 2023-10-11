package com.example.ruangan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SekjurRuanganActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Ruangan> dataList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    SearchView searchView;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sekjur_ruangan);
        recyclerView = findViewById(R.id.sekjurRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SekjurRuanganActivity.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();

        AlertDialog.Builder builder =   new AlertDialog.Builder(SekjurRuanganActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();

        adapter = new MyAdapter(SekjurRuanganActivity.this,dataList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("ruangan");


        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    Ruangan ruangan = itemSnapshot.getValue(Ruangan.class);
                    ruangan.setKey(itemSnapshot.getKey());
                    dataList.add(ruangan);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });


    }
    public void searchList(String text){
        ArrayList<Ruangan> searchList = new ArrayList<>();
        for(Ruangan ruangan: dataList){
            if(ruangan.getNama().toLowerCase().contains(text.toLowerCase())){
                searchList.add(ruangan);
            }
        }
        adapter.searchDataList(searchList);
    }

}