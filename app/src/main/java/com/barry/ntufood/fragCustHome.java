package com.barry.ntufood;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class fragCustHome extends Fragment {

    public fragCustHome() {
        // Required empty public constructor
    }

    public static fragCustHome newInstance() {
        fragCustHome fragment = new fragCustHome();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ((NTUFood)getActivity()).showHome(false);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_home, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnBrowseMenu).setOnClickListener(new View.OnClickListener() {
            //OnClick event listener for the Login Button
            @Override
            public void onClick(View view) {

                ((NTUFood)getActivity()).replaceFragment(new fragSelectOutlet(),false);
            }
        });

        view.findViewById(R.id.btnPlaceOrder).setOnClickListener(new View.OnClickListener() {
            //OnClick event listener for the Login Button
            public void onClick(View view) {
                ((NTUFood)getActivity()).replaceFragment(new fragCamera(),false);
            }
        });



    }
}