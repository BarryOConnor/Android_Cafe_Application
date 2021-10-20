package com.barry.ntufood;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class fragSelectOutlet extends Fragment {
    private FirebaseFirestore mfbDatabase = FirebaseFirestore.getInstance();
    private boolean itemSelected = false;
    private RecyclerView mrvOutlets;
    private OutletAdapter outletAdapter;

    public fragSelectOutlet() {
        // Required empty public constructor
    }

    public static fragSelectOutlet newInstance() {
        fragSelectOutlet fragment = new fragSelectOutlet();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((NTUFood)getActivity()).showHome(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_outlet, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mrvOutlets = view.findViewById(R.id.rvOutlets);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mrvOutlets.getContext(), LinearLayoutManager.VERTICAL);
        mrvOutlets.addItemDecoration(dividerItemDecoration);


        mfbDatabase.collection("food_outlets")
                //.orderBy("name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Snackbar.make(getActivity().findViewById(android.R.id.content), "Unable to retrieve Food Outlet List, please try again later.", Snackbar.LENGTH_LONG).show();
                            return;
                        }
                        List<Pair<String, String>> outlets = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : snapshot) {
                            outlets.add(new Pair(doc.getString("name"), doc.getString("logo")));
                        }

                        outletAdapter = new OutletAdapter(outlets, getContext());
                        mrvOutlets.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                        mrvOutlets.setAdapter(outletAdapter);
                    }
                });

        mrvOutlets.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mrvOutlets, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String selectedItem = ((TextView) mrvOutlets.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.tvOutletName)).getText().toString();
                NTUFood ntuFood = (NTUFood) getContext();

                ntuFood.setCurrentOutlet(selectedItem);
                ntuFood.replaceFragment(new fragViewMenu(),false);
            }
        }));
    }



}