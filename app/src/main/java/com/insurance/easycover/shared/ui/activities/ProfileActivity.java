package com.insurance.easycover.shared.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.insurance.easycover.AppSession;
import com.insurance.easycover.R;
import com.insurance.easycover.data.events.EventsIds;
import com.insurance.easycover.data.events.SingleDataEvent;
import com.insurance.easycover.data.local.AppSharedPreferences;
import com.insurance.easycover.data.models.ProgressRequestBody;
import com.insurance.easycover.data.models.UploadedDoc;
import com.insurance.easycover.data.models.response.User;
import com.insurance.easycover.data.network.NetworkController;
import com.insurance.easycover.shared.Utils.AppConstants;
import com.insurance.easycover.shared.Utils.AppUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import naveed.khakhrani.miscellaneous.SpinnerAdapter;
import naveed.khakhrani.miscellaneous.base.BaseActivity;
import naveed.khakhrani.miscellaneous.dialogs.DatePickerDialogFragment;
import naveed.khakhrani.miscellaneous.dialogs.DialogFilePickerFragment;
import naveed.khakhrani.miscellaneous.util.Dummy;
import naveed.khakhrani.miscellaneous.util.FileDownloaderFromFileDescriptorAsync;
import naveed.khakhrani.miscellaneous.util.ImageFilePath;
import naveed.khakhrani.miscellaneous.util.ImageUtility;
import naveed.khakhrani.miscellaneous.util.NetworkConnection;
import naveed.khakhrani.miscellaneous.util.ValidationHelper;

