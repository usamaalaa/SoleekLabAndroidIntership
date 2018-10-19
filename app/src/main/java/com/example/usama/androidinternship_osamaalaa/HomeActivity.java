package com.example.usama.androidinternship_osamaalaa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.usama.androidinternship_osamaalaa.Adapter.MyRecyclerViewAdapter;
import com.example.usama.androidinternship_osamaalaa.Country.Country;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyRecyclerViewAdapter myRecyclerViewAdapter;
    ArrayList<Country> countries;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ... ");
        progressDialog.show();

        recyclerView = findViewById(R.id.myRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        countries = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);

        parseJson();
    }

    public void parseJson() {
        final String url = "https://api.printful.com/countries";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                progressDialog.dismiss();
                JSONArray jsonArray = response.getJSONArray("result");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    countries.add(new Country(name));
                }
                myRecyclerViewAdapter = new MyRecyclerViewAdapter(HomeActivity.this, countries);
                recyclerView.setAdapter(myRecyclerViewAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(HomeActivity.this, "error", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        });
        requestQueue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.log_out, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuid) {
            firebaseAuth.signOut();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
