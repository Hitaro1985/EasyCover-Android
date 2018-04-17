package com.insurance.easycover.customer.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.insurance.easycover.AppSession;
import com.insurance.easycover.R;
import com.insurance.easycover.customer.ui.activities.JobCreateActivity;
import com.insurance.easycover.data.events.EventsIds;
import com.insurance.easycover.data.events.ListDataEvent;
import com.insurance.easycover.data.events.SingleDataEvent;
import com.insurance.easycover.data.models.response.ResponseCompletedJobs;
import com.insurance.easycover.data.models.response.ResponseGetInsuranceType;
import com.insurance.easycover.data.models.response.ResponseHandOverData;
import com.insurance.easycover.data.models.response.ResponseOrderHistory;
import com.insurance.easycover.data.network.NetworkController;
import com.insurance.easycover.shared.ui.adapters.OrderHistoryAdapter;
import com.insurance.easycover.shared.ui.adapters.QuotationAdapter;
import com.insurance.easycover.shared.ui.fragments.NotiDetailFragment;

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
import naveed.khakhrani.miscellaneous.util.AppButton;
import naveed.khakhrani.miscellaneous.util.Dummy;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchasedFragment extends ListBaseFragment<Dummy> {


    private Unbinder mUnbinder = null;
    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;
    private List<ResponseCompletedJobs> resultData;
    public OrderHistoryAdapter adapter;

    public PurchasedFragment() {
        // Required empty public constructor
    }

    public static PurchasedFragment newInstance() {
        //Bundle args = new Bundle();
        PurchasedFragment fragment = new PurchasedFragment();
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
    public void onResume() {
        super.onResume();
    }


    @Override
    protected void initAdapter() {
        mData = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        //setDummyData();
        mRecyclerView.setLayoutManager(mLayoutManager);
        //mAdapter = new QuotationAdapter(getContext(), mData);
        //mRecyclerView.setAdapter(mAdapter);
        showProgressDialog(getString(R.string.please_wait));
        NetworkController.getInstance().getCustomerCompletedJob();
    }

    @Subscribe
    public void onEvent(SingleDataEvent<ResponseHandOverData> event) {
        if (event.getStatus()) {
            if (event.getEventId() == EventsIds.ID_HANDOVER) {
                showToast(event.getMessage());
                showProgressDialog(R.string.please_wait);
                NetworkController.getInstance().getCustomerCompletedJob();
            }
        } else {
            showToast(event.getMessage());
        }
    }

    @Subscribe
    public void onEvent(ListDataEvent<ResponseCompletedJobs> event) {
        if (event.getStatus()) {
            if (event.getEventId() == EventsIds.ID_GETCUSTOMERCOMPLETEDJOB) {
                //ResponseCompletedJobs jobDetail = new ResponseCompletedJobs();
                /*for (int i = 0; i < event.getListData().size(); i ++){
                    jobDetail.jobId = event.getListData().get(i).getJobId();
                    NetworkController.getInstance().getJobDetail(jobDetail);
                }*/
                resultData = event.getListData();
                NetworkController.getInstance().getInsuranceType();
                showToast(event.getMessage());
            } else {
                dismissProgress();
                showToast(event.getMessage());
            }
        } else {
            if (event.getEventId() == EventsIds.ID_GETCUSTOMERCOMPLETEDJOB) {
                if (event.getMessage().equals("No job is completed by this agent")) {
                    resultData.clear();
                    adapter.notifyDataSetChanged();
                    dismissProgress();
                }
            } else {
                dismissProgress();
                showToast(event.getMessage());
            }
        }
    }

    @Subscribe
    public void onGetInsuraceEvent(ListDataEvent<ResponseGetInsuranceType> event) {
        if (event.getStatus()) {
            if (event.getEventId() == EventsIds.ID_GETINSURANCETYPE) {
                for (int i = 0; i < resultData.size(); i ++) {
                    Integer selectItem = Integer.parseInt(resultData.get(i).getInsuranceType());
                    resultData.get(i).setInsuranceType(event.getListData().get(selectItem - 1).getInsuranceName());
                }
                NetworkController.getInstance().getAllAssignedJobs();
                adapter = new OrderHistoryAdapter(getContext(),resultData);
                mRecyclerView.setAdapter(adapter);
                adapter.setRecyclerViewItemSelectedListener(this);
                adapter.notifyDataSetChanged();
                dismissProgress();
            }
        }
    }

    @Override
    public void onItemSelected(Object item, int position) {
        changeFragment(PurchasedDetailFragment.newInstance(item),R.id.fragmentContainer);
    }


    @Override
    public void onDestroy() {
        if (mUnbinder != null)
            mUnbinder.unbind();
        super.onDestroy();
    }

    public void setDummyData() {

        for (int i = 0; i < 10; i++) {
            Dummy dummy = new Dummy();
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
