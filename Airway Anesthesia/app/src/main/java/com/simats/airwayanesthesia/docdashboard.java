package com.simats.airwayanesthesia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class docdashboard extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    RecyclerView recyclerView;
    private CustomAdapter adapter;
    private List<PatientInfo1> dataList;
    private List<PatientInfo1> filteredList;
    private Button btnAddPatient;
    private Button button;
    String url = ip.ipn+"docdash.php";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docdashboard);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav);
        toolbar = findViewById(R.id.tool_bar1);
        setSupportActionBar(toolbar);
        fetchfromPHP();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        btnAddPatient = findViewById(R.id.sem1);
        button = findViewById(R.id.ap1);
        recyclerView = findViewById(R.id.recyclerViewMain);
        dataList = new ArrayList<>();
        filteredList = new ArrayList<>(dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomAdapter(filteredList);
        recyclerView.setAdapter(adapter);



        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.profile) {
                // Handle "Profile" item click
                Intent intent = new Intent(docdashboard.this, dProfile.class);

// Retrieve userId from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String userId = sharedPreferences.getString("id", "");

// Pass userId to the dProfile activity
                intent.putExtra("id", userId);
                startActivity(intent);

            } else if (id == R.id.logout) {
                Intent intent = new Intent(docdashboard.this, admindoctorselectionpage.class);
                startActivity(intent);
                finish();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Button click to navigate to Add Patient page
        btnAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToAddDoctor();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToDoctorList();
            }
        });

        // New approach to handle onBackPressed
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    // Your custom back button logic goes here
                    // For example, you can call super.onBackPressed() if needed
//                    docdashboard.super.onBackPressed();
//                    finishAffinity();
                }
            }
        };

        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }


    private void navigateToAddDoctor() {
        Intent intent = new Intent(this, searchp.class);
        startActivity(intent);
    }

    private void navigateToDoctorList() {
        Intent intent1 = new Intent(this, addPatient.class);
        String value = intent1.getStringExtra("id");
        intent1.putExtra("id",value);
        startActivity(intent1);
    }
    private void fetchfromPHP() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("sf","res"+response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String status = jsonResponse.optString("status");

                            if ("success".equals(status)) {
                                JSONArray patientsArray = jsonResponse.getJSONArray("data");

                                dataList.clear();
                                for (int i = 0; i < patientsArray.length(); i++) {
                                    JSONObject patientObject = patientsArray.getJSONObject(i);

                                    String id = patientObject.optString("pid");
                                    String name = patientObject.optString("name");
                                    String gender = patientObject.optString("gender");
                                    String phno = patientObject.optString("phno");
                                    String profilePhoto = patientObject.optString("img");

                                    P1 p1 = new P1(id, name, gender, phno, profilePhoto);
                                    dataList.add(p1.toPatientInfo());
                                }

                                filter("");
                            }  else {
                                Toast.makeText(docdashboard.this, "Error: Server returned failure status", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(docdashboard.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleError(error);
            }
        });

        queue.add(stringRequest);
    }

    // ... (other existing code)

    private void handleError(VolleyError error) {
        if (error instanceof TimeoutError) {
            Toast.makeText(this, "Request timed out. Check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void filter(String text) {
        filteredList.clear();
        if (text.isEmpty()) {
            filteredList.addAll(dataList);
        } else {
            text = text.toLowerCase().trim();
            for (PatientInfo1 item : dataList) {
                if (item.getId() != null && item.getName() != null && item.getGender() != null && item.getPhno() != null) {
                    if (item.getId().toLowerCase().contains(text)
                            || item.getName().toLowerCase().contains(text)
                            || item.getGender().toLowerCase().contains(text)
                            || item.getPhno().toLowerCase().contains(text)) {
                        filteredList.add(item);
                    }
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private List<PatientInfo1> dataList;

        public CustomAdapter(List<PatientInfo1> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview1, parent, false);
            return new CustomAdapter.ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {



            Intent intent1 = getIntent();

            String value = intent1.getStringExtra("id");
            PatientInfo1 patient = dataList.get(position);

            if (holder.idTextView != null) {
                holder.idTextView.setText("Id               : " + (patient.getId() != null ? patient.getId() : ""));
            }
            if (holder.nameTextView != null) {
                holder.nameTextView.setText("Name        : " + (patient.getName() != null ? patient.getName() : ""));
            }
            if (holder.genderTextView != null) {
                holder.genderTextView.setText("Gender     : " + (patient.getGender() != null ? patient.getGender() : ""));
            }
            if (holder.phnoTextView != null) {
                holder.phnoTextView.setText("Phno         : " + (patient.getPhno() != null ? patient.getPhno() : ""));
            }

            if (holder.profileImageView != null && patient.getProfilePhoto() != null
                    && !patient.getProfilePhoto().isEmpty()) {
                String completeImageUrl = ip.ipn+ patient.getProfilePhoto();
                Picasso.get().load(completeImageUrl).into(holder.profileImageView);
            } else {
                holder.profileImageView.setImageResource(R.drawable.ellipsep);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selectedItem = patient.getId() != null ? patient.getId() : "";

                    Intent intent = new Intent(docdashboard.this,pprofile.class);
                    intent.putExtra("pid", selectedItem);
                    String value = intent.getStringExtra(" id");
                    intent.putExtra("id",value);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView idTextView, nameTextView, genderTextView, phnoTextView;
            ImageView profileImageView;

            public ViewHolder(View itemView) {
                super(itemView);
                idTextView = itemView.findViewById(R.id.id);
                nameTextView = itemView.findViewById(R.id.name);
                genderTextView = itemView.findViewById(R.id.gender);
                phnoTextView = itemView.findViewById(R.id.phno);
                profileImageView = itemView.findViewById(R.id.profile);
            }
        }
    }
    @Override
    public void onBackPressed() {
        // Define the action to take when the back button is pressed
        // In this case, navigate to the previous activity (docdashboard)
        super.onBackPressed();
//        Intent intent = new Intent(docdashboard.this, admindoctorselectionpage.class);
//        intent.putExtra("id", getIntent().getStringExtra("id"));
//        startActivity(intent);
//        finish(); // Optional: finish the current activity to prevent coming back to it by pressing back again

        finishAffinity();
    }


}