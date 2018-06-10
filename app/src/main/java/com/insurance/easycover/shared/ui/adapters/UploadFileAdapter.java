package com.insurance.easycover.shared.ui.adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.insurance.easycover.R;
import com.insurance.easycover.data.models.UploadedDoc;

import java.util.List;

import at.grabner.circleprogress.CircleProgressView;
import butterknife.BindView;
import butterknife.ButterKnife;
import naveed.khakhrani.miscellaneous.base.RecyclerBaseAdapter;
import naveed.khakhrani.miscellaneous.listeners.RecyclerViewItemSelectedListener;
import naveed.khakhrani.miscellaneous.util.Dummy;

/**
 * Created by naveedali on 10/10/17.
 */

public class UploadFileAdapter extends RecyclerBaseAdapter<UploadedDoc, UploadFileAdapter.ViewHolder> {


    public UploadFileAdapter(Context context, List<UploadedDoc> data) {
        mDataList = data;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_doc_recyler, parent, false);


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        UploadedDoc uploadedDoc = mDataList.get(position);
        if (uploadedDoc.uploadProgress == 100) {
            holder.circleProgressView.setVisibility(View.GONE);

        } else {
            holder.circleProgressView.setVisibility(View.VISIBLE);
            holder.circleProgressView.setValue(uploadedDoc.uploadProgress);
        }

        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerViewItemSelectedListener != null)
                    recyclerViewItemSelectedListener.onItemSelected(mDataList.get(position), position, 0);
            }
        });

        holder.imvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerViewItemSelectedListener != null)
                    recyclerViewItemSelectedListener.onItemSelected(mDataList.get(position), position, 1);
            }
            }
        );

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layoutRoot)
        public RelativeLayout layoutRoot;
        @BindView(R.id.imvDoc)
        public ImageView imvDoc;
        @BindView(R.id.imvClose)
        public ImageView imvClose;

        @BindView(R.id.circleView)
        public CircleProgressView circleProgressView;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}
