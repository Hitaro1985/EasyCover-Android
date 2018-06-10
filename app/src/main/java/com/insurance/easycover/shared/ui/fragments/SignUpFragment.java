package com.insurance.easycover.shared.ui.fragments;


import android.Manifest;
import android.app.Service;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
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
import com.insurance.easycover.data.models.response.User;
import com.insurance.easycover.data.network.NetworkController;

import org.androidannotations.annotations.App;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

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

import static org.greenrobot.eventbus.EventBus.TAG;

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

    private String name;
    private String email;
    private String pass;
    private String contact;
    private String cpass;

    @BindView(R.id.btnRegister)
    protected AppButton btnRegister;
    private ValidationHelper mValidationHelper;
    private Register register;

    private Bundle state;

    @BindView(R.id.checkBox1)
    protected CheckBox checkBox;

    @BindView(R.id.textView2)
    protected TextView textView;

    public SignUpFragment() {
        // Required empty public constructor
        register = new Register();
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
        state = savedInstanceState;
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

        edtUserName.setText(name);
        edtEmail.setText(email);
        edtPass.setText(pass);
        edtConfirmPass.setText(cpass);
        edtContact.setText(contact);

        checkBox.setText("");
        textView.setText(Html.fromHtml("I accept " +
                "<a href='id.web.freelancer.example.TCActivity://Kode'>TERMS AND CONDITIONS</a>"));
        textView.setClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        mValidationHelper = new ValidationHelper(getContext());
    }

    private boolean isValidate() {
        boolean isSuccess = true;
        if (!checkBox.isChecked()) {
            isSuccess = false;
            showToast("please accept terms and conditions");
        }
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppSession.getInstance().getRegisterTemp() != null) {
            this.name = AppSession.getInstance().getRegisterTemp().getUsername();
            this.email = AppSession.getInstance().getRegisterTemp().getEmail();
            this.pass = AppSession.getInstance().getRegisterTemp().getPassword();
            this.contact = AppSession.getInstance().getRegisterTemp().getPhoneno();
            this.cpass = AppSession.getInstance().getRegisterTemp().getPassword();
            AppSession.getInstance().setRegisterTemp(null);
        }
    }

    private void requestRegister() {
        try {
            if (NetworkConnection.isConnection(getActivity())) {
                register.email = edtEmail.getText().toString();
                register.password = edtPass.getText().toString();
                register.phoneno = edtContact.getText().toString();
                //register.username = edtUserFirstName.getText().toString() + edtUserSurName.getText().toString();
                register.username = edtUserName.getText().toString();
                register.userType = AppSession.getInstance().getUserRoleStr();
                if (FirebaseInstanceId.getInstance().getToken() == null) {
                    register.deviceToken = "null";
                } else {
                    register.deviceToken = FirebaseInstanceId.getInstance().getToken();
                }
                register.verifyToken = "12345";
                register.latitude = (long)AppSession.getInstance().getLongitude();
                register.longitude = (long)AppSession.getInstance().getLatitude();
                //register.latitude = AppSession.getInstance().getLatitude();
                //register.longitude = AppSession.getInstance().getLongitude();
                String json = new Gson().toJson(register);
                showProgressDialog(getString(R.string.please_wait));
                Log.i("signup", "json = " + new Gson().toJson(register));
                NetworkController.getInstance().emailExist(register);
                //NetworkController.getInstance().signUp(register);
            } else showToast(R.string.no_internet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Subscribe
    public void onEvent(SimpleEvent event) {
        if (event.getStatus()) {
            if (event.getEventId() == EventsIds.ID_EMAILEXIST) {
                dismissProgress();
                AppSession.getInstance().setRegisterTemp(register);
                changeFragment(VerifyUserFragment.newInstance(register),R.id.fragmentContainer);
            } else {
                showToast(event.getMessage());
                dismissProgress();
            }
        } else {
            showToast(event.getMessage());
            dismissProgress();
        }
    }




    @Override
    public void onDestroy() {
        if (mUnBinder != null)
            mUnBinder.unbind();
        super.onDestroy();

    }

}
