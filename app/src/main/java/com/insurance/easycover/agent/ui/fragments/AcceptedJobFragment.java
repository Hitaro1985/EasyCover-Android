package com.insurance.easycover.agent.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;

import com.insurance.easycover.R;
import com.insurance.easycover.data.events.EventsIds;
import com.insurance.easycover.data.events.ListDataEvent;
import com.insurance.easycover.data.models.response.RequestJobDetail;
import com.insurance.easycover.data.models.response.ResponseAcceptedJobs;
import com.insurance.easycover.data.models.response.ResponseAssJob;
import com.insurance.easycover.data.models.response.ResponseCompletedJobs;
import com.insurance.easycover.data.models.response.ResponseGetInsuranceType;
import com.insurance.easycover.data.network.NetworkController;
import com.insurance.easycover.shared.ui.adapters.HistoryAdapter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import naveed.khakhrani.miscellaneous.base.ListBaseFragment;
import naveed.khakhrani.miscellaneous.util.Dummy;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcceptedJobFragment extends ListBaseFragment<Dummy> {


    private Unbinder mUnbinder = null;
    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;
    private List<ResponseAcceptedJobs> resultData;

    public AcceptedJobFragment() {
        // Required empty public constructor
    }

    public static AcceptedJobFragment newInstance() {
        //Bundle args = new Bundle();
        AcceptedJobFragment fragment = new AcceptedJobFragment();
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
        //mAdapter = new HistoryAdapter(getContext(), mData);
        //mRecyclerView.setAdapter(mAdapter);
        showProgressDialog(getString(R.string.please_wait));
        NetworkController.getInstance().getAcceptedJobList();//agent/accepted...
    }

    @Subscribe
    public void onEvent(ListDataEvent<ResponseAcceptedJobs> event) {
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
                showToast(event.getMessage());
                dismissProgress();
            }
        } else {
            showToast(event.getMessage());
            dismissProgress();
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
                HistoryAdapter adapter = new HistoryAdapter(getContext(),resultData);
                mRecyclerView.setAdapter(adapter);
                adapter.setRecyclerViewItemSelectedListener(this);
                dismissProgress();
            }
        }
    }

    @Override
    public void onItemSelected(Object item, int position) {
        changeFragment(AcceptedJobDetailFragment.newInstance(item), R.id.fragmentContainer);
        //changeChildFragment(AcceptedJobDetailFragment.newInstance(item), R.id.childFragmentContainer);
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


}
