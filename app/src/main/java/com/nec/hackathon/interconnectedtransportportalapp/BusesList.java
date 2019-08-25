package com.nec.hackathon.interconnectedtransportportalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.nec.hackathon.interconnectedtransportportalapp.Adapters.BusListAdapter;
import com.nec.hackathon.interconnectedtransportportalapp.Model.BusInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BusesList extends AppCompatActivity{

    private List<BusInfo> busList = new ArrayList<>();
    private ArrayList<LatLng> list;
    private RecyclerView recyclerView;
    private BusListAdapter mAdapter;
    private String source;
    private String destination;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buses_list);

        Intent intent = getIntent();
        source = intent.getStringExtra("source");
        destination = intent.getStringExtra("destination");

        recyclerView = findViewById(R.id.recycler_view);
        prepareMovieData();
        if(flag){
            mAdapter = new BusListAdapter(busList, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }
        else{
            Toast.makeText(this, "No direct bus, check for transit buses", Toast.LENGTH_LONG).show();
        }
    }

    private void prepareMovieData(){
        try {
            flag = false;
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset());
            BusInfo busInfo;
            list = new ArrayList<>();
            String label = "HIGH";
            int seatAvailable = 26;
            String arrival_time ="";

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.get("stop_name").equals(destination)){
                    flag = true;
                    arrival_time = jsonObject.get("arrival_time").toString();
                }
                LatLng latLng = new LatLng(jsonObject.getDouble("stop_lat"), jsonObject.getDouble("stop_lon"));
                list.add(latLng);
            }
            busInfo = new BusInfo("578-UP", null, null, destination, list, arrival_time, label, seatAvailable);
            busInfo.setMidStops(list);
            busList.add(busInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = BusesList.this.getAssets().open("busList.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
