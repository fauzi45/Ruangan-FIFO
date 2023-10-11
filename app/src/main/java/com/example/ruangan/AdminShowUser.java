package com.example.ruangan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class AdminShowUser extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Users> dataList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    SearchView searchView;
    AdapterShowUser adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_show_user);
        recyclerView = findViewById(R.id.recyclerShowUser);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(AdminShowUser.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminShowUser.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();

        adapter = new AdapterShowUser(AdminShowUser.this,dataList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        dialog.show();

        eventListener = databaseReference.orderByChild("userType").equalTo("pemohon").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){

                    Users users = itemSnapshot.getValue(Users.class);
                    users.setKey(itemSnapshot.getKey());
                    dataList.add(users);
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
        ArrayList<Users> searchList = new ArrayList<>();
        for(Users users: dataList){
            if(users.getName().toLowerCase().contains(text.toLowerCase())){
                searchList.add(users);
            }
        }
        adapter.searchDataList(searchList);
    }
}