package com.nec.hackathon.interconnectedtransportportalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class SelectSrcDest extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner destinationSpinner;
    private ArrayList<String> destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_src_dest);
        Button searchBuses = findViewById(R.id.searchBuses);
        destinationSpinner = findViewById(R.id.destinationSpinner);

        destination = new ArrayList<>();
        destination.add("Dwarka Mor Metro");
        destination.add("Dhaula Kuan");
        destination.add("Uttam Nagar");
        destination.add("Tilak Nagar");

        destinationSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, destination);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destinationSpinner.setAdapter(aa);

        searchBuses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String dest = destination.get(destinationSpinner.getSelectedItemPosition());
                final Intent i = new Intent(SelectSrcDest.this, BookTicketWithBusLocation.class);
                i.putExtra("destination", dest);
                final ProgressDialog pd = new ProgressDialog(SelectSrcDest.this);
                pd.setMessage("Searching..");
                pd.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pd.hide();
                        System.out.println("**** Destination selected : "+dest);
                        startActivity(i);
                    }
                }, 3000);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        destinationSpinner.setId(position);
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
