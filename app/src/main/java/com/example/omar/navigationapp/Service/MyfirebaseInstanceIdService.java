package com.example.omar.navigationapp.Service;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Config;
import android.util.Log;

import com.example.omar.navigationapp.app.config;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;



/**
 * Created by SaTa on 12/29/2017.
 */

public class MyfirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = MyfirebaseInstanceIdService.class.getSimpleName();

    public void  onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();


        storeRegIdInPref(refreshedToken);

        sendRegistrationToServer(refreshedToken);


        Intent registrationComplete = new Intent(config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        Log.e(TAG , "sendserviceID" +token);


    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();
    }

    }

