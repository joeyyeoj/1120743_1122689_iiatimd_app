package com.example.qrcontacts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    EditText edtNaam;
    EditText edtUsername;
    EditText edtPassword;
    EditText edtPassword2;
    Button btnLogin;
    Button btnRegister;
    final String BASE_URL = "https://api-iiatmd.tychovanveen.nl/public/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtNaam = (EditText) findViewById(R.id.edtNaam);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPassword2 = (EditText) findViewById(R.id.edtPassword2);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String naam = edtNaam.getText().toString();
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String password2 = edtPassword2.getText().toString();
                //validate form
                if(validateLogin(naam, username, password, password2)){
                    //do login
                    doRegister(naam, username, password);
                }
            }
        });

        RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
    }

    private boolean validateLogin(String naam, String username, String password, String password2){
        if(username == null || naam.trim().length() == 0){
            Toast.makeText(this, "Naam moet worden ingevuld", Toast.LENGTH_LONG).show();
            return false;
        }
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Email moet worden ingevuld", Toast.LENGTH_LONG).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Wachtwoord moet worden ingevuld", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password2 == null || password.trim().length() == 0){
            Toast.makeText(this, "Herhaal je wachtwoord", Toast.LENGTH_LONG).show();
            return false;
        }
        if(password.trim().equals(password2.trim())){
            return true;
        }
        Toast.makeText(this, "De wachtwoorden komen niet overeen", Toast.LENGTH_LONG).show();
        return false;
    }

    private void doRegister(final String naam, final String username,final String password){
        String URL = BASE_URL + "register?name=" + naam + "&email=" + username + "&password=" + password;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.get("success").toString().equals("true")) {
                        Toast.makeText(RegisterActivity.this, "Je bent succesvol geregistreerd", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    Toast.makeText(RegisterActivity.this, "Registeren mislukt :(", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("response", error.getMessage());
                    }
                });
        VolleySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}