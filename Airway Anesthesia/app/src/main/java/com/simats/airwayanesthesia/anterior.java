package com.simats.airwayanesthesia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class anterior extends AppCompatActivity {
    String value = " ";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anterior);
        Intent intent = getIntent();
        value =intent.getStringExtra("value");
        // Replace with your layout file name

        Button doneButton = findViewById(R.id.bn12);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the button click event
                Intent intent = new Intent(anterior.this, danterior.class);
                intent.putExtra("value", value);
                startActivity(intent);
            }
        });
    }
}