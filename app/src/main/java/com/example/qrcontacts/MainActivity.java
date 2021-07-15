package com.example.qrcontacts;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    final String BASE_URL = "https://polar-anchorage-54627.herokuapp.com/api/";
    String token;
    ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = prefs.getString("token", null);
        contactViewModel = ViewModelProviders.of(MainActivity.this).get(ContactViewModel.class);


        //Initialize Bottom Navigation View.
        BottomNavigationView navView = findViewById(R.id.bottomNav_view);
        //Pass the ID's of Different destinations
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_scan, R.id.navigation_contacts  )
                .build();

        //Initialize NavController.
        navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        Log.d("token voor request", token);
        String URL = BASE_URL + "getcontacts?token=" + token;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.get("success").toString().equals("true")) {
                        JSONArray contactArray = new JSONArray();
                        contactArray = (JSONArray) response.get("data");

                        for(int i=0; i < contactArray.length(); i++){
                            JSONObject contact = new JSONObject();
                            contact = contactArray.getJSONObject(i);
                            Contact newContact = new Contact(
                                    contact.getInt("id"),
                                    contact.getString("name"),
                                    contact.getString("public_email"),
                                    contact.getString("telefoonnummer"),
                                    contact.getString("twitter"),
                                    contact.getString("facebook"),
                                    contact.getString("snapchat"),
                                    contact.getString("instagram"),
                                    contact.getString("linkedin"),
                                    contact.getString("tiktok"),
                                    contact.getString("geboortedatum")
                            );

                            contactViewModel.insert(newContact);
                        }

                    }
                } catch (JSONException e) {
                    Log.d("errorJsonMainActivity", e.getMessage());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("errorVolleyMainActivity", error.toString());
                    }
                });
        VolleySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }




}
