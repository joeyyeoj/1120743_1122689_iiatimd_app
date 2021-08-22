package com.example.qrcontacts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GeneratorFragment extends Fragment {
    private ImageView qrImage;
    private String qrvalue;
    protected Integer userId;
    private String token;
    private Button scanButton;
    private TextView noInternet;

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View v = inflater.inflate(R.layout.generator_fragment, container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        token = prefs.getString("token", null);
        scanButton = v.findViewById(R.id.scanButton);
        noInternet = v.findViewById(R.id.noInternetText);
        String userUrl = "https://polar-anchorage-54627.herokuapp.com/api/get_user_info?token=" + token;

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navcontroller = Navigation.findNavController(v);
                navcontroller.navigate(R.id.navigation_scan);
            }
        });

        JsonObjectRequest jsonObjectRequestEigenData = new JsonObjectRequest(Request.Method.GET, userUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject userObject = new JSONObject();
                    userObject = response.getJSONObject("user");
                    userId = userObject.getInt("id");
                    qrvalue = "https://polar-anchorage-54627.herokuapp.com/api/get_user/" + userId;
                    qrImage = (ImageView)v.findViewById(R.id.qrImage);
                    QRGEncoder qrgEncoder = new QRGEncoder(qrvalue, null, QRGContents.Type.TEXT, 500);
                    Bitmap qrBits = qrgEncoder.getBitmap();
                    qrImage.setImageBitmap(qrBits);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Er is iets misgegaan met het ophalen van je data", Toast.LENGTH_LONG).show();
            }
        });

        if (internetConnectionAvailable(2000)) {
            VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequestEigenData);
            noInternet.setVisibility(View.INVISIBLE);
        } else {
            noInternet.setVisibility(View.VISIBLE);
        }

        return v;
    }

}
