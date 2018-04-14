package naveed.khakhrani.miscellaneous.dialogs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import naveed.khakhrani.miscellaneous.R;

/**
 * Created by Lenevo on 10/18/2017.
 *
 * @author Naveed Ali
 */
public class FullScreenImageDialog extends DialogFragment {

    public static String TAG = FullScreenImageDialog.class.getCanonicalName();

    private static final String EXTRAS_IMAGE_URL = "image_url";
    private static final String EXTRAS_IS_GIF = "is_gif";
    private String imageUrl = null;
    private boolean isGif = false;
    private static Context localContext;
    private static Bitmap bmp = null;
    //@butterknife.BindView(R.id.imvFullScreen)
    private ImageView imvFullScreen;
    private ProgressBar progressBar;

    /*private static FullScreenImageDialog getInstance(){

    }*/
    private static FullScreenImageDialog fullScreenImageDialog = new FullScreenImageDialog();

    public static FullScreenImageDialog newInstance(Context context, String imageUrl, boolean isGif) {
        localContext = context;
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_IMAGE_URL, imageUrl);
        bundle.putBoolean(EXTRAS_IS_GIF, isGif);
        fullScreenImageDialog.setArguments(bundle);
        return fullScreenImageDialog;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.dialog_fullscreen_image, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imvFullScreen = (ImageView) view.findViewById(R.id.imvFullScreen);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        view.findViewById(R.id.imvCancelFullScreen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelFullScreen();
            }
        });

        //imvFullScreen.att
        if (getArguments() != null) {
            imageUrl = getArguments().getString(EXTRAS_IMAGE_URL);
            isGif = getArguments().getBoolean(EXTRAS_IS_GIF, false);
            try {
                if (imageUrl != null) {
                    String[] imageParts = imageUrl.split(".");
                    if (imageParts.length > 0) {
                        String fileExtension = imageParts[imageParts.length - 1];
                        if (!isGif && fileExtension.equals("gif")) {
                            isGif = true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        // For testing purpose only
        if (imageUrl == null) {
           /* if (isGif) {
                imageUrl = "https://media.giphy.com/media/lCP95tGSbMmWI/giphy.gif";
            } else {*/
            imageUrl = "http://www.gstatic.com/webp/gallery/1.webp";
            // }
        }

        init();
    }

    private void init() {
        if (imageUrl != null) {
            progressBar.setVisibility(View.VISIBLE);
            /*if (isGif) {
                GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imvFullScreen);
                Glide.with(localContext).load(imageUrl).listener(requestListener).into(imageViewTarget);
            } else*/
            Glide.with(localContext)
                    .load(imageUrl)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imvFullScreen);
        }


    }

   /* RequestListener requestListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            progressBar.setVisibility(View.GONE);
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            progressBar.setVisibility(View.GONE);
            return false;
        }
    };*/

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


    public void cancelFullScreen() {
        dismiss();
    }


}
