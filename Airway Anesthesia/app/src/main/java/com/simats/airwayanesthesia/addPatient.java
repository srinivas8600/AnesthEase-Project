package com.simats.airwayanesthesia;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Locale;

public class addPatient extends AppCompatActivity implements TextWatcher {
    private Spinner genderSpinner;
    EditText pid, name, phno, age, height, weight, dob;
    TextView bmi;
    String value, name1, phno1, gender1, age1, height1, weight1, bmi1, dob1,doc;
    Button save;
    ImageView profile;
    FragmentActivity activity;
    Context context;

    ActivityResultLauncher<String> cameraPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean o) {
            Toast.makeText(activity,(o)? "Camera Permission Granted" : "Camera Permission Denied", Toast.LENGTH_SHORT).show();
        }
    });

    ActivityResultLauncher<String> imagesPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean o) {
            Toast.makeText(context,(o)? "Media Permission Granted" : "Media Permission Denied", Toast.LENGTH_SHORT).show();
        }
    });

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        activity = this;
        context = this;

        Intent intent=getIntent();
        doc=intent.getStringExtra("did");

        pid = findViewById(R.id.t1);
        genderSpinner = findViewById(R.id.t5);
        name = findViewById(R.id.t2);
        phno = findViewById(R.id.t3);
        age = findViewById(R.id.t4);
        height = findViewById(R.id.t6);
        weight = findViewById(R.id.t7);
        bmi = findViewById(R.id.t8);
        dob = findViewById(R.id.t9);
        profile = findViewById(R.id.img);
        save = findViewById(R.id.btn);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.gender,
                android.R.layout.simple_spinner_item
        );
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialog();
            }
        });

        height.addTextChangedListener(this);
        weight.addTextChangedListener(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values from EditText fields
                value = pid.getText().toString().trim();
                name1 = name.getText().toString().trim();
                phno1 = phno.getText().toString().trim();
                age1 = age.getText().toString().trim();
                height1 = height.getText().toString().trim();
                weight1 = weight.getText().toString().trim();
                dob1 = dob.getText().toString().trim();
                gender1 = genderSpinner.getSelectedItem().toString();
                // Check if any of the fields are empty
                if (gender1.isEmpty() || value.isEmpty() || name1.isEmpty() || phno1.isEmpty() || age1.isEmpty() || height1.isEmpty() || weight1.isEmpty() || dob1.isEmpty()) {
                    // If any field is empty, show a toast message and return
                    Toast.makeText(addPatient.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                    return;
                }

                // If all fields are filled, proceed to sendDataToDatabase()
                sendDataToDatabase();
            }
        });

    }

    public void selectDate(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                String date = String.format(Locale.getDefault(), "%d-%02d-%02d", year, month, dayOfMonth);
                EditText preferredDateEditText = findViewById(R.id.t9);
                preferredDateEditText.setText(date);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void calculateAndDisplayBMI() {
        String heightStr = height.getText().toString().trim();
        String weightStr = weight.getText().toString().trim();

        if (!heightStr.isEmpty() && !weightStr.isEmpty()) {
            double heightValue = Double.parseDouble(heightStr);
            double weightValue = Double.parseDouble(weightStr);
            double bmiValue = weightValue / (heightValue * heightValue);
            bmi.setText(String.format(Locale.getDefault(), "%.2f", bmiValue));
        } else {
            bmi.setText("");
        }
    }

    private void sendDataToDatabase() {
        showProgressBar();

        value = pid.getText().toString().trim();
        name1 = name.getText().toString().trim();
        age1 = age.getText().toString().trim();
        gender1 = genderSpinner.getSelectedItem().toString();
        height1 = height.getText().toString().trim();
        weight1 = weight.getText().toString().trim();
        dob1 = dob.getText().toString().trim();
        bmi1 = bmi.getText().toString().trim();

        BitmapDrawable drawable = (BitmapDrawable) profile.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        String profilePicBase64 = convertBitmapToBase64(bitmap);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = ip.ipn+"sPatient.php"; // Update this with your server address

        try {
            JSONObject jsonData = new JSONObject();
            jsonData.put("id", value);
            jsonData.put("name", name1);
            jsonData.put("gender", gender1);
            jsonData.put("age", age1);
            jsonData.put("height", height1);
            jsonData.put("weight", weight1);
            jsonData.put("phno", phno1);
            jsonData.put("bmi", bmi1);
            jsonData.put("dob", dob1);
            jsonData.put("profile_pic", profilePicBase64);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonData,
                    response -> {
                        try {
                            String status = response.getString("status");
                            if ("success".equals(status)) {
                                runOnUiThread(() -> {
                                    Toast.makeText(addPatient.this, "Details updated successfully", Toast.LENGTH_SHORT).show();
                                    navigateToClinicalParametersPage(); // Navigate to clinicalparameters page
                                });
                            } else {
                                runOnUiThread(() -> {
                                    Toast.makeText(addPatient.this, "Failed to update details", Toast.LENGTH_SHORT).show();
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            hideProgressBar();
                        }
                    },
                    error -> {
                        runOnUiThread(() -> {
                            Toast.makeText(addPatient.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                        hideProgressBar();
                    }
            );

            queue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
            hideProgressBar();
        }
    }

    private void navigateToClinicalParametersPage() {
        Intent intent = new Intent(addPatient.this, clinicalParameters.class);
        intent.putExtra("id", value);
        intent.putExtra("did",doc);
        // Pass pid value as "id" extra
        startActivity(intent);
        finish(); // Finish current activity if not needed anymore
    }

    private void showProgressBar() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                },
                1000
        );
    }

    private void hideProgressBar() {
        // Do nothing for now
    }

    private void showImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image Source");
        builder.setItems(new CharSequence[]{"Camera", "Gallery"}, (dialog, which) -> {
            if (which == 0) {
                dispatchTakePictureIntent();
            } else if (which == 1) {
                pickImageFromGallery();
            }
        });
        builder.show();
    }

    private void dispatchTakePictureIntent() {
        if(ip.checkCameraPermission(activity)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageCaptureLauncher.launch(takePictureIntent);
        } else {
            ip.requestCameraPermission(cameraPermission);
        }

    }

    private void pickImageFromGallery() {
        if(ip.checkMediaPermission(activity)) {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imageGalleryLauncher.launch(pickIntent);
        } else {
            ip.requestMediaPermission(imagesPermission);
        }

    }

    ActivityResultLauncher<Intent> imageCaptureLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        profile.setImageBitmap(imageBitmap);
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> imageGalleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        if (result.getData() != null) {
                            try {
                                Uri selectedImageUri = result.getData().getData();
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                                        addPatient.this.getContentResolver(),
                                        selectedImageUri
                                );
                                profile.setImageBitmap(bitmap);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
    );

    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        calculateAndDisplayBMI();
    }
}
