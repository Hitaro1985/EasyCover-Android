package com.insurance.easycover.shared.ui.fragments;


import android.media.Image;
import android.net.Network;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.google.gson.Gson;
import com.insurance.easycover.AppSession;
import com.insurance.easycover.R;
import com.insurance.easycover.agent.ui.activities.AgentHomeActivity;
import com.insurance.easycover.customer.ui.activities.CustomerHomeActivity;
import com.insurance.easycover.data.events.EventsIds;
import com.insurance.easycover.data.events.ListDataEvent;
import com.insurance.easycover.data.events.SimpleEvent;
import com.insurance.easycover.data.local.AppSharedPreferences;
import com.insurance.easycover.data.models.Register;
import com.insurance.easycover.data.models.Verify;
import com.insurance.easycover.data.models.response.RequestSMS;
import com.insurance.easycover.data.models.response.User;
import com.insurance.easycover.data.network.NetworkController;
import com.insurance.easycover.shared.ui.activities.TutorialActivity;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import naveed.khakhrani.miscellaneous.base.BaseFragment;
import naveed.khakhrani.miscellaneous.util.NetworkConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerifyUserFragment extends BaseFragment {

    private Unbinder mUnbinder;
    @BindView(R.id.edtVerifyCode)
    protected EditText edtVerifyCode;
    @BindView(R.id.btnResendCode)
    protected Button btnResendCode;
    @BindView(R.id.btnVerify)
    protected Button btnVerify;

    protected ImageView LeftIv;

    public static Register register;

    public VerifyUserFragment() {
        // Required empty public constructor
    }


    public static VerifyUserFragment newInstance(Register reg) {
        //Bundle args = new Bundle();
        VerifyUserFragment fragment = new VerifyUserFragment();
        register = reg;
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verify_user, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        edtVerifyCode.setEnabled(false);
        btnVerify.setEnabled(false);
        //LeftIv = (ImageView) view.findViewById(R.id.leftIv);
    }

    @OnClick(R.id.btnResendCode)
    public void onClickSendCode() {
        RequestSMS rsms = new RequestSMS();
        rsms.phoneno = register.phoneno;
        rsms.email = register.email;
        showProgressDialog(R.string.please_wait);
        NetworkController.getInstance().sendSMS(rsms);
        //LeftIv.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnVerify)
    public void onClickVerify() {
        if(isValidate()) {
            Verify verify = new Verify();
            verify.phoneno = register.phoneno;
            verify.verifyToken = edtVerifyCode.getText().toString();
            showProgressDialog(R.string.please_wait);
            NetworkController.getInstance().verifyPhone(verify);
        }

    }

    public boolean isValidate() {
        boolean isvalidate = true;

        if (edtVerifyCode.getText().toString().isEmpty()) {
            edtVerifyCode.setError(getString(R.string.required));
            isvalidate = false;
        }

        if (edtVerifyCode.getText().length() != 6) {
            edtVerifyCode.setError(getString(R.string.invalid_input));
            isvalidate = false;
        }

        return isvalidate;
    }

    @Subscribe
    public void onEvent(SimpleEvent event) {
        if (event.getStatus()) {
            if (event.getEventId() == EventsIds.ID_SENDSMS) {
                dismissProgress();
                showToast(event.getMessage());
                btnResendCode.setText(R.string.resend);
                edtVerifyCode.setEnabled(true);
                btnVerify.setEnabled(true);
            } else if (event.getEventId() == EventsIds.ID_VERIFY) {
                showToast(event.getMessage());
                showProgressDialog(R.string.please_wait);
                NetworkController.getInstance().signUp(register);
            } else {
                dismissProgress();
                showToast(event.getMessage());
            }
        } else {
            dismissProgress();
            showToast(event.getMessage());
        }
    }

    @Subscribe
    public void onEvent(ListDataEvent<User> event) {
        if (event.getStatus()) {
            if (event.listData != null && !event.listData.isEmpty()) {
                AppSharedPreferences.getInstance(getContext()).saveUserData(new Gson().toJson(event.listData.get(0)));
                AppSession.getInstance().setUserData(event.listData.get(0));
                //AppSharedPreferences.getInstance(getContext()).saveUser(edtUserName.getText().toString());
                //AppSession.getInstance().setSurName(edtUserSurName.getText().toString());
                //changeFragment(new VerifyUserFragment(), R.id.fragmentContainer);
                showToast(R.string.welcome);
                launchActivity(AppSession.getInstance().isAgent() ? AgentHomeActivity.class : CustomerHomeActivity.class);
                getActivity().finish();
            } else showToast(event.getMessage());
        } else {
            if (event.getMessage().equals("Signup is failed")) {
                showToast("Your email already registered.");
            } else {
                showToast(event.getMessage());
            }
        }

        dismissProgress();
    }

    @Override
    public void onDestroyView() {
        if (mUnbinder != null)
            mUnbinder.unbind();
        super.onDestroyView();

    }
}
