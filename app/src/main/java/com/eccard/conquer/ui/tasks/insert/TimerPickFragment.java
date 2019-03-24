package com.eccard.conquer.ui.tasks.insert;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class TimerPickFragment extends AppCompatDialogFragment {

    public static final String TAG = TimerPickFragment.class.getSimpleName();

    private TimePickerDialog.OnTimeSetListener onTimeSetListener;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            onTimeSetListener = (TimePickerDialog.OnTimeSetListener) getParentFragment();
        } catch (ClassCastException e){
            throw new ClassCastException("Calling fragment must implement TimePickerDialog.OnTimeSetListener interface");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), onTimeSetListener, hour, minute,
                DateFormat.is24HourFormat(getActivity()));

    }

}
