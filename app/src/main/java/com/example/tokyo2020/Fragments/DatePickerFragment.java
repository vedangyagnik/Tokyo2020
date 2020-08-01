package com.example.tokyo2020.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.tokyo2020.MainActivity;
import com.example.tokyo2020.R;
import com.example.tokyo2020.ScheduleByDayActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date strRange = null;
        Date endRange = null;
        Date selDate = null;
        String url = "";
        try {
            strRange = sdf.parse("2020-07-22");
            endRange = sdf.parse("2020-08-09");
            selDate = sdf.parse(year + "-" + (month + 1) + "-" + dayOfMonth);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        String btnIsVisible = "false";
        if (selDate.getTime() >= strRange.getTime() && selDate.getTime() <= endRange.getTime()) {
            String newDay = ""+dayOfMonth;
            if(dayOfMonth < 10){
                newDay = "0"+ dayOfMonth;
            }
            btnIsVisible = "true";
            url = "https://tokyo2020.org/en/schedule/" + year + "0" + (month+1) + newDay + "-schedule";
        } else {
            url = "file:///android_asset/no_events_found.html";
        }
        Intent i = new Intent(getActivity(), ScheduleByDayActivity.class);
        i.putExtra("url", url);
        i.putExtra("is_btn_visible", btnIsVisible);
        startActivity(i);
    }
}
