package com.simats.airwayanesthesia;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class middle extends AppCompatActivity {
    String value = " ";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle);
        Intent intent = getIntent();
        value =intent.getStringExtra("value");
        // Replace with your layout file name

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button doneButton = findViewById(R.id.bn14);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the button click event
                Intent intent = new Intent(middle.this, dmiddle.class);
                intent.putExtra("value", value);
                startActivity(intent);
            }
        });
    }
}