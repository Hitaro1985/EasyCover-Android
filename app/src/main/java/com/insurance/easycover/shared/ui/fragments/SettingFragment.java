package com.insurance.easycover.shared.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.insurance.easycover.AppSession;
import com.insurance.easycover.R;
import com.insurance.easycover.data.local.AppSharedPreferences;
import com.insurance.easycover.shared.ui.activities.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import naveed.khakhrani.miscellaneous.base.BaseFragment;

/**
 * Created by naveedali on 11/17/17.
 */

public class SettingFragment extends BaseFragment {
    private Unbinder mUnbinder;

    public static final String TAG = SettingFragment.class.getCanonicalName();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
    }


    @OnClick(R.id.btnLogout)
    public void onClickLogout() {
        /*AppSharedPreferences.getInstance(getContext()).clearData();
        AppSession.getInstance().clearData();
        launchActivity(LoginActivity.class);
        getActivity().finish();*/
        //addFragment(new ConfirmLogoutFragment(), R.id.fragmentContainer);
        changeFragment(ConfirmLogoutFragment.newInstance(),R.id.fragmentContainer);
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
