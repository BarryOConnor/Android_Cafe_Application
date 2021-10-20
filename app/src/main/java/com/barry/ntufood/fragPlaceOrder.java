package com.barry.ntufood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;



public class fragPlaceOrder extends Fragment {
    private FirebaseFirestore mfbDatabase = FirebaseFirestore.getInstance();
    private Button mbtnReturnHome;

    public fragPlaceOrder() {
        // Required empty public constructor
    }

    public static fragPlaceOrder newInstance() {
        fragPlaceOrder fragment = new fragPlaceOrder();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_order, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NTUFood ntuFood = (NTUFood) getContext();
        User currUser = ntuFood.getCurrentUser();
        Order currOrder = ntuFood.getCurrentOrder();
        currOrder.setUserID(currUser.getUid());
        currOrder.setTable(ntuFood.getCurrentTable());
        currOrder.setOutlet(ntuFood.getCurrentOutlet());
        currOrder.setDatetime(Timestamp.now());
        mbtnReturnHome = view.findViewById(R.id.btnReturnHome);

        mfbDatabase.collection("orders").add(currOrder)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Order successfully saved", Snackbar.LENGTH_LONG).show();
                        ntuFood.resetOrder();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Unable to save order at this time. Please try later.", Snackbar.LENGTH_LONG).show();
                    }
                });


        mbtnReturnHome.setOnClickListener(new View.OnClickListener() {
            //OnClick event listener for the Login Button
            @Override
            public void onClick(View view) {
                ntuFood.replaceFragment(new fragCustHome(),true);
            }
        });

    }
}
