package com.simats.airwayanesthesia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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

public class searchp extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private List<PatientInfo1> dataList;
    private List<PatientInfo1> filteredList;
    String url = ip.ipn+"searchp.php";

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchp);
        dataList = new ArrayList<>();
        filteredList = new ArrayList<>(dataList);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomAdapter(filteredList);
        recyclerView.setAdapter(adapter);
        fetchfromPHP();
        SearchView searchView = findViewById(R.id.searchview);
        searchView.setFocusable(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        filter("");
    }

    public void fetchfromPHP() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            dataList.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.optString("pid");
                                String name = jsonObject.optString("name");
                                String gender = jsonObject.optString("gender");
                                String phno = jsonObject.optString("phno");
                                String profilePhoto = jsonObject.optString("img");
                                Log.d("tag1", id);
                                Log.d("tag1", name);
                                Log.d("tag1", gender);
                                Log.d("tag1", phno);

                                P1 p1 = new P1(id, name, gender, phno, profilePhoto);
                                dataList.add(p1.toPatientInfo());
                            }
                            adapter.notifyDataSetChanged();
                            filter("");
                        } catch (JSONException e) {
                            e.printStackTrace();
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
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview1, parent, false);
            return new ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            PatientInfo1 patient = dataList.get(position);

            if (holder.idTextView != null) {
                holder.idTextView.setText("Id                 : " + (patient.getId() != null ? patient.getId() : ""));
            }
            if (holder.nameTextView != null) {
                holder.nameTextView.setText("Name          : " + (patient.getName() != null ? patient.getName() : ""));
            }
            if (holder.genderTextView != null) {
                holder.genderTextView.setText("Gender       : " + (patient.getGender() != null ? patient.getGender() : ""));
            }
            if (holder.phnoTextView != null) {
                holder.phnoTextView.setText("Phno           : " + (patient.getPhno() != null ? patient.getPhno() : ""));
            }

            if (holder.profileImageView != null && patient.getProfilePhoto() != null
                    && !patient.getProfilePhoto().isEmpty()) {
                String completeImageUrl = ip.ipn + patient.getProfilePhoto();
                Picasso.get().load(completeImageUrl).into(holder.profileImageView);
            } else {
                holder.profileImageView.setImageResource(R.drawable.ellipsep);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selectedItem = patient.getId() != null ? patient.getId() : "";

                    Intent intent = new Intent(searchp.this, pprofile.class);
                    intent.putExtra("pid", selectedItem);

                    // Retrieve the value passed from the previous activity
                    String userId = getIntent().getStringExtra("did");

                    // Pass the value received from the previous activity to the next activity
                    intent.putExtra("did", userId);

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

    private void handleError(VolleyError error) {
        if (error instanceof TimeoutError) {
            Toast.makeText(this, "Request timed out. Check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
