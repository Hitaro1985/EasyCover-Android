package com.insurance.easycover.shared.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.insurance.easycover.AppSession;
import com.insurance.easycover.R;
import com.insurance.easycover.customer.ui.fragments.PostJobSuccessFragment;
import com.insurance.easycover.data.events.EventsIds;
import com.insurance.easycover.data.events.ListDataEvent;
import com.insurance.easycover.data.events.SingleDataEvent;
import com.insurance.easycover.data.models.ForgotPassword;
import com.insurance.easycover.data.models.response.Job;
import com.insurance.easycover.data.models.response.ResponseAcceptedJobs;
import com.insurance.easycover.data.models.response.ResponseGetAllJob;
import com.insurance.easycover.data.models.response.ResponseGetInsuranceType;
import com.insurance.easycover.data.models.response.ShowJob;
import com.insurance.easycover.data.network.NetworkController;
import com.insurance.easycover.shared.ui.adapters.NotificationAdapter;

import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import naveed.khakhrani.miscellaneous.base.ListBaseFragment;
import naveed.khakhrani.miscellaneous.util.Dummy;
import naveed.khakhrani.miscellaneous.util.NetworkConnection;

/**
 * A simple {@link Fragment} subclass.
 */

public class NotificationsFragment extends ListBaseFragment<Dummy> {


    private Unbinder mUnbinder = null;
    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;

    List<ShowJob> jobList;
    private List<ResponseAcceptedJobs> resultData;
    /*@BindView(R.id.tvLabelPrefer)
    protected TextView tvLabelPrefer;*/

    public NotificationsFragment() {
        // Required empty public constructor
    }

    public static NotificationsFragment newInstance() {
        //Bundle args = new Bundle();
        NotificationsFragment fragment = new NotificationsFragment();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_wall, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        initAdapter();
    }

    @Override
    protected void initAdapter() {
        //mData = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        //setDummyData();
        mRecyclerView.setLayoutManager(mLayoutManager);
        //mAdapter = new NotificationAdapter(getContext(),R.layout.item_notification, mData);
        //mRecyclerView.setAdapter(mAdapter);
        showProgressDialog(getString(R.string.please_wait));
        NetworkController.getInstance().getAcceptedJobList();
    }

    @Subscribe
    public void onNotificationEvent(ListDataEvent<ResponseAcceptedJobs> event) {
        if (event.getStatus()) {
            if (event.getEventId() == EventsIds.ID_ACCEPTEDJOB) {
                //ResponseCompletedJobs jobDetail = new ResponseCompletedJobs();
                /*for (int i = 0; i < event.getListData().size(); i ++){
                    jobDetail.jobId = event.getListData().get(i).getJobId();
                    NetworkController.getInstance().getJobDetail(jobDetail);
                }*/
                NetworkController.getInstance().getInsuranceType();
                resultData = event.getListData();
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
    public void onGetInsuraceEvent(ListDataEvent<ResponseGetInsuranceType> event) {
        if (event.getStatus()) {
            if (event.getEventId() == EventsIds.ID_GETINSURANCETYPE) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date now = Calendar.getInstance().getTime();
                for (int i = 0; i < resultData.size(); i ++) {
                    String dtStart = resultData.get(i).getExpiredDate();
                    try {
                        Date date = format.parse(dtStart);
                        long diff = date.getTime() - now.getTime();
                        long diffDays = diff / (24 * 60 * 60 * 1000);
                        if (diffDays < 0) {
                            resultData.remove(i);
                        }
                        else if(diffDays > 30) {
                            resultData.remove(i);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < resultData.size(); i ++) {
                    Integer selectItem = Integer.parseInt(resultData.get(i).getInsuranceType());
                    resultData.get(i).setInsuranceType(event.getListData().get(selectItem - 1).getInsuranceName());
                }
                NetworkController.getInstance().getAllAssignedJobs();
                NotificationAdapter adapter = new NotificationAdapter(getContext(), resultData);
                mRecyclerView.setAdapter(adapter);
                adapter.setRecyclerViewItemSelectedListener(this);
                dismissProgress();
            }
        }
    }

    /*@Subscribe
    public void onEvent(ListDataEvent<ShowJob> event) {
        if (event.getStatus()) {
            if (event.getEventId() == EventsIds.ID_GETALL_JOB) {
                //Job job = event.getListData().get(0);
                jobList = event.getListData();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date now = Calendar.getInstance().getTime();
                for (int i = 0; i < jobList.size(); i ++) {
                    String dtStart = jobList.get(i).getExpiredDate();
                    try {
                        Date date = format.parse(dtStart);
                        long diff = date.getTime() - now.getTime();
                        long diffDays = diff / (24 * 60 * 60 * 1000);
                        if (diffDays < 0) {
                            jobList.remove(i);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                NotificationAdapter adapter = new NotificationAdapter(getContext(), jobList);
                mRecyclerView.setAdapter(adapter);
                dismissProgress();
                //launchActivity(JobPostSuccessActivity.class);
                //changeFragment(PostJobSuccessFragment.newInstance(), R.id.fragmentContainer);
                //getActivity().finish();
            } else {
                dismissProgress();
                showToast(event.getMessage());
            }
        } else {
            dismissProgress();
            showToast(event.getMessage());
        }
    }*/

    @Override
    public void onItemSelected(Object item, int position) {
        changeFragment(NotiDetailFragment.newInstance(item), R.id.fragmentContainer);
    }

    @Override
    public void onDestroy() {
        if (mUnbinder != null)
            mUnbinder.unbind();
        super.onDestroy();
    }

    public void setDummyData() {

        for (int i = 0; i < 3; i++) {
            Dummy dummy = new Dummy();
            if (i == 1) {
                //tvLabelPrefer.setText("kkk");
                dummy.setName("Okay");
            }
            mData.add(dummy);
        }
    }

    @Override
    protected void changeButtonsColor() {
        int newColor = ContextCompat.getColor(getContext(),
                AppSession.getInstance().getUserRole() == AppSession.ROLE_AGENT ?
                        R.color.colorPrimaryAgent : R.color.colorPrimaryCustomer);
        //btnLogin.setBackgroundColor(newColor);

    }
}