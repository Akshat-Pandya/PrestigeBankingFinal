package com.example.prestigebankingfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapterW extends RecyclerView.Adapter<RecyclerAdapterW.viewHolder> {

    Context context;
    List<Model> dataList;

    public RecyclerAdapterW(Context context, List<Model> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_layoutw,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.image.setImageResource(dataList.get(position).getCardImage());
        holder.textView1.setText(dataList.get(position).getCardName());
        holder.textView2.setText(dataList.get(position).getCardBenefits());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView textView1,textView2;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.recycler_cardImage);
            textView1=itemView.findViewById(R.id.recycler_cardName);
            textView2=itemView.findViewById(R.id.recycler_cardDescription);
        }
    }
}
