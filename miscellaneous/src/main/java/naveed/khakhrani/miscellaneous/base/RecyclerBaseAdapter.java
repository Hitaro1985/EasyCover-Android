package naveed.khakhrani.miscellaneous.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import naveed.khakhrani.miscellaneous.listeners.RecyclerViewItemSelectedListener;
import naveed.khakhrani.miscellaneous.util.Dummy;

/**
 * Created by naveedali on 10/22/17.
 */

public abstract class RecyclerBaseAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    protected List<T> mDataList;
    protected Context mContext;


    protected RecyclerViewItemSelectedListener recyclerViewItemSelectedListener;


    public RecyclerViewItemSelectedListener getRecyclerViewItemSelectedListener() {
        return recyclerViewItemSelectedListener;
    }

    public void setRecyclerViewItemSelectedListener(RecyclerViewItemSelectedListener recyclerViewItemSelectedListener) {
        this.recyclerViewItemSelectedListener = recyclerViewItemSelectedListener;
    }
}
