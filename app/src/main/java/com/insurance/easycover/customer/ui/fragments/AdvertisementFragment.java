package com.insurance.easycover.customer.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.insurance.easycover.R;
import com.insurance.easycover.customer.ui.activities.JobCreateActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import naveed.khakhrani.miscellaneous.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvertisementFragment extends BaseFragment {


    private Unbinder mUnbinder = null;

    @BindView(R.id.customerView)
    protected RelativeLayout customerView;

    public AdvertisementFragment() {
        // Required empty public constructor
    }

    public static AdvertisementFragment newInstance() {

        Bundle args = new Bundle();

        AdvertisementFragment fragment = new AdvertisementFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_advertisement, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);


    }

    @OnClick(R.id.btnTryNow)
    protected void onClickTryNow() {
        launchActivity(JobCreateActivity.class);
    }


    @Override
    public void onDestroy() {
        if (mUnbinder != null)
            mUnbinder.unbind();
        super.onDestroy();
    }

    @Override
    protected void changeButtonsColor() {
    }
}
