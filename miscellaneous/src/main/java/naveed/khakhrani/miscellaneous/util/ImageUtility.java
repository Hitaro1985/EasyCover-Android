package naveed.khakhrani.miscellaneous.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by naveedali on 9/19/2015.
 */
public class ImageUtility {

    /**
     * @param bm
     * @param degree
     * @return
     */
    public static Bitmap createRotatedBitmap(Bitmap bm, float degree) {
        Bitmap bitmap = null;
        if (degree != 0) {
            Matrix matrix = new Matrix();
            matrix.preRotate(degree);
            bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                    bm.getHeight(), matrix, true);
        }

        return bitmap;
    }


    /**
     * @param context
     * @param imageUri
     * @return
     */
    public static Bitmap getImageFromGallery(Context context, Uri imageUri) {
        Bitmap bmp = null;
        try {
            /*File imageFile = new File(imagePath);*/

            String path = getRealPathFromURI(context, imageUri);
            //Log.i("imageRealPath","path:"+path);
            bmp = BitmapFactory.decodeFile(path);
            // bmp = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
            return bmp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }


    /**
     * @param context
     * @param contentUri
     * @return
     */
    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        String path = "";


        try {
            final String scheme = contentUri.getScheme();
            if (null == scheme) {
                path = contentUri.getPath();
                //String[] proj = {MediaStore.Images.Media.DATA};

            } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
                path = contentUri.getPath();
            } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
                String[] proj = {MediaStore.Images.Media.DATA};
                cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                path = cursor.getString(column_index);
            }
            //if (path == null) path = getPath(context, contentUri);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (path == null || path.isEmpty())
                path = contentUri.getPath();
        }
        return path;
    }


    /**
     * @param context
     * @param imageUri
     * @param path
     * @return
     */
    public static int getImageOrientation(Context context, Uri imageUri, String path) {
        int rotate = 0;
        int orientation = 0;
        try {
            //File imageFile = new File(imagePath);

            ExifInterface exif = null;
            if (path == null) {
                exif = new ExifInterface(
                        getRealPathFromURI(context, imageUri));
            } else {
                exif = new ExifInterface(
                        path);
            }
            orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    return rotate;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    return rotate;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    return rotate;
            }
        } catch (IOException e) {
            e.printStackTrace();


        }

        return rotate;
    }


    /**
     * @param input string
     * @return bitmap
     */
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }


    /***
     *
     * @param
     * @param imageBitmap
     * @return
     */

    public static Bitmap scaleBitmap(Bitmap imageBitmap) {
        try {
            int defaultSize = 1000;

            int originalWidth = imageBitmap.getWidth();
            int originalHeight = imageBitmap.getHeight();
            if (originalWidth > defaultSize || originalHeight > defaultSize) {
                float ratio_WtoH = 1.0f * originalWidth / originalHeight;
                float ratio_HtoW = 1.0f * originalHeight / originalWidth;
                if (originalHeight > originalWidth) {
                    originalHeight = defaultSize;
                    originalWidth = (int) (ratio_WtoH * defaultSize);
                } else if (originalHeight < originalWidth) {
                    originalWidth = defaultSize;
                    originalHeight = (int) (ratio_HtoW * defaultSize);
                } else {
                    originalHeight = defaultSize;
                    originalWidth = defaultSize;
                }
                imageBitmap = Bitmap.createScaledBitmap(imageBitmap, originalWidth, originalHeight, false);
            }
        } catch (Exception e) {

        }
        return imageBitmap;
    }

    /**
     * @param imageBitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap scaleBitmap(Bitmap imageBitmap, int width, int height) {
        try {
            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageBitmap;
    }


    /**
     * @param imageBitmap
     * @return
     */
    public static Bitmap scaleBitmapProfile(Bitmap imageBitmap) {
        try {
            int defaultSize = 600;
            int originalWidth = imageBitmap.getWidth();
            int originalHeight = imageBitmap.getHeight();
            if (originalWidth > defaultSize || originalHeight > defaultSize) {
                float ratio_WtoH = 1.0f * originalWidth / originalHeight;
                float ratio_HtoW = 1.0f * originalHeight / originalWidth;
                if (originalHeight > originalWidth) {
                    originalHeight = defaultSize;
                    originalWidth = (int) (ratio_WtoH * defaultSize);
                } else if (originalHeight < originalWidth) {
                    originalWidth = defaultSize;
                    originalHeight = (int) (ratio_HtoW * defaultSize);
                } else {
                    originalHeight = defaultSize;
                    originalWidth = defaultSize;
                }
                imageBitmap = Bitmap.createScaledBitmap(imageBitmap, originalWidth, originalHeight, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageBitmap;
    }

    /**
     * @param imageBitmap
     * @return
     */
    public static String convertBitMapToBase64(Bitmap imageBitmap) {
        String encoded = "";

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        } catch (Exception ex) {
            Log.e("image utility", "Error occure in message = " + ex.getMessage());
        }


        return encoded;
    }

    /**
     * @param pContext
     * @param pUri
     * @return
     */
    public String convertUriToBase64(final Context pContext, Uri pUri) {
        String encoded = "";

        try {
            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(pContext.getContentResolver(), pUri);
            encoded = convertBitMapToBase64(imageBitmap);
            Log.e("ImageUti", "encoded = " + encoded);
        } catch (Exception ex) {
            Log.e("ImageUti", "Error occure in message = " + ex.getMessage());
        }

        return encoded;
    }


    public static File bitmapToFile(Context context, Bitmap bitmap, String fileName) {
        File f = new File(context.getCacheDir(), fileName);
        try {
            f.createNewFile();
            //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();
            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return f;
    }


    /**
     * @param bitmap
     * @param fileName
     * @param direName
     * @return
     */
    public static Uri saveImageToSDCard(Bitmap bitmap, String fileName, String direName) {
        FileOutputStream outStream;
        File pictureFile = null;
        try {

            File direct = new File(Environment.getExternalStorageDirectory() + "/" + direName);

            if (!direct.exists()) {
                File newDirect = new File("/sdcard/" + direName + "/");
                newDirect.mkdirs();
            }

            pictureFile = new File(new File("/sdcard/" + direName + "/"), fileName);
            if (pictureFile.exists()) {
                pictureFile.delete();
            }

            if (!pictureFile.exists())
                pictureFile.createNewFile();
            outStream = new FileOutputStream(pictureFile.getPath());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            //outStream.write(data);
            outStream.flush();
            outStream.close();
//            Toast toast = Toast.makeText(mContext, "Picture saved: " + fileName, Toast.LENGTH_LONG);
//            toast.show();
            return Uri.fromFile(pictureFile);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return null;
    }

    /**
     * @param data
     * @param fileName
     * @param dirName
     * @return
     */
    public static Uri saveImageToSDCard(byte[] data, String fileName, String dirName) {
        FileOutputStream outStream;
        File pictureFile = null;
        try {
            File direct = new File(Environment.getExternalStorageDirectory() + "/" + dirName);

            if (!direct.exists()) {
                File newDirect = new File("/sdcard/" + dirName + "/");
                newDirect.mkdirs();
            }

            pictureFile = new File(new File("/sdcard/" + dirName + "/"), fileName);
            if (pictureFile.exists()) {
                pictureFile.delete();
            }

            if (!pictureFile.exists())
                pictureFile.createNewFile();
            outStream = new FileOutputStream(pictureFile.getPath());
            outStream.write(data);
            outStream.flush();
            outStream.close();
//            Toast toast = Toast.makeText(mContext, "Picture saved: " + fileName, Toast.LENGTH_LONG);
//            toast.show();
            return Uri.fromFile(pictureFile);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return null;
    }

    public static Bitmap getImageFromSDCard(String fileName, String direName) {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + direName, fileName);
        if (file.exists()) {
            // Log.i("Image",fileName+" isExists=> "+true);
            return BitmapFactory.decodeFile(file.getAbsolutePath(), null);
        } else {
            //Log.i("Image",fileName+" isExists=> "+false);
        }
        return null;
    }

    public static Uri getImageURIFromSDCard(String fileName, String direName) {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + direName, fileName);
        if (file.exists()) {
            // Log.i("Image",fileName+" isExists=> "+true);
            return Uri.fromFile(file);
        }/*else{
            //Log.i("Image",fileName+" isExists=> "+false);
        }*/
        return null;
    }

    public static void deleteDirectory(String direName) {
        try {
            File dir = new File(Environment.getExternalStorageDirectory() + "/" + direName);
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    new File(dir, children[i]).delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap cropImage(Bitmap bitmap, int width, int height) {
        final BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        // bitmapOptions.inDensity=sampleSize;
        bitmapOptions.inTargetDensity = 1;
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        return bitmap;
    }

    public static Bitmap flip(Bitmap bitmap) {
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        // Bitmap src = d.getBitmap();
        Bitmap dst = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, false);
        dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        return dst;
    }


    public static Drawable bitmapToDrawable(Context context, Bitmap bitmap) {
        Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        return drawable;
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        String path = null;
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                path = getPathFromContentUri(context, uri);
            /*String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    path = cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
                e.printStackTrace();
            } finally {
                if (cursor != null) cursor.close();
            }*/
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            path = uri.getPath();
        }

        return path;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPathFromContentUri(Context context, Uri uri) {
        String filePath = null;
        try {
            String wholeID = DocumentsContract.getDocumentId(uri);
            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];
            String[] column = {MediaStore.Images.Media.DATA};
            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().
                    query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            column, sel, new String[]{id}, null);


            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }

            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public static String getFileExtension(Context context, Uri uri) {
        String extension = "";
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        }
        return extension;
    }

    public static Uri getFileUri(Context context, Intent data) throws Exception {
        Uri selectedFileUri;
        if (data != null && data.getData() != null) {
            selectedFileUri = data.getData();
        } else {
            File imageFile = new File(context.getExternalCacheDir(), "image");
            selectedFileUri = FileProvider.getUriForFile(
                    context, "com.ia.fe.provider", imageFile);

        }
        return selectedFileUri;
    }

}
