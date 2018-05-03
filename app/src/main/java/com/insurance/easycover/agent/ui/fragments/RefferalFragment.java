package com.insurance.easycover.agent.ui.fragments;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.insurance.easycover.R;
import com.insurance.easycover.data.events.EventsIds;
import com.insurance.easycover.data.events.ListDataEvent;
import com.insurance.easycover.data.events.SingleDataEvent;
import com.insurance.easycover.data.models.response.ResponseReferralCode;
import com.insurance.easycover.data.network.NetworkController;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import naveed.khakhrani.miscellaneous.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RefferalFragment extends BaseFragment {


    private Unbinder mUnbinder = null;
    @BindView(R.id.agentView)
    protected LinearLayout agentView;

    @BindView(R.id.tvReferralCode)
    protected TextView tvReferralCode;

    static Context mContext;

    public RefferalFragment() {
        // Required empty public constructor
    }

    public static RefferalFragment newInstance(Context mcontext) {

        Bundle args = new Bundle();
        mContext = mcontext;

        RefferalFragment fragment = new RefferalFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_refferal, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        showProgressDialog(getString(R.string.please_wait));
        NetworkController.getInstance().getReferralCode();
    }

    @Subscribe
    public void onEvent(SingleDataEvent<ResponseReferralCode> event) {
        if (event.getStatus()) {
            if (event.getEventId() == EventsIds.ID_GETREFERRALCODE) {
                tvReferralCode.setText(event.data.getReferralCode());
                dismissProgress();
                showToast(event.getMessage());
            } else {
                dismissProgress();
                showToast(event.getMessage());
            }
        } else {
            dismissProgress();
            showToast(event.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        if (mUnbinder != null)
            mUnbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.shareCode)
    public void onClickDone() {
        ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", tvReferralCode.getText());
        clipboard.setPrimaryClip(clip);
        showToast("Referral code copied");
    }
}
