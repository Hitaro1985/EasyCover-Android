package naveed.khakhrani.miscellaneous.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

/**
 * Created by naveedali on 10/8/17.
 */

public abstract class SplashBase extends BaseActivity {

    private final static int TIME_DELAY = 2500;
    private Class<?> activityClass;
    Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();

    }

    protected void runDelay() {
        mHandler.postDelayed(runnable, TIME_DELAY);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            onDelayComplete();
        }
    };

    protected abstract void onDelayComplete();

}
