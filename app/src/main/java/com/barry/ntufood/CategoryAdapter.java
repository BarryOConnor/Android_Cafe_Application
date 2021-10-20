package com.barry.ntufood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.HorizontalCategories>{
    private List<String> categorylist;
    Context context;

    public CategoryAdapter(List<String> categorylist, Context context){
        this.categorylist = categorylist;
        this.context = context;
    }

    @Override
    public HorizontalCategories onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View categoryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_category, parent, false);
        HorizontalCategories hc = new HorizontalCategories(categoryView);
        return hc;
    }

    @Override
    public void onBindViewHolder(HorizontalCategories holder, final int position) {
        holder.txtview.setText(categorylist.get(position));
    }

    @Override
    public int getItemCount() {
        return categorylist.size();
    }

    public class HorizontalCategories extends RecyclerView.ViewHolder {
        TextView txtview;
        public HorizontalCategories(View view) {
            super(view);
            txtview = view.findViewById(R.id.idCategoryName);
        }
    }
}