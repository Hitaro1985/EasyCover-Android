package naveed.khakhrani.miscellaneous.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import naveed.khakhrani.miscellaneous.R;
import naveed.khakhrani.miscellaneous.base.BaseActivity;

/**
 * Created by naveedali on 9/23/17.
 */

public class TabbedActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
    }
}
