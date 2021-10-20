package com.barry.ntufood;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class fragCamera extends Fragment {
    private static final int QR_CODE_LENGTH = 3;
    private static final int PERMISSION_REQUEST_CAMERA = 0;
    private Button mbtnTryAgain;
    private TextView mtvMessage;

    public fragCamera() {
        // Required empty public constructor
    }

    public static fragCamera newInstance() {
        fragCamera fragment = new fragCamera();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mbtnTryAgain = view.findViewById(R.id.btnScanCode);
        mtvMessage = view.findViewById(R.id.tvMessage);
        mtvMessage.setText(getContext().getString(R.string.QR_Message));
        //requestCamera();

        mbtnTryAgain.setOnClickListener(new View.OnClickListener() {
            //OnClick event listener for the Login Button
            public void onClick(View view) {
                requestCamera();
            }
        });
    }

    private void requestCamera() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Snackbar.make(getActivity().findViewById(android.R.id.content), getContext().getString(R.string.Camera_denied), Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void startCamera() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);

        integrator.setOrientationLocked(false);
        integrator.setPrompt(getContext().getString(R.string.QR_Integrator));
        integrator.setBeepEnabled(true);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);

        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Snackbar.make(getActivity().findViewById(android.R.id.content), getContext().getString(R.string.Camera_cancelled), Snackbar.LENGTH_LONG).show();
            } else {
                String[] resultArray = result.getContents().split("\\|");
                if (resultArray.length == QR_CODE_LENGTH && resultArray[0].equalsIgnoreCase("NTUFood")) {
                    NTUFood ntuFood = (NTUFood) getContext();
                    ntuFood.resetOrder();
                    ntuFood.setCurrentOutlet(resultArray[1]);
                    ntuFood.setCurrentTable(resultArray[2]);
                    ntuFood.replaceFragment(new fragOrderMenu(), false);
                } else {
                    AlertDialog.Builder errorMessage = new AlertDialog.Builder(getContext());
                    errorMessage.setTitle("Information");
                    errorMessage.setMessage(getContext().getString(R.string.QR_not_valid));
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
        }
    }



}