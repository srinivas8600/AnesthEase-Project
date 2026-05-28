package com.simats.airwayanesthesia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class ScoreActivity extends AppCompatActivity {
    String value="";
    String doc;
    String url = ip.ipn+"score.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent1=getIntent();
        doc=intent1.getStringExtra("did");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Intent intent = getIntent();
        value =intent.getStringExtra("value");

        int total1 = getIntent().getIntExtra("total1", 20);
        int fTotal = getIntent().getIntExtra("fTotal", 30);
        int totalScore = getIntent().getIntExtra("totalScore", 50);

        TextView s1TextView = findViewById(R.id.s1);
        TextView s2TextView = findViewById(R.id.s2);
        TextView s12TextView = findViewById(R.id.s12);
        TextView s11TextView = findViewById(R.id.s11);
        Button b11Button = findViewById(R.id.b11);

        // Set the text for s1 and s2
        s1TextView.setText(String.valueOf(total1));
        s2TextView.setText(String.valueOf(totalScore));
        s11TextView.setText(String.valueOf(fTotal));

        // Set the text for s12 based on fTotal
        if (fTotal <20) {
            s12TextView.setText("Normal airway");
        } else if (fTotal >= 20 && fTotal <= 30) {
            s12TextView.setText("Likely to be difficult airway");
        } else if (fTotal > 30) {
            s12TextView.setText("Difficult airway");
        } else {
            s12TextView.setText("");
        }

        // Set click listener for the button
        b11Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform the Volley request to send data to the server
                Intent intent = new Intent(ScoreActivity.this, why.class);
                intent.putExtra("value", value);
                intent.putExtra("did",doc);
                sendDataToServer(
                        s1TextView.getText().toString(),
                        s2TextView.getText().toString(),
                        s11TextView.getText().toString(),
                        s12TextView.getText().toString()

                );
                startActivity(intent);
            }
        });
    }

    private void sendDataToServer(final String s1, final String s2, final String s11, final String s12) {
        // Specify your server URL
        String url = ip.ipn+"score.php"; // Replace with your actual server URL

        // Create a StringRequest to make a POST request using Volley
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the server response (if needed)
                        Log.d("ServerResponse", response);
                        Toast.makeText(ScoreActivity.this, response, Toast.LENGTH_SHORT).show();
                        // You might add navigation code here if needed
                        // Example: startActivity(new Intent(ScoreActivity.this, NextActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error
                        Log.e("VolleyError", "Error during request: " + error.toString());
                        Toast.makeText(ScoreActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Set the POST parameters
                Map<String, String> params = new HashMap<>();
                params.put("s1", s1);
                params.put("s2", s2);
                params.put("s11", s11);
                params.put("s12", s12);
                params.put("value", value);
                return params;
            }
        };

        // Add the request to the RequestQueue (Volley automatically handles the request queue for you)
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
