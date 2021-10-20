package com.barry.ntufood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Map;

public class SingleChoiceAdapter extends RecyclerView.Adapter<SingleChoiceAdapter.SingleChoice>{
    private List<Map.Entry<String, Double>> priceList;

    private static RadioButton lastChecked = null;
    private static int lastCheckedPos = 0;
    private Context context;

    public SingleChoiceAdapter(List<Map.Entry<String, Double>> priceList, Context context){
        this.priceList = priceList;
        this.context = context;
        lastCheckedPos = 0;
    }

    @Override
    public SingleChoice onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_choice, parent, false);
        SingleChoice choice = new SingleChoice(itemView);
        return choice;
    }


    @Override
    public void onBindViewHolder(SingleChoice holder, final int position) {
        Map.Entry<String, Double> currentItem = priceList.get(position);
        holder.mradChoice.setText(currentItem.getKey() + " - £" + String.format("%.2f", currentItem.getValue()));

        if(position == lastCheckedPos) {
            holder.mradChoice.setChecked(true);
        } else {
            holder.mradChoice.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return priceList.size();
    }

    public class SingleChoice extends RecyclerView.ViewHolder {
        RadioButton mradChoice;
        public SingleChoice(View view) {
            super(view);
            mradChoice = view.findViewById(R.id.radChoice);

            mradChoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastCheckedPos = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }
}