package com.insurance.easycover.shared.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.insurance.easycover.AppSession;
import com.insurance.easycover.R;

import naveed.khakhrani.miscellaneous.base.BaseFragment;
import naveed.khakhrani.miscellaneous.util.BundleConstants;

/**
 * Created by NaveedAli on 4/20/2017.
 */

public class TutorialFragment extends BaseFragment {

    public static TutorialFragment getNewInstance(int backgroundResource, String textDescription) {
        TutorialFragment tutorialFragment = new TutorialFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BundleConstants.RESOURCE_ID, backgroundResource);
        bundle.putString(BundleConstants.DESCRIPTION, textDescription);
        tutorialFragment.setArguments(bundle);
        return tutorialFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_tutorial, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ImageView imvBg = (ImageView) view.findViewById(R.id.imvBg);
        //final ImageView imvTutorial = (ImageView) view.findViewById(R.id.imvTutorial);
        final TextView tvDescription = (TextView) view.findViewById(R.id.tvTutorialText);
        int res = getArguments().getInt(BundleConstants.RESOURCE_ID);
        imvBg.setBackgroundColor(res);
        String description = getArguments().getString(BundleConstants.DESCRIPTION);
        tvDescription.setText(description);
    }

    @Override
    protected void changeButtonsColor() {
        int newColor = ContextCompat.getColor(getContext(),
                AppSession.getInstance().getUserRole() == AppSession.ROLE_AGENT ?
                        R.color.colorPrimaryAgent : R.color.colorPrimaryCustomer);
        //btnLogin.setBackgroundColor(newColor);

    }
}
