package com.barry.ntufood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class fragDisplayFood extends Fragment {
    private ImageView mivImage;
    private TextView mtvLocation;
    private TextView mtvName;
    private TextView mtvPrice;
    private TextView mtvDescription;
    private TextView mtvAllergens;
    private TextView mtvDietary;
    private TextView mtvNutrition;
    private FoodItem currentFood;


    public fragDisplayFood() {
        // Required empty public constructor
    }

    public static fragDisplayFood newInstance() {
        fragDisplayFood fragment = new fragDisplayFood();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_food, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NTUFood ntuFood = (NTUFood) getContext();

        currentFood = ntuFood.getCurrentFood();
        mivImage = view.findViewById(R.id.ivImage);
        mtvLocation = view.findViewById(R.id.tvLocation);
        mtvName = view.findViewById(R.id.tvDate);
        mtvPrice = view.findViewById(R.id.tvPrice);
        mtvDescription = view.findViewById(R.id.tvLocation);
        mtvAllergens = view.findViewById(R.id.tvAllergens);
        mtvDietary = view.findViewById(R.id.tvDietary);
        mtvNutrition = view.findViewById(R.id.tvNutrition);

        mtvLocation.setText(((NTUFood)getActivity()).getCurrentOutlet());
        mtvName.setText(currentFood.getName());
        mtvPrice.setText(currentFood.getLowestPrice());
        mtvDescription.setText(currentFood.getDescription());
        mtvAllergens.setText(currentFood.getAllergens());


        mtvDietary.setText(currentFood.getDietaryAsString());
        mtvNutrition.setText(currentFood.getNutrition());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageReference.child(currentFood.getImage());

        GlideApp.with(ntuFood).load(imageRef).into(mivImage);
    }






}
