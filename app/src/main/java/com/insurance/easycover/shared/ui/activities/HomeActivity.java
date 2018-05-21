package com.insurance.easycover.shared.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.insurance.easycover.AppSession;
import com.insurance.easycover.R;
import com.insurance.easycover.customer.ui.activities.JobPostSuccessActivity;
import com.insurance.easycover.shared.ui.fragments.SettingFragment;
import com.insurance.easycover.agent.ui.fragments.JobWallDetailFragment;
import com.insurance.easycover.agent.ui.fragments.JobWallFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import naveed.khakhrani.miscellaneous.base.BaseActivity;

public abstract class HomeActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.tabs)
    protected TabLayout mTabLayout;


    @BindView(R.id.tvTitle)
    protected TextView tvTitle;
    @BindView(R.id.tvSubTitle)
    protected TextView tvSubTitle;

    @BindView(R.id.leftIv)
    protected ImageView imvLeft;
    @BindView(R.id.imvUser)
    protected ImageView imvUser;
    @BindView(R.id.imvSettings)
    protected ImageView imvSettings;
    @BindView(R.id.imvNotification)
    protected ImageView imvNotification;
    @BindView(R.id.imvNotificationDot)
    protected ImageView imvNotificationDot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(AppSession.getInstance().getUserRole() == AppSession.ROLE_AGENT ? R.style.AppThemeAgent : R.style.AppThemeCustomer);
        super.onCreate(savedInstanceState);
        /*final IntentFilter myFilter = new

        IntentFilter(BroadcastService.ACTION_FROM_SERVICE);

        registerReceiver(mReceiver, myFilter);*/

        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        if (AppSession.getInstance().getUserRole() == AppSession.ROLE_AGENT) {
            tvSubTitle.setText("" + AppSession.getInstance().getUserData().getUsername());
            tvTitle.setText("Dashboard");
        } else {
            tvSubTitle.setText(AppSession.getInstance().getUserData().getUsername());
        }

        imvLeft.setVisibility(View.GONE);
    }

    private BroadcastReceiver broadcastReceiverLoadTodays = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getApplicationContext(), intent.getExtras().getString("message"), Toast.LENGTH_SHORT).show();
            if (intent.getExtras().getString("message").equals("New Notification")) {
                imvNotificationDot.setVisibility(View.VISIBLE);
                AppSession.getInstance().setNewNoti(true);
            }
        }
    };

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiverLoadTodays, new IntentFilter("update-message"));
        super.onResume();
        tvSubTitle.setText(AppSession.getInstance().getUserData().getUsername());
        if (AppSession.getInstance().getUserData().getImage() != null && !AppSession.getInstance().getUserData().getImage().isEmpty()) {
            RequestOptions cropOptions = new RequestOptions().fitCenter();
            //Glide.with(this).load(AppSession.getInstance().getUserData().image).apply(cropOptions).into(imvUser);
        /*    Glide.with(this).load(AppSession.getInstance().getUserData().image).apply(cropOptions).apply(RequestOptions.overrideOf(imvUser.getWidth(), imvUser.getHeight()))
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_user))
                    .into(imvUser);*/
           /* Glide.with(this).load(AppSession.getInstance().getUserData().image).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    showToast("failed");
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    showToast("success");
                    imvUser.setImageDrawable(resource);
                    return false;
                }
            });*/

            //Glide.with(this).load("").

        }
    }

    protected abstract void initTabs();


    protected abstract void switchFragment(int position);


    @OnClick(R.id.imvSettings)
    protected void onClickSettings() {
        // launchActivity(JobPostSuccessActivity.class);
        addFragment(new SettingFragment(), R.id.fragmentContainer);
        mTabLayout.setVisibility(View.GONE);
        imvNotification.setVisibility(View.GONE);
        imvNotificationDot.setVisibility(View.GONE);
    }

    @OnClick(R.id.leftIv)
    protected void onClickleftItem() {
        Toast.makeText(this, "In Progress", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.imvNotification)
    protected void onClickNotification() {
        imvNotificationDot.setVisibility(View.GONE);
        AppSession.getInstance().setNewNoti(false);
    }


    @OnClick(R.id.imvUser)
    protected abstract void onClickedUserImage();


    @Override
    public void onBackPressed() {
        if (mCurrentFragment instanceof SettingFragment) {
            getSupportFragmentManager().popBackStack();
            mCurrentFragment = previousFragment;
            mTabLayout.setVisibility(View.VISIBLE);
            imvNotification.setVisibility(View.VISIBLE);
            if (AppSession.getInstance().isNewNoti()) imvNotificationDot.setVisibility(View.VISIBLE);
        } else if (mCurrentFragment instanceof JobWallDetailFragment) {
            changeFragment(JobWallFragment.newInstance(), R.id.fragmentContainer);
        } else
            super.onBackPressed();
    }
}
