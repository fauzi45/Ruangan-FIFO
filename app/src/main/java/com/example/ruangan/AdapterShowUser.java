package com.example.ruangan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterShowUser extends RecyclerView.Adapter<HolderShowUser> {
    private Context context;
    private List<Users> userList;

    public AdapterShowUser(Context context, List<Users> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public HolderShowUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_show_user,parent,false);
        return new HolderShowUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderShowUser holder, int position) {
        holder.recShowTitle.setText(userList.get(position).getName());
        holder.recShowUserType.setText(userList.get(position).getUserType());
        holder.recShowId.setText(userList.get(position).getUid());
        holder.recShowEmail.setText(userList.get(position).getEmail());
        holder.recShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(context, AdminShowUserDetail.class);
                inten.putExtra("name",userList.get(holder.getAdapterPosition()).getName());
                inten.putExtra("userType",userList.get(holder.getAdapterPosition()).getUserType());
                inten.putExtra("uid",userList.get(holder.getAdapterPosition()).getUid());
                inten.putExtra("email",userList.get(holder.getAdapterPosition()).getEmail());
                inten.putExtra("Key",userList.get(holder.getAdapterPosition()).getKey());
                context.startActivity(inten);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
    public void searchDataList(ArrayList<Users> searchList){
        userList = searchList;
        notifyDataSetChanged();
    }
}
class HolderShowUser extends RecyclerView.ViewHolder{
    TextView recShowTitle, recShowUserType, recShowId, recShowEmail;
    CardView recShow;
    public HolderShowUser(@NonNull View itemView) {
        super(itemView);

        recShowTitle = itemView.findViewById(R.id.recShowTitle);
        recShowUserType = itemView.findViewById(R.id.recShowUserType);
        recShowId = itemView.findViewById(R.id.recShowId);
        recShowEmail = itemView.findViewById(R.id.recShowEmail);
        recShow = itemView.findViewById(R.id.recShow);
    }
}