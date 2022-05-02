package com.example.workroute.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.workroute.R;
import com.example.workroute.model.SubscribedUser;

import java.util.ArrayList;

public class ActiveSubAdapter extends RecyclerView.Adapter<ActiveSubAdapter.ViewHolder> {

    private ArrayList<SubscribedUser> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ActiveSubAdapter(ArrayList<SubscribedUser> itemList,Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_people_subscribe,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.profilePhoto.setImageResource(mData.get(position).getProfilePhoto());
        holder.userName.setText(mData.get(position).getUserName());
        holder.userWorkDirection.setText(mData.get(position).getUserWorkDirection());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView profilePhoto;
        private TextView userName;
        private TextView userWorkDirection;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            profilePhoto = itemView.findViewById(R.id.profile_photo_sub);
            userName = itemView.findViewById(R.id.txt_user_name_sub);
            userWorkDirection = itemView.findViewById(R.id.tv_user_work_direction_sub);

        }
    }
}
