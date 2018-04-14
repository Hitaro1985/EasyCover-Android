package com.insurance.easycover.agent.ui.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.insurance.easycover.R;
import com.insurance.easycover.data.events.EventsIds;
import com.insurance.easycover.data.events.SingleDataEvent;
import com.insurance.easycover.data.local.AppSharedPreferences;
import com.insurance.easycover.data.models.response.RequestJobDetail;
import com.insurance.easycover.data.models.response.ResponseCompletedJobs;
import com.insurance.easycover.data.models.response.ShowJob;
import com.insurance.easycover.data.network.NetworkController;

import org.greenrobot.eventbus.Subscribe;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import naveed.khakhrani.miscellaneous.base.BaseFragment;


/**
 * Created by PDC100 on 4/9/2018.
 */

public class CompleteDetailFragment extends BaseFragment {

    private Unbinder mUnbinder = null;

    @BindView(R.id.scrollView)
    protected ScrollView scrollView;

    @BindView(R.id.layoutAccept)
    protected LinearLayout layoutAccept;

    @BindView(R.id.layoutBack)
    protected LinearLayout layoutBack;

    @BindView(R.id.btnSend)
    protected Button btnSend;

    @BindView(R.id.btnClear)
    protected Button btnClear;

    @BindView(R.id.layoutSend)
    protected LinearLayout layoutSend;

    @BindView(R.id.tvInsuranceName)
    protected TextView tvInsuranceName;

    @BindView(R.id.tvLanguage)
    protected TextView tvLanguage;

    @BindView(R.id.tvPostCode)
    protected TextView tvPostCode;

    @BindView(R.id.tvCountry)
    protected TextView tvCountry;

    @BindView(R.id.tvName)
    protected TextView tvName;

    @BindView(R.id.tvNRIC)
    protected TextView tvNRIC;

    @BindView(R.id.tvMobile)
    protected TextView tvMobile;

    @BindView(R.id.tvLanguageDetail)
    protected TextView tvLanguageDetail;

    @BindView(R.id.tvInterestedInsurance)
    protected TextView tvInterestedInsurance;

    @BindView(R.id.tvIndicativeSum)
    protected TextView tvIndicativeSum;

    @BindView(R.id.edtQuotationTotalSum)
    protected TextView edtQuotationTotalSum;

    @BindView(R.id.edtRemarksQuotation)
    protected TextView edtRemarksQuotation;

    // Progress Dialog
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;

    @BindView(R.id.doc1)
    protected ImageView doc1;

    @BindView(R.id.doc2)
    protected ImageView doc2;

    @BindView(R.id.doc3)
    protected ImageView doc3;

    @BindView(R.id.doc4)
    protected ImageView doc4;

    @BindView(R.id.imvUser)
    protected ImageView imvUser;

    @BindView(R.id.tvDate)
    protected TextView tvDate;

    public ArrayList<String> fileNameList;
    public static Object job;

    public ShowJob jobDetail;

    public CompleteDetailFragment() {
        // Required empty public constructor
    }


    public static CompleteDetailFragment newInstance(Object item) {

        Bundle args = new Bundle();

        CompleteDetailFragment fragment = new CompleteDetailFragment();
        fragment.setArguments(args);
        job = item;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fileNameList = new ArrayList<String>();
        RequestJobDetail jobDetail = new RequestJobDetail();
        jobDetail.jobId = ((ResponseCompletedJobs) job).getJobId();
        NetworkController.getInstance().getJobDetail(jobDetail);
        return inflater.inflate(R.layout.fragment_job_wall_detail, container, false);
    }

    @Subscribe
    public void onJobDetailEvent(SingleDataEvent<ShowJob> event) {
        if (event.getStatus()) {
            if (event.getEventId() == EventsIds.ID_GETJOBDETAIL) {
                jobDetail = event.data;
                for (int i = 0; i < jobDetail.getDocuments().size(); i ++) {
                    String fileName = jobDetail.getDocuments().get(i).getFileName();
                    fileNameList.add(fileName);
                    switch (i) {
                        case 0 :
                            doc1.setVisibility(View.VISIBLE);
                            doc1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new DownloadFileFromURL().execute(fileNameList.get(0));
                                }
                            });
                            break;
                        case 1 :
                            doc2.setVisibility(View.VISIBLE);
                            doc2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new DownloadFileFromURL().execute(fileNameList.get(1));
                                }
                            });
                            break;
                        case 2 :
                            doc3.setVisibility(View.VISIBLE);
                            doc3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new DownloadFileFromURL().execute(fileNameList.get(2));
                                }
                            });
                            break;
                        case 3 :
                            doc4.setVisibility(View.VISIBLE);
                            doc4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new DownloadFileFromURL().execute(fileNameList.get(3));
                                }
                            });
                            break;
                    }
                }
            } else {
                showToast(event.getMessage());
            }
        } else {
            showToast(event.getMessage());
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        tvInsuranceName.setText(((ResponseCompletedJobs) job).getInsuranceType());
        tvLanguage.setText(AppSharedPreferences.getInstance(getContext()).getCurrentLanguage());
        tvPostCode.setText(((ResponseCompletedJobs) job).getPostcode());
        tvCountry.setText(((ResponseCompletedJobs) job).getCountry());
        tvName.setText(((ResponseCompletedJobs) job).getName());
        tvNRIC.setText(String.valueOf(((ResponseCompletedJobs) job).getNric()));
        tvMobile.setText(((ResponseCompletedJobs) job).getPhoneno());
        tvLanguageDetail.setText(AppSharedPreferences.getInstance(getContext()).getCurrentLanguage());
        tvInterestedInsurance.setText(((ResponseCompletedJobs) job).getInsuranceType());
        tvIndicativeSum.setText(String.valueOf(((ResponseCompletedJobs) job).getIndicativeSum()));
        layoutAccept.setVisibility(View.GONE);
        layoutBack.setVisibility(View.VISIBLE);
        layoutSend.setVisibility(View.GONE);
        btnSend.setVisibility(View.GONE);
        btnClear.setVisibility(View.GONE);
        /*if (jobDetail..getImage() != null) {
            if (!((ResponseGetQuotation) job).getImage().equals("null")) {
                new DownLoadImageTask(imvUser).execute(((ResponseGetQuotation) job).getImage());
            }
        }*/
        String dtStart = ((ResponseCompletedJobs) job).getUpdatedAt();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
            tvDate.setText(SinceDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    //Event Handling
    @OnClick(R.id.btnBack)
    public void onClickBack() {
        changeFragment(HistoryFragment.newInstance(),R.id.fragmentContainer);
        //changeChildFragment(AcceptedJobFragment.newInstance(),R.id.childFragmentContainer);
    }

    /**
     * Background Async Task to download file
     * */
    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        public String filename;
        public String filePath;

        /**
         * Before starting background thread Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog(R.string.please_wait);
            //showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                filename=url.getPath().substring(url.getPath().lastIndexOf("/")+1);
                URLConnection conection = url.openConnection();
                conection.connect();

                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);


                filePath = Environment
                        .getExternalStorageDirectory().toString()
                        + "/" +filename;

                // Output stream
                OutputStream output = new FileOutputStream(Environment
                        .getExternalStorageDirectory().toString()
                        + "/" + filename);


                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            //pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            //dismissDialog(progress_bar_type);
            dismissProgress();
            showToast(String.valueOf("Download File Success to ") + filename);
            if (filePath != null) {
                File file = new File( filePath  );
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri fileUri = FileProvider.getUriForFile(getContext(),
                        "com.insurance.easycover",
                        file);
                intent.setData(fileUri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);
            }
        }
    }
}
