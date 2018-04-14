package com.insurance.easycover.shared.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.insurance.easycover.AppSession;
import com.insurance.easycover.R;
import com.insurance.easycover.agent.ui.activities.AgentHomeActivity;
import com.insurance.easycover.customer.ui.activities.CustomerHomeActivity;
import com.insurance.easycover.data.local.AppSharedPreferences;
import com.insurance.easycover.data.models.response.User;

import naveed.khakhrani.miscellaneous.base.SplashBase;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends SplashBase {

    private boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        FirebaseMessaging.getInstance().subscribeToTopic("EasyCover");
        String token = FirebaseInstanceId.getInstance().getToken();
        if (token != null) {
            Log.d("FCM token:", token);
        } else {
            Log.d("FCM token", "NULL");
        }

        // hide();
        runDelay();
        // Set up the user interaction to manually show or hide the system UI.


    }

    @Override
    protected void onDelayComplete() {
        String user = AppSharedPreferences.getInstance(this).getUserData();
        String token = AppSharedPreferences.getInstance(this).getToken();
        if (user == null) {
            isLoggedIn = false;
        } else {
            isLoggedIn = true;
            AppSession.getInstance().setUserData(new Gson().fromJson(user, User.class));
            AppSession.getInstance().setToken(token);
            //AppSession

        }
        launchActivity(isLoggedIn ? (AppSession.getInstance().isAgent() ? AgentHomeActivity.class : CustomerHomeActivity.class) : ChooseRoleActivity.class);
        //launchActivity(AgentHomeActivity.class);
        finish();
    }


 /*   private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

    }*/


}

