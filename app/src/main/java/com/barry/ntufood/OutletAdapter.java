package com.barry.ntufood;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class OutletAdapter extends RecyclerView.Adapter<OutletAdapter.Outlet>{
    private List<Pair<String, String>> outletList;
    Context context;

    public OutletAdapter(List<Pair<String, String>> outletList, Context context){
        this.outletList = outletList;
        this.context = context;
    }

    @Override
    public Outlet onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View outletView = LayoutInflater.from(parent.getContext()).inflate(R.layout.outlet, parent, false);
        Outlet outlet = new Outlet(outletView);
        return outlet;
    }

    @Override
    public void onBindViewHolder(Outlet holder, final int position) {
        Pair<String, String> currentOutlet = outletList.get(position);
        holder.mtvOutletName.setText(currentOutlet.first);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageReference.child("logos/" + currentOutlet.second);
        GlideApp.with(context).load(imageRef).into(holder.mivOutlet);
    }

    @Override
    public int getItemCount() {
        return outletList.size();
    }

    public class Outlet extends RecyclerView.ViewHolder {
        ImageView mivOutlet;
        TextView mtvOutletName;
        public Outlet(View view) {
            super(view);
            mivOutlet = view.findViewById(R.id.ivOutlet);
            mtvOutletName = view.findViewById(R.id.tvOutletName);
        }
    }
}