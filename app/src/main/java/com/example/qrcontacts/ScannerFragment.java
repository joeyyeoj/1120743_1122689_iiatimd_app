package com.example.qrcontacts;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;


public class ScannerFragment extends Fragment {
    IntentIntegrator qrScan;
    TextView results;
    RequestQueue queue;
    String url;
    AppDatabase db;
    ContactViewModel contactViewModel;

    public static final String EXTRA_CONTACT = "com.example.qrcontacts.EXTRA_CONTACT";

   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
       View v = inflater.inflate(R.layout.scanner_fragment, container, false);
       results = v.findViewById(R.id.qrResult);
       queue =  Volley.newRequestQueue(v.getContext());
       IntentIntegrator integrator = IntentIntegrator.forSupportFragment(ScannerFragment.this);
       integrator.setOrientationLocked(false);
       integrator.setPrompt("Scan QR code");
       integrator.setBeepEnabled(false);
       integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
       integrator.initiateScan();
       db = AppDatabase.getInstance(getActivity());
       return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getContext(), "Gestopt :(", Toast.LENGTH_LONG).show();
            } else {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, result.getContents(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            results.setText(response.get("title").toString());
                            contactViewModel = ViewModelProviders.of(getActivity()).get(ContactViewModel.class);
                            String responseText = response.get("title").toString();
                            Contact contact = new Contact(responseText, "", "", "", "", "", "", "", "", "", "", "", "", "");
                            contactViewModel.insert(contact);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        results.setText(error.toString());
                    }
                });
                VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
            }
        }
    }




    }






