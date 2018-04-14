package com.insurance.easycover.agent.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.insurance.easycover.R;
import com.insurance.easycover.agent.ui.activities.AgentHomeActivity;
import com.insurance.easycover.customer.ui.activities.CustomerHomeActivity;
import com.insurance.easycover.data.events.EventsIds;
import com.insurance.easycover.data.events.ListDataEvent;
import com.insurance.easycover.data.models.response.RequestJobDetail;
import com.insurance.easycover.data.models.response.ResponseAssJob;
import com.insurance.easycover.data.models.response.ResponseCompletedJobs;
import com.insurance.easycover.data.models.response.ResponseGetInsuranceType;
import com.insurance.easycover.data.models.response.ShowJob;
import com.insurance.easycover.data.network.NetworkController;
import com.insurance.easycover.shared.ui.adapters.HistoryCompletedAdapter;
import com.insurance.easycover.shared.ui.adapters.JobWallAdapter;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import naveed.khakhrani.miscellaneous.base.ListBaseFragment;
import naveed.khakhrani.miscellaneous.util.Dummy;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobWallFragment extends ListBaseFragment<Dummy> {


    private Unbinder mUnbinder = null;
    public List<ResponseCompletedJobs> resultData;

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;
    List<ShowJob> jobList;
    JobWallAdapter adapter;

    public JobWallFragment() {
        // Required empty public constructor
    }

    public static JobWallFragment newInstance() {
        //Bundle args = new Bundle();
        JobWallFragment fragment = new JobWallFragment();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //adapter = new JobWallAdapter(getContext(), jobList);
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
        //mAdapter = new JobWallAdapter(getContext(), mData);
        //mRecyclerView.setAdapter(mAdapter);
        //mAdapter.setRecyclerViewItemSelectedListener(this);
        showProgressDialog(getString(R.string.please_wait));
        NetworkController.getInstance().getAllAssignedJobs();//agent/allview
    }

    @Subscribe
    public void onEvent(ListDataEvent<ResponseCompletedJobs> event) {
        if (event.getStatus()) {
            if (event.getEventId() == EventsIds.ID_ALLASSIGNEDJOBS) {
//                RequestJobDetail jobDetail = new RequestJobDetail();
//                for (int i = 0; i < event.getListData().size(); i ++){
//                    jobDetail.jobId = event.getListData().get(i).getJobId();
//                    NetworkController.getInstance().getJobDetail(jobDetail);
//                }
                //HistoryAdapter adapter = new HistoryAdapter(getContext(),event.getListData());
                //mRecyclerView.setAdapter(adapter);
                NetworkController.getInstance().getInsuranceType();
                resultData = event.getListData();
                //dismissProgress();
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
                    //Log.i("aaaaaaaa", resultData.get(i).getInsuranceType());
                    Integer selectItem = Integer.parseInt(resultData.get(i).getInsuranceType());
                    resultData.get(i).setInsuranceType(event.getListData().get(selectItem - 1).getInsuranceName());
                }
                JobWallAdapter adapter = new JobWallAdapter(getContext(),resultData);
                mRecyclerView.setAdapter(adapter);
                adapter.setRecyclerViewItemSelectedListener(this);
                dismissProgress();
            }
        }
    }

//    @Subscribe
//    public void onJobDetailEvent(ListDataEvent<ShowJob> event) {
//        if (event.getStatus()) {
//            if (event.getEventId() == EventsIds.ID_GETJOBDETAIL) {
//                ShowJob job = event.getListData().get(0);
//                adapter.addAdapter(job);
//                mRecyclerView.setAdapter(adapter);
//            } else {
//                showToast(event.getMessage());
//            }
//        } else {
//            showToast(event.getMessage());
//        }
//    }

    @Override
    public void onItemSelected(Object item, int position) {
        if (getActivity() instanceof CustomerHomeActivity)
            ((CustomerHomeActivity) getActivity()).
                    changeFragment(JobWallDetailFragment.newInstance(item), R.id.fragmentContainer);
        else if (getActivity() instanceof AgentHomeActivity)
            ((AgentHomeActivity) getActivity()).
                    changeFragment(JobWallDetailFragment.newInstance(item), R.id.fragmentContainer);
    }


    @Override
    public void onDestroy() {
        if (mUnbinder != null)
            mUnbinder.unbind();
        super.onDestroy();
    }

//    public void setDummyData() {
//        for (int i = 0; i < 10; i++) {
//            Dummy dummy = new Dummy();
//            mData.add(dummy);
//        }
//    }


}
