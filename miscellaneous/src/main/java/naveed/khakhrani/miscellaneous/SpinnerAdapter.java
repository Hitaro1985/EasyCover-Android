package naveed.khakhrani.miscellaneous;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by naveedali on 10/26/17.
 */

public class SpinnerAdapter<T> extends ArrayAdapter {

    private List<T> mListData;

    public SpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
        mListData = objects;
    }

    @Override
    public int getCount() {
        return mListData.size() > 1 ? mListData.size() - 1 : mListData.size();
    }
}
