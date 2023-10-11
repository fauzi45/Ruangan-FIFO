package com.example.ruangan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<Ruangan>dataList;

    private FirebaseAuth firebaseAuth;

    public MyAdapter(Context context, List<Ruangan> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_ruangan,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getImg()).into(holder.recImage);
        holder.recTitle.setText(dataList.get(position).getNama());
        holder.recKap.setText(dataList.get(position).getKapasitas());
        holder.recDesc.setText(dataList.get(position).getDeksripsi());
        holder.recFas.setText(dataList.get(position).getFasilitas());
        holder.recIdRuangan.setText(dataList.get(position).getIdruangan());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser fUser = firebaseAuth.getCurrentUser();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                ref.child(fUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String userType = ""+snapshot.child("userType").getValue();

                        if(userType.equals("pemohon")){
                            Intent intent = new Intent(context, RuanganDetailActivity.class);

                            intent.putExtra("idruangan",dataList.get(holder.getAdapterPosition()).getIdruangan());
                            intent.putExtra("Image",dataList.get(holder.getAdapterPosition()).getImg());
                            intent.putExtra("Description",dataList.get(holder.getAdapterPosition()).getDeksripsi());
                            intent.putExtra("Title",dataList.get(holder.getAdapterPosition()).getNama());
                            intent.putExtra("Kapasitas",dataList.get(holder.getAdapterPosition()).getKapasitas());
                            intent.putExtra("Fasilitas",dataList.get(holder.getAdapterPosition()).getFasilitas());
                            intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());
                            context.startActivity(intent);
                        }
                        else if(userType.equals("sekjur")){
                            Intent intent = new Intent(context, SekjurRuanganDetailActivity.class);
                            intent.putExtra("Image",dataList.get(holder.getAdapterPosition()).getImg());
                            intent.putExtra("Description",dataList.get(holder.getAdapterPosition()).getDeksripsi());
                            intent.putExtra("Title",dataList.get(holder.getAdapterPosition()).getNama());
                            intent.putExtra("Kapasitas",dataList.get(holder.getAdapterPosition()).getKapasitas());
                            intent.putExtra("Fasilitas",dataList.get(holder.getAdapterPosition()).getFasilitas());
                            intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());
                            context.startActivity(intent);
                        }
                        else if(userType.equals("petugas")){
                            Intent intent = new Intent(context, AdminRuanganDetailActivity.class);
                            intent.putExtra("Image",dataList.get(holder.getAdapterPosition()).getImg());
                            intent.putExtra("Description",dataList.get(holder.getAdapterPosition()).getDeksripsi());
                            intent.putExtra("Title",dataList.get(holder.getAdapterPosition()).getNama());
                            intent.putExtra("Kapasitas",dataList.get(holder.getAdapterPosition()).getKapasitas());
                            intent.putExtra("Fasilitas",dataList.get(holder.getAdapterPosition()).getFasilitas());
                            intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());
                            context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public void searchDataList(ArrayList<Ruangan> searchList){
    dataList = searchList;
    notifyDataSetChanged();
    }
}



class MyViewHolder extends RecyclerView.ViewHolder{
    ImageView recImage;
    TextView recTitle, recDesc, recKap, recFas,recIdRuangan;
    CardView recCard;

    public MyViewHolder(@NonNull View itemView){
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage);
        recDesc = itemView.findViewById(R.id.recDesc);
        recFas = itemView.findViewById(R.id.recFas);
        recCard = itemView.findViewById(R.id.reccard);
        recTitle = itemView.findViewById(R.id.recTitle);
        recKap = itemView.findViewById(R.id.recKap);
        recIdRuangan = itemView.findViewById(R.id.recIdRuangan);
    }
}