import static com.insurance.easycover.AppClass.getContext;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.tvTitle)
    protected TextView tvTitle;
    @BindView(R.id.tvSubTitle)
    protected TextView tvSubTitle;

    @BindView(R.id.leftIv)
    protected ImageView imvLeft;

    @BindView(R.id.imvUser)
    protected ImageView imvUser;
    @BindView(R.id.imvSettings)
    protected ImageView imvSettings;
    @BindView(R.id.imvNotification)
    protected ImageView imvNotification;
    @BindView(R.id.imvNotificationDot)
    protected ImageView imvNotificationDot;

    @BindView(R.id.layoutAgent)
    protected LinearLayout layoutAgent;


    @BindView(R.id.edtNricNumber)
    protected EditText edtNricNumber;
    @BindView(R.id.edtUserName)
    protected EditText edtUserName;
    //@BindView(R.id.edtFullName)
    //protected EditText edtFullName;
    @BindView(R.id.edtNewPass)
    protected EditText edtNewPass;
    @BindView(R.id.edtPhoneno)
    protected EditText edtPhoneno;

    @BindView(R.id.edtEmail)
    protected EditText edtEmailAddress;

    @BindView(R.id.edtPiamNo)
    protected EditText edtPiamNo;
    @BindView(R.id.spinner1)
    protected Spinner spinner1;
    @BindView(R.id.spinner2)
    protected Spinner spinner2;


    @BindView(R.id.edtAddress)
    protected EditText edtAddress;
    @BindView(R.id.edtPostCode)
    protected EditText edtPostCode;
    @BindView(R.id.edtState)
    protected EditText edtState;
    @BindView(R.id.edtCountry)
    protected EditText edtCountry;

    @BindView(R.id.imvProfile)
    protected ImageView imvProfile;
    //private RecyclerBaseAdapter filesAdapter;

    @BindView(R.id.tvDateOfBirth)
    protected TextView tvDateOfBirth;

    @BindView(R.id.spinnerLanguageType)
    protected Spinner spinnerLanguageType;

    @BindView(R.id.spinnerChineseType)
    protected Spinner spinnerChineseType;

    @BindView(R.id.chineseLanguage)
    protected RelativeLayout chineseLanguage;

    private List<UploadedDoc> uploadedDocs = new ArrayList<>();


    @OnClick(R.id.leftIv)
    protected void onPressBack() {
        super.onBackPressed();
        finish();
    }

    private ValidationHelper mValidationHelper;
    private User mUserData;
    private boolean openCamOrGallery = false;
    private Intent selectedIntent = null;
    private int selectedOption;
    private String photoPath;

    public String uploadedImagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(AppSession.getInstance().getUserRole() == AppSession.ROLE_AGENT ? R.style.AppThemeAgent : R.style.AppThemeCustomer);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
        mValidationHelper = new ValidationHelper(this);
        edtEmailAddress.setEnabled(false);


        imvLeft.setVisibility(View.VISIBLE);
        imvNotification.setVisibility(View.GONE);
        imvNotificationDot.setVisibility(View.GONE);
        imvSettings.setVisibility(View.GONE);
        imvLeft.setImageResource(R.drawable.ic_arrow_back);

        layoutAgent.setVisibility(AppSession.getInstance().isAgent() ? View.VISIBLE : View.GONE);

        if (AppSession.getInstance().getUserRole() == AppSession.ROLE_AGENT) {
            tvSubTitle.setText("" + AppSession.getInstance().getUserData().getUsername());
            tvTitle.setText("Dashboard");
        } else tvSubTitle.setText("" + AppSession.getInstance().getUserData().getUsername());


        initSpinner1();
        initSpinner2();
        initLanguageSpinner();
        initChineseSpinner();
        loadData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //       EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (openCamOrGallery) {
            startActivityForResult(selectedIntent, selectedOption);
            openCamOrGallery = false;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        //EventBus.getDefault().unregister(this);
    }

    private void initSpinner1() {
        List<Dummy> list1 = new ArrayList<>();
        Dummy dummy = new Dummy();
        dummy.name = "Select Value";
        Dummy dummy2 = new Dummy();
        dummy2.name = "1st Principle";
        list1.add(dummy2);
        list1.add(dummy);
        ArrayAdapter adapter = new SpinnerAdapter<Dummy>(ProfileActivity.this, R.layout.item_spinner, list1);
        spinner1.setAdapter(adapter);
        spinner1.setSelection(list1.size() - 1);
    }

    private void initSpinner2() {
        List<Dummy> list2 = new ArrayList<>();
        Dummy dummy = new Dummy();
        dummy.name = "Select Value";
        Dummy dummy2 = new Dummy();
        dummy2.name = "2nd Principle";
        list2.add(dummy2);
        list2.add(dummy);
        ArrayAdapter adapter = new SpinnerAdapter<Dummy>(ProfileActivity.this, R.layout.item_spinner, list2);
        spinner2.setAdapter(adapter);
        spinner2.setSelection(list2.size() - 1);
    }

    private void initLanguageSpinner() {
        List<Dummy> languageList = new ArrayList<>();
        Dummy dummy2 = new Dummy();
        dummy2.name = "Malay";
        languageList.add(dummy2);
        Dummy dummy3 = new Dummy();
        dummy3.name = "English";
        languageList.add(dummy3);
        Dummy dummy4 = new Dummy();
        dummy4.name = "Chinese (Mandarin)";
        languageList.add(dummy4);
        Dummy dummy5 = new Dummy();
        dummy5.name = "Chinese (Cantonese)";
        languageList.add(dummy5);
        Dummy dummy6 = new Dummy();
        dummy6.name = "Chinese (Hokkien)";
        languageList.add(dummy6);
        Dummy dummy7 = new Dummy();
        dummy7.name = "Chinese (Hakka)";
        languageList.add(dummy7);
        Dummy dummy8 = new Dummy();
        dummy8.name = "Chinese (TeoChew)";
        languageList.add(dummy8);
        Dummy dummy9 = new Dummy();
        dummy9.name = "Chinese (Other)";
        languageList.add(dummy9);
        Dummy dummy = new Dummy();
        dummy.name = "Select Language";
        languageList.add(dummy);
        ArrayAdapter adapter = new SpinnerAdapter<Dummy>(ProfileActivity.this, R.layout.item_spinner, languageList);
        spinnerLanguageType.setAdapter(adapter);
        spinnerLanguageType.setSelection(languageList.size() - 1);
//        spinnerLanguageType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//              @Override
//              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                  if (id == Long.valueOf(2)) {
//                      chineseLanguage.setVisibility(View.VISIBLE);
//                  } else {
//                      chineseLanguage.setVisibility(View.GONE);
//                  }
//              }
//
//              @Override
//              public void onNothingSelected(AdapterView<?> parent) {
//
//              }
//          }
//        );
    }

    private void initChineseSpinner() {
        List<Dummy> chineseList = new ArrayList<>();
        Dummy dummy2 = new Dummy();
        dummy2.name = "Mandarin";
        chineseList.add(dummy2);
        Dummy dummy3 = new Dummy();
        dummy3.name = "Cantonese";
        chineseList.add(dummy3);
        Dummy dummy4 = new Dummy();
        dummy4.name = "Hokkien";
        chineseList.add(dummy4);
        Dummy dummy5 = new Dummy();
        dummy5.name = "TeoChew";
        chineseList.add(dummy5);
        Dummy dummy6 = new Dummy();
        dummy6.name = "Other";
        chineseList.add(dummy6);
        Dummy dummy = new Dummy();
        dummy.name = "Select Language";
        chineseList.add(dummy);
        ArrayAdapter adapter = new SpinnerAdapter<Dummy>(ProfileActivity.this, R.layout.item_spinner, chineseList);
        spinnerChineseType.setAdapter(adapter);
        spinnerChineseType.setSelection(chineseList.size() - 1);
    }

    private void loadData() {
        mUserData = AppSession.getInstance().getUserData();

        edtUserName.setText(mUserData.getUsername());
        edtEmailAddress.setText(mUserData.getEmail());
        tvDateOfBirth.setText((mUserData.getDob() != null && !mUserData.getDob().equals("null")) ? mUserData.getDob() : "");
        edtNricNumber.setText((mUserData.getNrc() != null && !mUserData.getNrc().equals("null")) ? mUserData.getNrc() : "");
        edtPhoneno.setText((mUserData.getPhoneno() != null && !mUserData.getPhoneno().equals("null")) ? mUserData.getPhoneno() : "");
        edtAddress.setText((mUserData.getAddress() != null && !mUserData.getAddress().equals("null")) ? mUserData.getAddress() : "");
        edtPostCode.setText((mUserData.getPostcode() != null && !mUserData.getPostcode().equals("null")) ? mUserData.getPostcode() : "");
        edtCountry.setText((mUserData.getCountry() != null && !mUserData.getCountry().equals("null")) ? mUserData.getCountry() : "");
        edtState.setText((mUserData.getState() != null && !mUserData.getState().equals("null")) ? mUserData.getState() : "");
        if (mUserData.getLanguage() != null) {
            if (mUserData.getLanguage().equals("Malay")) {
                spinnerLanguageType.setSelection(0);
            } else if (mUserData.getLanguage().equals("English")) {
                spinnerLanguageType.setSelection(1);
            } else if (mUserData.getLanguage().equals("Chinese")) {
                spinnerLanguageType.setSelection(2);
            } else {
                spinnerLanguageType.setSelection(2);
                spinnerChineseType.setVisibility(View.VISIBLE);
                if (mUserData.getLanguage().equals("Mandarin")) {
                    spinnerChineseType.setSelection(0);
                } else if (mUserData.getLanguage().equals("Cantonese")){
                    spinnerChineseType.setSelection(1);
                } else if (mUserData.getLanguage().equals("Hokkien")) {
                    spinnerChineseType.setSelection(2);
                } else if (mUserData.getLanguage().equals("Hakka")) {
                    spinnerChineseType.setSelection(3);
                } else if (mUserData.getLanguage().equals("TeoChew")) {
                    spinnerChineseType.setSelection(4);
                } else {
                    spinnerChineseType.setSelection(5);
                }
            }
        }
        if (mUserData.getImage() != null) {
            if (mUserData.getImage().equals("null") == false && !mUserData.getImage().isEmpty()) {
                imvProfile.setVisibility(View.VISIBLE);
                Glide.with(ProfileActivity.this).load(mUserData.getImage()).apply(RequestOptions.circleCropTransform()).into(imvProfile);
                imvProfile.setEnabled(true);
                Glide.with(ProfileActivity.this).load(mUserData.getImage()).apply(RequestOptions.circleCropTransform()).into(imvUser);
            } else {
                imvProfile.setEnabled(false);
            }
        }
    }


    // Event Handling

    @OnClick(R.id.tvDateOfBirth)
    public void onClickDob() {

        DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment.getNewInstance(AppConstants.ID_DIALOG_DOB);

        datePickerDialogFragment.setOnDateSelected(new DatePickerDialogFragment.OnDateSelected() {
            @Override
            public void onAppDate(int year, int month, int dayOfMonth, String dateFormat) {
                tvDateOfBirth.setText(year + "-" + month + "-" + dayOfMonth);
                //tvDateOfBirth.setText(dateFormat);
            }
        });
        datePickerDialogFragment.show(getSupportFragmentManager(), DatePickerDialogFragment.TAG);

    }

    @OnClick({R.id.btnUploadPic, R.id.imvProfile})
    protected void onClickUploadPic() {
        fileUploadingPermission();
    }

    @OnClick(R.id.btnUpdate)
    protected void onClickUpdate() {

        if (validate()) {

            if (NetworkConnection.isConnection(ProfileActivity.this)) {
                Map<String, String> map = new HashMap<>();
                map.put("username", "" + edtUserName.getText().toString());
                //map.put("password", "" + edtNewPass.getText().toString());
                map.put("email", "" + edtEmailAddress.getText().toString());
                map.put("phoneno", "" + edtPhoneno.getText().toString());
                map.put("nrc", "" + edtNricNumber.getText().toString());
                map.put("dob", "" + tvDateOfBirth.getText().toString());
                map.put("address", "" + edtAddress.getText().toString());
                map.put("postcode", "" + edtPostCode.getText().toString());
                map.put("state", "" + edtState.getText().toString());
                map.put("country", "" + edtCountry.getText().toString());
                if (chineseLanguage.getVisibility() == View.VISIBLE) {
                    map.put("language", "" + spinnerChineseType.getSelectedItem().toString());
                } else {
                    map.put("language", "" + spinnerLanguageType.getSelectedItem().toString());
                }
                if (uploadedImagePath != null) {
                    map.put("photo", "" + uploadedImagePath);
                }

                //map.put("photo","")
                //map.put("password", "" + edtNewPass.getText().toString());
                //map.put("id", "" + mUserData.getId());
                //map.put("newPassword,")

                showProgressDialog(R.string.please_wait);
                NetworkController.getInstance().updateUser(map,null);
                //NetworkController.getInstance().updateUser(map, photoPath);
            } else showToast(R.string.no_internet);

        }
    }


    private void fileUploadingPermission() {

        if (AppUtils.arePermissionGranted(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})) {

            DialogFilePickerFragment.newInstance(new DialogFilePickerFragment.FilePickerOptionListener() {
                @Override
                public void selectedFileOption(Intent intent, int optionSelected, String... permission) {
                    if (intent.resolveActivity(ProfileActivity.this.getPackageManager()) != null) {
                        startActivityForResult(intent, optionSelected);
                    }
                }
            }, AppSession.getInstance().isAgent() ? R.style.AppThemeAgent : R.style.AppThemeCustomer)
                    .show(getSupportFragmentManager(), null);
        } else
            TedPermission.with(this)
                    .setPermissionListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            DialogFilePickerFragment.newInstance(new DialogFilePickerFragment.FilePickerOptionListener() {
                                @Override
                                public void selectedFileOption(Intent intent, int optionSelected, String... permission) {
                                    if (intent.resolveActivity(ProfileActivity.this.getPackageManager()) != null) {
                                        openCamOrGallery = true;
                                        selectedIntent = intent;
                                        selectedOption = optionSelected;
                                    }
                                }
                            }, AppSession.getInstance().isAgent() ? R.style.AppThemeAgent : R.style.AppThemeCustomer)
                                    .show(getSupportFragmentManager(), null);

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

            if (!(resultCode != RESULT_OK)) {
                try {
                    Uri selectedImageUri = ImageUtility.getFileUri(ProfileActivity.this, data);
                    fileLoadingProcess(selectedImageUri, requestCode);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    //ValidUtils.showToast(getLocalContext(), getLocalContext().getString(R.string.try_again_str));
                }
            } else {
                //showToast(getString(R.string.try_again_str));
            }
        }
    }

    private void fileLoadingProcess(Uri selectedUri, int code) throws Exception {
        //String filePath = ImageFilePath.getPath(getActivity(), selectedImageUri);
        //String filePath = ImageUtility.getRealPathFromURI(getActivity(), selectedImageUri);
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedUri);

        if (bitmap != null) {
            int orientation = ImageUtility.getImageOrientation(this, selectedUri, null);
            if (orientation != 0) {
                bitmap = ImageUtility.createRotatedBitmap(bitmap, orientation);
            }

            imvProfile.setVisibility(View.VISIBLE);
            Glide.with(this).load(bitmap).apply(RequestOptions.circleCropTransform()).into(imvProfile);
        }

        // String filePath = null;
        if (code == DialogFilePickerFragment.SRC_CAMERA) {
            photoPath = ImageUtility.getRealPathFromURI(this, selectedUri);
        } else
            photoPath = ImageFilePath.getPath(this, selectedUri);
        if (photoPath == null) {
            new FileDownloaderFromFileDescriptorAsync(getContext(), selectedUri, new FileDownloaderFromFileDescriptorAsync.FilePathListener() {
                @Override
                public void onPathFromUriListener(String path) {
                    uploadFile(path);
                }
            }).execute();
        } else {
            uploadFile(photoPath);
        }
    }

    private void uploadFile(String path) {
        showProgressDialog(R.string.please_wait);
        //if (NetworkConnection.isConnection(getContext())) {
            NetworkController.getInstance().uploadImageFile(path, new ProgressRequestBody.UploadCallbacks() {
                @Override
                public void onProgressUpdate(int percentage) {
                }

                @Override
                public void onError(String errorMsg) {
                    showToast(R.string.file_upload_fail);
                }

                @Override
                public void onFinish(String fileId) {
                    if (fileId != null) {
                        showToast("Uploading photo success");
                    }
                }
            });
        //} else showToast(R.string.no_internet);
    }

