package com.insurance.easycover.shared.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.insurance.easycover.R;
import com.insurance.easycover.data.models.response.ResponseAcceptedJobs;
import com.insurance.easycover.data.models.response.ResponseCompletedJobs;
import com.insurance.easycover.shared.Utils.DownLoadImageTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import naveed.khakhrani.miscellaneous.base.RecyclerBaseAdapter;
import naveed.khakhrani.miscellaneous.listeners.RecyclerViewItemSelectedListener;

/**
 * Created by naveedali on 10/10/17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context mCtx;
    private List<ResponseAcceptedJobs> jobList;


    protected RecyclerViewItemSelectedListener recyclerViewItemSelectedListener;


    public RecyclerViewItemSelectedListener getRecyclerViewItemSelectedListener() {
        return recyclerViewItemSelectedListener;
    }

    public void setRecyclerViewItemSelectedListener(RecyclerViewItemSelectedListener recyclerViewItemSelectedListener) {
        this.recyclerViewItemSelectedListener = recyclerViewItemSelectedListener;
    }

    public HistoryAdapter(Context context, List<ResponseAcceptedJobs> data) {
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
        /*Dummy obj = mDataList.get(position);
        Log.d("dumm", "" + obj.name);

        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerViewItemSelectedListener != null) {
                    recyclerViewItemSelectedListener.onItemSelected(mDataList.get(position), position);
                } else
                    Toast.makeText(mContext, "Work In progress", Toast.LENGTH_SHORT).show();
            }
        });*/
        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerViewItemSelectedListener != null) {
                    recyclerViewItemSelectedListener.onItemSelected(jobList.get(position), position);
                } else
                    Toast.makeText(mCtx, "Work In progress", Toast.LENGTH_SHORT).show();
            }
        });
        ResponseAcceptedJobs job;
        job = jobList.get(position);
        holder.tvName.setText(job.getUsername());
        holder.tvLanguage.setText(job.getLanguage());
        holder.tvPostCode.setText(job.getPostcode());
        holder.tvCountry.setText(job.getCountry());
        holder.edtRating.setRating((float)1.5);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dtStart = job.getUpdatedAt();
        try {
            Date date = format.parse(dtStart);
            Date now = Calendar.getInstance().getTime();
            long diff = now.getTime() - date.getTime();
            String SinceDate = String.valueOf("Since ");
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if (diffDays > 1) SinceDate += String.valueOf(diffDays) + " days ";
            if (diffDays == 1) SinceDate += String.valueOf(diffDays) + " day ";
            long diffHour = (diff - (diffDays * 24 * 60 * 60 * 1000)) / ( 60 * 60 * 1000 );
            if (diffHour > 1) SinceDate += String.valueOf(diffHour) + " hours ";
            if (diffHour == 1) SinceDate += String.valueOf(diffHour) + " hour ";
            long diffMins = (diff - (diffDays * 24 * 60 * 60 * 1000) - (diffHour * 60 * 60 * 1000)) / ( 60 * 1000 );
            if (diffMins > 1) SinceDate += String.valueOf(diffMins) + " minutes ";
            if (diffMins == 1) SinceDate += String.valueOf(diffMins) + " minute ";
            holder.tvDate.setText(SinceDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (job.getImage() != null) {
            if (!job.getImage().equals("null")) {
                new DownLoadImageTask(holder.imvUser).execute(job.getImage());
            }
        }
        holder.edtUserName.setText(job.getName());
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
        TextView edtUserName;
        ImageView imvUser;
        SimpleRatingBar edtRating;

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
            edtUserName = itemView.findViewById(R.id.edtusername);
            imvUser = itemView.findViewById(R.id.imvUser);
            edtRating = itemView.findViewById(R.id.edtRating);
        }
    }
}
