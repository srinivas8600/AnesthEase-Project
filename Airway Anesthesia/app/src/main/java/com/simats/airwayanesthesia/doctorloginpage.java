package com.simats.airwayanesthesia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.InputType;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
public class doctorloginpage extends AppCompatActivity {
    Button btn;
    private EditText eid, epassword;
    private String username, password;
    private String URL = ip.ipn+"dlogin.php";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorloginpage);

        eid = findViewById(R.id.e2);
        epassword = findViewById(R.id.p2);
        epassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        // Inside onCreate method after initializing epassword EditText
        // Inside your activity's onCreate method
        EditText passwordEditText = findViewById(R.id.p2);
        final boolean[] isPasswordVisible = {false};

// Set the OnTouchListener to detect clicks on the drawable
        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Check if the event is an ACTION_UP (finger lifted from screen)
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Drawable[] drawables = passwordEditText.getCompoundDrawables();

                    if (drawables[2] != null) { // Check if the right drawable exists
                        // Get the width of the right drawable
                        int drawableWidth = drawables[2].getBounds().width();

                        // If the touch occurred within the bounds of the drawable
                        if (event.getX() >= (passwordEditText.getWidth() - passwordEditText.getPaddingRight() - drawableWidth)) {
                            // Toggle password visibility
                            isPasswordVisible[0] = !isPasswordVisible[0];

                            // Get the current cursor position
                            int cursorPosition = passwordEditText.getSelectionStart();

                            if (isPasswordVisible[0]) {
                                passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            } else {
                                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            }

                            // Restore the cursor position
                            passwordEditText.setSelection(cursorPosition);

                            return true; // Indicates that the event was handled
                        }
                    }
                }

                return false; // Indicates that the event wasn't handled, allowing default behavior
            }
        });


        btn = findViewById(R.id.btn1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = eid.getText().toString();
                password = epassword.getText().toString();
                if (!username.isEmpty() && !password.isEmpty()) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Handle the response

                                    handleResponse(response);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            handleError(error);
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            // Send the username and password as POST parameters
                            Map<String, String> data = new HashMap<>();
                            data.put("username", username);
                            data.put("password", password);
                            return data;
                        }
                    };

                    // Customize the retry policy
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    // Initialize the Volley request queue and add the request
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(doctorloginpage.this, "Fields cannot be empty1111", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Handle the JSON response
    private void handleResponse(String response) {
        Gson gson = new Gson();
        Log.d("JSON Response", response);
        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);

        String status = jsonObject.get("status").getAsString();
        Log.d("JSON Response", status);

        if ("success".equals(status)) {
            Intent intent = new Intent(doctorloginpage.this,docdashboard.class);
            Log.d("mes",username);
            // After receiving userId from server and before navigating to docdashboard
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("id", username);
            editor.apply();

            startActivity(intent);

        } else if ("failure".equals(status)) {
            Toast.makeText(doctorloginpage.this, "Invalid login", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle network request errors
    private void handleError(VolleyError error) {
        if (error instanceof TimeoutError) {
            Toast.makeText(doctorloginpage.this, "Request timed out. Check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(doctorloginpage.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
        }
    }
}