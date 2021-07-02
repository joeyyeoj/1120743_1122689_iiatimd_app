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
import androidx.cardview.widget.CardView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ScannerFragment extends Fragment {
    IntentIntegrator qrScan;
    TextView results;
    RequestQueue queue;
    AppDatabase db;
    ContactViewModel contactViewModel;
    CardView scannedContact;
    Contact contact;
    TextView naamValue;
    TextView emailValue;
    TextView telefoonValue;
    TextView twitterValue;
    TextView facebookValue;
    TextView snapchatValue;
    TextView instagramValue;
    TextView linkedinValue;
    TextView tiktokValue;
    TextView geboortedatumValue;
    TextView adresValue;
    TextView woonplaatsValue;
    TextView postcodeValue;
    TextView landValue;
    Button opslaanButton;


   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
       View v = inflater.inflate(R.layout.scanner_fragment, container, false);
       scannedContact = v.findViewById(R.id.scannedContact);
       scannedContact.setVisibility(View.INVISIBLE);
       naamValue = v.findViewById(R.id.naamValue);
       emailValue = v.findViewById(R.id.publiekeEmailValue);
       telefoonValue = v.findViewById(R.id.telefoonValue);
       twitterValue = v.findViewById(R.id.twitterValue);
       facebookValue = v.findViewById(R.id.facebookValue);
       snapchatValue = v.findViewById(R.id.snapchatValue);
       instagramValue = v.findViewById(R.id.instagramValue);
       linkedinValue = v.findViewById(R.id.linkedinValue);
       tiktokValue = v.findViewById(R.id.tiktokValue);
       geboortedatumValue = v.findViewById(R.id.geboortedatumValue);
       adresValue  = v.findViewById(R.id.adresValue);
       woonplaatsValue  = v.findViewById(R.id.woonplaatsValue);
       postcodeValue  = v.findViewById(R.id.postcodeValue);
       landValue  = v.findViewById(R.id.landValue);
       results = v.findViewById(R.id.qrResult);
       opslaanButton = v.findViewById(R.id.addContactButton);

       opslaanButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               contactViewModel.insert(contact);
           }
       });
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
                            JSONObject contactInfo = new JSONObject();
                            contactInfo = (JSONObject) response.get("contactInfo");
                            results.setText(contactInfo.getString("naam"));
                            String contactNaam = contactInfo.getString("naam");
                            String contactEmail = contactInfo.getString("publieke_email");
                            String contactTelefoonnummer = contactInfo.getString("telefoonnummer");
                            String contactTwitter = contactInfo.getString("twitter");
                            String contactFacebook = contactInfo.getString("facebook");
                            String contactSnapchat = contactInfo.getString("snapchat");
                            String contactInstagram = contactInfo.getString("instagram");
                            String contactLinkedIn = contactInfo.getString("linkedin");
                            String contactTikTok = contactInfo.getString("tiktok");
                            String contactGeboortedatum = contactInfo.getString("geboortedatum");
                            String contactAdres = contactInfo.getString("adres");
                            String contactWoonplaats = contactInfo.getString("woonplaats");
                            String contactPostcode = contactInfo.getString("postcode");
                            String contactLand = contactInfo.getString("land");
                            contactViewModel = ViewModelProviders.of(getActivity()).get(ContactViewModel.class);
                            contact = new Contact(contactNaam, contactEmail, contactTelefoonnummer, contactTwitter, contactFacebook, contactSnapchat, contactInstagram, contactLinkedIn, contactTikTok, contactGeboortedatum, contactAdres, contactWoonplaats, contactPostcode, contactLand);

                            naamValue.setText(contactNaam);
                            emailValue.setText(contactEmail);
                            telefoonValue.setText(contactTelefoonnummer);
                            twitterValue.setText(contactTwitter);
                            facebookValue.setText(contactFacebook);
                            snapchatValue.setText(contactSnapchat);
                            instagramValue.setText(contactInstagram);
                            linkedinValue.setText(contactLinkedIn);
                            tiktokValue.setText(contactTikTok);
                            geboortedatumValue.setText(contactGeboortedatum);
                            adresValue.setText(contactAdres);
                            woonplaatsValue.setText(contactWoonplaats);
                            postcodeValue.setText(contactPostcode);
                            landValue.setText(contactLand);
                            scannedContact.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            results.setText("Oepsie woepsie er is iets heul erg misgegaan :( Misschien kun je het opnieuw pwoberen?");
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






