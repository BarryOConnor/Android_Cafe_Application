package com.barry.ntufood;

import android.os.Bundle;
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

public class fragOrderFood extends Fragment {
    private ImageView mivImage;
    private TextView mtvLocation, mtvName, mtvDescription, mtvAllergens, mtvDietary, mtvNutrition, mtvAmount, mtvUnavailable, mtvAdditionLabel;
    private Button mbtnAddToOrder;
    private FoodItem currentFood;
    private RecyclerView mrvPrices, mrvAdditions;
    private SingleChoiceAdapter priceAdapter;
    private MultipleChoiceAdapter additionsAdapter;
    private FloatingActionButton mfabMinus, mfabPlus;
    private List<Boolean> additionStatus = new ArrayList();
    private Double additionTotal, total;
    private int amount;

    private int currentlySelectedPrice;


    public fragOrderFood() {
        // Required empty public constructor
    }

    public static fragOrderFood newInstance() {
        fragOrderFood fragment = new fragOrderFood();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        additionTotal = 0.00;
        currentlySelectedPrice = 0;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_food, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NTUFood ntuFood = (NTUFood) getContext();


        currentFood = ntuFood.getCurrentFood();
        mivImage = view.findViewById(R.id.ivImage);
        mtvLocation = view.findViewById(R.id.tvLocation);
        mtvName = view.findViewById(R.id.tvDate);
        mtvDescription = view.findViewById(R.id.tvLocation);
        mtvAllergens = view.findViewById(R.id.tvAllergens);
        mtvDietary = view.findViewById(R.id.tvDietary);
        mtvNutrition = view.findViewById(R.id.tvNutrition);
        mtvAdditionLabel = view.findViewById(R.id.tvAdditionLabel);
        mrvPrices = view.findViewById(R.id.rvOrderItems);
        mrvAdditions = view.findViewById(R.id.rvAdditions);
        mtvAmount = view.findViewById(R.id.tvTotal);
        mfabPlus = view.findViewById(R.id.fabPlus);
        mfabMinus = view.findViewById(R.id.fabMinus);
        mtvUnavailable = view.findViewById(R.id.tvUnavailable);
        mbtnAddToOrder = view.findViewById(R.id.btnCompleteOrder);

        mtvName.setText(currentFood.getName());
        mtvDescription.setText(currentFood.getDescription());
        mtvAllergens.setText(currentFood.getAllergens());
        mtvLocation.setText(ntuFood.getCurrentOutlet() + " - Table " + ntuFood.getCurrentTable());

        mtvDietary.setText(currentFood.getDietaryAsString());
        mtvNutrition.setText(currentFood.getNutrition());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageReference.child(currentFood.getImage());

        GlideApp.with(ntuFood).load(imageRef).into(mivImage);

        List<Map.Entry<String, Double>> prices = currentFood.getPriceSorted();
        Collections.sort(prices, new AscPriceComparator());

        List<Map.Entry<String, Double>> additions = currentFood.getAdditionsSorted();
        Collections.sort(additions, new AscPriceComparator());

        for (int i = 0; i < additions.size(); i++){
            additionStatus.add(false);
        }


        mbtnAddToOrder.setText( "£" + String.format("%.2f", prices.get(0).getValue())+ " - Add to Order");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String today = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());

        if (currentFood.getAvailability().size() > 0 && !currentFood.getAvailability().contains(today)) {
            mtvUnavailable.setVisibility(View.VISIBLE);
            mbtnAddToOrder.setVisibility(View.GONE);
            mfabMinus.setVisibility(View.GONE);
            mfabPlus.setVisibility(View.GONE);
            mtvAmount.setVisibility(View.GONE);
        } else {
            mtvUnavailable.setVisibility(View.GONE);
            mbtnAddToOrder.setVisibility(View.VISIBLE);
            mfabMinus.setVisibility(View.VISIBLE);
            mfabPlus.setVisibility(View.VISIBLE);
            mtvAmount.setVisibility(View.VISIBLE);
        }


        if(currentFood.getAdditions().size() > 0) {
            mrvAdditions.setVisibility(View.VISIBLE);
            mtvAdditionLabel.setVisibility(View.VISIBLE);
        } else {
            mrvAdditions.setVisibility(View.GONE);
            mtvAdditionLabel.setVisibility(View.GONE);
        }

        priceAdapter = new SingleChoiceAdapter(prices, getContext());
        mrvPrices.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mrvPrices.setAdapter(priceAdapter);


        mrvPrices.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mrvPrices, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                currentlySelectedPrice = position;
                total = prices.get(currentlySelectedPrice).getValue() + additionTotal;
                mbtnAddToOrder.setText( "£" + String.format("%.2f", total) + " - Add to Order");
                mtvAmount.setText("1");
            }
        }));


        additionsAdapter = new MultipleChoiceAdapter(additions, getContext());
        mrvAdditions.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mrvAdditions.setAdapter(additionsAdapter);

        mrvAdditions.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mrvPrices, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                amount = Integer.parseInt(mtvAmount.getText().toString());
                total = 0.00;

                additionStatus.set(position, !additionStatus.get(position));
                if (additionStatus.get(position)){
                    additionTotal += additions.get(position).getValue();
                } else {
                    additionTotal -= additions.get(position).getValue();
                }
                total = prices.get(currentlySelectedPrice).getValue() + additionTotal;
                mbtnAddToOrder.setText( "£" + String.format("%.2f", total * amount) + " - Add to Order");
            }
        }));


        mfabMinus.setOnClickListener(new View.OnClickListener() {
            //OnClick event listener for the Login Button
            public void onClick(View view) {
                amount = Integer.parseInt(mtvAmount.getText().toString());
                total = prices.get(currentlySelectedPrice).getValue() + additionTotal;

                if (amount == 0 ){
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "You can't go lower than zero", Snackbar.LENGTH_LONG).show();
                } else {
                    amount --;
                    mtvAmount.setText(String.valueOf(amount));
                    mbtnAddToOrder.setText( "£" + String.format("%.2f", total * amount) + " - Add to Order");
                }
            }
        });


        mfabPlus.setOnClickListener(new View.OnClickListener() {
            //OnClick event listener for the Login Button
            public void onClick(View view) {
                amount = Integer.parseInt((String) mtvAmount.getText());
                total = prices.get(currentlySelectedPrice).getValue() + additionTotal;
                amount ++;
                mtvAmount.setText(String.valueOf(amount));
                mbtnAddToOrder.setText( "£" + String.format("%.2f", total * amount)+ " - Add to Order");
            }
        });


        mbtnAddToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = Integer.parseInt((String) mtvAmount.getText());
                total = prices.get(currentlySelectedPrice).getValue() + additionTotal;
                double totalCost = total * amount;
                OrderItem newItem = new OrderItem(currentFood.getName() + " - " + prices.get(currentlySelectedPrice).getKey(), prices.get(currentlySelectedPrice).getValue(), amount, totalCost);
                for (int i = 0; i < additionStatus.size(); i++){
                    if (additionStatus.get(i)) {
                        newItem.addAddition(additions.get(i).getKey(), additions.get(i).getValue());
                    }
                }
                ntuFood.addToOrder(newItem);
                Snackbar.make(getActivity().findViewById(android.R.id.content), amount + " x " + currentFood.getName() + " added to your order", Snackbar.LENGTH_SHORT).show();
                ntuFood.replaceFragment(new fragOrderMenu(),true);
            }
        });
    }
}
