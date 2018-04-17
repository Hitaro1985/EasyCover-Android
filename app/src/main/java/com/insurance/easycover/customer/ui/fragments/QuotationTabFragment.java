package com.insurance.easycover.customer.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.insurance.easycover.R;
import com.insurance.easycover.data.events.EventsIds;
import com.insurance.easycover.data.events.ListDataEvent;
import com.insurance.easycover.data.events.SingleDataEvent;
import com.insurance.easycover.data.models.response.ResponseGetInsuranceType;
import com.insurance.easycover.data.models.response.ResponseGetQuotation;
import com.insurance.easycover.data.network.NetworkController;
import com.insurance.easycover.shared.ui.adapters.QuotationAdapter;

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
public class QuotationTabFragment extends ListBaseFragment<Dummy> {


    private Unbinder mUnbinder = null;
    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;
    private List<ResponseGetQuotation> resultData;

    public QuotationTabFragment() {
        // Required empty public constructor
    }

    public static QuotationTabFragment newInstance() {
        //Bundle args = new Bundle();
        QuotationTabFragment fragment = new QuotationTabFragment();
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
    protected void changeButtonsColor() {

    }

    @Override
    protected void initAdapter() {
        //mData = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        //setDummyData();
        mRecyclerView.setLayoutManager(mLayoutManager);
        //mAdapter = new QuotationAdapter(getContext(), mData);
        //mRecyclerView.setAdapter(mAdapter);
        showProgressDialog(getString(R.string.please_wait));
        NetworkController.getInstance().getQuotation();
    }

    @Subscribe
    public void onEvent(ListDataEvent<ResponseGetQuotation> event){
        if (event.getStatus()) {
            if (event.getEventId() == EventsIds.ID_GETQUOTATION) {
                NetworkController.getInstance().getInsuranceType();
                resultData = event.getListData();
            } else {
                showToast(event.getMessage());
            }
        } else {
            showToast(event.getMessage());
        }
    }

    @Subscribe
    public void onGetInsuraceEvent(ListDataEvent<ResponseGetInsuranceType> event) {
        if (event.getStatus()) {
            if (event.getEventId() == EventsIds.ID_GETINSURANCETYPE) {
                List<ResponseGetQuotation> tempList;
                tempList = new ArrayList<ResponseGetQuotation>();
                for (int i = 0; i < resultData.size(); i++) {
                    //Log.i("aaaaaaaa", resultData.get(i).getInsuranceType());
                    Integer selectItem = Integer.parseInt(resultData.get(i).getInsuranceType());
                    resultData.get(i).setInsuranceType(event.getListData().get(selectItem - 1).getInsuranceName());
                    if (resultData.get(i).getJobstatus() != null) {
                        if (resultData.get(i).getJobstatus().equals("3")) {
                            tempList.add(resultData.get(i));
                        }
                    }
                }
                resultData = tempList;
                QuotationAdapter adapter = new QuotationAdapter(getContext(), resultData);
                mRecyclerView.setAdapter(adapter);
                adapter.setRecyclerViewItemSelectedListener(this);
                dismissProgress();
            }
        }
    }


    @Override
    public void onItemSelected(Object item, int position) {
        changeFragment(QuotationDetailFragment.newInstance(item), R.id.fragmentContainer);
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