//    @Subscribe
//    public void onImageEvent(SingleDataEvent<String> event) {
//        if (EventsIds.ID_UPLOAD_IMAGE == event.getEventId()) {
//            if (event.getStatus() && event.data != null) {
//            } else
//                showToast(event.getMessage());
//            dismissProgress();
//        }
//    }

    private boolean validate() {
        boolean isValidData = true;
        String phoneno = edtPhoneno.getText().toString();
        if (!phoneno.isEmpty()) {
            if (phoneno.length() < 3) {
                isValidData = false;
                edtPhoneno.setError("Invalid phone number");
            } else {
                String region = phoneno.substring(0, 3);
                if (!region.equals("+60")) {
                    isValidData = false;
                    edtPhoneno.setError("Input Malaysia Phone number.");
                }
            }
        }
        if (spinnerLanguageType.getSelectedItem().toString().equals("Malay")) {
            if (!edtNricNumber.getText().toString().isEmpty()) {
                if (edtNricNumber.getText().toString().length() != 12) {
                    isValidData = false;
                    edtNricNumber.setError("Invalid Nric.");
                }
            }
        } else {
            if (!edtNricNumber.getText().toString().isEmpty()) {
                if (edtNricNumber.getText().toString().length() != 12) {
                    isValidData = false;
                    edtNricNumber.setError("Invalid Nric.");
                }
            }
        }
//        String oldPassword = edtOldPass.getText().toString();
//        if (!AppSession.getInstance().getPassword().equals(oldPassword)) {
//            edtOldPass.setError(getString(R.string.password_mismatch));
//            isValidData = false;
//        }
        /*if (!newPassword.isEmpty()) {
            if (mValidationHelper.isPasswordValid(edtNewPass)) {
                String oldPassword = edtOldPass.getText().toString();
                if (!AppSession.getInstance().getPassword().equals(oldPassword)) {
                    edtOldPass.setError(getString(R.string.password_mismatch));
                    isValidData = false;
                }
            } else {
                edtNewPass.setError(getString(R.string.invalid_password));
                isValidData = false;
            }
        }*/
        return isValidData;
    }

    @Subscribe
    public void onEvent(SingleDataEvent<Object> event) {
        if (event.getStatus()) {
            if (event.getEventId() == EventsIds.ID_UPDATE_PROFILE) {
                dismissProgress();
                AppSharedPreferences.getInstance(getContext()).saveUserData(new Gson().toJson(event.data));
                AppSession.getInstance().setUserData((User) event.data);
                finish();
            } else if(event.getEventId() == EventsIds.ID_UPLOAD_IMAGE) {
                uploadedImagePath = (String)event.data;
                dismissProgress();
                showToast(event.getMessage());
            } else {
                dismissProgress();
                showToast(event.getMessage());
            }
        } else {
            dismissProgress();
            showToast(event.getMessage());
        }
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
