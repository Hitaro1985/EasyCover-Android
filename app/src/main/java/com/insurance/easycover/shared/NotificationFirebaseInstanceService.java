package com.insurance.easycover.shared;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.insurance.easycover.AppSession;
import com.insurance.easycover.data.local.AppSharedPreferences;
import com.insurance.easycover.data.network.NetworkController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by PDC100 on 3/22/2018.
 */

public class NotificationFirebaseInstanceService  extends FirebaseInstanceIdService {

    private static final String REG_TOKEN = "REG_TOKEN";
    @Override
    public void onTokenRefresh() {

        String recent_token = FirebaseInstanceId.getInstance().getToken();
        AppSharedPreferences.getInstance(getApplicationContext()).setDeviceToken(recent_token);
//        Map<String, String> map = new HashMap<>();
//        map.put("devicetoken", "" + recent_token);
        //NetworkController.getInstance().updateUser(map,null);
    }
}
