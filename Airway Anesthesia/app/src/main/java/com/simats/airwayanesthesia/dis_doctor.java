package com.simats.airwayanesthesia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

public class dis_doctor extends AppCompatActivity {

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private ImageView img;
    Button button1;
    private String value="";

    // Declare pid at the class level
    private String pid;
//    public void onBackPressed() {
//        // Navigate to the docdash page when the back button is pressed
//        super.onBackPressed();
//        Intent intent = new Intent(dis_doctor.this, admindashboard.class);
//        startActivity(intent);
//        finish(); // Optionally finish the current activity
//    }

    ActivityResultLauncher<String> imagesPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean o) {
            Toast.makeText(dis_doctor.this, (o)? "Media Permission Granted" : "Media Permission Denied", Toast.LENGTH_SHORT).show();
        }
    });

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dis_doctor);

        img = findViewById(R.id.img33);
        textView1 = findViewById(R.id.tx1);
        textView2 = findViewById(R.id.tx2);
        textView3 = findViewById(R.id.tx3);
        textView4 = findViewById(R.id.tx4);
        textView5 = findViewById(R.id.tx5);
        textView6 = findViewById(R.id.tx6);
        button1=findViewById(R.id.button);

        // Get pid from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pid = extras.getString("item");
        }

        // Make an HTTP request to your PHP script
        fetchStringFromPHP();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Ed_doctor activity
                Intent intent = new Intent(dis_doctor.this, Ed_doctor.class);
                intent.putExtra("id", pid);
                startActivity(intent);
            }
        });
    }

    private void fetchStringFromPHP() {
        String url = ip.ipn+"printDocInfodummy.php";

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override

            public void onResponse(String response) {
                try {
                    Gson gson = new Gson();
                    Log.d("JSON Response", response);
                    JsonObject jsonObject = gson.fromJson(response, JsonObject.class);

                    if (jsonObject.has("did")) {
                        final String status = jsonObject.get("did").getAsString();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView1.setText(status);

                                // Print patient ID for debugging
                                Log.d("Patient ID", "Patient ID: " + status);
                            }
                        });

                        // Update other TextViews similarly...
                        if (jsonObject.has("name")) {
                            final String tx2 = jsonObject.get("name").getAsString();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView2.setText(tx2);
                                }
                            });
                        }
                        if (jsonObject.has("phno")) {
                            final String tx3 = jsonObject.get("phno").getAsString();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView3.setText(tx3);
                                }
                            });
                        }
                        if (jsonObject.has("pass")) {
                            final String tx4 = jsonObject.get("pass").getAsString();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView4.setText(tx4);
                                }
                            });
                        }
                        if (jsonObject.has("gender")) {
                            final String tx5 = jsonObject.get("gender").getAsString();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView5.setText(tx5);
                                }
                            });
                        }
                        if (jsonObject.has("speciality")) {
                            final String tx6 = jsonObject.get("speciality").getAsString();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView6.setText(tx6);
                                }
                            });
                        }

                        // Repeat for other text views...

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
