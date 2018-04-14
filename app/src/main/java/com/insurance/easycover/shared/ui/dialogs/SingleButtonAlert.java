package com.insurance.easycover.shared.ui.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.insurance.easycover.R;
import com.insurance.easycover.data.events.AlertEvent;
import com.insurance.easycover.shared.Utils.BundleConsts;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by naveedali on 11/21/17.
 */

public class SingleButtonAlert extends DialogFragment {

    private Unbinder mUnBinder;
    @BindView(R.id.tvAlert)
    protected TextView tvAlert;

    public static SingleButtonAlert newInstance(String message) {

        Bundle args = new Bundle();
        args.putString(BundleConsts.DESCRIPTION, message);
        SingleButtonAlert fragment = new SingleButtonAlert();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_single_btn, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);


        if (getArguments() != null) {
            tvAlert.setText(getArguments().getString(BundleConsts.DESCRIPTION));

        }

    }

    @OnClick(R.id.btnAlert)
    public void onClickDone() {
        dismiss();
        AlertEvent alertEvent = new AlertEvent();
        alertEvent.isPositive = true;
        EventBus.getDefault().post(alertEvent);
    }

    @Override
    public void onDestroyView() {
        if (mUnBinder != null) mUnBinder.unbind();
        super.onDestroyView();
    }
}
