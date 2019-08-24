package com.nec.hackathon.interconnectedtransportportalapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;

import static android.content.Context.MODE_PRIVATE;

public class CommonUtils {

    public void waitAndNavigate(final Context context, final Intent i, String message, int mSeconds){
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage(message);
        pd.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pd.hide();
                context.startActivity(i);
            }
        }, mSeconds);
    }

    void saveDataToSP(Context context, String data, String password){
        SharedPreferences.Editor editor = context.getSharedPreferences("SecretDetails", MODE_PRIVATE).edit();
        editor.putString("adhaarNumber", data);
        editor.putString("password", password);
        editor.apply();
    }

    String retrieveDataFromSP(Context context){
        SharedPreferences prefs = context.getSharedPreferences("SecretDetails", MODE_PRIVATE);
        return prefs.getString("adhaarNumber", "4565231256");//"No name defined" is the default value.
    }

}
