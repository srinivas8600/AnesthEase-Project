package com.simats.airwayanesthesia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
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

public class ultrasound extends AppCompatActivity {
    private RadioGroup shRadioGroup, ttRadioGroup, ttmRadioGroup, hmdRadioGroup, dshbRadioGroup, dseRadioGroup, dsacRadioGroup, preERadioGroup, vaRadioGroup;
    private Button nextButton;
    private int sh = 0;
    private int tt = 0;
    private int ttm = 0;
    private int hmd = 0;
    private int dshb = 0;
    private int dse = 0;
    private int dsac = 0;
    private int preE = 0;
    private int va = 0;
    private int total1 = 0;
    private int fTotal = 0;
    String value=" ";
    String url = ip.ipn+"uscore.php";
    int extraValue = 0;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultrasound); // Replace with your actual layout file name
        Intent intent = getIntent();
        value =intent.getStringExtra("value");
        shRadioGroup = findViewById(R.id.shrg);
        ttRadioGroup = findViewById(R.id.ttrg);
        ttmRadioGroup = findViewById(R.id.ttmrg);
        hmdRadioGroup = findViewById(R.id.hmdrg);
        dshbRadioGroup = findViewById(R.id.dshbrg);
        dseRadioGroup = findViewById(R.id.dserg);
        dsacRadioGroup = findViewById(R.id.dsacrg);
        preERadioGroup = findViewById(R.id.preErg);
        vaRadioGroup = findViewById(R.id.varg);

        nextButton = findViewById(R.id.btn22);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFields()) {
                    Toast.makeText(ultrasound.this, "Please select an option in each category", Toast.LENGTH_SHORT).show();
                    return;
                }

                int totalScore = calculateTotalScore();
                sendSurveyDataToServer(totalScore, value);

                Intent receivedIntent = getIntent(); // Change the variable name to receivedIntent
                extraValue = receivedIntent.getIntExtra("totalScore", 0);
                Log.d("teguhf", String.valueOf(extraValue));

                try {
                    total1 = extraValue;
                    fTotal = total1 + totalScore;
                    Toast.makeText(ultrasound.this, "final Score: " + fTotal, Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    // Handle the case where the value is not a valid integer
                    Toast.makeText(ultrasound.this, "Invalid value for totalScore: " + extraValue, Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(ultrasound.this, "Total Score: " + fTotal, Toast.LENGTH_SHORT).show();
                Intent newIntent = new Intent(ultrasound.this, ScoreActivity.class); // Change the variable name to newIntent
                newIntent.putExtra("totalScore", totalScore);
                //startActivity(newIntent);
                // Intent newInten = new Intent(ultrasound.this, score.class);
                newIntent.putExtra("totalScore", totalScore);
                newIntent.putExtra("total1", total1);
                newIntent.putExtra("fTotal", fTotal);
                newIntent.putExtra("totalScore", totalScore);
                newIntent.putExtra("value",value);
                startActivity(newIntent);
            }
        });

        setRadioButtonClickListeners();
    }

    private void setRadioButtonClickListeners() {
        // Implement radio button click listeners if needed
        // You can follow the structure used in the clinicalParameters class
    }

    private int calculateTotalScore() {
        int totalScore = 0;

        // You need to calculate the total score based on the selected radio buttons
        // For example:
        sh = calculatesh();
        totalScore += calculatesh();
        tt = calculatett();
        totalScore += calculatett();
        ttm = calculatettm();
        totalScore += calculatettm();
        hmd = calculatehmd();
        totalScore += calculatehmd();
        dshb = calculatedshb();
        totalScore += calculatedshb();
        dse = calculatedse();
        totalScore += calculatedse();
        dsac = calculatedsac();
        totalScore += calculatedsac();
        preE = calculatepreE();
        totalScore += calculatepreE();
        va = calculateva();
        totalScore += calculateva();
        // Add similar lines for other RadioGroups

        return totalScore;
    }

    private int calculateva() {
        int vaScore = 0;
        int checkedRadioButtonId = vaRadioGroup.getCheckedRadioButtonId();

        if (checkedRadioButtonId == R.id.va1) {
            vaScore = 0;
        } else if (checkedRadioButtonId == R.id.va2) {
            vaScore = 1;
        } else if (checkedRadioButtonId == R.id.va3) {
            vaScore = 2;
        } else if (checkedRadioButtonId == R.id.va4) {
            vaScore = 3;
        }
        return vaScore;
    }

    private int calculatepreE() {
        int preEScore = 0;
        int checkedRadioButtonId = preERadioGroup.getCheckedRadioButtonId();

        if (checkedRadioButtonId == R.id.preE1) {
            preEScore = 0;
        } else if (checkedRadioButtonId == R.id.preE2) {
            preEScore = 1;
        } else if (checkedRadioButtonId == R.id.preE3) {
            preEScore = 2;
        } else if (checkedRadioButtonId == R.id.preE4) {
            preEScore = 3;
        } else if (checkedRadioButtonId == R.id.preE5) {
            preEScore = 4;
        }

        return preEScore;

    }

    private int calculatedsac() {
        int dsacScore = 0;
        int checkedRadioButtonId = dsacRadioGroup.getCheckedRadioButtonId();

        if (checkedRadioButtonId == R.id.dsac1) {
            dsacScore = 1;
        } else if (checkedRadioButtonId == R.id.dsac2) {
            dsacScore = 0;
        }

        return dsacScore;

    }

    private int calculatedse() {
        int dseScore = 0;
        int checkedRadioButtonId = dseRadioGroup.getCheckedRadioButtonId();

        if (checkedRadioButtonId == R.id.dse1) {
            dseScore = 1;
        } else if (checkedRadioButtonId == R.id.dse2) {
            dseScore = 0;
        }

        return dseScore;

    }

    private int calculatedshb() {
        int dshbScore = 0;
        int checkedRadioButtonId = dshbRadioGroup.getCheckedRadioButtonId();

        if (checkedRadioButtonId == R.id.dshb1) {
            dshbScore = 1;
        } else if (checkedRadioButtonId == R.id.dshb2) {
            dshbScore = 0;
        }

        return dshbScore;

    }

    private int calculatehmd() {
        int hmdScore = 0;
        int checkedRadioButtonId = hmdRadioGroup.getCheckedRadioButtonId();

        if (checkedRadioButtonId == R.id.hmd1) {
            hmdScore = 1;
        } else if (checkedRadioButtonId == R.id.hmd2) {
            hmdScore = 0;
        }

        return hmdScore;

    }

    private int calculatesh() {
        int shScore = 0;
        int checkedRadioButtonId = shRadioGroup.getCheckedRadioButtonId();

        if (checkedRadioButtonId == R.id.sh1) {
            shScore = 0;
        } else if (checkedRadioButtonId == R.id.sh2) {
            shScore = 1;
        }

        return shScore;
    }

    private int calculatett() {
        int ttScore = 0;
        int checkedRadioButtonId = ttRadioGroup.getCheckedRadioButtonId();

        if (checkedRadioButtonId == R.id.tt1) {
            ttScore = 1;
        } else if (checkedRadioButtonId == R.id.tt2) {
            ttScore = 0;
        }

        return ttScore;

    }

    private int calculatettm() {
        int ttmScore = 0;
        int checkedRadioButtonId = ttmRadioGroup.getCheckedRadioButtonId();

        if (checkedRadioButtonId == R.id.ttm1) {
            ttmScore = 1;
        } else if (checkedRadioButtonId == R.id.ttm2) {
            ttmScore = 0;
        }

        return ttmScore;

    }

    private boolean validateFields() {
        // Check if all radio groups have a selection
        if (shRadioGroup.getCheckedRadioButtonId() == -1 ||
                ttRadioGroup.getCheckedRadioButtonId() == -1 ||
                ttmRadioGroup.getCheckedRadioButtonId() == -1 ||
                hmdRadioGroup.getCheckedRadioButtonId() == -1 ||
                dshbRadioGroup.getCheckedRadioButtonId() == -1 ||
                dseRadioGroup.getCheckedRadioButtonId() == -1 ||
                dsacRadioGroup.getCheckedRadioButtonId() == -1 ||
                preERadioGroup.getCheckedRadioButtonId() == -1 ||
                vaRadioGroup.getCheckedRadioButtonId() == -1) {
            return false; // At least one radio group has no selection
        }
        return true; // All fields are filled
    }

    private void sendSurveyDataToServer(final int totalScore, final String value) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Define the parameters to be sent to the server
        Map<String, String> params = new HashMap<>();
        params.put("total", String.valueOf(fTotal));
        params.put("Utotal", String.valueOf(totalScore)); // Change this if necessary
        params.put("Ctotal", String.valueOf(extraValue));
        params.put("sh", String.valueOf(sh));
        params.put("tt", String.valueOf(tt));
        params.put("ttm", String.valueOf(ttm));
        params.put("hmd", String.valueOf(hmd));
        params.put("dshb", String.valueOf(dshb));
        params.put("dse", String.valueOf(dse));
        params.put("dsac", String.valueOf(dsac));
        params.put("preE", String.valueOf(preE));
        params.put("va", String.valueOf(va));
        params.put("value", value);

        // Create the request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the server
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            // Process the jsonResponse if needed
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Handle success or failure accordingly
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        Log.e("VolleyError", "Error during request: " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
