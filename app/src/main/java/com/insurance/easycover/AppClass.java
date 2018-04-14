package com.insurance.easycover;

import android.app.Application;
import android.content.Context;

/**
 * Created by naveedali on 12/4/17.
 */

public class AppClass extends Application {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }


}
