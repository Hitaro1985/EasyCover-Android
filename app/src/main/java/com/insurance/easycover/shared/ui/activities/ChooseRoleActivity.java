package com.insurance.easycover.shared.ui.activities;

import android.os.Bundle;
import android.view.WindowManager;

import com.insurance.easycover.R;
import com.insurance.easycover.shared.ui.fragments.ChooseRoleFragment;

import naveed.khakhrani.miscellaneous.base.BaseActivity;

public class ChooseRoleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_customer);
        changeFragment(ChooseRoleFragment.newInstance(),R.id.fragmentContainer);
    }
}
