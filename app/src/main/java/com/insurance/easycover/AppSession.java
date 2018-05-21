package com.insurance.easycover;

import com.google.gson.Gson;
import com.insurance.easycover.data.local.AppSharedPreferences;
import com.insurance.easycover.data.models.Register;
import com.insurance.easycover.data.models.response.User;
import com.insurance.easycover.shared.enums.UserRoles;

/**
 * Created by naveedali on 10/9/17.
 */

public class AppSession {

    public static final int ROLE_AGENT = 1;
    public static final int ROLE_CUSTOMER = 2;

    public static final String AGENT = "agent";
    public static final String CUSTOMER = "customer";


    private int userRole = 1;
    private User mUserData;
    private String token;
    private String password;
    private String surName;
    private Integer lastCreateJobID;

    private long longitude;
    private long latitude;

    private Register RegisterTemp;

    private  boolean newNoti;

    private static final AppSession ourInstance = new AppSession();

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public static AppSession getInstance() {
        return ourInstance;
    }

    public Integer getLastCreateJobID() {
        return lastCreateJobID;
    }

    public void setLastCreateJobID(Integer lastCreateJobID) {
        this.lastCreateJobID = lastCreateJobID;
    }

    private AppSession() {

    }

    public boolean isNewNoti() {
        return newNoti;
    }

    public void setNewNoti(boolean newNoti) {
        this.newNoti = newNoti;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void clearData() {
        mUserData = null;
    }

    public int getUserRole() {
        return userRole;
    }

    public boolean isAgent() {
        return userRole == ROLE_AGENT;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public String getUserRoleStr() {
        return isAgent() ? AGENT : CUSTOMER;
    }

    public User getUserData() {
        try {
            if (mUserData == null) {
                String user = AppSharedPreferences.getInstance(AppClass.getContext()).getUserData();
                if (user != null) {
                    setUserData(new Gson().fromJson(user, User.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mUserData;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserData(User mUserData) {
        this.mUserData = mUserData;
        setUserRole(mUserData.getUsertype().equals(CUSTOMER) ? ROLE_CUSTOMER : ROLE_AGENT);
    }

    public Register getRegisterTemp() {
        return RegisterTemp;
    }

    public void setRegisterTemp(Register registerTemp) {
        RegisterTemp = registerTemp;
    }
}
