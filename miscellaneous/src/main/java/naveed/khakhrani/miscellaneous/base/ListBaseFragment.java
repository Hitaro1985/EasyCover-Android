package naveed.khakhrani.miscellaneous.base;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import naveed.khakhrani.miscellaneous.listeners.RecyclerViewItemSelectedListener;

/**
 * Created by naveedali on 9/23/17.
 */

public abstract class ListBaseFragment<T> extends BaseFragment implements RecyclerViewItemSelectedListener {
    //private
    protected RecyclerView.LayoutManager mLayoutManager;
    protected RecyclerBaseAdapter mAdapter;

    protected List<T> mData;

    protected abstract void initAdapter();

}
