package com.example.qrcontacts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
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
    Button opslaanButton;
    private String token;


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
       results = v.findViewById(R.id.qrResult);
       results.setVisibility(View.INVISIBLE);
       opslaanButton = v.findViewById(R.id.closeContactButton);
       opslaanButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               contactViewModel.insert(contact);
               scannedContact.setVisibility(View.INVISIBLE);
               NavController navcontroller = Navigation.findNavController(v);
               navcontroller.navigate(R.id.navigation_contacts);
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
       SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
       token = prefs.getString("token", null);

       return v;
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                results.setText("Scanner gestopt :(");
                results.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Gestopt :(", Toast.LENGTH_LONG).show();
            } else {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, result.getContents() + "?token=" + token, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(result.getContents().startsWith("https://polar-anchorage-54627.herokuapp.com/api/get_user/")){
                            try {
                                Log.d("scannen", String.valueOf(response.get("user")));
                                JSONObject info = response.getJSONObject("user");
                                Log.d("scannen", String.valueOf(info.get("name")));
//                                JSONObject contactResponse = new JSONObject();
//                                contactResponse = response;
//                                JSONArray userInfoArray = new JSONArray();
//                                userInfoArray = (JSONArray) response.get("user");
//                                JSONObject contactObject = new JSONObject();
//                                contactObject = (JSONObject) userInfoArray.get(0);
                                int contactId = (int) info.get("id");
                                String contactNaam = String.valueOf(info.get("name"));
                                String contactEmail = String.valueOf(info.get("public_email"));
                                String contactTelefoonnummer = String.valueOf(info.get("telefoonnummer"));
                                String contactTwitter = String.valueOf(info.get("twitter"));
                                String contactFacebook = String.valueOf(info.get("facebook"));
                                String contactSnapchat = String.valueOf(info.get("snapchat"));
                                String contactInstagram = String.valueOf(info.get("instagram"));
                                String contactLinkedIn = String.valueOf(info.get("linkedin"));
                                String contactTikTok = String.valueOf(info.get("tiktok"));
                                String contactGeboortedatum = String.valueOf(info.get("geboortedatum"));
                                contactViewModel = ViewModelProviders.of(getActivity()).get(ContactViewModel.class);
                                contact = new Contact(contactId , contactNaam, contactEmail, contactTelefoonnummer, contactTwitter, contactFacebook, contactSnapchat, contactInstagram, contactLinkedIn, contactTikTok, contactGeboortedatum);//, contactAdres, contactWoonplaats, contactPostcode, contactLand);
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
                                scannedContact.setVisibility(View.VISIBLE);

                            } catch (Exception e) {
                                e.printStackTrace();
                                results.setText(e.toString());
                                results.setVisibility(View.VISIBLE);
                            }
                        }
                        else{
                            results.setText("Oeps! Dat is geen geldige QR code :(");
                            results.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        results.setText(error.getMessage());
                        results.setVisibility(View.VISIBLE);
                    }
                });
                VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
            }
        }
    }




    }






