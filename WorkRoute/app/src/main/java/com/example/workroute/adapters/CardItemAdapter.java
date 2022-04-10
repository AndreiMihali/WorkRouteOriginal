package com.example.workroute.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workroute.R;
import com.example.workroute.model.CardItem;

import java.util.ArrayList;

public class CardItemAdapter extends RecyclerView.Adapter<CardItemAdapter.ViewHolder> {

    private ArrayList<CardItem> data;
    private Context context;

    public CardItemAdapter(Context context,ArrayList<CardItem> data){
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.tarjeta_metodo_pago_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cardStatus.setImageResource(data.get(position).getStatus());
        holder.cardNumber.setText(data.get(position).getNumberCard());
        holder.cardName.setText(data.get(position).getCardName());
        holder.cardType.setImageResource(data.get(position).getCardType());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView cardStatus;
        private TextView cardNumber;
        private TextView cardName;
        private ImageView cardType;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardStatus=itemView.findViewById(R.id.card_status);
            cardNumber=itemView.findViewById(R.id.card_number);
            cardName=itemView.findViewById(R.id.card_name);
            cardType=itemView.findViewById(R.id.card_type);
        }
    }
}
