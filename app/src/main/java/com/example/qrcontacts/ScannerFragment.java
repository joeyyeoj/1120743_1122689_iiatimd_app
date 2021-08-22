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

import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


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
    Button scanAgainButton;
    private String token;

    private boolean internetConnectionAvailable(int timeOut) {
        InetAddress inetAddress = null;
        try {
            Future<InetAddress> future = Executors.newSingleThreadExecutor().submit(new Callable<InetAddress>() {
                @Override
                public InetAddress call() {
                    try {
                        return InetAddress.getByName("google.com");
                    } catch (UnknownHostException e) {
                        return null;
                    }
                }
            });
            inetAddress = future.get(timeOut, TimeUnit.MILLISECONDS);
            future.cancel(true);
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        } catch (TimeoutException e) {
        }
        return inetAddress!=null && !inetAddress.equals("");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
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
        scanAgainButton = v.findViewById(R.id.scanButton);
        scanAgainButton.setVisibility(View.INVISIBLE);
        opslaanButton = v.findViewById(R.id.closeContactButton);

        scanAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navcontroller = Navigation.findNavController(v);
                navcontroller.navigate(R.id.navigation_scan);
            }
        });

        opslaanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactViewModel.insert(contact);
                scannedContact.setVisibility(View.INVISIBLE);
                String newContactUrl = "https://polar-anchorage-54627.herokuapp.com/api/addcontact?token=" + token + "&contactId=" + contact.getDatabaseId();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, newContactUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // do nothing.
                        } catch (Exception e) {
                            Log.d("Error", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.toString());
                    }
                });
                VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);

                NavController navcontroller = Navigation.findNavController(v);
                navcontroller.navigate(R.id.navigation_contacts);
            }
        });

        if (internetConnectionAvailable(2000)) {
            queue = Volley.newRequestQueue(v.getContext());
            IntentIntegrator integrator = IntentIntegrator.forSupportFragment(ScannerFragment.this);
            integrator.setOrientationLocked(false);
            integrator.setPrompt("Scan QR-code");
            integrator.setBeepEnabled(false);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            integrator.initiateScan();
            db = AppDatabase.getInstance(getActivity());
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            token = prefs.getString("token", null);
        } else {
            results.setText("Je kan geen QR-code scannen als je offline bent!");
            results.setVisibility(View.VISIBLE);
        }


        return v;
    }

    public String checkIfNull(String value) {
        if (value.equals("")) {
            return "-";
        } else {
            return value;
        }
    }

    public String twitterLink(String value) {
        if (value.equals("-")) {
            return value;
        } else {
            return "https://twitter.com/" + value + "/";
        }
    }

    public String linkedinLink(String value) {
        if (value.equals("-")) {
            return value;
        } else {
            return "https://linkedin.com/in/" + value + "/";
        }
    }

    public String instagramLink(String value) {
        if (value.equals("-")) {
            return value;
        } else {
            return "https://instagram.com/" + value + "/";
        }
    }

    public String snapchatLink(String value) {
        if (value.equals("-")) {
            return value;
        } else {
            return "https://snapchat.com/add/" + value + "/";
        }
    }

    public String facebookLink(String value) {
        if (value.equals("-")) {
            return value;
        } else {
            return "https://facebook.com/" + value + "/";
        }
    }

    public String tiktokLink(String value) {
        if (value.equals("-")) {
            return value;
        } else {
            return "https://tiktok.com/@" + value + "/";
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                results.setText("De Scanner is gestopt :(");
                results.setVisibility(View.VISIBLE);
                scanAgainButton.setVisibility(View.VISIBLE);
//                Toast.makeText(getContext(), "Gestopt :(", Toast.LENGTH_LONG).show();
            } else {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, result.getContents() + "?token=" + token, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(result.getContents().startsWith("https://polar-anchorage-54627.herokuapp.com/api/get_user/")){
                            try {
                                Log.d("scannen", String.valueOf(response.get("user")));
                                JSONObject info = response.getJSONObject("user");
                                Log.d("scannen", String.valueOf(info.get("name")));
                                int contactId = (int) info.get("id");
                                String contactNaam = checkIfNull(String.valueOf(info.get("name")));
                                String contactEmail = checkIfNull(String.valueOf(info.get("public_email")));
                                String contactTelefoonnummer = checkIfNull(String.valueOf(info.get("telefoonnummer")));
                                String contactTwitter = twitterLink(checkIfNull(String.valueOf(info.get("twitter"))));
                                String contactFacebook = facebookLink(checkIfNull(String.valueOf(info.get("facebook"))));
                                String contactSnapchat = snapchatLink(checkIfNull(String.valueOf(info.get("snapchat"))));
                                String contactInstagram = instagramLink(checkIfNull(String.valueOf(info.get("instagram"))));
                                String contactLinkedIn = linkedinLink(checkIfNull(String.valueOf(info.get("linkedin"))));
                                String contactTikTok = tiktokLink(checkIfNull(String.valueOf(info.get("tiktok"))));
                                String contactGeboortedatum = checkIfNull(String.valueOf(info.get("geboortedatum")));
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
                            results.setText("Oeps! Dit is geen geldige QR code :(");
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






