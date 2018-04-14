package naveed.khakhrani.miscellaneous.util;

import android.text.TextUtils;

/**
 * Created by Naveed Ali on 11/17/2015.
 */
public class RegexComparison {

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public static boolean isValidPhoneNo(CharSequence phoneNo) {
        if (!TextUtils.isEmpty(phoneNo)) {
            return android.util.Patterns.PHONE.matcher(phoneNo).matches();
        }
        return false;
    }
}
