package com.insurance.easycover.shared.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.insurance.easycover.R;
import com.insurance.easycover.data.models.response.ResponseGetQuotation;
import com.insurance.easycover.shared.Utils.DownLoadImageTask;

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

public class QuotationAdapter extends RecyclerView.Adapter<QuotationAdapter.ViewHolder>{

    private Context mCtx;
    private List<ResponseGetQuotation> quotationList;

    protected RecyclerViewItemSelectedListener recyclerViewItemSelectedListener;


    public RecyclerViewItemSelectedListener getRecyclerViewItemSelectedListener() {
        return recyclerViewItemSelectedListener;
    }

    public void setRecyclerViewItemSelectedListener(RecyclerViewItemSelectedListener recyclerViewItemSelectedListener) {
        this.recyclerViewItemSelectedListener = recyclerViewItemSelectedListener;
    }

    public QuotationAdapter(Context context, List<ResponseGetQuotation> data) {
        this.mCtx = context;
        this.quotationList = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_qoutation, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ResponseGetQuotation quot;
        quot = quotationList.get(position);

        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerViewItemSelectedListener != null) {
                    recyclerViewItemSelectedListener.onItemSelected(quotationList.get(position), position, 0);
                } else
                    Toast.makeText(mCtx, "Work In progress", Toast.LENGTH_SHORT).show();
            }
        });
        //holder.tvInsurance.setText(quot.getInsuranceType());
        holder.tvName.setText(quot.getUsername());
        holder.rating.setVisibility(View.GONE);
        holder.tvLanguage.setText(quot.getLanguage());
        holder.tvQuotPrice.setText(quot.getQuotationPrice());
        holder.tvCountry.setText(quot.getCountry());
        if (quot.getImage() != null) {
            if (!quot.getImage().equals("null")) {
                new DownLoadImageTask(holder.imvUser).execute(quot.getImage());
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dtStart = quot.getUpdatedAt();
        try {
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date now = new Date();
            Date date = format.parse(dtStart.trim());
            long diff = now.getTime() - date.getTime();
            String SinceDate = String.valueOf("Response time:  ");
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if (diffDays > 1) SinceDate += String.valueOf(diffDays) + " days ";
            if (diffDays == 1) SinceDate += String.valueOf(diffDays) + " day ";
            long diffHour = (diff - (diffDays * 24 * 60 * 60 * 1000)) / ( 60 * 60 * 1000 );
            if (diffHour > 1) SinceDate += String.valueOf(diffHour) + " hours ";
            if (diffHour == 1) SinceDate += String.valueOf(diffHour) + " hour ";
            long diffMins = (diff - (diffDays * 24 * 60 * 60 * 1000) - (diffHour * 60 * 60 * 1000)) / ( 60 * 1000 );
            if (diffMins > 1) SinceDate += String.valueOf(diffMins) + " minutes ";
            if (diffMins == 1) SinceDate += String.valueOf(diffMins) + " minute ";
            if (SinceDate.equals("Response time:  ")) {
                SinceDate += "less then 1 minute";
            }
            holder.tvDate.setText(SinceDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return quotationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //TextView tvInsurance;
        TextView tvName;
        TextView tvQuotPrice;
        TextView tvCountry;
        TextView tvDate;
        TextView tvLanguage;
        ImageView imvUser;
        LinearLayout rating;

        @BindView(R.id.layoutRoot)
        public RelativeLayout layoutRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //tvInsurance = itemView.findViewById(R.id.tvInsurance);
            tvName = itemView.findViewById(R.id.tvName);
            tvQuotPrice = itemView.findViewById(R.id.tvQuotPrice);
            tvCountry = itemView.findViewById(R.id.tvCountry);
            tvDate = itemView.findViewById(R.id.tvDate);
            imvUser = itemView.findViewById(R.id.imvUser);
            rating = itemView.findViewById(R.id.rating);
            tvLanguage = itemView.findViewById(R.id.tvLanguage);
        }
    }
}
