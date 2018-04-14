package com.insurance.easycover.shared.ui.fragments;


import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.insurance.easycover.data.models.Register;
import com.insurance.easycover.data.models.response.User;
import com.insurance.easycover.data.network.NetworkController;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import im.delight.android.location.SimpleLocation;
import naveed.khakhrani.miscellaneous.base.BaseActivity;
import naveed.khakhrani.miscellaneous.base.BaseFragment;
import naveed.khakhrani.miscellaneous.util.AppButton;
import naveed.khakhrani.miscellaneous.util.NetworkConnection;
import naveed.khakhrani.miscellaneous.util.ValidationHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends BaseFragment {

    private Unbinder mUnBinder;

    @BindView(R.id.edtReferralCode)
    protected EditText edtReferralCode;
//    @BindView(R.id.edtUserfirstName)
//    protected EditText edtUserFirstName;
//    @BindView(R.id.edtUserSurName)
//    protected EditText edtUserSurName;
    @BindView(R.id.edtUserName)
    protected EditText edtUserName;
    @BindView(R.id.edtPass)
    protected EditText edtPass;
    @BindView(R.id.edtConfirmPass)
    protected EditText edtConfirmPass;
    @BindView(R.id.edtEmail)
    protected EditText edtEmail;
    @BindView(R.id.edtContact)
    protected EditText edtContact;

    @BindView(R.id.btnRegister)
    protected AppButton btnRegister;
    private ValidationHelper mValidationHelper;
    private SimpleLocation location;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance() {

        //Bundle args = new Bundle();
        SignUpFragment fragment = new SignUpFragment();
        //fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        location = new SimpleLocation(getContext());
        // if we can't access the location yet
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(getContext());
        }
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);

        if (AppSession.getInstance().getUserRole() == AppSession.ROLE_AGENT) {
            //edtReferralCode.setVisibility(View.VISIBLE);
            edtReferralCode.setVisibility(View.GONE);
        } else edtReferralCode.setVisibility(View.GONE);

        mValidationHelper = new ValidationHelper(getContext());
    }

    private boolean isValidate() {
        boolean isSuccess = true;
        if (!mValidationHelper.isEmailValid(edtEmail)) isSuccess = false;
        if (!mValidationHelper.isPasswordValid(edtPass)) isSuccess = false;
        if (!mValidationHelper.isContactValid(edtContact)) isSuccess = false;
        //if (!mValidationHelper.isFullNameValid(edtFullName)) isSuccess = false;
        //if (!mValidationHelper.isUserNameValid(edtUserFirstName)) isSuccess = false;
        //if (!mValidationHelper.isUserNameValid(edtUserSurName)) isSuccess = false;
        if (!mValidationHelper.isUserNameValid(edtUserName)) isSuccess = false;
        String password = edtPass.getText().toString();
        if (!password.isEmpty() && !password.equals(edtConfirmPass.getText().toString())) {
            edtConfirmPass.setError(getString(R.string.password_mismatch));
            isSuccess = false;
        }

        //if (mValidationHelper.(edtEmail)) isSuccess = false;
        //if (mValidationHelper.isEmailValid(edtEmail)) isSuccess = false;

        return isSuccess;
    }

    // Event Handling
    @OnClick(R.id.btnRegister)
    protected void onClickRegister() {
        if (isValidate()) {
            requestRegister();
        }
    }


    private void requestRegister() {
        try {
            if (NetworkConnection.isConnection(getActivity())) {
                Register register = new Register();
                register.email = edtEmail.getText().toString();
                register.password = edtPass.getText().toString();
                register.phoneno = edtContact.getText().toString();
                //register.username = edtUserFirstName.getText().toString() + edtUserSurName.getText().toString();
                register.username = edtUserName.getText().toString();
                register.userType = AppSession.getInstance().getUserRoleStr();
                register.deviceToken = FirebaseInstanceId.getInstance().getToken();
                register.verifyToken = "12345";
                register.latitude = (long)location.getLatitude();
                register.longitude = (long)location.getLongitude();
                //register.latitude = AppSession.getInstance().getLatitude();
                //register.longitude = AppSession.getInstance().getLongitude();
                String json = new Gson().toJson(register);
                showProgressDialog(getString(R.string.please_wait));
                Log.i("signup", "json = " + new Gson().toJson(register));
                changeFragment(VerifyUserFragment.newInstance(register),R.id.fragmentContainer);
                //NetworkController.getInstance().signUp(register);
            } else showToast(R.string.no_internet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onDestroy() {
        if (mUnBinder != null)
            mUnBinder.unbind();
        super.onDestroy();

    }

}
