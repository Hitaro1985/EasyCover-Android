package naveed.khakhrani.miscellaneous.util;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by naveedali on 10/17/17.
 */

public class FileDownloaderFromFileDescriptorAsync extends AsyncTask<Void, Void, String> {

    private Context mContext;
    private Uri mUri;
    private FilePathListener mFilePathListener;

    public FileDownloaderFromFileDescriptorAsync(Context context, Uri uri, FilePathListener filePathListener) {
        mContext = context;
        mUri = uri;
        mFilePathListener = filePathListener;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Loaders.showProgressDialog(mContext, mContext.getString(R.string.loader_wait));
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            final String extension = ImageUtility.getFileExtension(mContext, mUri);
            ParcelFileDescriptor mInput = mContext.getContentResolver().openFileDescriptor(mUri, "r");
            FileDescriptor fd = mInput.getFileDescriptor();
            //Saving file to local
            FileInputStream fin = new FileInputStream(fd);
            File newFile = new File("sdcard/temp." + extension);
            OutputStream file = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int length;

            while ((length = fin.read(buffer)) > 0) {
                file.write(buffer, 0, length);
            }
            file.flush();
            fin.close();
            file.close();
            mInput.close();
            return newFile.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("MainActivity", "File not found.");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Loaders.cancelProgressDialog();
        if (mFilePathListener != null) {
            mFilePathListener.onPathFromUriListener(result);
        }
    }

    public interface FilePathListener {
        public void onPathFromUriListener(String path);
    }

}
