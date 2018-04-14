package com.insurance.easycover.customer.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.insurance.easycover.AppSession;
import com.insurance.easycover.R;
import com.insurance.easycover.agent.ui.activities.AgentHomeActivity;
import com.insurance.easycover.data.events.EventsIds;
import com.insurance.easycover.data.events.ListDataEvent;
import com.insurance.easycover.data.events.SimpleEvent;
import com.insurance.easycover.data.events.SingleDataEvent;
import com.insurance.easycover.data.models.response.HandOverData;
import com.insurance.easycover.data.models.response.ResponseHandOverData;
import com.insurance.easycover.data.models.response.assignJob.JobAssignJob;
import com.insurance.easycover.data.models.response.assignJob.ResponseAssignJob;
import com.insurance.easycover.data.network.NetworkController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import naveed.khakhrani.miscellaneous.base.BaseActivity;

public class JobPostSuccessActivity extends BaseActivity {


    @BindView(R.id.tvTitle)
    protected TextView tvTitle;

    @BindView(R.id.rightIv)
    protected ImageView rightIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(AppSession.getInstance().getUserRole() == AppSession.ROLE_AGENT ? R.style.AppThemeAgent : R.style.AppThemeCustomer);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_post_success);
        ButterKnife.bind(this);
        tvTitle.setText("Check your mailbox");
        rightIv.setImageResource(R.drawable.ic_arrow_back);
        rightIv.setVisibility(View.GONE);

    }
    @OnClick(R.id.rightIv)
    public void onClickleft() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        dismissProgress();
        launchActivity(CustomerHomeActivity.class);
    }

    @OnClick(R.id.btnDone)
    public void onClickDone() {
        showProgressDialog(getString(R.string.please_wait));
        NetworkController.getInstance().assignJob();
    }

    @Subscribe
    public void onEvent(ListDataEvent<JobAssignJob> event) {
        if (event.getStatus()) {
            if (event.getEventId() == EventsIds.ID_ASSIGNJOB) {
                //dismissProgress();
                HandOverData handover = new HandOverData();
                handover.jobid = AppSession.getInstance().getLastCreateJobID();
                handover.customerid = AppSession.getInstance().getUserData().getId();
                for ( int i = 0; i < event.getListData().size(); i ++) {
                    handover.agentid= event.getListData().get(i).getId();
                    //event.getListData().get(i).getData().get(0).getId();
                    Log.i("handover", "json = " + new Gson().toJson(handover));
                    NetworkController.getInstance().handover(handover);
                }
                dismissProgress();
                launchActivity(CustomerHomeActivity.class);
            }  else {
                dismissProgress();
                showToast(event.getMessage());
            }
        } else {
            dismissProgress();
            showToast(event.getMessage());
        }
    }

    @Subscribe
    public void onEvent(SingleDataEvent<ResponseHandOverData> event) {
        if (event.getStatus()) {
            if (event.getEventId() == EventsIds.ID_HANDOVER) {
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
}
