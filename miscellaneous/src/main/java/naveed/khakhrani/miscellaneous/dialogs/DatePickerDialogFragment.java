package naveed.khakhrani.miscellaneous.dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

import naveed.khakhrani.miscellaneous.R;

/**
 *
 *
 */

public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    public static final String TAG = DatePickerDialogFragment.class.getCanonicalName();

    private OnDateSelected onDateSelected = null;
    private int year = 0;
    private int month = 0;
    private int day = 0;

    DatePickerDialog dialog = null;

    private boolean disablePastDate = false;
    private boolean disableFutureDate = false;

    private long currentDateInMillis = 0;


    public static DatePickerDialogFragment getNewInstance(int id) {
        DatePickerDialogFragment diaFragment = new DatePickerDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        diaFragment.setArguments(bundle);
        return diaFragment;
    }

    public DatePickerDialogFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        if (getArguments() != null && getArguments().getInt("id", 0) == 1) {
            year = year - 13;
            disableFutureDate = true;
        }

        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        //int theme;
        //if (Build.VERSION.SDK_INT < 23) theme = AlertDialog.THEME_HOLO_DARK;
        //else theme = android.R.style.Theme_Holo_Dialog;
        //dialog = new DatePickerDialog(getActivity(),
        //        theme, this, year, month, day);


        dialog = new DatePickerDialog(getActivity(), R.style.date_picker, this, year, month, day);

        if (disablePastDate) {
            dialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        }
        if (disableFutureDate) {
            dialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        }

        if (currentDateInMillis > 0) {
            dialog.getDatePicker().setMinDate(currentDateInMillis);
        }

        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        if (getOnDateSelected() != null) {
            String dateFormat = dayOfMonth + "/" + (month + 1) + "/" + year;
            getOnDateSelected().onAppDate(year, month + 1, dayOfMonth, dateFormat);
        }
    }

    /**
     * If set to true, it will not allow user to select previous date
     */
    public void disablePastDate(long timeInMillis) {
        disablePastDate = true;
        currentDateInMillis = timeInMillis;
    }

    /**
     * If set to true, it will not allow user to select future date
     */
    public void disableFutureDate() {
        disableFutureDate = true;
    }

    public interface OnDateSelected {
        public void onAppDate(int year, int month, int dayOfMonth, String dateFormate);
    }

    public OnDateSelected getOnDateSelected() {
        return onDateSelected;
    }

    public void setOnDateSelected(OnDateSelected onDateSelected) {
        this.onDateSelected = onDateSelected;
    }
}
