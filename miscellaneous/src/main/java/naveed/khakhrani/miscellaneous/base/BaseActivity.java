package naveed.khakhrani.miscellaneous.base;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import naveed.khakhrani.miscellaneous.R;
import naveed.khakhrani.miscellaneous.util.BundleConstants;


/**
 * Created by NaveedAli on 1/24/2017.
 */


public class BaseActivity extends AppCompatActivity implements LocationListener {


    protected BaseFragment mCurrentFragment;
    protected BaseFragment previousFragment;
    protected ProgressDialog mProgressDialog;

    public  static final int RequestPermissionCode  = 1 ;
    Context context;
    Intent intent1 ;
    Location location;
    LocationManager locationManager ;
    boolean GpsStatus = false ;
    Criteria criteria ;
    String Holder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);

        EnableRuntimePermission();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        criteria = new Criteria();

        Holder = locationManager.getBestProvider(criteria, false);

        context = getApplicationContext();

        //CheckGpsStatus();

        getLocation();

        //Log.i("FCM toked","token: "+ NotificationFirebaseInstanceService.getInstance().getToken());

       /* if (!(this instanceof Splash)) {
            String deviceToken = FirebaseInstanceId.getInstance().getToken();
            if (deviceToken == null || deviceToken.isEmpty()) return;
            if (!AppSharedPreferences.getInstance(this).isSyncedDeviceToken(deviceToken)) {
                new RegisterDeviceAsync(this).execute(FirebaseInstanceId.getInstance().getToken());
            }
        }*/

    }


   /* @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }*/

    public void launchActivity(Class<?> className) {
        launchActivity(className, null);
    }

    public void launchActivity(Class<?> className, Bundle bundle) {
        Intent i = new Intent(this, className);
        if (bundle != null)
            i.putExtra(BundleConstants.BUNDLE, bundle);
        startActivity(i);
    }

    public void addFragment(BaseFragment fragment, int containerId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.add(containerId, fragment);
        fragmentTransaction.addToBackStack(fragment.getFragmentTag());
        fragmentTransaction.commit();
        previousFragment = mCurrentFragment;
        mCurrentFragment = fragment;
    }

    public void changeFragment(BaseFragment fragment, int containerId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerId, fragment);
        fragmentTransaction.commit();
        mCurrentFragment = fragment;
    }

    public void changeFragment(BaseFragment fragment, boolean addToStack, int childContainerId) {
        changeFragment(fragment, childContainerId);
    }

    public void showProgressDialog(String message) {
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing())
                return;
            else {
                mProgressDialog.setMessage(message);
                mProgressDialog.show();
            }
        } else {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }

    }

    public void showProgressDialog(int stringResId) {
        showProgressDialog(getString(stringResId));
    }

    public void dismissProgress() {
        if (mProgressDialog == null || !mProgressDialog.isShowing())
            return;
        mProgressDialog.dismiss();
    }

    public void showToast(int stringId) {
        Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {

//        textViewLongitude.setText("Longitude:" + location.getLongitude());
//        textViewLatitude.setText("Latitude:" + location.getLatitude());
        Toast.makeText(BaseActivity.this,Double.toString(location.getLongitude()), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void CheckGpsStatus(){

        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    //Toast.makeText(BaseActivity.this,"Permission Granted, Now your application can access GPS.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(BaseActivity.this,"Permission Canceled, Now your application cannot access GPS.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    public void getLocation() {
        CheckGpsStatus();

        if(GpsStatus == true) {
            if (Holder != null) {
                if (ActivityCompat.checkSelfPermission(
                        BaseActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        &&
                        ActivityCompat.checkSelfPermission(BaseActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                location = locationManager.getLastKnownLocation(Holder);
                locationManager.requestLocationUpdates(Holder, 3000, 0, BaseActivity.this);
            }
        }else {

            showToast("Please Enable GPS First");

        }
    }

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(BaseActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION))
        {

            Toast.makeText(BaseActivity.this,"ACCESS_FINE_LOCATION permission allows us to Access GPS in app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(BaseActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, RequestPermissionCode);

        }
    }

}
