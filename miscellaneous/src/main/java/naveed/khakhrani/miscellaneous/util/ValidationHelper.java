package naveed.khakhrani.miscellaneous.util;

import android.content.Context;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import naveed.khakhrani.miscellaneous.R;

/**
 * Created by naveedali on 9/16/17.
 */

public class ValidationHelper {
    Context mContext;

    public ValidationHelper(Context pContext) {
        mContext = pContext;
    }

    public boolean isEmailValid(EditText emailEdt) {
        String email = emailEdt.getText().toString();
        if (!email.isEmpty() && RegexComparison.isValidEmail(email)) {
            return true;
        } else {
            emailEdt.setError("Enter Valid Email Address");
            return false;
        }
    }

    public boolean isPasswordValid(EditText editTextPass) {
        String password = editTextPass.getText().toString();
        char ch;
        if (password.isEmpty()) {
            editTextPass.setError("Enter Password");
            return false;
        }
        boolean capitalFlag = false;
        //boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        for(int i=0;i < password.length();i++) {
            ch = password.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            }
        }
        if (password.length() < 6) {
            editTextPass.setError(mContext.getString(R.string.min_length_check));
            return false;
        } else if (numberFlag == false) {
            editTextPass.setError("Password must be at least 6 characters consisting of numeric, uppercase and lowercase.");
            return false;
        } else if (capitalFlag == false) {
            editTextPass.setError("Password must be at least 6 characters consisting of numeric, uppercase and lowercase.");
            return false;
        } else
            return true;
    }

    public boolean isUserNameValid(EditText pUserNameEdt) {
        String userName = pUserNameEdt.getText().toString();
        if (userName.isEmpty() && userName.length() < 3) {
            pUserNameEdt.setError("Invalid UserName");
            return false;
        } else
            return true;
    }

    public boolean isFullNameValid(EditText pFullNameEdt) {
        String fullName = pFullNameEdt.getText().toString();
        if (fullName.isEmpty() && fullName.length() < 3) {
            pFullNameEdt.setError("Invalid User Name");
            return false;
        } else
            return true;
    }

    public boolean isContactValid(EditText pContactEdt) {
        String contact = pContactEdt.getText().toString();
        if (contact.isEmpty()) {
            pContactEdt.setError("Invalid Contact No.");
            return false;
        } else {
            if (contact.length() < 3) {
                pContactEdt.setError("Invalid phone number");
                return false;
            } else {
                String region = contact.substring(0, 3);
                if (!region.equals("+60") || contact.length()!= 12) {
                    pContactEdt.setError("Malaysia Phonumber length should be 12");
                    return false;
                }
            }
            return true;
        }
    }

    public boolean isDate(EditText pDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date now = Calendar.getInstance().getTime();
        String dtStart = pDate.getText().toString();
        try {
            Date date = format.parse(dtStart);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


}
