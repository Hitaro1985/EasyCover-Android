package com.insurance.easycover.shared.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.insurance.easycover.AppSession;
import com.insurance.easycover.R;
import com.insurance.easycover.agent.ui.activities.AgentHomeActivity;
import com.insurance.easycover.customer.ui.activities.CustomerHomeActivity;
import com.insurance.easycover.shared.ui.fragments.TutorialFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;
import naveed.khakhrani.miscellaneous.base.BaseActivity;

/**
 * Created by NaveedAli on 4/20/2017.
 */

public class TutorialActivity extends BaseActivity {

    Fragment fragmentIntro1;// = TutorialFragment.getNewInstance(R.drawable.app_bg);
    Fragment fragmentIntro2;// = TutorialFragment.getNewInstance(R.drawable.app_bg);
    Fragment fragmentIntro3;
    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;

    protected String TAG = "TutorialActivity";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(AppSession.getInstance().getUserRole() == AppSession.ROLE_AGENT ? R.style.AppThemeAgent : R.style.AppThemeCustomer);

        setContentView(R.layout.activity_tutorial);
        ButterKnife.bind(this);


        fragmentIntro1 = TutorialFragment.getNewInstance(Color.RED, getString(R.string.app_name));
        fragmentIntro2 = TutorialFragment.getNewInstance(Color.GREEN, getString(R.string.app_name));
        fragmentIntro3 = TutorialFragment.getNewInstance(Color.BLUE, getString(R.string.app_name));

        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

    }


    @OnClick(R.id.btnNext)
    protected void onClickNext() {
        int position = mPager.getCurrentItem();
        if (position == 2) {
            if (AppSession.getInstance().isAgent()) {
                launchActivity(AgentHomeActivity.class);
            } else {
                launchActivity(CustomerHomeActivity.class);
            }
            finish();
        } else {
            position++;
            mPager.setCurrentItem(position);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }


    @Override
    protected void onStart() {
        super.onStart();
        //EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        //EventBus.getDefault().unregister(this);
        super.onStop();
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return fragmentIntro1;
            } else if (position == 1) {
                return fragmentIntro2;
            } else {
                return fragmentIntro3;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
