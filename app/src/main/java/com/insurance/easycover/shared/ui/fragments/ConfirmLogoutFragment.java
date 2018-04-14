package com.insurance.easycover.shared.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.insurance.easycover.AppSession;
import com.insurance.easycover.R;
import com.insurance.easycover.agent.ui.activities.AgentHomeActivity;
import com.insurance.easycover.customer.ui.activities.CustomerHomeActivity;
import com.insurance.easycover.data.local.AppSharedPreferences;
import com.insurance.easycover.shared.ui.activities.LoginActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import naveed.khakhrani.miscellaneous.base.BaseFragment;

/**
 * Created by PDC100 on 3/13/2018.
 */

public class ConfirmLogoutFragment extends BaseFragment {
    private Unbinder mUnbinder;

    public static final String TAG = SettingFragment.class.getCanonicalName();

    public static ConfirmLogoutFragment newInstance() {

        Bundle args = new Bundle();

        ConfirmLogoutFragment fragment = new ConfirmLogoutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirm_logout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
    }


    @OnClick(R.id.confirm_YES)
    public void onClickYES() {
        AppSharedPreferences.getInstance(getContext()).clearData();
        AppSession.getInstance().clearData();
        launchActivity(LoginActivity.class);
        getActivity().finish();
    }

    @OnClick(R.id.confirm_NO)
    public void onClickNO() {
        if (AppSession.getInstance().getUserRole() == AppSession.ROLE_AGENT) {
            launchActivity(AgentHomeActivity.class);
        } else launchActivity(CustomerHomeActivity.class);
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public void onDestroyView() {
        if (mUnbinder != null)
            mUnbinder.unbind();
        super.onDestroyView();
    }
}
