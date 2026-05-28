package com.simats.airwayanesthesia;

import android.content.Intent;
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

public class pprofile extends AppCompatActivity {

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private TextView textView7;
    private ImageView img;
    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pprofile);

        img = findViewById(R.id.img1);
        textView1 = findViewById(R.id.tx1);
        textView2 = findViewById(R.id.tx2);
        textView3 = findViewById(R.id.tx3);
        textView4 = findViewById(R.id.tx4);
        textView5 = findViewById(R.id.tx5);
        textView6 = findViewById(R.id.tx6);
        textView7 = findViewById(R.id.tx7);

        // Get pid from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pid = extras.getString("pid");
        }

        // Make HTTP request to fetch patient profile data
        fetchStringFromPHP();

        // Initialize and handle click for "Edit Patient" button
        Button editButton = findViewById(R.id.btn);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to editpatient activity
                Intent intent = new Intent(pprofile.this, editpatient.class);
                intent.putExtra("id", pid);
                startActivity(intent);
            }
        });
    }


    private void fetchStringFromPHP() {
        String url = ip.ipn+"pprofile.php";

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override

            public void onResponse(String response) {
                try {
                    Gson gson = new Gson();
                    Log.d("JSON Response", response);
                    JsonObject jsonObject = gson.fromJson(response, JsonObject.class);

                    if (jsonObject.has("tx1")) {
                        final String status = jsonObject.get("tx1").getAsString();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView1.setText(status);

                                // Print patient ID for debugging
                                Log.d("Patient ID", "Patient ID: " + status);
                            }
                        });

                        // Update other TextViews similarly...
                        if (jsonObject.has("tx2")) {
                            final String tx2 = jsonObject.get("tx2").getAsString();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView2.setText(tx2);
                                }
                            });
                        }
                        if (jsonObject.has("tx3")) {
                            final String tx3 = jsonObject.get("tx3").getAsString();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView3.setText(tx3);
                                }
                            });
                        }
                        if (jsonObject.has("tx4")) {
                            final String tx4 = jsonObject.get("tx4").getAsString();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView4.setText(tx4);
                                }
                            });
                        }
                        if (jsonObject.has("tx5")) {
                            final String tx5 = jsonObject.get("tx5").getAsString();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView5.setText(tx5);
                                }
                            });
                        }
                        if (jsonObject.has("tx6")) {
                            final String tx6 = jsonObject.get("tx6").getAsString();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView6.setText(tx6);
                                }
                            });
                        }
                        if (jsonObject.has("tx7")) {
                            final String tx7 = jsonObject.get("tx7").getAsString();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView7.setText(tx7);
                                }
                            });
                        }

                        // Repeat for other text views...

                        if (jsonObject.has("img1")) {
                            final String imageUrl = jsonObject.get("img1").getAsString();
                            final String completeImageUrl = ip.ipn + imageUrl;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Picasso.get().load(completeImageUrl).resize(500, 500).into(img);
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView1.setText("D_name not found in JSON response");
                            }
                        });
                    }
                } catch (Exception e) {
                    final String errorMessage = "Error: " + e.getMessage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView1.setText(errorMessage);
                        }
                    });
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final String errorMessage = "Error: " + error.getMessage();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView1.setText(errorMessage);
                    }
                });
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                Log.d("giving", pid);
                data.put("id", pid);
                return data;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
