package com.insurance.easycover.shared.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.insurance.easycover.R;
import com.insurance.easycover.data.models.response.Job;
import com.insurance.easycover.data.models.response.ResponseAcceptedJobs;
import com.insurance.easycover.data.models.response.ShowJob;
import com.insurance.easycover.shared.ui.fragments.NotificationsFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import naveed.khakhrani.miscellaneous.base.RecyclerBaseAdapter;
import naveed.khakhrani.miscellaneous.listeners.RecyclerViewItemSelectedListener;
import naveed.khakhrani.miscellaneous.util.Dummy;

/**
 * Created by naveedali on 10/10/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.JobViewHolder> {

    private Context mCtx;
    //private List<Dummy> events;
    private List<ResponseAcceptedJobs> jobList;


    protected RecyclerViewItemSelectedListener recyclerViewItemSelectedListener;


    public RecyclerViewItemSelectedListener getRecyclerViewItemSelectedListener() {
        return recyclerViewItemSelectedListener;
    }

    public void setRecyclerViewItemSelectedListener(RecyclerViewItemSelectedListener recyclerViewItemSelectedListener) {
        this.recyclerViewItemSelectedListener = recyclerViewItemSelectedListener;
    }

    //getting the context and product list with constructor
    public NotificationAdapter(Context mCtx, List<ResponseAcceptedJobs> productList) {
        this.mCtx = mCtx;
        this.jobList = productList;
    }

    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_notification, null);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JobViewHolder holder, final int position) {
        //getting the product of the specified position
        ResponseAcceptedJobs job = jobList.get(position);

        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerViewItemSelectedListener != null) {
                    recyclerViewItemSelectedListener.onItemSelected(jobList.get(position), position, 0);
                } else
                    Toast.makeText(mCtx, "Work In progress", Toast.LENGTH_SHORT).show();
            }
        });

        //binding the data with the viewholder views
        holder.tvLabelPrefer.setText(job.getName());
        holder.tvName.setText(job.getInsuranceType());
        holder.tvExDate.setText(job.getExpiredDate());
        String dtStart = job.getExpiredDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date now = Calendar.getInstance().getTime();
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = format.parse(dtStart.trim());
            long diff = date.getTime() - now.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            String remainDay = String.valueOf(diffDays);
            holder.tvRemainDate.setText(remainDay);
            String monthString  = (String) DateFormat.format("MMM",  date);
            String year         = (String) DateFormat.format("yyyy", date);
            holder.tvDate.setText(monthString + ", " + year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return jobList.size();
    }

    class JobViewHolder extends RecyclerView.ViewHolder {
        TextView tvLabelPrefer;
        TextView tvName;
        TextView tvExDate;
        TextView tvRemainDate;
        TextView tvDate;

        @BindView(R.id.layoutRoot)
        public RelativeLayout layoutRoot;

        public JobViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvLabelPrefer = itemView.findViewById(R.id.tvLabelPrefer);
            tvName = itemView.findViewById(R.id.tvName);
            tvExDate = itemView.findViewById(R.id.tvExDate);
            tvRemainDate = itemView.findViewById(R.id.tvRemainDay);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}

/*public class NotificationAdapter extends RecyclerBaseAdapter<Dummy, NotificationAdapter.ViewHolder> {

    private final Context mContext;
    private List<Dummy> events;
    private List<Job> mJob;

    public NotificationAdapter(Context context,int layoutResourceId, List<Dummy> data, List<Job> job) {
        //super(context, layoutResourceId, data);
        mDataList = data;
        mContext = context;
        mJob = job;
    }

    public void refreshEvents(List<Dummy> data) {
        this.events.clear();
        this.events.addAll(events);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        ViewHolder vh = new ViewHolder(v, mJob.get(0));
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvLabelPrefer;

        public ViewHolder(View itemView, Job job) {
            super(itemView);
            tvLabelPrefer = itemView.findViewById(R.id.tvLabelPrefer);
            tvLabelPrefer.setText(job.getName());
            //ButterKnife.bind(this, itemView);
        }
    }
}*/
