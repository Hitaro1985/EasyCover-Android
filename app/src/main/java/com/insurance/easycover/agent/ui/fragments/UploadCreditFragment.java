package com.insurance.easycover.agent.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.insurance.easycover.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import naveed.khakhrani.miscellaneous.base.BaseFragment;

/**
 * Created by naveedali on 12/19/17.
 */

public class UploadCreditFragment extends BaseFragment {


    private Unbinder mUnbinder;
    //@BindView(R.id)

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upload_credit, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) mUnbinder.unbind();
    }
}
