package com.insurance.easycover.shared.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.insurance.easycover.R;
import com.insurance.easycover.data.local.AppSharedPreferences;
import com.insurance.easycover.data.models.response.ResponseCompletedJobs;
import com.insurance.easycover.data.models.response.ShowJob;
import com.insurance.easycover.shared.Utils.DownLoadImageTask;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import naveed.khakhrani.miscellaneous.base.RecyclerBaseAdapter;
import naveed.khakhrani.miscellaneous.listeners.RecyclerViewItemSelectedListener;
import naveed.khakhrani.miscellaneous.util.Dummy;

/**
 * Created by naveedali on 10/10/17.
 */

/*public class JobWallAdapter extends RecyclerView.Adapter<JobWallAdapter.ViewHolder>{

    private Context mCtx;
    private List<ResponseCompletedJobs> jobList;

    public JobWallAdapter(Context mCtx, List<ShowJob> productList) {
        this.mCtx = mCtx;
        this.jobList = productList;
    }

    public void addAdapter(ShowJob addjob) {
        jobList.add(addjob);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_job_wall, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerViewItemSelectedListener != null)
                    recyclerViewItemSelectedListener.onItemSelected(mDataList.get(position), position);
            }
        });

        ShowJob job = jobList.get(position);
        holder.tvLanguage.setText(AppSharedPreferences.getInstance(mCtx).getCurrentLanguage());
        holder.tvName.setText(job.getInsuranceType());
        holder.tvPostCode.setText(job.getPostcode());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvLanguage;
        TextView tvName;
        TextView tvPostCode;
        @BindView(R.id.layoutRoot)
        public RelativeLayout layoutRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvLanguage = itemView.findViewById(R.id.tvLanguage);
            tvPostCode = itemView.findViewById(R.id.tvPostCode);
        }
    }
}*/

public class JobWallAdapter extends RecyclerView.Adapter<JobWallAdapter.ViewHolder>{

    private Context mCtx;
    private List<ResponseCompletedJobs> jobList;

    protected RecyclerViewItemSelectedListener recyclerViewItemSelectedListener;


    public RecyclerViewItemSelectedListener getRecyclerViewItemSelectedListener() {
        return recyclerViewItemSelectedListener;
    }

    public void setRecyclerViewItemSelectedListener(RecyclerViewItemSelectedListener recyclerViewItemSelectedListener) {
        this.recyclerViewItemSelectedListener = recyclerViewItemSelectedListener;
    }

    public JobWallAdapter(Context context, List<ResponseCompletedJobs> data) {
        this.mCtx = context;
        this.jobList = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_history, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ResponseCompletedJobs job;
        job = jobList.get(position);
        /*Dummy obj = mDataList.get(position);
        Log.d("dumm", "" + obj.name);*/

        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerViewItemSelectedListener != null) {
                    recyclerViewItemSelectedListener.onItemSelected(jobList.get(position), position);
                } else
                    Toast.makeText(mCtx, "Work In progress", Toast.LENGTH_SHORT).show();
            }
        });
        holder.tvName.setText(job.getInsuranceType());
        //holder.tvLanguage.setText(AppSharedPreferences.getInstance(mCtx).getCurrentLanguage());
        holder.tvPostCode.setText(job.getPostcode());
        holder.tvCountry.setText(job.getCountry());
        if (job.getImage() != null) {
            if (!job.getImage().equals("null")) {
                new DownLoadImageTask(holder.imvProfile).execute(job.getImage());
            }
        }
        String dtStart = job.getUpdatedAt();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(dtStart);
            Date now = Calendar.getInstance().getTime();
            long diff = now.getTime() - date.getTime();
            Log.i("updatedAt", job.getUpdatedAt());
            Log.i("now", now.toString());
            Log.i("diff", String.valueOf(diff));
            String SinceDate = String.valueOf("Since ");
            long diffDays = diff / (24 * 60 * 60 * 1000);
            Log.i("diffDays", String.valueOf(diffDays));
            if (diffDays > 1) SinceDate += String.valueOf(diffDays) + " days ";
            if (diffDays == 1) SinceDate += String.valueOf(diffDays) + " day ";
            long diffHour = (diff - (diffDays * 24 * 60 * 60 * 1000)) / ( 60 * 60 * 1000 );
            Log.i("diffHour", String.valueOf(diffHour));
            if (diffHour > 1) SinceDate += String.valueOf(diffHour) + " hours ";
            if (diffHour == 1) SinceDate += String.valueOf(diffHour) + " hour ";
            long diffMins = (diff - (diffDays * 24 * 60 * 60 * 1000) - (diffHour * 60 * 60 * 1000)) / ( 60 * 1000 );
            if (diffMins > 1) SinceDate += String.valueOf(diffMins) + " minutes ";
            if (diffMins == 1) SinceDate += String.valueOf(diffMins) + " minute ";
            holder.tvDate.setText(SinceDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.edtusername.setText(job.getName());
    }

    @Override
    public int getItemCount() {
        int count = jobList.size();
        return count;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvLanguage;
        TextView tvName;
        TextView tvPostCode;
        TextView tvCountry;
        TextView tvDate;
        TextView edtusername;
        ImageView imvProfile;

        @BindView(R.id.layoutRoot)
        public RelativeLayout layoutRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvLanguage = itemView.findViewById(R.id.tvLanguage);
            tvPostCode = itemView.findViewById(R.id.tvPostCode);
            tvCountry = itemView.findViewById(R.id.tvCountry);
            tvDate = itemView.findViewById(R.id.tvDate);
            edtusername = itemView.findViewById(R.id.edtusername);
            imvProfile = itemView.findViewById(R.id.imvUser);
            //edtusername.setVisibility(View.GONE);
        }
    }
}