package naveed.khakhrani.miscellaneous.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import naveed.khakhrani.miscellaneous.R;


/**
 * Created by naveedali on 9/20/17.
 */

public class AppButton extends AppCompatButton {


    public AppButton(Context context) {
        //super(context);
        //parseAttr(null);
        this(context, null);
    }

    public AppButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundResource(R.drawable.bg_rounded_corner_white);
        setAllCaps(true);
        setGravity(Gravity.CENTER);
        setMinimumWidth(getResources().getDimensionPixelSize(R.dimen._100sdp));
        setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen._12sdp));
        parseAttr(attrs);
    }

    private void parseAttr(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AppButtonAttr,
                0, 0);

        try {
            //boolean showLeftDrawable = a.getBoolean(R.styleable.AppButton_btn_color, true);
            // CharSequence text = a.getText(R.styleable.DetailTopTagView_txtValue);
            //Drawable src = a.getDrawable(R.styleable.DetailTopTagView_leftDrawable);
            int bgColor = a.getColor(R.styleable.AppButtonAttr_btn_color, Color.WHITE);
            //if (bgColor != 0)
            setBackgroundColor(bgColor);
            //int textColor = a.getColor(R.styleable.DetailTopTagView_txtColor, 0);
            //int textSize = (int) a.getDimension(R.styleable.DetailTopTagView_txtSize, 0);


        } finally {
            a.recycle();
        }
    }


    public void setBackgroundColor(int color) {
        GradientDrawable bgShape = (GradientDrawable) getBackground();
        bgShape.setColor(color);
    }

    private void setFont() {
        /*if (attrs!=null) {
            Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/font-name.ttf");
            setTypeface(myTypeface);
        }*/
    }
}
