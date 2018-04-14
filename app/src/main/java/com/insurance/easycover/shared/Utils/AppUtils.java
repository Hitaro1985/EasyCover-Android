package com.insurance.easycover.shared.Utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by naveedali on 12/4/17.
 */

public class AppUtils {
    public static boolean isPermissionGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean arePermissionGranted(Context context, String... permissions) {
        boolean areGranted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                areGranted = false;
            }
        }

        return areGranted;
    }
}
