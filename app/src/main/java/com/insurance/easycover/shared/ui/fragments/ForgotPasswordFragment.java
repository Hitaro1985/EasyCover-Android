package com.insurance.easycover.shared.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.insurance.easycover.AppSession;
import com.insurance.easycover.R;
import com.insurance.easycover.data.events.AlertEvent;
import com.insurance.easycover.data.events.SimpleEvent;
import com.insurance.easycover.data.models.ForgotPassword;
import com.insurance.easycover.data.models.response.RequestResetPassword;
import com.insurance.easycover.data.network.NetworkController;
import com.insurance.easycover.shared.ui.dialogs.SingleButtonAlert;

import org.androidannotations.annotations.App;
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
public class ForgotPasswordFragment extends BaseFragment {
    private Unbinder mUnBinder = null;

    @BindView(R.id.edtEmailAddress)
    protected EditText edtEmailAddress;

    @BindView(R.id.edtPass)
    protected EditText edtPass;

    @BindView(R.id.edtConfirmPass)
    protected EditText edtConfirmPass;

    @BindView(R.id.edtVerifyCode)
    protected EditText edtVerifyCode;

    @BindView(R.id.btnDone)
    protected AppButton btnDone;

    private ValidationHelper mValidationHelper;

    static String Email;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }


    public static ForgotPasswordFragment newInstance(String email) {

        //Bundle args = new Bundle();
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        Email = email;
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        mValidationHelper = new ValidationHelper(getContext());
        edtEmailAddress.setText(Email);
    }


    //Event handling
    @OnClick(R.id.btnDone)
    protected void onClickDone() {
        if (!mValidationHelper.isEmailValid(edtEmailAddress)) {
            return;
        } else
            requestRemote();
        //showToast(R.string.work_in_progress);
    }


    private void requestRemote() {
        if (isValidate()) {
            if (NetworkConnection.isConnection(getActivity())) {
                showProgressDialog(getString(R.string.please_wait));
                RequestResetPassword forgotPassword = new RequestResetPassword();
                forgotPassword.email = edtEmailAddress.getText().toString();
                forgotPassword.password = edtPass.getText().toString();
                forgotPassword.verifyCode = edtVerifyCode.getText().toString();
                if (AppSession.getInstance().getUserRole() == AppSession.ROLE_AGENT) {
                    forgotPassword.userrole = "agent";
                } else if (AppSession.getInstance().getUserRole() == AppSession.ROLE_CUSTOMER) {
                    forgotPassword.userrole = "customer";
                }
                NetworkController.getInstance().resetPassword(forgotPassword);
            } else showToast(R.string.no_internet);
        }
    }

    @Subscribe
    public void onEvent(SimpleEvent simpleEvent) {
        if (simpleEvent.status) {
            //SingleButtonAlert.newInstance(getString(R.string.reset_password_message)).show(getFragmentManager(), null);
            dismissProgress();
            showToast(getString(R.string.send_email_success));
            super.onBackPressed();
        } else showToast(simpleEvent.message);
        dismissProgress();
    }

    @Subscribe
    public void onEvent(AlertEvent event) {
        getActivity().onBackPressed();
    }

    @Override
    public void onDestroy() {
        if (mUnBinder != null)
            mUnBinder.unbind();
        super.onDestroy();

    }

    private boolean isValidate() {
        boolean isSuccess = true;
        if (!mValidationHelper.isPasswordValid(edtPass)) isSuccess = false;

        String password = edtPass.getText().toString();
        if (!password.isEmpty() && !password.equals(edtConfirmPass.getText().toString())) {
            edtConfirmPass.setError(getString(R.string.password_mismatch));
            isSuccess = false;
        }

        if (edtVerifyCode.getText().length() != 6) {
            edtVerifyCode.setError(getString(R.string.verifycode_mismatch));
        }

        return isSuccess;
    }

}
