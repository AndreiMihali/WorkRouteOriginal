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
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class CardItemAdapter extends RecyclerView.Adapter<CardItemAdapter.ViewHolder> {

    private ArrayList<CardItem> data;
    private Context context;
    private ItemClickListener itemClickListener;
    public int itemIndexSelected=0;

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

    public interface ItemClickListener{
        void onItemClick(View view);
    }

    public void setClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private ImageView cardStatus;
        private TextView cardNumber;
        private TextView cardName;
        private ImageView cardType;
        private MaterialCardView cardTarjeta;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardStatus=itemView.findViewById(R.id.card_status);
            cardNumber=itemView.findViewById(R.id.card_number);
            cardName=itemView.findViewById(R.id.card_name);
            cardType=itemView.findViewById(R.id.card_type);
            cardTarjeta=itemView.findViewById(R.id.card_tarjeta);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            if(itemClickListener!=null){
                itemClickListener.onItemClick(v);
                itemIndexSelected=getAdapterPosition();
            }
            return true;
        }
    }
}
