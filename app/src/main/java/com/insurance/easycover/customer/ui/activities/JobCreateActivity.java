package com.insurance.easycover.customer.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.insurance.easycover.AppSession;
import com.insurance.easycover.R;
import com.insurance.easycover.customer.ui.fragments.CreateJobFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import naveed.khakhrani.miscellaneous.base.BaseActivity;

public class JobCreateActivity extends BaseActivity {


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
        setContentView(R.layout.activity_create_job);
        ButterKnife.bind(this);
        changeFragment(CreateJobFragment.newInstance(), R.id.fragmentContainer);

        if (AppSession.getInstance().getUserRole() == AppSession.ROLE_AGENT) {
            tvSubTitle.setText("" + AppSession.getInstance().getUserData().getUsername());
            tvTitle.setText("Dashboard");
        } else {
            tvSubTitle.setText(AppSession.getInstance().getUserData().getUsername());
        }

        imvLeft.setVisibility(View.VISIBLE);
        imvLeft.setImageResource(R.drawable.ic_arrow_back);
        imvSettings.setVisibility(View.GONE);
        imvNotification.setVisibility(View.GONE);
        imvNotificationDot.setVisibility(View.GONE);
        if (AppSession.getInstance().getUserData().getImage() != null) {
            if (!AppSession.getInstance().getUserData().getImage().equals("null")) {
                Glide.with(JobCreateActivity.this).load(AppSession.getInstance().getUserData().getImage()).apply(RequestOptions.circleCropTransform()).into(imvUser);
            }
        }


        if (AppSession.getInstance().getUserData().getImage() == null) {
            Glide.with(this).load(AppSession.getInstance().getUserData().getImage()).apply(RequestOptions.circleCropTransform()).into(imvUser);
        }
        /*if (AppSession.getInstance().getUserRole() == AppSession.ROLE_AGENT){
            tvSubTitle.setText("CPAJ003242");
            tvTitle.setText("Jack Ma");
        }*/

    }


    @OnClick(R.id.leftIv)
    public void onClickLeftImv() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
