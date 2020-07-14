package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mynotes.helper.SharedPreferenceHelper;
import com.example.mynotes.helper.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText emailET, passwordET;
    Button loginBtn;
    TextView newUserLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        newUserLabel = findViewById(R.id.newUser);
        loginBtn = findViewById(R.id.loginButton);
        emailET = findViewById(R.id.emailEditText);
        passwordET = findViewById(R.id.passwordEditText);

        newUserLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailET.getText().length()>0 && passwordET.getText().length()>0)
                {
                    attemptLogin();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Both fields Required.",Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void attemptLogin() {
        String url = "http://alllinks.online/andproject/loginUser.php?email="+emailET.getText().toString()+"&password="+passwordET.getText().toString();
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loggin In...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.hide();
                        try {
                            if(response.getString("status").equals("success"))
                            {
//                                JSONObject responseJson = new JSONObject(response.toString());
//                                JSONObject pollution = responseJson.getJSONObject("data").getJSONObject("current").getJSONObject("pollution");
//                                JSONObject weather = responseJson.getJSONObject("data").getJSONObject("current").getJSONObject("weather");

                                Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_LONG).show();
                                SharedPreferenceHelper.setSharedPreferenceString(getApplicationContext(),"username",emailET.getText().toString());
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Sorry, Some error occured",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Sorry, Some error occured",Toast.LENGTH_LONG).show();
                // hide the progress dialog
                pDialog.hide();
            }
        });

        // Adding request to request queue
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjReq);
    }
}
