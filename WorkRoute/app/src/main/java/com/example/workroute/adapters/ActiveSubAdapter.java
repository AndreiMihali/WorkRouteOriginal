package com.example.workroute.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.workroute.R;
import com.example.workroute.driverActivities.DriverMap;
import com.example.workroute.model.SubscribedUser;

import java.util.ArrayList;

public class ActiveSubAdapter extends RecyclerView.Adapter<ActiveSubAdapter.ViewHolder> {

    private ArrayList<SubscribedUser> mData;
    private Context context;
    private MyItemClickListener myItemClickListener;
    public int itemSelected=0;

    public ActiveSubAdapter(ArrayList<SubscribedUser> itemList,Context context) {
        this.mData = itemList;
        this.context=context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_people_subscribe,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(mData.get(position).getProfilePhoto().equals("")){
            holder.profilePhoto.setImageResource(R.drawable.default_user_login);
        }else{
            Glide.with(context).load(mData.get(position).getProfilePhoto()).into(holder.profilePhoto);
        }
        holder.userName.setText(mData.get(position).getUserName());
        holder.userWorkDirection.setText(mData.get(position).getUserWorkDirection());
        if(mData.get(position).getStatus().equals("pending")){
            holder.status.setImageResource(R.drawable.bxs_time_five);
            holder.status.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFE604")));
        }else if(mData.get(position).getStatus().equals("accepted")){
            holder.status.setImageResource(R.drawable.ic_baseline_star_24);
            holder.status.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFE604")));
        }else{
            holder.status.setImageResource(R.drawable.bxs_x_circle);
            holder.status.setImageTintList(ColorStateList.valueOf(Color.parseColor("#C32115")));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface MyItemClickListener{
        void setOnItemClick(View view);
    }

    public void setMyItemClickListener(MyItemClickListener myItemClickListener){
        this.myItemClickListener=myItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView profilePhoto;
        private TextView userName;
        private TextView userWorkDirection;
        private ImageView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePhoto = itemView.findViewById(R.id.profile_photo_sub);
            userName = itemView.findViewById(R.id.txt_user_name_sub);
            userWorkDirection = itemView.findViewById(R.id.tv_user_work_direction_sub);
            status=itemView.findViewById(R.id.imageViewStar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(myItemClickListener!=null){
                itemSelected=getAdapterPosition();
                notifyDataSetChanged();
                myItemClickListener.setOnItemClick(itemView);
            }
        }
    }
}
