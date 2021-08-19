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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GeneratorFragment extends Fragment {
    private ImageView qrImage;
    private String qrvalue;
    protected Integer userId;
    private String token;
    private Button scanButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View v = inflater.inflate(R.layout.generator_fragment, container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        token = prefs.getString("token", null);
        scanButton = v.findViewById(R.id.scanButton);
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

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequestEigenData);

        return v;
    }

}
