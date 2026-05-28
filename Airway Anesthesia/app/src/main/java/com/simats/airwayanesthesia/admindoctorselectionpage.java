package com.simats.airwayanesthesia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class admindoctorselectionpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindoctorselectionpage);

        Button navigateButton = findViewById(R.id.button4);
        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdminLoginPage();
            }
        });

        Button navigateButton1 = findViewById(R.id.button6);
        navigateButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDoctorLoginPage();
            }
        });
    }

    private void openAdminLoginPage() {
        Intent intent = new Intent(this, adminloginpage.class);
        startActivity(intent);
    }

    private void openDoctorLoginPage() {
        Intent intent = new Intent(this, doctorloginpage.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        // Define the action to take when the back button is pressed
        // In this case, navigate to the previous activity (docdashboard)
        super.onBackPressed();
//        Intent intent = new Intent(admindoctorselectionpage.this, MainActivity.class);
//        intent.putExtra("id", getIntent().getStringExtra("id"));
//        startActivity(intent);
//        finish(); // Optional: finish the current activity to prevent coming back to it by pressing back again
        finishAffinity();
    }
}
