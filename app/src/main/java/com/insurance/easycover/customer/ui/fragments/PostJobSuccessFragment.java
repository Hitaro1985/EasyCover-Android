package com.insurance.easycover.customer.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.insurance.easycover.R;

import butterknife.OnClick;
import naveed.khakhrani.miscellaneous.base.BaseFragment;

/**
 * Created by PDC100 on 3/13/2018.
 */

public class PostJobSuccessFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_post_success, container, false);
    }

    public PostJobSuccessFragment() {
    }

    public static PostJobSuccessFragment newInstance() {

        Bundle args = new Bundle();

        PostJobSuccessFragment fragment = new PostJobSuccessFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.btnDone,R.id.leftIv})
    public void onClickDone() {
        super.onBackPressed();
    }

    /*@OnClick(R.id.leftIv)
    protected void onClickLeft() {
        changeFragment(CreateJobFragment.newInstance(),R.id.fragmentContainer);
    }*/
}
