package com.insurance.easycover.agent.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.insurance.easycover.AppSession;
import com.insurance.easycover.R;
import com.insurance.easycover.customer.ui.activities.CustomerHomeActivity;
import com.insurance.easycover.shared.ui.activities.HomeActivity;
import com.insurance.easycover.shared.ui.activities.ProfileActivity;
import com.insurance.easycover.shared.ui.fragments.NotificationsFragment;
import com.insurance.easycover.agent.ui.fragments.HistoryFragment;
import com.insurance.easycover.agent.ui.fragments.JobWallFragment;
import com.insurance.easycover.agent.ui.fragments.RefferalFragment;
import com.insurance.easycover.agent.ui.fragments.ReloadCreditFragment;

/**
 * Created by naveedali on 10/18/17.
 */

public class AgentHomeActivity extends HomeActivity {

    private int[] tabsValues = new int[]{R.string.home, R.string.notification, R.string.job_wall, R.string.history, R.string.reload_credit};
    //private int[] tabsValues = new int[]{R.string.home, R.string.job_wall, R.string.history, R.string.reload_credit};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTabs();
        imvNotification.setVisibility(View.VISIBLE);
    }


    @Override
    protected void initTabs() {
        for (int resId : tabsValues) {
            TabLayout.Tab tab = mTabLayout.newTab().setText(getString(resId));
            mTabLayout.addTab(tab);
        }
        mTabLayout.addOnTabSelectedListener(this);
        changeFragment(RefferalFragment.newInstance(this), R.id.fragmentContainer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppSession.getInstance().getUserData().getImage() != null) {
            if (!AppSession.getInstance().getUserData().getImage().equals("null")) {
                Glide.with(AgentHomeActivity.this).load(AppSession.getInstance().getUserData().getImage()).apply(RequestOptions.circleCropTransform()).into(imvUser);
            }
        }
    }


    // Event Handling

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
    protected void onClickedUserImage() {
        launchActivity(ProfileActivity.class);
    }

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
    protected void switchFragment(int position) {
        switch (position) {
            case 0:
                changeFragment(RefferalFragment.newInstance(this), R.id.fragmentContainer);
                break;
            case 1:
                changeFragment(NotificationsFragment.newInstance(), R.id.fragmentContainer);
                break;
            case 2:
                changeFragment(JobWallFragment.newInstance(), R.id.fragmentContainer);
                break;
            case 3:
                changeFragment(HistoryFragment.newInstance(1), R.id.fragmentContainer);
                break;
            case 4:
                changeFragment(ReloadCreditFragment.newInstance(), R.id.fragmentContainer);
                break;
            default:
                break;

        }
    }
}
