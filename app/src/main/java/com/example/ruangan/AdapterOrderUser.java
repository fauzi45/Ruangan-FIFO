package com.example.ruangan;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class AdapterOrderUser extends RecyclerView.Adapter<AdapterOrderUser.HolderOrderUser> implements Filterable {
    private Context context;
    public ArrayList<ModelOrderUser> orderUserList, filterList;
    private FilterOrderUser filter;
    private FirebaseAuth firebaseAuth;

    public AdapterOrderUser(Context context, ArrayList<ModelOrderUser> orderUserList) {
        this.context = context;
        this.orderUserList = orderUserList;
        this.filterList = orderUserList;
    }

    @NonNull
    @Override
    public HolderOrderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_order_user,parent,false);
        return new HolderOrderUser(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HolderOrderUser holder, int position) {
        ModelOrderUser modelOrderUser = orderUserList.get(position);
        loadShopInfo(modelOrderUser, holder);
        String orderId = modelOrderUser.getOrderId();
        String orderDesc = modelOrderUser.getOrderDesc();
        String orderPhone = modelOrderUser.getOrderPhone();
        String orderStatus = modelOrderUser.getOrderStatus();
        String orderTimeStart = modelOrderUser.getOrderStartTime();
        String orderTimeEnd = modelOrderUser.getOrderFinishTime();
        String date = modelOrderUser.getOrderDate();
        String showidRuangan = modelOrderUser.getRuanganId();

        String getUserId = modelOrderUser.getOrderUserId();

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
                Intent inten = new Intent(context, PemohonOrderDetail.class);
                inten.putExtra("orderId",orderId);
                inten.putExtra("ruanganId",showidRuangan);
                inten.putExtra("orderUserId",getUserId);
                context.startActivity(inten);
            }
        });
    }

    private void loadShopInfo(ModelOrderUser modelOrderUser, HolderOrderUser holder) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ruangan");
                ref.child(modelOrderUser.getRuanganId())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String ruanganName = ""+dataSnapshot.child("nama").getValue();
                                holder.ruanganNameTv.setText(ruanganName);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
    }


    @Override
    public int getItemCount() {
        return orderUserList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new FilterOrderUser(this,filterList);
        }
        return filter;
    }
    class HolderOrderUser extends RecyclerView.ViewHolder{
        private TextView ruanganId, orderPhone,orderDesc,orderIdTv, dateTv, ruanganNameTv, timeStartTv, timeEndTv, statusTv;
        public HolderOrderUser(@NonNull View itemView) {
            super(itemView);

            orderIdTv = itemView.findViewById(R.id.orderTv);
            dateTv = itemView.findViewById(R.id.dateTv);
            ruanganNameTv = itemView.findViewById(R.id.ruanganNameTv);
            timeStartTv = itemView.findViewById(R.id.timeStartTv);
            timeEndTv = itemView.findViewById(R.id.timeEndTv);
            statusTv = itemView.findViewById(R.id.statusTV);
            orderPhone = itemView.findViewById(R.id.showPhone);
            orderDesc = itemView.findViewById(R.id.showDesc);
            ruanganId = itemView.findViewById(R.id.showIdRuangan);


        }
    }
}
