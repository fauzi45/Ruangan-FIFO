package com.example.ruangan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterOrderAdmin extends RecyclerView.Adapter<AdapterOrderAdmin.HolderOrderAdmin> implements Filterable {
    private Context context;
    private FirebaseAuth firebaseAuth;

    public AdapterOrderAdmin(Context context, ArrayList<ModelOrderAdmin> orderAdminList) {
        this.context = context;
        this.orderAdminList = orderAdminList;
        this.filterList = orderAdminList;
    }

    public ArrayList<ModelOrderAdmin> orderAdminList, filterList;
    private FilterOrderAdmin filter;
    @NonNull
    @Override
    public HolderOrderAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_order_admin,parent,false);
        return new HolderOrderAdmin(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderOrderAdmin holder, int position) {
        ModelOrderAdmin modelOrderAdmin = orderAdminList.get(position);
        loadShopInfo(modelOrderAdmin, holder);
        String orderId = modelOrderAdmin.getOrderId();
        String orderDesc = modelOrderAdmin.getOrderDesc();
        String orderPhone = modelOrderAdmin.getOrderPhone();
        String orderStatus = modelOrderAdmin.getOrderStatus();
        String orderTimeStart = modelOrderAdmin.getOrderStartTime();
        String orderTimeEnd = modelOrderAdmin.getOrderFinishTime();
        String date = modelOrderAdmin.getOrderDate();
        String showidRuangan = modelOrderAdmin.getRuanganId();

        String getUserId = modelOrderAdmin.getOrderUserId();
        holder.nourut.setText(String.valueOf(position+1));
        holder.orderDesc.setText(orderDesc);
        holder.orderPhone.setText(orderPhone);
        holder.statusTv.setText(orderStatus);
        holder.orderIdTv.setText("Order ID: "+orderId);
        holder.timeStartTv.setText(orderTimeStart);
        holder.timeEndTv.setText(orderTimeEnd);
        if(orderStatus.equals("Menunggu Persetujuan")){
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.grey));
        }else if(orderStatus.equals("Disetujui")){
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.greem));
        }else if(orderStatus.equals("Ditolak")){
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.red));
        }
        holder.dateTv.setText(date);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser fUser = firebaseAuth.getCurrentUser();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                ref.child(fUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String userType = ""+snapshot.child("userType").getValue();

                        if(userType.equals("petugas")){
                                Intent intent = new Intent(context, AdminPermohonanDetailActivity.class);
                                intent.putExtra("orderId",orderId);
                                intent.putExtra("ruanganId",showidRuangan);
                                intent.putExtra("orderUserId",getUserId);
                                context.startActivity(intent);
                        }else if(userType.equals("sekjur")){
                            Intent intent = new Intent(context, SekjurPermohonanDetailActivity.class);
                            intent.putExtra("orderId",orderId);
                            intent.putExtra("ruanganId",showidRuangan);
                            intent.putExtra("orderUserId",getUserId);
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

    private void loadShopInfo(ModelOrderAdmin modelOrderAdmin, AdapterOrderAdmin.HolderOrderAdmin holder) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ruangan");
        ref.child(modelOrderAdmin.getRuanganId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String ruanganName = ""+dataSnapshot.child("nama").getValue();
                        holder.NameAdminTv.setText(ruanganName);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    @Override
    public int getItemCount() {
        return orderAdminList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new FilterOrderAdmin(this,filterList);
        }

        return filter;
    }

    class HolderOrderAdmin extends RecyclerView.ViewHolder{
        private TextView nourut, ruanganId, orderPhone,orderDesc,orderIdTv, dateTv, NameAdminTv, timeStartTv, timeEndTv, statusTv;
        public HolderOrderAdmin(@NonNull View itemView) {
            super(itemView);
            nourut = itemView.findViewById(R.id.noUrut);
            orderIdTv = itemView.findViewById(R.id.orderAdminTv);
            dateTv = itemView.findViewById(R.id.dateAdminTv);
            NameAdminTv = itemView.findViewById(R.id.NameAdminTv);
            timeStartTv = itemView.findViewById(R.id.timeStartTvAdmin);
            timeEndTv = itemView.findViewById(R.id.timeEndTvAdmin);
            statusTv = itemView.findViewById(R.id.statusTVAdmin);
            orderPhone = itemView.findViewById(R.id.showPhoneAdmin);
            orderDesc = itemView.findViewById(R.id.showAdminDesc);
            ruanganId = itemView.findViewById(R.id.showIdRuangan);


        }
    }
}
