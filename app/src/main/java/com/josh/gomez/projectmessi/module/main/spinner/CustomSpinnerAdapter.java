package com.josh.gomez.projectmessi.module.main.spinner;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.josh.gomez.projectmessi.R;
import com.josh.gomez.projectmessi.generic.utils.DateUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Joshua Gomez on 02/02/2018.
 */

public class CustomSpinnerAdapter extends BaseAdapter {

    List<SpinnerModel> spinnerModelList;

    public void setSpinnerModelList(List<SpinnerModel> spinnerModelList) {
        this.spinnerModelList = spinnerModelList;
    }

    @Override
    public int getCount() {
        if (spinnerModelList != null)
            return spinnerModelList.size();
        else return 0;
    }

    public int getPosition(int month, int year) {
        for (int i = 0; i < spinnerModelList.size(); i++) {
            if (spinnerModelList.get(i).month == month
                    && spinnerModelList.get(i).year == year)
                return i;
        }
        return getCurrentMonthPosition();
    }

    private int getCurrentMonthPosition() {
        for (int i = 0; i < spinnerModelList.size(); i++) {
            if (spinnerModelList.get(i).month == DateUtil.getMonthFromDate(DateUtil.getCurrentDate())
                    && spinnerModelList.get(i).year == DateUtil.getYearFromDate(DateUtil.getCurrentDate()))
                return i;
        }
        return 0;
    }

    public int getMonth(int i) {
        return spinnerModelList.get(i).month;
    }

    public int getYear(int i) {
        return spinnerModelList.get(i).year;
    }

    @Override
    public String getItem(int i) {
        return DateUtil.getMonthWord(spinnerModelList.get(i).month) + " " + spinnerModelList.get(i).year;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public static String month_key = "month_key";
    public static String year_key = "year_key";

    public HashMap<String, Integer> getValues(int i) {
        HashMap<String, Integer> hashMap = new HashMap();
        hashMap.put(month_key, spinnerModelList.get(i).month);
        hashMap.put(year_key, spinnerModelList.get(i).year);
        return hashMap;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(getItem(i));
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(getItem(position));
        return view;
    }
}
