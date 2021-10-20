package com.barry.ntufood;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class fragCurrentOrder extends Fragment {
    private TextView mtvLocation;
    private Button mbtnCompleteOrder;
    private Order currentOrder;
    private RecyclerView mrvOrderItems;
    private TextView mtvTotal, mtvSubtotal;
    private List<OrderItem> currItems = new ArrayList<>();


    public fragCurrentOrder() {
        // Required empty public constructor
    }

    public static fragCurrentOrder newInstance() {
        fragCurrentOrder fragment = new fragCurrentOrder();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_order, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NTUFood ntuFood = (NTUFood) getContext();
        currentOrder = ntuFood.getCurrentOrder();
        currItems = currentOrder.getItems();

        mtvLocation = view.findViewById(R.id.tvLocation);
        mtvTotal = view.findViewById(R.id.tvTotal);
        mrvOrderItems = view.findViewById(R.id.rvOrderItems);
        mbtnCompleteOrder = view.findViewById(R.id.btnCompleteOrder);

        mtvLocation.setText(ntuFood.getCurrentOutlet() + " - Table " + ntuFood.getCurrentTable());

        CurrentOrderAdapter itemAdapter = new CurrentOrderAdapter(currItems, getContext());
        mrvOrderItems.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mrvOrderItems.setAdapter(itemAdapter);

        mtvTotal.setText("Total £" + String.format("%.2f", currentOrder.getTotal()));

        mrvOrderItems.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mrvOrderItems, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                AlertDialog.Builder deleteConfirm = new AlertDialog.Builder(getContext());
                deleteConfirm.setTitle("Remove an Item");
                deleteConfirm.setMessage("Are you sure you want to remove this item from your order?");
                deleteConfirm.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentOrder.setTotal(currentOrder.getTotal() - currItems.get(position).getTotal());
                        currItems.remove(position);
                        mrvOrderItems.removeViewAt(position);
                        itemAdapter.notifyItemRemoved(position);
                        itemAdapter.notifyItemRangeChanged(position, currItems.size());
                        mtvTotal.setText("Total £" + String.format("%.2f", currentOrder.getTotal()));
                    }
                });

                // Set the alert dialog no button click listener
                deleteConfirm.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                    }
                });

                AlertDialog dialog = deleteConfirm.create();
                // Display the alert dialog on interface
                dialog.show();

            }
        }));

        mbtnCompleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentOrder.getItems().size() > 0){
                    ntuFood.replaceFragment(new fragPlaceOrder(),false);
                } else {
                    AlertDialog.Builder errorMessage = new AlertDialog.Builder(getContext());
                    errorMessage.setTitle("Information");
                    errorMessage.setMessage(getContext().getString(R.string.order_no_items));
                    errorMessage.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = errorMessage.create();
                    // Display the alert dialog on interface
                    dialog.show();
                }
            }
        });
    }
}
