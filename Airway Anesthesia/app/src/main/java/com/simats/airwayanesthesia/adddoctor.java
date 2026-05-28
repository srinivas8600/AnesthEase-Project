package com.simats.airwayanesthesia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.content.DialogInterface;

import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class adddoctor extends AppCompatActivity {
    private Button button;
    private ImageView profile;
    private EditText name, did, pass, phno, spe;
    private Spinner genderSpinner;
    private ProgressDialog progressDialog;
    private static final String URL =ip.ipn+"adocdummy.php";

    private Context context;
    private FragmentActivity activity;
    ActivityResultLauncher<String> imagesPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean o) {
            Toast.makeText(context,(o)? "Media Permission Granted" : "Media Permission Denied", Toast.LENGTH_SHORT).show();
        }
    });

    ActivityResultLauncher<String> cameraPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean o) {
            Toast.makeText(activity,(o)? "Camera Permission Granted" : "Camera Permission Denied", Toast.LENGTH_SHORT).show();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddoctor);

        context  = this;
        activity = this;

        button = findViewById(R.id.button);
        name = findViewById(R.id.name);
        did = findViewById(R.id.doc_id);
        profile = findViewById(R.id.img);
        pass = findViewById(R.id.password);
        phno = findViewById(R.id.phone);
        spe = findViewById(R.id.speciality);
        genderSpinner = findViewById(R.id.genderspinner);

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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = name.getText().toString().trim();
                String cno1 = phno.getText().toString().trim();
                String did1 = did.getText().toString().trim();
                String pass1 = pass.getText().toString().trim();
                String spe1 = spe.getText().toString().trim();

                if (TextUtils.isEmpty(name1) || TextUtils.isEmpty(cno1) || TextUtils.isEmpty(did1) ||
                        TextUtils.isEmpty(pass1) || TextUtils.isEmpty(spe1)) {
                    Toast.makeText(adddoctor.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                    return;
                }

                sendDataToDatabase(name1, cno1, did1, pass1, spe1);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);
    }

    private void sendDataToDatabase(String name1, String cno1, String did1, String pass1, String spe1) {
        progressDialog.show();

        String gender1 = genderSpinner.getSelectedItem().toString();

        BitmapDrawable drawable = (BitmapDrawable) profile.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        String profilePicBase64 = convertBitmapToBase64(bitmap);

        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("did", did1);
            jsonData.put("name", name1);
            jsonData.put("gender", gender1);
            jsonData.put("password", pass1);
            jsonData.put("speciality", spe1);
            jsonData.put("phno", cno1);
            jsonData.put("profile_pic", profilePicBase64);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if ("success".equals(status)) {
                                Toast.makeText(adddoctor.this, "Details updated successfully", Toast.LENGTH_SHORT).show();
                                navigateToClinicalParametersPage();
                            } else {
                                Toast.makeText(adddoctor.this, "Failed to update details", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(adddoctor.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
        );

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000, // 60 seconds
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsonObjectRequest);
    }

    private void navigateToClinicalParametersPage() {
        Intent intent = new Intent(adddoctor.this, admindashboard.class);
        startActivity(intent);
        finish();
    }

    private void showImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image Source");
        builder.setItems(new CharSequence[]{"Camera", "Gallery"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    dispatchTakePictureIntent();
                } else if (which == 1) {
                    pickImageFromGallery();
                }
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
                                        adddoctor.this.getContentResolver(),
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
}
