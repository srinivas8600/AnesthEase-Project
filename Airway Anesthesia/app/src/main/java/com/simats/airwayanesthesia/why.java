package com.simats.airwayanesthesia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class why extends AppCompatActivity {
    String value = " ";
    String doc;
    String url = ip.ipn+"why.php";
    private TextView w1, w2, w3, w4, w5, w6, w7, w8, w9, w10, w11, w12, w13, w14, w15, w16, w17, w18, w19;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent1=getIntent();
        doc=intent1.getStringExtra("did");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_why);
        Intent intent = getIntent();
        value =intent.getStringExtra("value");

        // Initialize your TextViews
        w1 = findViewById(R.id.w1);
        w2 = findViewById(R.id.w2);
        w3 = findViewById(R.id.w3);
        w4 = findViewById(R.id.w4);
        w5 = findViewById(R.id.w5);
        w6 = findViewById(R.id.w6);
        w7 = findViewById(R.id.w7);
        w8 = findViewById(R.id.w8);
        w9 = findViewById(R.id.w9);
        w10 = findViewById(R.id.w10);
        w11 = findViewById(R.id.w11);
        w12 = findViewById(R.id.w12);
        w13 = findViewById(R.id.w13);
        w14 = findViewById(R.id.w14);
        w15 = findViewById(R.id.w15);
        w16 = findViewById(R.id.w16);
        w17 = findViewById(R.id.w17);
        w18 = findViewById(R.id.w18);
        w19 = findViewById(R.id.w19);

        // Call the method to send the value to PHP
        fetchData(value);
        Button btn = findViewById(R.id.btn11);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(why.this,primarycurve.class);
                intent.putExtra("did",doc);
                startActivity(intent);
            }
        });
        Button btn1 = findViewById(R.id.btn12);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(why.this,secondarycurve.class);
                intent.putExtra("did",doc);
                startActivity(intent);
            }
        });
        Button btn2 = findViewById(R.id.btn13);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(why.this,anterior.class);
                intent.putExtra("value", value);
                intent.putExtra("did",doc);
                startActivity(intent);
            }
        });
        Button btn3 = findViewById(R.id.btn14);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(why.this,middle.class);
                intent.putExtra("value", value);
                intent.putExtra("did",doc);
                startActivity(intent);
            }
        });
        Button btn4 = findViewById(R.id.btn15);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(why.this,posterior.class);
                intent.putExtra("value", value);
                intent.putExtra("did",doc);
                startActivity(intent);
            }
        });

    }

    private void fetchData(final String value) {
        // Your PHP script URL
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            w1.setText(jsonObject.optString("w1"));
                            w2.setText(jsonObject.optString("w2"));
                            w3.setText(jsonObject.optString("w3"));
                            w4.setText(jsonObject.optString("w4"));
                            w5.setText(jsonObject.optString("w5"));
                            w6.setText(jsonObject.optString("w6"));
                            w7.setText(jsonObject.optString("w7"));
                            w8.setText(jsonObject.optString("w8"));
                            w9.setText(jsonObject.optString("w9"));
                            w10.setText(jsonObject.optString("w10"));
                            w11.setText(jsonObject.optString("w11"));
                            w12.setText(jsonObject.optString("w12"));
                            w13.setText(jsonObject.optString("w13"));
                            w14.setText(jsonObject.optString("w14"));
                            w15.setText(jsonObject.optString("w15"));
                            w16.setText(jsonObject.optString("w16"));
                            w17.setText(jsonObject.optString("w17"));
                            w18.setText(jsonObject.optString("w18"));
                            w19.setText(jsonObject.optString("w19"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error fetching data: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("value", value);  // Change the parameter name to match the PHP script
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}
