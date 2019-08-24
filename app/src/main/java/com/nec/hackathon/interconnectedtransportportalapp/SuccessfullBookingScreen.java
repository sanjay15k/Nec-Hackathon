package com.nec.hackathon.interconnectedtransportportalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class SuccessfullBookingScreen extends AppCompatActivity {

    private Button activeBookingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successfull_booking_screen);

        activeBookingsBtn = findViewById(R.id.activeBookingsBtn);
        activeBookingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessfullBookingScreen.this, SelectSrcDest.class); //Return to the root activity: A
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //this will clear the entire task stack and create a new instance of A
                startActivity(intent);
            }
        });
        CommonUtils commonUtils = new CommonUtils();
        String content = commonUtils.retrieveDataFromSP(SuccessfullBookingScreen.this);
        qrGenerator(content);
    }

    private void qrGenerator(String content){
        QRCodeWriter writer = new QRCodeWriter();
        try {
            System.out.println("**** Content is : "+content);
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 200, 200);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            ((ImageView) findViewById(R.id.qrCodeImageView)).setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

}
