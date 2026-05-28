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

public class dSearch extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private List<DoctorInfo1> dataList;
    private List<DoctorInfo1> filteredList;
    String url = ip.ipn+"docpand.php";

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
                                String id = jsonObject.optString("did");
                                String name = jsonObject.optString("name");
                                String gender = jsonObject.optString("gender");
                                String cno = jsonObject.optString("phno");
                                String profilePhoto = jsonObject.optString("img");
                                Log.d("tag1", id);
                                Log.d("tag1", name);
                                Log.d("tag1", gender);
                                Log.d("tag1", cno);
                                Log.d("tag1", profilePhoto);

                                d1 d1 = new d1(id, name, gender, cno,profilePhoto);
                                dataList.add(d1.toDoctorInfo());
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
            for (DoctorInfo1 item : dataList) {
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

        private List<DoctorInfo1> dataList;

        public CustomAdapter(List<DoctorInfo1> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
            return new ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            DoctorInfo1 doctorInfo = dataList.get(position);

            if (holder.idTextView != null) {
                holder.idTextView.setText("ID                 : " + (doctorInfo.getId() != null ? doctorInfo.getId() : ""));
            }
            if (holder.nameTextView != null) {
                holder.nameTextView.setText("Name          : " + (doctorInfo.getName() != null ? doctorInfo.getName() : ""));
            }
            if (holder.genderTextView != null) {
                holder.genderTextView.setText("Gender       : " + (doctorInfo.getGender() != null ? doctorInfo.getGender() : ""));
            }
            if (holder.phnoTextView != null) {
                holder.phnoTextView.setText("Contact no: " + (doctorInfo.getPhno() != null ? doctorInfo.getPhno() : ""));
            }

            // Uncomment the following code if you have a profile image view in your layout
            if (holder.profileImageView != null && doctorInfo.getProfile() != null
                    && !doctorInfo.getProfile().isEmpty()) {
                String completeImageUrl = ip.ipn + doctorInfo.getProfile();
                Picasso.get().load(completeImageUrl).into(holder.profileImageView);
            } else {
                holder.profileImageView.setImageResource(R.drawable.ellipsep);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selectedItem = doctorInfo.getId() != null ? doctorInfo.getId() : "";

                    Intent intent = new Intent(dSearch.this, dis_doctor.class);
                    intent.putExtra("item", doctorInfo.getId());
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
