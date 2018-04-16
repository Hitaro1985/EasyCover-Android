package com.insurance.easycover.agent.ui.fragments;


import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Network;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.insurance.easycover.AppSession;
import com.insurance.easycover.R;
import com.insurance.easycover.data.events.EventsIds;
import com.insurance.easycover.data.events.ListDataEvent;
import com.insurance.easycover.data.events.SimpleEvent;
import com.insurance.easycover.data.events.SingleDataEvent;
import com.insurance.easycover.data.local.AppSharedPreferences;
import com.insurance.easycover.data.models.ProgressRequestBody;
import com.insurance.easycover.data.models.UploadedDoc;
import com.insurance.easycover.data.models.response.HandOverData;
import com.insurance.easycover.data.models.response.RequestAccept;
import com.insurance.easycover.data.models.response.RequestAddQuotation;
import com.insurance.easycover.data.models.response.RequestJobDetail;
import com.insurance.easycover.data.models.response.ResponseAccept;
import com.insurance.easycover.data.models.response.ResponseCompletedJobs;
import com.insurance.easycover.data.models.response.ShowJob;
import com.insurance.easycover.data.models.response.assignJob.JobAssignJob;
import com.insurance.easycover.data.network.NetworkController;
import com.insurance.easycover.shared.Utils.AppUtils;
import com.insurance.easycover.shared.Utils.DownLoadImageTask;
import com.insurance.easycover.shared.ui.adapters.UploadFileAdapter;

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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import naveed.khakhrani.miscellaneous.base.BaseFragment;
import naveed.khakhrani.miscellaneous.base.RecyclerBaseAdapter;
import naveed.khakhrani.miscellaneous.dialogs.DialogFilePickerFragment;
import naveed.khakhrani.miscellaneous.util.AppButton;
import naveed.khakhrani.miscellaneous.util.FileDownloaderFromFileDescriptorAsync;
import naveed.khakhrani.miscellaneous.util.ImageFilePath;
import naveed.khakhrani.miscellaneous.util.ImageUtility;
import naveed.khakhrani.miscellaneous.util.NetworkConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobWallDetailFragment extends BaseFragment {

    private Unbinder mUnbinder = null;

    @BindView(R.id.scrollView)
    protected ScrollView scrollView;

    @BindView(R.id.layoutAccept)
    protected LinearLayout layoutAccept;

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

    @BindView(R.id.text1)
    protected TextView text1;

    @BindView(R.id.ChooseFileLayout)
    protected RelativeLayout ChooseFileLayout;

    @BindView(R.id.text2)
    protected TextView text2;

    @BindView(R.id.text3)
    protected TextView text3;

    @BindView(R.id.docs)
    protected LinearLayout docs;

    @BindView(R.id.btnChooseFile)
    protected AppButton btnChooseFile;

    @BindView(R.id.rViewUploadedFiles)
    protected RecyclerView rViewUploadedFiles;

    public ArrayList<String> fileNameList;
    public static Object job;
    boolean areGrantedPermissions = false;
    boolean openFilePickerOnResume = false;
    private Intent selectedIntent;
    private int selectedOption;
    private List<UploadedDoc> uploadedDocs = new ArrayList<>();
    private RecyclerBaseAdapter filesAdapter;
    private Boolean upload_file = false;

    public JobWallDetailFragment() {
        // Required empty public constructor
    }


    public static JobWallDetailFragment newInstance(Object item) {

        Bundle args = new Bundle();

        JobWallDetailFragment fragment = new JobWallDetailFragment();
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
        if (((ResponseCompletedJobs) job).getImage() != null) {
            if (!((ResponseCompletedJobs) job).getImage().equals("null")) {
                new DownLoadImageTask(imvUser).execute(((ResponseCompletedJobs) job).getImage());
            }
        }
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
        initFilesAdapter();
    }

    private void initFilesAdapter() {
        filesAdapter = new UploadFileAdapter(getContext(), uploadedDocs);
        rViewUploadedFiles.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rViewUploadedFiles.setAdapter(filesAdapter);
    }

    //Event Handling
    @OnClick(R.id.btnAccept)
    public void onClickAccept() {
        RequestAccept rat = new RequestAccept();
        rat.costomerId = ((ResponseCompletedJobs) job).getCustomerId();
        rat.jobid = ((ResponseCompletedJobs) job).getJobId();
        rat.status = 3;
        showProgressDialog(getString(R.string.please_wait));
        NetworkController.getInstance().acceptJob(rat);
    }

    //Event Handling
    @OnClick(R.id.btnReject)
    public void onClickReject() {
        RequestAccept rat = new RequestAccept();
        rat.costomerId = ((ResponseCompletedJobs) job).getCustomerId();
        rat.jobid = ((ResponseCompletedJobs) job).getJobId();
        rat.status = 2;
        showProgressDialog(getString(R.string.please_wait));
        NetworkController.getInstance().acceptJob(rat);
    }

    @Subscribe
    public void onAssignJob(SingleDataEvent<Object> event) {
        if (event.getStatus()) {
            if (event.getEventId() == EventsIds.ID_ACCEPTJOB) {
                ResponseAccept data = (ResponseAccept) event.data;
                if (data.getJobstatus().equals("3")) {
                    text1.setVisibility(View.VISIBLE);
                    ChooseFileLayout.setVisibility(View.VISIBLE);
                    text2.setVisibility(View.VISIBLE);
                    text3.setVisibility(View.GONE);
                    docs.setVisibility(View.GONE);
                    layoutAccept.setVisibility(View.GONE);
                    layoutSend.setVisibility(View.VISIBLE);
                    scrollTobottom();
                    dismissProgress();
                    showToast(event.getMessage());
                } else if (data.equals("0")) {
                    dismissProgress();
                    showToast(event.getMessage());
                    onBackPressed();
                } else {
                    dismissProgress();
                    showToast(event.getMessage());
                    super.onBackPressed();
                }
            } else if (event.getEventId() == EventsIds.ID_GETJOBDETAIL) {
                ShowJob jobDetail = (ShowJob) event.data;
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
                dismissProgress();
                showToast(event.getMessage());
            }
        } else {
            dismissProgress();
            showToast(event.getMessage());
        }
    }

    //Event Handling
    @OnClick(R.id.btnSend)
    public void onClickSend() {
        showProgressDialog(getString(R.string.please_wait));
        RequestAddQuotation raq = new RequestAddQuotation();
        //raq.assignId = ((ResponseCompletedJobs) job)
        raq.assignId = ((ResponseCompletedJobs) job).getAssignJobsId();
        //((ResponseCompletedJobs) job).
        raq.jobId = ((ResponseCompletedJobs) job).getJobId();
        raq.quotationPrice = String.valueOf(edtQuotationTotalSum.getText());
        raq.quotationDescription = String.valueOf(edtRemarksQuotation.getText());
        List<String> uploadedDocsIds = new ArrayList<>();
        for (int i = 0; i < uploadedDocs.size(); i ++ ) {
            uploadedDocsIds.add(uploadedDocs.get(0).id);
        }
        raq.documents = uploadedDocsIds;
        Log.i("handover", "json = " + new Gson().toJson(raq));
        NetworkController.getInstance().addQuotation(raq);
        //onBackPressed();
    }

    @Subscribe
    public void onEvent(SimpleEvent event){
        if (event.getStatus()) {
            if (event.getEventId() == EventsIds.ID_ADDQUOTATION) {
                dismissProgress();
                showToast(event.getMessage());
                onBackPressed();
            } else {
                dismissProgress();
                showToast(event.getMessage());
            }
        } else {
            dismissProgress();
            showToast(event.getMessage());
        }
    }

    //Event Handling
    @OnClick(R.id.btnClear)
    public void onClickClear() {
        edtQuotationTotalSum.setText("");
        edtRemarksQuotation.setText("");
    }

    private void scrollTobottom() {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                // This method works but animates the scrolling
                // scroll_view.fullScroll(View.FOCUS_DOWN);
                // This method works even better because there are no animations.
                scrollView.scrollTo(0, scrollView.getBottom());
            }
        });
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
                        + "/easycover/" +filename;

                String easycoverFolder = Environment
                        .getExternalStorageDirectory().toString()
                        + "/easycover";
                File dir = new File(easycoverFolder);

                if (!dir.exists())
                    dir.mkdir();

                // Output stream
                OutputStream output = new FileOutputStream(Environment
                        .getExternalStorageDirectory().toString()
                        + "/easycover/" + filename);


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

    @OnClick(R.id.btnChooseFile)
    public void onClickAttachFileBtn() {
        if (!AppUtils.arePermissionGranted(getContext(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
            areGrantedPermissions = false;
        } else areGrantedPermissions = true;
        TedPermission.with(getContext())
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        DialogFilePickerFragment.newInstance(new DialogFilePickerFragment.FilePickerOptionListener() {
                            @Override
                            public void selectedFileOption(Intent intent, int optionSelected, String... permission) {
                                if (areGrantedPermissions) {
                                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                                        startActivityForResult(intent, optionSelected);
                                    }
                                } else {
                                    openFilePickerOnResume = true;
                                    selectedIntent = intent;
                                    selectedOption = optionSelected;
                                }
                            }
                        }, AppSession.getInstance().isAgent() ? R.style.AppThemeAgent : R.style.AppThemeCustomer, true)
                                .show(getChildFragmentManager(), null);

                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        //ValidUtils.showToast(getLocalContext(), getLocalContext().getString(R.string.permission_txt));
                        //showToast(R.string.permission_header_txt);
                    }
                })
                .setDeniedMessage(getString(R.string.permission_header_txt))
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (openFilePickerOnResume) {
            startActivityForResult(selectedIntent, selectedOption);
            openFilePickerOnResume = false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DialogFilePickerFragment.SRC_GALLERY ||
                requestCode == DialogFilePickerFragment.SRC_CAMERA) {

            if (!(resultCode != getActivity().RESULT_OK)) {
                try {
                    Uri selectedImageUri = ImageUtility.getFileUri(getContext(), data);
                    uploadFileProcess(selectedImageUri, requestCode);
                } catch (Exception ex) {
                    //ValidUtils.showToast(getLocalContext(), getLocalContext().getString(R.string.try_again_str));
                }
            } else {
                //showToast(getString(R.string.try_again_str));
            }
        }
    }

    public void uploadFileProcess(Uri uri, int code) {
        String filePath = null;
        if (code == DialogFilePickerFragment.SRC_CAMERA) {
            filePath = ImageUtility.getRealPathFromURI(getActivity(), uri);
        } else
            filePath = ImageFilePath.getPath(getActivity(), uri);

        if (filePath == null) {
            showProgressDialog(getString(R.string.please_wait));
            new FileDownloaderFromFileDescriptorAsync(getContext(), uri, new FileDownloaderFromFileDescriptorAsync.FilePathListener() {
                @Override
                public void onPathFromUriListener(String path) {
                    uploadDocument(path);
                    dismissProgress();
                }
            }).execute();
        } else uploadDocument(filePath);
    }


    private void uploadDocument(String path) {
        //final File file = new File(path);
        if (NetworkConnection.isConnection(getContext())) {
            //showProgressDialog(R.string.please_wait);
            rViewUploadedFiles.setVisibility(View.VISIBLE);
            final UploadedDoc uploadedDoc = new UploadedDoc();
            uploadedDoc.id = "";
            uploadedDocs.add(uploadedDoc);
            //uploadedDocsIds.add(event.data);
            //uploadedDocs.add(uploadedDoc);
            filesAdapter.notifyDataSetChanged();
            NetworkController.getInstance().uploadDocument(path, new ProgressRequestBody.UploadCallbacks() {
                @Override
                public void onProgressUpdate(int percentage) {
                    uploadedDoc.uploadProgress = percentage;
                    int position = uploadedDocs.indexOf(uploadedDoc);
                    filesAdapter.notifyItemChanged(position);
                }

                @Override
                public void onError(String errorMsg) {
                    showToast(R.string.file_upload_fail);
                    uploadedDocs.remove(uploadedDoc);
                    if (uploadedDocs.size() == 0)
                        rViewUploadedFiles.setVisibility(View.GONE);
                    filesAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFinish(String fileId) {
                    if (fileId != null) {
                        uploadedDoc.id = fileId;
                        upload_file = true;
                        //Log.i("fileId", "file " + fileId);
                        uploadedDoc.uploadProgress = 100;
                        int position = uploadedDocs.indexOf(uploadedDoc);
                        filesAdapter.notifyItemChanged(position);
                    }
                }
            });
        } else showToast(R.string.no_internet);
    }
}
