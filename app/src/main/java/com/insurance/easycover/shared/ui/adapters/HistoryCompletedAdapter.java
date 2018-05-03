package com.insurance.easycover.shared.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.insurance.easycover.R;
import com.insurance.easycover.data.models.response.ResponseAcceptedJobs;
import com.insurance.easycover.data.models.response.ResponseCompletedJobs;
import com.insurance.easycover.shared.Utils.DownLoadImageTask;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import naveed.khakhrani.miscellaneous.base.RecyclerBaseAdapter;
import naveed.khakhrani.miscellaneous.listeners.RecyclerViewItemSelectedListener;

/**
 * Created by naveedali on 10/10/17.
 */

public class HistoryCompletedAdapter extends RecyclerView.Adapter<HistoryCompletedAdapter.ViewHolder> {

    private Context mCtx;
    private List<ResponseCompletedJobs> jobList;

    protected RecyclerViewItemSelectedListener recyclerViewItemSelectedListener;


    public RecyclerViewItemSelectedListener getRecyclerViewItemSelectedListener() {
        return recyclerViewItemSelectedListener;
    }

    public void setRecyclerViewItemSelectedListener(RecyclerViewItemSelectedListener recyclerViewItemSelectedListener) {
        this.recyclerViewItemSelectedListener = recyclerViewItemSelectedListener;
    }

    public HistoryCompletedAdapter(Context context, List<ResponseCompletedJobs> data) {
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
        ResponseCompletedJobs job;
        job = jobList.get(position);
        holder.edtusername.setText(job.getUsername());
        holder.tvName.setText(job.getUsername());
        holder.tvLanguage.setText(job.getLanguage());
        if (job.getImage() != null) {
            if (!job.getImage().equals("null")) {
                new DownLoadImageTask(holder.imvUser).execute(job.getImage());
            }
        }
        holder.tvPostCode.setText(job.getPostcode());
        holder.tvCountry.setText(job.getCountry());
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
        TextView edtusername;
        ImageView imvUser;

        @BindView(R.id.layoutRoot)
        public RelativeLayout layoutRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvLanguage = itemView.findViewById(R.id.tvLanguage);
            tvPostCode = itemView.findViewById(R.id.tvPostCode);
            tvCountry = itemView.findViewById(R.id.tvCountry);
            edtusername = itemView.findViewById(R.id.edtusername);
            imvUser = itemView.findViewById(R.id.imvUser);
        }
    }
}
