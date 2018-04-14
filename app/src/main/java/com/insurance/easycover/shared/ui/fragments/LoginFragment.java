package com.insurance.easycover.shared.ui.fragments;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.insurance.easycover.AppSession;
import com.insurance.easycover.R;
import com.insurance.easycover.agent.ui.activities.AgentHomeActivity;
import com.insurance.easycover.customer.ui.activities.CustomerHomeActivity;
import com.insurance.easycover.data.events.ListDataEvent;
import com.insurance.easycover.data.local.AppSharedPreferences;
import com.insurance.easycover.data.models.Login;
import com.insurance.easycover.data.models.response.User;
import com.insurance.easycover.data.network.NetworkController;

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
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends BaseFragment {


    protected Unbinder mUnBinder = null;
    private ValidationHelper validationHelper;

    @BindView(R.id.edtEmailAddress)
    protected EditText editEmailAddress;
    @BindView(R.id.edtPassword)
    protected EditText edtPassword;

    @BindView(R.id.btnLogin)
    protected AppButton btnLogin;


    public LoginFragment() {

    }

    public static LoginFragment newInstance() {

        //Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        view.setBackground(ContextCompat.getDrawable(getContext(), AppSession.getInstance().isAgent() ? R.drawable.bg_agent_login : R.drawable.bg_customer_login));
        validationHelper = new ValidationHelper(getContext());
    }


    @OnClick(R.id.btnLogin)
    protected void onClickLogin() {
        if (isValidate()) {
            requestRemote();
        }
    }

    private void requestRemote() {
        if (NetworkConnection.isConnection(getActivity())) {
            showProgressDialog(getString(R.string.please_wait));
            Login login = new Login();
            login.email = editEmailAddress.getText().toString();
            login.password = edtPassword.getText().toString();
            AppSession.getInstance().setPassword(login.password);
            login.deviceToken = "12345";
            //login.deviceToken = FirebaseInstanceId.getInstance().getToken();
            NetworkController.getInstance().login(login);
        } else showToast(R.string.no_internet);


    }

    @OnClick(R.id.btnDoNotHaveAccount)
    protected void onClickRegisterNow() {
        changeFragment(SignUpFragment.newInstance(), R.id.fragmentContainer);
    }

    @OnClick(R.id.btnForgotPassword)
    protected void onClickForgotPassword() {
        changeFragment(SendEmailFragment.newInstance(), R.id.fragmentContainer);
    }

    private boolean isValidate() {
        boolean isValidate = true;

        if (!validationHelper.isEmailValid(editEmailAddress)) {
            isValidate = false;
        }
        if (!validationHelper.isPasswordValid(edtPassword)) {
            isValidate = false;
        }
        return isValidate;
    }


    @Subscribe
    public void onEvent(ListDataEvent<User> event) {
        if (event.getStatus()) {
            if (event.listData != null && !event.listData.isEmpty()) {
                AppSharedPreferences.getInstance(getContext()).saveUserData(new Gson().toJson(event.listData.get(0)));
                AppSession.getInstance().setUserData(event.listData.get(0));
                if (AppSession.getInstance().getUserRole() == AppSession.ROLE_AGENT) {
                    launchActivity(AgentHomeActivity.class);
                } else launchActivity(CustomerHomeActivity.class);
                getActivity().finish();
            } else {
                showToast("" + event.getMessage());
            }
        } else showToast("" + event.getMessage());
        dismissProgress();
    }


    @Override
    public void onDestroy() {
        if (mUnBinder != null)
            mUnBinder.unbind();
        super.onDestroy();
    }
}
