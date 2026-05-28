package com.simats.airwayanesthesia;

import android.annotation.SuppressLint;
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

public class Aprofile extends AppCompatActivity {

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private ImageView img;
    private Button btn;
    public void onBackPressed() {
        // Navigate to the docdash page when the back button is pressed
        super.onBackPressed();
        Intent intent = new Intent(Aprofile.this, admindashboard.class);
        startActivity(intent);
        finish(); // Optionally finish the current activity
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprofile);

        textView1 = findViewById(R.id.tx1);
        textView2 = findViewById(R.id.tx2);
        textView3 = findViewById(R.id.tx3);
        textView4 = findViewById(R.id.tx4);
        textView5 = findViewById(R.id.tx5);
        img = findViewById(R.id.img);

        // Make an HTTP request to your PHP script
        fetchStringFromPHP();

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Aprofile.this, AprofileE.class);
                intent.putExtra("id", "saveetha123");
                startActivity(intent);
            }
        });
    }

    private void fetchStringFromPHP() {
        String value = "saveetha123"; // Set the constant value here
        String url = ip.ipn+"aprofile.php"; // Replace with your PHP script's URL

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Gson gson = new Gson();
                    Log.d("JSON Response", response);
                    JsonObject jsonObject = gson.fromJson(response, JsonObject.class);

                    if (jsonObject.has("id")) {
                        String status = jsonObject.get("name").getAsString();
                        textView2.setText(status);

                        status = jsonObject.get("phno").getAsString();
                        textView3.setText(status);

                        status = jsonObject.get("id").getAsString();
                        textView1.setText(status);

                        status = jsonObject.get("gender").getAsString();
                        textView4.setText(status);

                        status = jsonObject.get("email").getAsString();
                        textView5.setText(status);

                    }

                    if (jsonObject.has("img")) {
                        final String imageUrl = jsonObject.get("img").getAsString();
                        final String completeImageUrl = ip.ipn+imageUrl;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Picasso.get().load(completeImageUrl).resize(500, 500).into(img);
                            }
                        });
                    }else {
                        textView1.setText("id not found in JSON response");
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("P_id", value);
                return data;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
