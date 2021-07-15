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

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername;
    EditText edtPassword;
    Button btnLogin;
    Button btnRegister;
    String token;
    final String BASE_URL = "https://polar-anchorage-54627.herokuapp.com/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        token = prefs.getString("token", null);

        if (token != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                //validate form
                if(validateLogin(username, password)){
                    //do login
                    doLogin(username, password);
                }
            }
        });

        RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
    }

    private boolean validateLogin(String username, String password){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Email moet worden ingevuld", Toast.LENGTH_LONG).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Wachtwoord moet worden ingevuld", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void doLogin(final String username,final String password){
        String URL = BASE_URL + "login?email=" + username + "&password=" + password;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("token", response.get("token").toString());
                    if (response.get("success").toString().equals("true")) {
                        Toast.makeText(LoginActivity.this, "Je bent ingelogd", Toast.LENGTH_LONG).show();
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        prefs.edit().putString("token", response.get("token").toString()).commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    Toast.makeText(LoginActivity.this, "Het wachtwoord of emailadres is verkeerd", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Er is wat misgegaan :( Weet je zeker dat je internet hebt?", Toast.LENGTH_SHORT).show();
                    }
                });
        VolleySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}