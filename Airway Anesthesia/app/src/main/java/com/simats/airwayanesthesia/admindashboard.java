package com.simats.airwayanesthesia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;


public class admindashboard extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Button btnAddPatient;
    private Button button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindashboard);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav);
        toolbar = findViewById(R.id.tool_bar1);
        btnAddPatient = findViewById(R.id.e1);
        button = findViewById(R.id.p1);


        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.profile) {
                // Handle "Profile" item click
                Intent intent = new Intent(admindashboard.this, Aprofile.class);
                startActivity(intent);
            } else if (id == R.id.logout) {
                Intent intent = new Intent(admindashboard.this, admindoctorselectionpage.class);
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
                    // If drawer is not open, navigate to admindDoctorSelection
                    navigateToDoctorSelection();
                }
            }
        };

        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

    }


    private void navigateToAddDoctor() {
        Intent intent = new Intent(this, adddoctor.class);
        startActivity(intent);
    }

    private void navigateToDoctorList() {
        Intent intent = new Intent(this,dSearch.class);
        startActivity(intent);

    }
    private void navigateToDoctorSelection() {
//        Intent intent = new Intent(this, admindoctorselectionpage.class);
//        startActivity(intent);
//        finish(); // Finish the current activity
        finishAffinity();
    }

}
