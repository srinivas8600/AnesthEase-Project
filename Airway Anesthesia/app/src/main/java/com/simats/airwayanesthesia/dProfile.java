package com.simats.airwayanesthesia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class dProfile extends AppCompatActivity {

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5,textView8;
    private ImageView img;

    private Button btn;
    private String userId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dprofile);

        // Retrieve user ID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("id", "");

        textView1= findViewById(R.id.tx1);
        textView2=findViewById(R.id.tx2);
        textView3=findViewById(R.id.tx3);
        textView4=findViewById(R.id.tx4);
        textView5=findViewById(R.id.tx5);
        textView8=findViewById(R.id.tx8);
        img = findViewById(R.id.img33);

        // Make an HTTP request to your PHP script
        fetchStringFromPHP();

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dProfile.this,dProfileE.class);
                intent.putExtra("id", userId);
                Log.d("mmm", userId);// Pass the stored ID to the next activity
                startActivity(intent);
            }
        });

    }

    private void fetchStringFromPHP() {
        String url = ip.ipn+"dprofile.php";

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Gson gson = new Gson();
                    Log.d("JSON Response", response);
                    JsonObject jsonObject = gson.fromJson(response, JsonObject.class);

                    if (jsonObject.has("did")) {
                        String status = jsonObject.get("did").getAsString();
                        textView1.setText(status);
                        status = jsonObject.get("name").getAsString();
                        textView2.setText(status);
                        status = jsonObject.get("speciality").getAsString();
                        textView3.setText(status);
                        status = jsonObject.get("gender").getAsString();
                        textView4.setText(status);
                        status = jsonObject.get("phno").getAsString();
                        textView5.setText(status);
                        status = jsonObject.get("pass").getAsString();
                        textView8.setText(status);

                    }
                    if (jsonObject.has("img")) {
                        final String imageUrl = jsonObject.get("img").getAsString();
                        final String completeImageUrl = ip.ipn + imageUrl;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Picasso.get().load(completeImageUrl).resize(500, 500).into(img);
                            }
                        });
                    }

                    else {
                        textView1.setText("D_name not found in JSON response");
                    }
                } catch (Exception e) {
                    textView1.setText("Error: " + e.getMessage());
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView1.setText("Error: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("id", userId); // Pass the stored ID to the PHP script
                return data;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
