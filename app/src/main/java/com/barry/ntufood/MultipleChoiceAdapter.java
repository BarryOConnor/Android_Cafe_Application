package com.barry.ntufood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class MultipleChoiceAdapter extends RecyclerView.Adapter<MultipleChoiceAdapter.MultipleChoice>{
    private List<Map.Entry<String, Double>> priceList;

    private static CheckBox lastChecked = null;
    private static int lastCheckedPos = 0;
    private Context context;

    public MultipleChoiceAdapter(List<Map.Entry<String, Double>> priceList, Context context){
        this.priceList = priceList;
        this.context = context;
    }

    @Override
    public MultipleChoice onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.multiple_choice, parent, false);
        MultipleChoice choice = new MultipleChoice(itemView);
        return choice;
    }

    @Override
    public void onBindViewHolder(MultipleChoice holder, final int position) {
        Map.Entry<String, Double> currentItem = priceList.get(position);
        holder.mchkChoice.setText("hello");
        holder.mchkChoice.setText(currentItem.getKey() + " - £" + String.format("%.2f", currentItem.getValue()));

        holder.mchkChoice.setOnCheckedChangeListener(null);

        holder.mchkChoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set your object's last status
                holder.mchkChoice.setChecked(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return priceList.size();
    }

    public class MultipleChoice extends RecyclerView.ViewHolder {
        CheckBox mchkChoice;
        public MultipleChoice(View view) {
            super(view);
            mchkChoice = view.findViewById(R.id.chkChoice);
        }
    }
}