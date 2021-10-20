package com.barry.ntufood;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CurrentOrderAdapter extends RecyclerView.Adapter<CurrentOrderAdapter.OrderEntry>{
    private List<OrderItem> currOrderItems;
    private Context context;


    public CurrentOrderAdapter(List<OrderItem> currOrderItems, Context context){
        this.currOrderItems = currOrderItems;
        this.context = context;
    }


    @Override
    public OrderEntry onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_order, parent, false);
        OrderEntry entry = new OrderEntry(itemView);
        return entry;
    }


    @Override
    public void onBindViewHolder(OrderEntry holder, final int position) {
        OrderItem currentItem = currOrderItems.get(position);
        String finalString = "";

        List<Map.Entry<String, Double>> additionsList = new ArrayList<Map.Entry<String, Double>>(currentItem.getAdditions().entrySet());

        for(int i = 0; i < additionsList.size(); i++){
            finalString += additionsList.get(i).getKey() + " @ £" + String.format("%.2f", additionsList.get(i).getValue());
            finalString += System.getProperty("line.separator");
        }

        holder.mtvItem.setText(currentItem.getAmount() + " x " + currentItem.getItemName() + " @ £" + String.format("%.2f", currentItem.getPrice()));
        holder.mtvAdditions.setText(finalString);
        holder.mtvSubtotal.setText("£" + String.format("%.2f", currentItem.getTotal()));
    }

    @Override
    public int getItemCount() {
        return currOrderItems.size();
    }

    public class OrderEntry extends RecyclerView.ViewHolder{
        TextView mtvItem,mtvSubtotal, mtvAdditions;

        public OrderEntry(View view) {
            super(view);
            mtvItem = view.findViewById(R.id.tvItem);
            mtvSubtotal = view.findViewById(R.id.tvSubtotal);
            mtvAdditions = view.findViewById(R.id.tvAdditions);
        }
    }
}