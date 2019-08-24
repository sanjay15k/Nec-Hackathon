package com.nec.hackathon.interconnectedtransportportalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class PayableAmountScreen extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup featureRadioGroup;
    private TextView totalPayableAmountTextView;
    private TextView normalFare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payable_amount_screen);

        featureRadioGroup = findViewById(R.id.featureRadioGroup);

        TextView arFare = findViewById(R.id.arFare);
        TextView vrFare = findViewById(R.id.vrFare);
        TextView netflixFare = findViewById(R.id.netflixFare);
        normalFare = findViewById(R.id.normalFare);
        totalPayableAmountTextView = findViewById(R.id.totalPayableAmountTextView);

        featureRadioGroup.setOnCheckedChangeListener(this);
        Button confirmBookingBtn = findViewById(R.id.confirmBookingBtn);
        confirmBookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PayableAmountScreen.this, SuccessfullBookingScreen.class);
                CommonUtils commonUtils = new CommonUtils();
                commonUtils.waitAndNavigate(PayableAmountScreen.this, intent, "Just wait for few seconds while we book your seat", 2500);
            }
        });

        RadioButton noneRadioBtn = findViewById(R.id.noneRadioBtn);
        noneRadioBtn.setChecked(true);

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        String tag = (String) radioButton.getTag();
        int fare = Integer.parseInt(normalFare.getText().toString().substring(3));

        if(!tag.equals("-1")) {
            fare += Integer.parseInt(tag);
        }
        totalPayableAmountTextView.setText("Rs "+String.valueOf(fare));
    }
}
