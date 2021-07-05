package com.example.qrcontacts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {
    Button delenbutton;
    Button opslaanButton;
    EditText naamInput;
    EditText telefoonInput;
    EditText emailInput;
    EditText twitterInput;
    EditText facebookInput;
    EditText snapchatInput;
    EditText instagramInput;
    EditText linkedinInput;
    EditText tiktokInput;
    EditText birthdayInput;
    String token;
    String userLoginEmail;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View v = inflater.inflate(R.layout.profile_fragment, container, false);
        naamInput = v.findViewById(R.id.naamInput);
        telefoonInput = v.findViewById(R.id.telefoonInput);
        emailInput = v.findViewById(R.id.emailInput);
        twitterInput = v.findViewById(R.id.twitterInput);
        facebookInput = v.findViewById(R.id.facebookInput);
        snapchatInput = v.findViewById(R.id.snapchatInput);
        instagramInput = v.findViewById(R.id.instagramInput);
        linkedinInput = v.findViewById(R.id.linkedInInput);
        tiktokInput = v.findViewById(R.id.tiktokInput);
        birthdayInput = v.findViewById(R.id.birthdayInput);
        delenbutton = v.findViewById(R.id.delenButton);
        opslaanButton = v.findViewById(R.id.opslaanButton);
        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvYXBpLWlpYXRtZC50eWNob3ZhbnZlZW4ubmxcL3B1YmxpY1wvYXBpXC9sb2dpbiIsImlhdCI6MTYyNTM4Nzk5MiwibmJmIjoxNjI1Mzg3OTkyLCJqdGkiOiJYMkRrQ0pRVFFLVHNpNzg0Iiwic3ViIjoxLCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0._WYYrRMGioucJv_RZ-ey6HCm7U6d8FRWvSeoWq6QtEY";

        delenbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navcontroller = Navigation.findNavController(v);
                navcontroller.navigate(R.id.qrcode_show);
            }
        });

        opslaanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!naamInput.getText().toString().matches("")){
                    String updateUserUrl = "https://api-iiatmd.tychovanveen.nl/public/api/update?token=" + token;
                    StringRequest jsonObjectRequestUpdate = new StringRequest(Request.Method.PUT, updateUserUrl,  new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.d("response", response.toString());
                                Toast.makeText(getContext(), "Opgeslagen!", Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d("foutje", e.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("volley", error.toString());
                            Toast.makeText(getContext(), "Er is iets misgegaan met het wegschrijven van je data =(", Toast.LENGTH_LONG).show();
                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams(){
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("email", userLoginEmail);
                            params.put("name", naamInput.getText().toString());
                            params.put("public_email", emailInput.getText().toString());
                            params.put("telefoonnummer", telefoonInput.getText().toString());
                            params.put("twitter", twitterInput.getText().toString());
                            params.put("facebook", facebookInput.getText().toString());
                            params.put("snapchat", snapchatInput.getText().toString());
                            params.put("instagram", instagramInput.getText().toString());
                            params.put("linkedin", linkedinInput.getText().toString());
                            params.put("tiktok", tiktokInput.getText().toString());
                            return params;
                        }
                    };
                    VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequestUpdate);
                    NavController navcontroller = Navigation.findNavController(v);
                    navcontroller.navigate(R.id.qrcode_show);
                }
                else{
                    Toast.makeText(getContext(), "Naam mag niet leeg zijn!", Toast.LENGTH_LONG).show();
                }
            }
        });


        getUserData();
        return v;
    }

    public void getUserData(){
        String userUrl = "https://api-iiatmd.tychovanveen.nl/public/api/get_user_info?token=" + token;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, userUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject userObject = new JSONObject();
                    userObject = response.getJSONObject("user");
                    userLoginEmail = userObject.getString("email");
                    naamInput.setText(userObject.getString("name"));
                    emailInput.setText(userObject.getString("public_email"));
                    telefoonInput.setText(userObject.getString("telefoonnummer"));
                    twitterInput.setText(userObject.getString("twitter"));
                    facebookInput.setText(userObject.getString("facebook"));
                    instagramInput.setText(userObject.getString("instagram"));
                    snapchatInput.setText(userObject.getString("snapchat"));
                    linkedinInput.setText(userObject.getString("linkedin"));
                    tiktokInput.setText(userObject.getString("tiktok"));
                    birthdayInput.setText(userObject.getString("geboortedatum"));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("exception123", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Er is iets misgegaan met het ophalen van je data", Toast.LENGTH_LONG).show();
            }
        });
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }

}
