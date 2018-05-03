package com.insurance.easycover.shared.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.insurance.easycover.AppSession;
import com.insurance.easycover.R;
import com.insurance.easycover.data.local.AppSharedPreferences;
import com.insurance.easycover.shared.ui.fragments.ForgotPasswordFragment;
import com.insurance.easycover.shared.ui.fragments.LoginFragment;
import com.insurance.easycover.shared.ui.fragments.SignUpFragment;
import com.insurance.easycover.shared.ui.fragments.VerifyUserFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import naveed.khakhrani.miscellaneous.base.BaseActivity;
import naveed.khakhrani.miscellaneous.base.BaseFragment;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tvTitle)
    protected TextView tvTitle;

    @BindView(R.id.leftIv)
    protected ImageView leftIv;
    @BindView(R.id.viewActionBar)
    protected View viewActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(AppSession.getInstance().getUserRole() == AppSession.ROLE_AGENT ? R.style.AppThemeAgent : R.style.AppThemeCustomer);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        changeFragment(LoginFragment.newInstance(), R.id.fragmentContainer);

    }

    @Override
    public void onBackPressed() {
        if (mCurrentFragment instanceof VerifyUserFragment) {
            finish();
        } else if (mCurrentFragment instanceof SignUpFragment) {
            changeFragment(LoginFragment.newInstance(), R.id.fragmentContainer);
        } else if (mCurrentFragment instanceof ForgotPasswordFragment) {
            changeFragment(LoginFragment.newInstance(), R.id.fragmentContainer);
        } else {
            launchActivity(ChooseRoleActivity.class);
            finish();
        }
        //super.onBackPressed();
    }

    @Override
    public void changeFragment(BaseFragment fragment, int containerId) {
        super.changeFragment(fragment, containerId);
        mCurrentFragment = fragment;
        if (fragment instanceof LoginFragment) {
            tvTitle.setText(R.string.login);
            leftIv.setVisibility(View.GONE);
            viewActionBar.setVisibility(View.GONE);
        } else if (fragment instanceof SignUpFragment) {
            tvTitle.setText(R.string.sign_up);
            leftIv.setVisibility(View.VISIBLE);
            viewActionBar.setVisibility(View.VISIBLE);

        } else if (fragment instanceof ForgotPasswordFragment) {
            tvTitle.setText(R.string.forgot_password);
            leftIv.setVisibility(View.VISIBLE);
            viewActionBar.setVisibility(View.VISIBLE);
        }
    }

    /**/
    @OnClick(R.id.leftIv)
    protected void onClickLeftMenuBtn() {
        if (mCurrentFragment instanceof VerifyUserFragment) {
            changeFragment(SignUpFragment.newInstance(), R.id.fragmentContainer);
        } else if (mCurrentFragment instanceof LoginFragment) {
            onBackPressed();
        } else if (mCurrentFragment instanceof SignUpFragment) {
            changeFragment(LoginFragment.newInstance(), R.id.fragmentContainer);

        } else if (mCurrentFragment instanceof ForgotPasswordFragment) {
            changeFragment(LoginFragment.newInstance(), R.id.fragmentContainer);
        }
    }
}
