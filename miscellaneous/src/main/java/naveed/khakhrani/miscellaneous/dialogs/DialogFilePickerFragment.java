package naveed.khakhrani.miscellaneous.dialogs;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;


import java.io.File;
import java.util.ArrayList;

import naveed.khakhrani.miscellaneous.R;

/**
 * Created by Naveed Ali on 9/18/2017.
 */
public class DialogFilePickerFragment extends DialogFragment implements View.OnClickListener {


    public static final int SRC_CAMERA = 11;
    public static final int SRC_GALLERY = 12;
    public static final int SRC_BROWS = 13;


    protected Button btnCam;
    protected Button btnGallery;
    protected Button btnFileDir;
    protected ImageButton btnCancelImagePicker;

    public View slideView;
    public View outOfBoxView;


    private Context localContext;
    private static FilePickerOptionListener filePickerOptionListener;
    private static int theme;
    private static boolean filePicker;

    final Handler handler = new Handler();

    public static DialogFilePickerFragment newInstance(FilePickerOptionListener imagePickerListener, int pTheme) {

        filePickerOptionListener = imagePickerListener;
        theme = pTheme;
        //filePickerOptionListener = (FilePickerOptionListener)context;
        return new DialogFilePickerFragment();
    }

    public static DialogFilePickerFragment newInstance(FilePickerOptionListener imagePickerListener, int pTheme, boolean pFilePicker) {

        filePicker = pFilePicker;
        filePickerOptionListener = imagePickerListener;
        theme = pTheme;
        //filePickerOptionListener = (FilePickerOptionListener)context;
        return new DialogFilePickerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int style = DialogFragment.STYLE_NO_TITLE;
        //int theme = R.style.;
        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.dialog_select_image, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        outOfBoxView = (LinearLayout) view.findViewById(R.id.outOfBoxView);

        btnCam = (Button) view.findViewById(R.id.btnCamera);
        btnCancelImagePicker = (ImageButton) view.findViewById(R.id.closeBtn);
        btnGallery = (Button) view.findViewById(R.id.btnPhotoLibrary);
        btnFileDir = (Button) view.findViewById(R.id.btnFileDirectory);

        btnGallery.setOnClickListener(this);
        outOfBoxView.setOnClickListener(this);
        btnCam.setOnClickListener(this);
        btnCancelImagePicker.setOnClickListener(this);
        btnFileDir.setOnClickListener(this);


    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }

    public static File outFile = null;

    public static Intent cameraIntentView(final Context pContext) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //File outFile = DialogFilePickerFragment.createImageFile(getLocalContext());
        outFile = new File(pContext.getExternalCacheDir(), "image");
        //Uri outUri = Uri.fromFile(outFile);
        takePictureIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        takePictureIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        Uri outUri = FileProvider.getUriForFile(
                pContext, "com.insurance.easycover.provider", outFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);

        return takePictureIntent;

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.btnCamera) {
            filePickerOptionListener.selectedFileOption(cameraIntent(), SRC_CAMERA, "");
        } else if (id == R.id.btnPhotoLibrary) {
            //filePickerOptionListener.selectedFileOption(galleryIntent(), SRC_GALLERY, "");
            if (filePicker)
                filePickerOptionListener.selectedFileOption(browserIntent(), SRC_GALLERY, "");
            else
                filePickerOptionListener.selectedFileOption(galleryIntent(), SRC_GALLERY, "");

        } else if (id == R.id.btnFileDirectory) {
            if (filePicker)
                filePickerOptionListener.selectedFileOption(browserIntent(), SRC_BROWS, "");
            else
                filePickerOptionListener.selectedFileOption(galleryIntent(), SRC_BROWS, "");
        }

        /*if (id == R.id.btnCamera) {
            filePermissions(SRC_CAMERA, cameraIntent(),
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE);
             filePickerOptionListener.selectedFileOption(cameraIntent(),SRC_CAMERA,"");
        } else if (id == R.id.btnPhotoLibrary || id == R.id.btnFileDirectory) {
            filePermissions(SRC_BROWS, browserIntent(), Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE);

        } else if (id == R.id.closeBtn || id == R.id.outOfBoxView) {

        }*/
        dismiss();

    }

   /* private void filePermissions(final int action, final Intent intent, final String... permission) {
         TedPermission.w(getActivity())
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        filePickerOptionListener.selectedFileOption(intent, action, permission);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        ValidUtils.showToast(getActivity().getApplicationContext()
                                , getActivity().getApplicationContext().getString(R.string.permission_txt));
                    }
                })
                .setDeniedMessage(getActivity().getApplicationContext().getString(R.string.permission_header_txt))
                .setPermissions(permission)
                .check();

    }*/


    private Intent cameraIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        return takePictureIntent;
       /* File outFile = createImageFile();
        Uri outUri = Uri.fromFile(outFile);
        return takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);*/
    }

    /**
     * @param context
     * @return
     */
    public static File createImageFile(final Context context) {
        // Create an image file name
        File image = null;

        try {
            File outputDir = context.getCacheDir(); // context being the Activity pointer
            image = File.createTempFile("image", ".jpg", outputDir);
            /*//File storageDir = context.getCacheDir(Environment.DIRECTORY_PICTURES);
             image = File.createTempFile(
                    "image",  *//* prefix *//*
                    ".jpg",         *//* suffix *//*
                    storageDir      *//* directory *//*
            );*/
        } catch (Exception ex) {

        }

        // Save a file: path for use with ACTION_VIEW intents
        // mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public static String getFilePath(final Context pContext, int requestCode, int resultCode, Intent data) {

        String filePath = "";
        try {
            Uri selectedImageUri;
            if (data != null && data.getData() != null) {
                selectedImageUri = data.getData();
            } else {
                File imageFile = new File(pContext.getExternalCacheDir(), "image");
                selectedImageUri = FileProvider.getUriForFile(
                        pContext, "com.insurance.easycover.provider", imageFile);

                // selectedImageUri = Uri.fromFile(imageFile);
            }

            if (requestCode == DialogFilePickerFragment.SRC_CAMERA) {
                filePath = DialogFilePickerFragment.outFile.getAbsolutePath();
                //uploadFile(filePath);
            } else {
                //filePath = ImageFilePath.getPath(pContext, selectedImageUri);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return filePath;
    }

    private Intent galleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return i;
    }

    private Intent browserIntent() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        return intent.setType("*/*");
    }


    public interface FilePickerOptionListener {
        void selectedFileOption(Intent intent, int optionSelected, String... permission);

    }

    public Context getLocalContext() {
        return localContext;
    }

    public void setLocalContext(Context localContext) {
        this.localContext = localContext;
    }
}
