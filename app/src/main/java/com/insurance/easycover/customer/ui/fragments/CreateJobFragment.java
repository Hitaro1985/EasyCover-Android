package com.insurance.easycover.customer.ui.fragments;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.insurance.easycover.AppSession;
import com.insurance.easycover.R;
import com.insurance.easycover.customer.ui.activities.JobPostSuccessActivity;
import com.insurance.easycover.data.events.EventsIds;
import com.insurance.easycover.data.events.ListDataEvent;
import com.insurance.easycover.data.events.SingleDataEvent;
import com.insurance.easycover.data.local.AppSharedPreferences;
import com.insurance.easycover.data.models.CompanyType;
import com.insurance.easycover.data.models.CreateJob;
import com.insurance.easycover.data.models.ForgotPassword;
import com.insurance.easycover.data.models.InsuranceType;
import com.insurance.easycover.data.models.ProgressRequestBody;
import com.insurance.easycover.data.models.UploadedDoc;
import com.insurance.easycover.data.models.response.ResponseCreateJob;
import com.insurance.easycover.data.models.response.ResponseCompany;
import com.insurance.easycover.data.models.response.ResponseGetInsuranceType;
import com.insurance.easycover.data.network.NetworkController;
import com.insurance.easycover.shared.Utils.AppConstants;
import com.insurance.easycover.shared.Utils.AppUtils;
import com.insurance.easycover.shared.ui.adapters.UploadFileAdapter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import naveed.khakhrani.miscellaneous.SpinnerAdapter;
import naveed.khakhrani.miscellaneous.base.BaseFragment;
import naveed.khakhrani.miscellaneous.base.RecyclerBaseAdapter;
import naveed.khakhrani.miscellaneous.dialogs.DialogFilePickerFragment;
import naveed.khakhrani.miscellaneous.dialogs.JobDatePickerDialogFragment;
import naveed.khakhrani.miscellaneous.listeners.RecyclerViewItemSelectedListener;
import naveed.khakhrani.miscellaneous.util.AppButton;
import naveed.khakhrani.miscellaneous.util.FileDownloaderFromFileDescriptorAsync;
import naveed.khakhrani.miscellaneous.util.ImageFilePath;
import naveed.khakhrani.miscellaneous.util.ImageUtility;
import naveed.khakhrani.miscellaneous.util.NetworkConnection;
import naveed.khakhrani.miscellaneous.util.ValidationHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateJobFragment extends BaseFragment implements RecyclerViewItemSelectedListener {


    protected Unbinder mUnbinder;
    @BindView(R.id.edtFullName)
    protected EditText edtFullName;
    @BindView(R.id.edtNricNumber)
    protected EditText edtNricNumber;
    @BindView(R.id.edtContact)
    protected EditText edtContact;
    @BindView(R.id.edtAddress)
    protected EditText edtAddress;
    @BindView(R.id.edtIndicativeSum)
    protected EditText edtIndicativeSum;
    @BindView(R.id.edtPostCode)
    protected EditText edtPostCode;
    @BindView(R.id.edtState)
    protected EditText edtState;
    @BindView(R.id.edtCountry)
    protected EditText edtCountry;
    @BindView(R.id.spinnerInsuranceType)
    protected Spinner spinnerInsuranceType;
    @BindView(R.id.spinnerCompany)
    protected Spinner spinnerCompany;
    @BindView(R.id.rViewUploadedFiles)
    protected RecyclerView rViewUploadedFiles;
    @BindView(R.id.expired_date)
    protected EditText expired_date;
    @BindView(R.id.btnChooseFile)
    protected AppButton btnChooseFile;

    private ValidationHelper validationHelper;

    boolean areGrantedPermissions = false;
    boolean openFilePickerOnResume = false;
    private Intent selectedIntent;
    private int selectedOption;
    private List<InsuranceType> insuranceTypes;
    private List<CompanyType> companyTypes;
    private int insuranceTypePosition = -1;
    private List<UploadedDoc> uploadedDocs = new ArrayList<>();
    private UploadFileAdapter filesAdapter;

    private Boolean upload_file = false;

    public Integer jobid;

    //private Map<String, String> map = new HashMap<>();

    //private  Uri selectedImageUri;

    public CreateJobFragment() {
        // Required empty public constructor
    }

    public static CreateJobFragment newInstance() {

        Bundle args = new Bundle();

        CreateJobFragment fragment = new CreateJobFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_job, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        validationHelper = new ValidationHelper(getContext());
        insuranceTypes = InsuranceType.getInsuranceTypes();
        companyTypes = CompanyType.getCompanyType();
        if (AppSession.getInstance().getUserData().getUsername() != null) {
            edtFullName.setText(AppSession.getInstance().getUserData().getUsername());
        }
        if (AppSession.getInstance().getUserData().getNrc() != null) {
            if (!AppSession.getInstance().getUserData().getNrc().equals("null")) {
                edtNricNumber.setText(AppSession.getInstance().getUserData().getNrc());
            }
        }
        if (AppSession.getInstance().getUserData().getPhoneno() != null) {
            edtContact.setText(AppSession.getInstance().getUserData().getPhoneno());
        }
        initInsuranceTypeAdapter();
        initFilesAdapter();
        expired_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    FocusDate();
                } else {
                }
            }
        });
        expired_date.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    // Perform action on Enter key press
                    //expired_date.clearFocus();
                    edtAddress.requestFocus();
                    //return true;
                }
                return false;
                //return false;
            }
        });
        edtAddress.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    // Perform action on Enter key press
                    //edtIndicativeSum.clearFocus();
                    edtPostCode.requestFocus();
                    return true;
                }
                return false;
                //return false;
            }
        });
        edtPostCode.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    // Perform action on Enter key press
                    //edtIndicativeSum.clearFocus();
                    edtState.requestFocus();
                    return true;
                }
                return false;
                //return false;
            }
        });
        edtState.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    // Perform action on Enter key press
                    //edtIndicativeSum.clearFocus();
                    edtCountry.requestFocus();
                    return true;
                }
                return false;
                //return false;
            }
        });
        edtCountry.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    // Perform action on Enter key press
                    //edtIndicativeSum.clearFocus();
                    //edtCountry.requestFocus();
                    return true;
                }
                return false;
                //return false;
            }
        });
        edtFullName.requestFocus();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (openFilePickerOnResume) {
            startActivityForResult(selectedIntent, selectedOption);
            openFilePickerOnResume = false;
        }

    }


    private void initInsuranceTypeAdapter() {

//        InsuranceType insuranceType = new InsuranceType(0, "Select Insurance Type");
//        insuranceTypes.add(insuranceType);

        showProgressDialog(R.string.please_wait);
        NetworkController.getInstance().getInsuranceType();
        NetworkController.getInstance().getCompany();

        //ArrayAdapter adapter = new SpinnerAdapter<InsuranceType>(getContext(), R.layout.item_spinner, insuranceTypes);
        //spinnerInsuranceType.setAdapter(adapter);
        //spinnerInsuranceType.setSelection(insuranceTypes.size() - 1);
    }

    @Subscribe
    public void onEvent(ListDataEvent<ResponseGetInsuranceType> event) {
        if (event.getStatus()) {
            if (event.getEventId() == EventsIds.ID_GETINSURANCETYPE) {
                InsuranceType insuranceType;
                for (int i = 0; i < event.getListData().size(); i ++ ) {
                    insuranceType = new InsuranceType(i, event.getListData().get(i).getInsuranceName());
                    insuranceTypes.add(insuranceType);
                }
                insuranceType = new InsuranceType(event.getListData().size(), "Select Insurance Type");
                insuranceTypes.add(insuranceType);
                ArrayAdapter adapter = new SpinnerAdapter<InsuranceType>(getContext(), R.layout.item_spinner,insuranceTypes);
                spinnerInsuranceType.setAdapter(adapter);
                spinnerInsuranceType.setSelection(insuranceTypes.size() - 1);
                dismissProgress();
            } else {
                dismissProgress();
                showToast(event.getMessage());
            }
        } else {
            dismissProgress();
            showToast(event.getMessage());
        }
    }

    @Subscribe
    public void onGetCompany(ListDataEvent<ResponseCompany> event) {
        if (event.getStatus()) {
            if (event.getEventId() == EventsIds.ID_GETCOMPANY) {
                CompanyType companyType;
                for (int i = 0; i < event.getListData().size(); i ++ ) {
                    companyType = new CompanyType(i, event.getListData().get(i).getCompanyName());
                    companyTypes.add(companyType);
                }
                companyType = new CompanyType(event.getListData().size(), "Select Company Type");
                companyTypes.add(companyType);
                ArrayAdapter adapter = new SpinnerAdapter<CompanyType>(getContext(), R.layout.item_spinner,companyTypes);
                spinnerCompany.setAdapter(adapter);
                spinnerCompany.setSelection(companyTypes.size() - 1);
                dismissProgress();
            } else {
                dismissProgress();
                showToast(event.getMessage());
            }
        } else {
            dismissProgress();
            showToast(event.getMessage());
        }
    }

    private void initFilesAdapter() {
        filesAdapter = new UploadFileAdapter(getContext(), uploadedDocs);
        rViewUploadedFiles.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rViewUploadedFiles.setAdapter(filesAdapter);
        filesAdapter.setRecyclerViewItemSelectedListener(this);
    }


    @OnClick(R.id.btnSend)
    public void onClickSend() {
        createNewJob();
        // onBackPressed();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DialogFilePickerFragment.SRC_GALLERY ||
                requestCode == DialogFilePickerFragment.SRC_CAMERA) {

            if (!(resultCode != getActivity().RESULT_OK)) {
                try {
                    Uri selectedImageUri = ImageUtility.getFileUri(getContext(), data);
                    String mimeType = getContext().getContentResolver().getType(selectedImageUri);
                    //Toast.makeText(getContext(),"" + mimeType, Toast.LENGTH_LONG).show();
                    String ext =  selectedImageUri.toString().substring(selectedImageUri.toString().lastIndexOf(".") + 1);
                    if (ext.equals("doc")
                            || ext.equals("pdf")
                            || ext.equals("png")
                            || ext.equals("jpg")
                            || ext.equals("jpeg")
                            || ext.equals("docx")
                            || mimeType.equals("image/jpeg")
                            || mimeType.equals("application/pdf")
                            || mimeType.equals("application/msword")
                            || mimeType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                            || mimeType.equals("application/vnd.ms-excel")
                            || mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                            || mimeType.equals("application/vnd.ms-powerpoint")
                            || mimeType.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation"))
                        uploadFileProcess(selectedImageUri, requestCode);
                    else
                        showToast("Only attach .doc .pdf .png .jpg .doc .docx .xls .xlsx .ppt .pptx");
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

    private boolean validate() {
        boolean isValidate = true;
        String name = edtFullName.getText().toString();
        String nricNumbere = edtNricNumber.getText().toString();
        String address = edtAddress.getText().toString();
        String state = edtState.getText().toString();
        String country = edtCountry.getText().toString();
        String indicativeSum = edtIndicativeSum.getText().toString();
        if (country.isEmpty()) {
            isValidate = false;
            edtCountry.setError("Please Input Country");
        }
        /*if (!validationHelper.isDate(expired_date)) {
            isValidate = false;
            expired_date.setError("Invalid DateTime");
        }*/
        if (state.isEmpty()) {
            isValidate = false;
            edtState.setError("Please Input state");
        }
        if (address.isEmpty()) {
            isValidate = false;
            edtAddress.setError("Please Input address");
        }
        if (name.isEmpty()) {
            isValidate = false;
            edtFullName.setError("Please input name");
        }
        if (!validationHelper.isFullNameValid(edtFullName)) {
            isValidate = false;
            edtFullName.setError("Invalid Full Name");
        }
        if (!validationHelper.isContactValid(edtContact)){
            isValidate = false;
            edtContact.setError("Invalid PhoneNumber");
        }

        if (nricNumbere.isEmpty()) {
            edtNricNumber.setError("Input NRIC.");
        } else {
            if (nricNumbere.length() < 12) {
                isValidate = false;
                edtNricNumber.setError("Invalid NRIC. more than 12 digits.");
            }
        }
        if (spinnerInsuranceType.getSelectedItemPosition() == spinnerInsuranceType.getAdapter().getCount()) {
            isValidate = false;
            ((TextView)spinnerInsuranceType.getSelectedView()).setError("Please select InsuranceType");
        }
        if (spinnerCompany.getSelectedItemPosition() == spinnerCompany.getAdapter().getCount()) {
            isValidate = false;
            ((TextView)spinnerCompany.getSelectedView()).setError("Please select InsuranceType");
        }
        if (indicativeSum.isEmpty()) {
            isValidate = false;
            edtIndicativeSum.setError("Please Input IndicativeSum");
        } else {
            if (Integer.parseInt(indicativeSum) < 0) {
                isValidate = false;
                edtIndicativeSum.setError("Indicative sum must be bigger than 0");
            }
        }
        if (uploadedDocs.size() == 0) {
            isValidate = false;
            btnChooseFile.setError("Please upload document");
        }
        return isValidate;
    }

    private void createNewJob() {
        if (validate()) {
            if (NetworkConnection.isConnection(getActivity())) {
                showProgressDialog(R.string.please_wait);

                CreateJob createJob = new CreateJob();
                createJob.address = edtAddress.getText().toString();
                createJob.insurencetype = insuranceTypes.get(0).toString();
                //createJob.userid = "" + AppSession.getInstance().getUserData().getId();
                createJob.country = edtCountry.getText().toString();
                createJob.state = edtState.getText().toString();
                createJob.postcode = edtPostCode.getText().toString();
                List<String> uploadedDocsIds = new ArrayList<>();
                for (int i = 0; i < uploadedDocs.size(); i ++ ) {
                    uploadedDocsIds.add(uploadedDocs.get(i).id);
                }
                createJob.documents = uploadedDocsIds;
                createJob.name = edtFullName.getText().toString();
                createJob.phoneno = edtContact.getText().toString();
                //createJob.indicative_sum = Long.parseLong(edtIndicativeSum.getText().toString());
                createJob.indicative_sum = edtIndicativeSum.getText().toString();
                Calendar cal = Calendar.getInstance();
                Date today = cal.getTime();
                cal.add(Calendar.YEAR, 1); // to get previous year add -1
                String exp_date = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH);
                createJob.expired_date = exp_date;
                //if (insuranceTypePosition != -1)
                //createJob.insurencetype = insuranceTypes.get(insuranceTypePosition).insuranceType;
                //createJob.insurencetype = spinnerInsuranceType.getSelectedItem().toString();
                createJob.insurencetype = String.valueOf(spinnerInsuranceType.getSelectedItemPosition() + 1);
                createJob.nric = edtNricNumber.getText().toString();
                createJob.companyId = spinnerCompany.getSelectedItemPosition() + 1;

                Log.i("CreateJob", "json = " + new Gson().toJson(createJob));

                showProgressDialog(R.string.please_wait);
                NetworkController.getInstance().createNewJob(createJob);
            } else showToast(R.string.no_internet);
        }
    }


    @Subscribe
    public void onFileUploaded(SingleDataEvent<String> event) {
        if (EventsIds.ID_UPLOAD_DOCUMENT == event.getEventId()) {
            if (event.getStatus() && event.data != null) {
                UploadedDoc uploadedDoc = new UploadedDoc();
                uploadedDoc.id = event.data;
                //uploadedDocsIds.add(event.data);
                uploadedDocs.add(uploadedDoc);
                filesAdapter.notifyDataSetChanged();
            } else
                showToast(event.getMessage());

            dismissProgress();
        }
    }

    @Subscribe
    public void onJobCreateResponse(SingleDataEvent<ResponseCreateJob> event) {
        if (EventsIds.ID_CREATE_JOB == event.getEventId()) {
            if (event.getStatus()) {
                //jobid = event.getListData().get(0).getId();
                jobid = event.data.getJob().getId();
                AppSession.getInstance().setLastCreateJobID(jobid);
                dismissProgress();
                launchActivity(JobPostSuccessActivity.class);
                //changeFragment(PostJobSuccessFragment.newInstance(), R.id.fragmentContainer);
                //getActivity().finish();
            } else {
                dismissProgress();
                showToast(event.getMessage());
            }
        }
    }


    @Override
    public void onDestroyView() {
        if (mUnbinder != null) mUnbinder.unbind();
        super.onDestroyView();
    }

//    @Override
//    public void onItemSelected(Object item, int position) {
//        showToast("Here");
//    }

    @OnClick(R.id.expired_date)
    public void onClickEpDate(){

        FocusDate();

    }

    public void FocusDate() {
        JobDatePickerDialogFragment datePickerDialogFragment = JobDatePickerDialogFragment.getNewInstance(AppConstants.ID_DIALOG_DOB);

        datePickerDialogFragment.setOnDateSelected(new JobDatePickerDialogFragment.OnDateSelected() {
            @Override
            public void onAppDate(int year, int month, int dayOfMonth, String dateFormat) {
                //tvDateOfBirth.setText(dayOfMonth + "" + month + "" + year);
                //expired_date.setText(dateFormat);
                String month_m;
                String dayOfMonth_m;
                if(month < 10){
                    month_m = "0" + month;
                } else {
                    month_m = String.valueOf(month);
                }
                if(dayOfMonth < 10){
                    dayOfMonth_m = "0" + dayOfMonth ;
                } else {
                    dayOfMonth_m = String.valueOf(dayOfMonth);
                }
                expired_date.setText(year + "-" + month_m + "-" + dayOfMonth_m);
            }
        });
        datePickerDialogFragment.show(getActivity().getSupportFragmentManager(), JobDatePickerDialogFragment.TAG);
    }

    private TextWatcher mDateEntryWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String working = s.toString();
            boolean isValid = true;
            if (working.length()==2 && before ==0) {
                if (Integer.parseInt(working) < 1 || Integer.parseInt(working)>12) {
                    isValid = false;
                } else {
                    working+="/";
                    expired_date.setText(working);
                    expired_date.setSelection(working.length());
                }
            }
            else if (working.length()==7 && before ==0) {
                String enteredYear = working.substring(3);
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                if (Integer.parseInt(enteredYear) < currentYear) {
                    isValid = false;
                }
            } else if (working.length()!=7) {
                isValid = false;
            }

            if (!isValid) {
                expired_date.setError("Enter a valid date: MM/YYYY");
            } else {
                expired_date.setError(null);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {}

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    };

    @Override
    public void onItemSelected(Object item, int position) {
        uploadedDocs.remove(position);
        filesAdapter.notifyDataSetChanged();
        showToast("Removed" + Integer.toString(position + 1));
    }
}
