package com.example.akhileshlamba.sample;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by akhileshlamba on 21/3/18.
 */

public class DatePicker extends DialogFragment
        implements DatePickerDialog.OnDateSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {

    }
}
