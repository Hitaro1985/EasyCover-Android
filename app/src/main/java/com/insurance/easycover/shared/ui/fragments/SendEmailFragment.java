package com.insurance.easycover.shared.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.insurance.easycover.R;
import com.insurance.easycover.data.events.AlertEvent;
import com.insurance.easycover.data.events.SimpleEvent;
import com.insurance.easycover.data.models.ForgotPassword;
import com.insurance.easycover.data.network.NetworkController;
import com.insurance.easycover.shared.ui.dialogs.SingleButtonAlert;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import naveed.khakhrani.miscellaneous.base.BaseFragment;
import naveed.khakhrani.miscellaneous.util.AppButton;
import naveed.khakhrani.miscellaneous.util.NetworkConnection;
import naveed.khakhrani.miscellaneous.util.ValidationHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendEmailFragment extends BaseFragment {

    private Unbinder mUnBinder = null;

    @BindView(R.id.edtEmailAddress)
    protected EditText edtEmailAddress;
    @BindView(R.id.btnSend)
    protected AppButton btnSend;

    private ValidationHelper mValidationHelper;

    public SendEmailFragment() {
        // Required empty public constructor
    }


    public static SendEmailFragment newInstance() {

        //Bundle args = new Bundle();
        SendEmailFragment fragment = new SendEmailFragment();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send_email, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        mValidationHelper = new ValidationHelper(getContext());
    }


    //Event handling
    @OnClick(R.id.btnSend)
    protected void onClickDone() {
        if (!mValidationHelper.isEmailValid(edtEmailAddress)) {
            return;
        } else
            requestRemote();
        //showToast(R.string.work_in_progress);
    }


    private void requestRemote() {
        if (NetworkConnection.isConnection(getActivity())) {
            showProgressDialog(getString(R.string.please_wait));
            ForgotPassword forgotPassword = new ForgotPassword();
            forgotPassword.mEmail = edtEmailAddress.getText().toString();
            NetworkController.getInstance().forgetPassword(forgotPassword);
        } else showToast(R.string.no_internet);
    }

    @Subscribe
    public void onEvent(SimpleEvent simpleEvent) {
        if (simpleEvent.status) {
            showToast(getString(R.string.reset_password_message));
            dismissProgress();
            changeFragment(ForgotPasswordFragment.newInstance(edtEmailAddress.getText().toString()),R.id.fragmentContainer);
            //SingleButtonAlert.newInstance(getString(R.string.reset_password_message)).show(getFragmentManager(), null);
        } else showToast(simpleEvent.message);
        dismissProgress();
    }

    @Subscribe
    public void onEvent(AlertEvent event) {
        //getActivity().onBackPressed();
        changeFragment(ForgotPasswordFragment.newInstance(edtEmailAddress.getText().toString()),R.id.fragmentContainer);
    }

    @Override
    public void onDestroy() {
        if (mUnBinder != null)
            mUnBinder.unbind();
        super.onDestroy();

    }

}
