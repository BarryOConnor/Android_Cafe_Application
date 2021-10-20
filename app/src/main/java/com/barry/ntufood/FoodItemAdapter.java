package com.barry.ntufood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.MenuItem>{
    private List<FoodItem> mFoodList;
    Context mContext;

    public FoodItemAdapter(List<FoodItem> foodList, Context context){
        this.mFoodList = foodList;
        this.mContext = context;
    }

    @Override
    public MenuItem onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View foodItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        MenuItem menuItem = new MenuItem(foodItemView);
        return menuItem;
    }

    @Override
    public void onBindViewHolder(MenuItem holder, final int position) {
        FoodItem foodItem = (mFoodList.get(position));
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageReference.child("thumbs/" + foodItem.getImage());

        GlideApp.with(mContext).load(imageRef).into(holder.mivThumb);

        holder.mtvName.setText(foodItem.getName());
        holder.mtvPrice.setText(foodItem.getLowestPrice());
        holder.mtvDescription.setText(foodItem.getDescription());

    }

    @Override
    public int getItemCount() {
        return mFoodList.size();
    }

    public class MenuItem extends RecyclerView.ViewHolder {
        ImageView mivThumb;
        TextView mtvName;
        TextView mtvPrice;
        TextView mtvDescription;
        public MenuItem(View view) {
            super(view);
            mivThumb = view.findViewById(R.id.ivThumb);
            mtvName = view.findViewById(R.id.tvDate);
            mtvPrice = view.findViewById(R.id.tvPrice);
            mtvDescription = view.findViewById(R.id.tvLocation);
        }
    }
}