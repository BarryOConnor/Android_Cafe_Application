package com.barry.ntufood;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

import static com.barry.ntufood.ValidationHelper.*;


public class fragRegister extends Fragment {
    private FirebaseAuth mfbAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mfbDatabase = FirebaseFirestore.getInstance();

    private TextInputLayout mtilForename;
    private TextInputLayout mtilSurname;
    private TextInputLayout mtilEmail;
    private TextInputLayout mtilPassword;

    private String mForename;
    private String mSurname;
    private String mEmail;
    private String mPassword;



    public fragRegister() {
        // Required empty public constructor
    }

    public static fragRegister newInstance() {
        fragRegister fragment = new fragRegister();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((NTUFood)getActivity()).showHome(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mtilForename = view.findViewById(R.id.tilForename);
        mtilSurname = view.findViewById(R.id.tilSurname);
        mtilEmail = view.findViewById(R.id.tilEmail);
        mtilPassword = view.findViewById(R.id.tilPassword);

        mtilForename.getEditText().addTextChangedListener(new AutoTextWatcher(mtilForename, getActivity().getApplicationContext()));
        mtilSurname.getEditText().addTextChangedListener(new AutoTextWatcher(mtilSurname, getActivity().getApplicationContext()));
        mtilEmail.getEditText().addTextChangedListener(new AutoTextWatcher(mtilEmail, getActivity().getApplicationContext()));
        mtilPassword.getEditText().addTextChangedListener(new AutoTextWatcher(mtilPassword, getActivity().getApplicationContext()));


        view.findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            //OnClick event listener for the Login Button
            public void onClick(View view) {
                mEmail = mtilEmail.getEditText().getText().toString().trim();
                mPassword = mtilPassword.getEditText().getText().toString().trim();
                mForename = mtilForename.getEditText().getText().toString().trim();
                mSurname = mtilSurname.getEditText().getText().toString().trim();

                if (validateForm()) {
                    createAccount();
                }
            }
        });
    }

    private boolean validateForm() {
        boolean misValid = true;

        if (isNullOrEmpty(mForename)) {
            mtilForename.setError("Forename is required.");
            misValid = false;
        } else {
            mtilForename.setErrorEnabled(false);
        }

        if (isNullOrEmpty(mSurname)) {
            mtilSurname.setError("Surname is required.");
            misValid = false;
        } else {
            mtilEmail.setError(null);
        }

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


    private void createAccount() {
        mfbAuth.fetchSignInMethodsForEmail(mEmail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                    if (task.getResult().getSignInMethods().size() != 0){
                        // email already exists in firebase auth
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "This email address is already in use!", Snackbar.LENGTH_LONG).show();
                    } else {
                        mfbAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser currentUser = mfbAuth.getCurrentUser();

                                        User mUser = new User(mForename, mSurname, mEmail, new Timestamp(new Date()), currentUser.getUid());

                                        mfbDatabase.collection("users").add(mUser)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Account Successfully Created!", Snackbar.LENGTH_LONG).show();
                                                        ((NTUFood)getActivity()).setCurrentUser(mUser);

                                                        ((NTUFood)getActivity()).replaceFragment(new fragCustHome(),false);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        mfbAuth.getCurrentUser().delete();
                                                        ((NTUFood)getActivity()).setCurrentUser(null);
                                                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Unable to create account at this time. Please try later.", Snackbar.LENGTH_LONG).show();
                                                    }
                                                });

                                    } else {
                                        // If sign in fails, display a message to the user.

                                    }

                                }
                            });
                    }

                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });
        }

}
