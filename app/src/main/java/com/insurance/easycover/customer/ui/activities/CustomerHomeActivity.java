package com.insurance.easycover.customer.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.insurance.easycover.AppSession;
import com.insurance.easycover.R;
import com.insurance.easycover.shared.ui.activities.HomeActivity;
import com.insurance.easycover.shared.ui.activities.ProfileActivity;
import com.insurance.easycover.shared.ui.fragments.NotificationsFragment;
import com.insurance.easycover.customer.ui.fragments.AdvertisementFragment;
import com.insurance.easycover.customer.ui.fragments.PurchasedFragment;
import com.insurance.easycover.customer.ui.fragments.QuotationTabFragment;

/**
 * Created by naveedali on 10/18/17.
 */

public class CustomerHomeActivity extends HomeActivity {

    private int[] tabsValues = new int[]{R.string.home, R.string.notification, R.string.qoutation, R.string.purchased};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTabs();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (AppSession.getInstance().getUserData().getImage() != null) {
            if (!AppSession.getInstance().getUserData().getImage().equals("null")) {
                Glide.with(CustomerHomeActivity.this).load(AppSession.getInstance().getUserData().getImage()).apply(RequestOptions.circleCropTransform()).into(imvUser);
            }
        }

    }

    @Override
    protected void initTabs() {
        mTabLayout.setTabTextColors(ContextCompat.getColor(this,R.color.colorTabUnselectedCustomer), Color.parseColor("#ffffff"));

        for (int resId : tabsValues) {
            TabLayout.Tab tab = mTabLayout.newTab().setText(getString(resId));
            mTabLayout.addTab(tab);
        }
        mTabLayout.addOnTabSelectedListener(this);
        changeFragment(AdvertisementFragment.newInstance(), R.id.fragmentContainer);
    }


    // Event Handling


    @Override
    protected void onClickNotification() {
        super.onClickNotification();
        if (mTabLayout.getSelectedTabPosition() == 1) {
            return;
        } else {
            TabLayout.Tab tab = mTabLayout.getTabAt(1);
            tab.select();
        }
    }

    @Override
    protected void onClickedUserImage() {
        launchActivity(ProfileActivity.class);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switchFragment(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    protected void switchFragment(int position) {
        switch (position) {
            case 0:
                changeFragment(AdvertisementFragment.newInstance(), R.id.fragmentContainer);
                break;
            case 1:
                changeFragment(NotificationsFragment.newInstance(), R.id.fragmentContainer);
                break;
            case 2:
                changeFragment(QuotationTabFragment.newInstance(), R.id.fragmentContainer);
                break;
            case 3:
                changeFragment(PurchasedFragment.newInstance(), R.id.fragmentContainer);
                break;
            default:
                break;
        }
    }
}
