package com.insurance.easycover.shared.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.insurance.easycover.AppSession;
import com.insurance.easycover.R;
import com.insurance.easycover.shared.ui.activities.LoginActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import naveed.khakhrani.miscellaneous.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseRoleFragment extends BaseFragment {


    private Unbinder mUnbinder = null;

    public ChooseRoleFragment() {
        // Required empty public constructor
    }

    public static ChooseRoleFragment newInstance() {
        ChooseRoleFragment fragment = new ChooseRoleFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_role, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
    }



    @OnClick(R.id.btnAgentRole)
    protected void onClickAgent() {
        AppSession.getInstance().setUserRole(AppSession.ROLE_AGENT);
        //changeChildFragment(LoginFragment.newInstance(), R.id.fragmentContainer);
        launchActivity(LoginActivity.class);
        getActivity().finish();
    }

    @OnClick(R.id.btnCustomerRole)
    protected void onClickCustomer() {
        AppSession.getInstance().setUserRole(AppSession.ROLE_CUSTOMER);
        //changeChildFragment(LoginFragment.newInstance(), R.id.fragmentContainer);
        launchActivity(LoginActivity.class);
        getActivity().finish();
    }


    @Override
    public void onDestroy() {
        if (mUnbinder!=null)
            mUnbinder.unbind();
        super.onDestroy();
    }

    @Override
    protected void changeButtonsColor() {


    }



}
