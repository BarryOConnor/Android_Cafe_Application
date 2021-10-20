package com.barry.ntufood;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.barry.ntufood.ValidationHelper.*;


public class fragLogin extends Fragment {

    private FirebaseAuth mfbAuth = FirebaseAuth.getInstance();
    private TextInputLayout mtilEmail, mtilPassword;
    private Button mbtnLogin, mbtnRegister;
    private String mEmail;
    private String mPassword;



    public fragLogin() {
        // Required empty public constructor
    }


    public static fragLogin newInstance() {
        fragLogin fragment = new fragLogin();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        NTUFood ntuFood = (NTUFood) getContext();
        super.onViewCreated(view, savedInstanceState);

        mtilEmail = view.findViewById(R.id.tilEmail);
        mtilPassword = view.findViewById(R.id.tilPassword);
        mbtnLogin = view.findViewById(R.id.btnLogin);
        mbtnRegister = view.findViewById(R.id.btnRegister);

        mtilEmail.getEditText().addTextChangedListener(new AutoTextWatcher(mtilEmail, getActivity().getApplicationContext()));
        mtilPassword.getEditText().addTextChangedListener(new AutoTextWatcher(mtilPassword, getActivity().getApplicationContext()));

        FirebaseUser currentUser = mfbAuth.getCurrentUser();

        mbtnLogin.setOnClickListener(new View.OnClickListener() {
            //OnClick event listener for the Login Button
            @Override
            public void onClick(View view) {
                mEmail = mtilEmail.getEditText().getText().toString();
                mPassword = mtilPassword.getEditText().getText().toString();

                if (validateForm()) {
                    signIn(mEmail, mPassword);
                }
            }
        });

        mbtnRegister.setOnClickListener(new View.OnClickListener() {
            //OnClick event listener for the Login Button
            public void onClick(View view) {
                ntuFood.replaceFragment(new fragRegister(),false);
            }
        });

    }

    private boolean validateForm() {
        boolean misValid = true;

        if (isNullOrEmpty(mEmail) || !isValidEmail(mEmail)) {
            mtilEmail.setError("Not a valid email");
            misValid = false;
        } else {
            mtilEmail.setError(null);
        }

        if (isNullOrEmpty(mPassword) || !isValidPassword(mPassword)){
            mtilPassword.setError("Password must be 8 or more characters and contain contain uppercase, lowercase and one of @#$%^&+=!");
            misValid = false;
        } else {
            mtilPassword.setError(null);
        }

        return misValid;
    }

    private void signIn(String email, String password) {
        //
        mfbAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        FirebaseFirestore mfbDatabase = FirebaseFirestore.getInstance();
                        //mfbDatabase.useEmulator("10.0.2.2", 8080);
                        // Sign in success, save current user uid and redirect to the customer homescreen
                        mfbDatabase.collection("users").document(mfbAuth.getCurrentUser().getUid()).get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        NTUFood ntuFood = (NTUFood) getContext();
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                User mUser = document.toObject(User.class);
                                                ntuFood.setCurrentUser(mUser);
                                            }
                                        } else {
                                            User mUser = new User("","",mfbAuth.getCurrentUser().getEmail(),null, mfbAuth.getCurrentUser().getUid());
                                            ntuFood.setCurrentUser(mUser);
                                        }
                                    }
                                });



                        mfbDatabase.collection("food_items").orderBy("category", Query.Direction.ASCENDING)
                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Snackbar.make(getActivity().findViewById(android.R.id.content), "unable to retrieve Food List, please try again later.", Snackbar.LENGTH_LONG).show();
                                            return;
                                        } else {
                                            List<FoodItem> foodItems = new ArrayList<>();
                                            NTUFood ntuFood = (NTUFood) getContext();
                                            for (DocumentSnapshot document : snapshot.getDocuments()) {
                                                FoodItem currentItem = document.toObject(FoodItem.class);
                                                currentItem.setPriceSorted(currentItem.getPrice());
                                                currentItem.setAdditionsSorted(currentItem.getAdditions());
                                                foodItems.add(currentItem);
                                            }
                                            ntuFood.setMenu(foodItems);
                                            ((NTUFood)getActivity()).replaceFragment(new fragCustHome(),false);
                                        }
                                    };
                                });


                    } else {
                        // If sign in fails, display a message to the user.
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Invalid login credentials.", Snackbar.LENGTH_LONG).show();
                    }
                }
            });
    }
}