package com.insurance.easycover.agent.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.insurance.easycover.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import naveed.khakhrani.miscellaneous.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {


    private Unbinder mUnbinder = null;

    @BindView(R.id.tabs)
    protected TabLayout mTabLayout;

    private int containerId;
    private int[] tabsValues = {R.string.accepted_jobs, R.string.completed_jobs};

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance() {
        //Bundle args = new Bundle();
        HistoryFragment fragment = new HistoryFragment();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        containerId = R.id.childFragmentContainer;
        initTabs();
        changeChildFragment(AcceptedJobFragment.newInstance(), containerId);

    }

    protected void initTabs() {
        for (int resId : tabsValues) {
            TabLayout.Tab tab = mTabLayout.newTab().setText(getString(resId));
            mTabLayout.addTab(tab);
        }
        mTabLayout.addOnTabSelectedListener(this);
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


    protected void switchFragment(int position) {
        switch (position) {
            case 0:
                changeChildFragment(AcceptedJobFragment.newInstance(), containerId);
                break;
            case 1:
                changeChildFragment(CompletedFragment.newInstance(), containerId);
                break;
            default:
                break;

        }

    }


    @Override
    public void onDestroy() {
        if (mUnbinder != null)
            mUnbinder.unbind();
        super.onDestroy();
    }


}
