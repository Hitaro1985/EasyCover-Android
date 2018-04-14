package naveed.khakhrani.miscellaneous.base;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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


public class BaseActivity extends AppCompatActivity {


    protected BaseFragment mCurrentFragment;
    protected BaseFragment previousFragment;
    protected ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);


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

   /*  public void changeFragmentWithStack(BaseFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }*/


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

}
