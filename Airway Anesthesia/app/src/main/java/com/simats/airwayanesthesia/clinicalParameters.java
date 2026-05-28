package com.simats.airwayanesthesia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
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

public class clinicalParameters extends AppCompatActivity {
    private RadioGroup[] radioGroups;
    private Button submitButton;
    private RadioGroup rg1, rg2;
    private RadioGroup weightRadioGroup;
    private RadioGroup mmpRadioGroup;
    private RadioGroup mirg;
    private RadioGroup tmdRadioGroup;
    private RadioGroup tmjRadioGroup;
    private RadioGroup nmRadioGroup;
    private RadioGroup ncRadioGroup;
    private RadioGroup bmiRadiGroup;
    private int rhm = 0;
    private int ds = 0;
    private int wt = 0;
    private int mmp = 0;
    private int mi = 0;
    private int tmd = 0;
    private int tmj = 0;
    private int nm = 0;
    private int nc = 0;
    private int bmi = 0;
    private String value;
    String doc;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinical_parameters);
        Intent intent1=getIntent();
        doc=intent1.getStringExtra("did");

        weightRadioGroup = findViewById(R.id.weight_radio_group);
        mmpRadioGroup = findViewById(R.id.mrg);
        mirg = findViewById(R.id.mirg);
        tmdRadioGroup = findViewById(R.id.tmdrg);
        tmjRadioGroup = findViewById(R.id.tmjrg);
        nmRadioGroup = findViewById(R.id.nmrg);
        ncRadioGroup = findViewById(R.id.ncrg);
        bmiRadiGroup = findViewById(R.id.bmirg);

        Intent intent = getIntent();
        if (intent != null) {
            value = intent.getStringExtra("id");
        }

        radioGroups = new RadioGroup[5];
        radioGroups[0] = findViewById(R.id.radiogroup1);
        radioGroups[1] = findViewById(R.id.radiogroup2);
        // Add remaining RadioGroups...

        radioGroups[2] = findViewById(R.id.radiogroup3);
        radioGroups[3] = findViewById(R.id.radiogroup4);
        radioGroups[4] = findViewById(R.id.radiogroup5);

        submitButton = findViewById(R.id.btn);
        rg1 = findViewById(R.id.rg1);
        rg2 = findViewById(R.id.rg2);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFields()) {
                    Toast.makeText(clinicalParameters.this, "Please select an option in each category", Toast.LENGTH_SHORT).show();
                    return;
                }

                int totalScore = 0;
                totalScore = calculateTotalScore();
                ds = totalScore;
                totalScore += calculateTotalRHM();
                wt = calculateWt();
                totalScore += wt;
                mmp = calculatemmp();
                totalScore += calculatemmp();
                mi = calculatemi();
                totalScore += calculatemi();
                tmd = calculatetmd();
                totalScore += calculatetmd();
                tmj = calculatetmj();
                totalScore += calculatetmj();
                nm = calculatenm();
                totalScore += calculatenm();
                nc = calculatenc();
                totalScore += calculatenc();
                bmi = calculatebmi();
                totalScore += calculatebmi();

                sendSurveyDataToServer(totalScore, value);

                Toast.makeText(clinicalParameters.this, "Total Score: " + totalScore, Toast.LENGTH_SHORT).show();
                Intent ultrasoundIntent = new Intent(clinicalParameters.this, ultrasound.class);
                ultrasoundIntent.putExtra("totalScore", totalScore);
                ultrasoundIntent.putExtra("value", value);
                ultrasoundIntent.putExtra("did",doc);
                startActivity(ultrasoundIntent);
            }
        });

        setRadioButtonClickListeners();
    }

    private int calculatebmi() {
        int bmiScore = 0;
        int checkedRadioButtonId = bmiRadiGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and update the bmiScore accordingly
        if (checkedRadioButtonId == R.id.bm1) {
            bmiScore = 0;
        } else if (checkedRadioButtonId == R.id.bm2) {
            bmiScore = 1;
        } else if (checkedRadioButtonId == R.id.bm3) {
            bmiScore = 2;
          } else if (checkedRadioButtonId == R.id.bm4) {
            bmiScore = 3;
        }
        return bmiScore;
    }

    private int calculatenc() {
        int ncScore = 0;
        int checkedRadioButtonId = ncRadioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.nc1) {
            ncScore = 0;
        } else if (checkedRadioButtonId == R.id.nc2) {
            ncScore = 1;
        } else if (checkedRadioButtonId == R.id.nc3) {
            ncScore = 2;
        }
        return ncScore;
    }

    private int calculatenm() {
        int nmScore = 0;
        int checkedRadioButtonId = nmRadioGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and update the nmScore accordingly
        if (checkedRadioButtonId == R.id.n1) {
            nmScore = 0;
        } else if (checkedRadioButtonId == R.id.n2) {
            nmScore = 1;
        } else if (checkedRadioButtonId == R.id.n3) {
            nmScore = 2;
        } else if (checkedRadioButtonId == R.id.n4) {
            nmScore = 3;
        }
        return nmScore;
    }

    private int calculatetmj() {
        int tmjScore = 0;
        int checkedRadioButtonId = tmjRadioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.tj1) {
            tmjScore = 0;
        } else if (checkedRadioButtonId == R.id.tj2) {
            tmjScore = 1;
        } else if (checkedRadioButtonId == R.id.tj3) {
            tmjScore = 2;
        }
        return tmjScore;
    }

    private int calculatetmd() {
        int tmdScore = 0;
        int checkedRadioButtonId = tmdRadioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.t1) {
            tmdScore = 0;
        } else if (checkedRadioButtonId == R.id.t2) {
            tmdScore = 1;
        } else if (checkedRadioButtonId == R.id.t3) {
            tmdScore = 2;
        }
        return tmdScore;
    }

    private int calculatemi() {
        int miScore = 0;
        int checkedRadioButtonId = mirg.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.mo1) {
            miScore = 0;
        } else if (checkedRadioButtonId == R.id.mo2) {
            miScore = 1;
        } else if (checkedRadioButtonId == R.id.mo3) {
            miScore = 2;
        }
        return miScore;
    }

    private int calculatemmp() {
        int mmpScore = 0;
        int checkedRadioButtonId = mmpRadioGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and update the mmpScore accordingly
        if (checkedRadioButtonId == R.id.m1) {
            mmpScore = 0;
        } else if (checkedRadioButtonId == R.id.m2) {
            mmpScore = 1;
        } else if (checkedRadioButtonId == R.id.m3) {
            mmpScore = 2;
        } else if (checkedRadioButtonId == R.id.m4) {
            mmpScore = 3;
        }
        return mmpScore;
    }

    private int calculateWt() {
        int weightScore = 0;
        int checkedRadioButtonId = weightRadioGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and update the weightScore accordingly
        if (checkedRadioButtonId == R.id.lessThan90Kgs) {
            weightScore = 0;
        } else if (checkedRadioButtonId == R.id.r1) {
            weightScore = 1;
        } else if (checkedRadioButtonId == R.id.greaterThan110Kgs) {
            weightScore = 2;
        }
        return weightScore;
    }

    private int calculateTotalRHM() {
        rhm = 0;


        // Calculate points for Question 1
        RadioButton radioButton1 = findViewById(rg1.getCheckedRadioButtonId());
        if (radioButton1 != null) {
            if (radioButton1.getId() == R.id.yes1) {
                rhm += 1;
            }
        }

        // Calculate points for Question 2
        RadioButton radioButton2 = findViewById(rg2.getCheckedRadioButtonId());
        if (radioButton2 != null) {
            if (radioButton2.getId() == R.id.yes2) {
                rhm += 1;
            }
        }
        return rhm;
    }

    private void setRadioButtonClickListeners() {
        for (final RadioGroup radioGroup : radioGroups) {
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton radioButton = group.findViewById(checkedId);
                    if (radioButton != null) {
                        String scoreText = radioButton.getText().toString();
                        try {
                            int score = Integer.parseInt(scoreText);
                            // Do something with the checked score if needed
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    private int calculateTotalScore() {
        int totalScore = 0;

        for (RadioGroup radioGroup : radioGroups) {
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(checkedRadioButtonId);

            if (radioButton != null) {
                // Check if the selected RadioButton is one of the "Yes" options
                if (radioButton.getId() == R.id.dyes1 ||
                        radioButton.getId() == R.id.dyes2 ||
                        radioButton.getId() == R.id.dyes3 ||
                        radioButton.getId() == R.id.dyes4 ||
                        radioButton.getId() == R.id.dyes5) {
                    totalScore += 1;
                }
                // No need to explicitly check for "No" options as the default is 0
            }
        }

        return totalScore;
    }

    private boolean validateFields() {
        // Check if all radio groups have a selection
        if (rg1.getCheckedRadioButtonId() == -1 ||
                rg2.getCheckedRadioButtonId() == -1 ||
                radioGroups[0].getCheckedRadioButtonId() == -1 ||
                radioGroups[1].getCheckedRadioButtonId() == -1 ||
                radioGroups[2].getCheckedRadioButtonId() == -1 ||
                radioGroups[3].getCheckedRadioButtonId() == -1 ||
                radioGroups[4].getCheckedRadioButtonId() == -1 ||
                weightRadioGroup.getCheckedRadioButtonId() == -1 ||
                mmpRadioGroup.getCheckedRadioButtonId() == -1 ||
                mirg.getCheckedRadioButtonId() == -1 ||
                tmdRadioGroup.getCheckedRadioButtonId() == -1 ||
                tmjRadioGroup.getCheckedRadioButtonId() == -1 ||
                nmRadioGroup.getCheckedRadioButtonId() == -1 ||
                ncRadioGroup.getCheckedRadioButtonId() == -1 ||
                bmiRadiGroup.getCheckedRadioButtonId() == -1) {
            return false; // At least one radio group has no selection
        }

        return true; // All fields are filled
    }


    private void sendSurveyDataToServer(final int totalScore, final String value) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Replace "YOUR_PHP_ENDPOINT" with the actual endpoint of your PHP file
        String url =ip.ipn+"cscore.php";;

        // Define the parameters to be sent to the server
        Map<String, String> params = new HashMap<>();
        params.put("totalScore", String.valueOf(totalScore));
        params.put("rhm", String.valueOf(rhm));
        params.put("ds", String.valueOf(ds));
        params.put("wt", String.valueOf(wt));
        params.put("mmp", String.valueOf(mmp));
        params.put("mi", String.valueOf(mi));
        params.put("tmd", String.valueOf(tmd));
        params.put("tmj", String.valueOf(tmj));
        params.put("nm", String.valueOf(nm));
        params.put("nc", String.valueOf(nc));
        params.put("bmi", String.valueOf(bmi));
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
