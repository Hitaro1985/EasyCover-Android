package com.insurance.easycover.agent.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.insurance.easycover.AppSession;
import com.insurance.easycover.R;

import butterknife.ButterKnife;
import naveed.khakhrani.miscellaneous.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReloadCreditFragment extends BaseFragment {


    public ReloadCreditFragment() {
        // Required empty public constructor
    }

    public static ReloadCreditFragment newInstance() {

        Bundle args = new Bundle();

        ReloadCreditFragment fragment = new ReloadCreditFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reload_credit, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }


}
