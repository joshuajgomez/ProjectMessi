package com.josh.gomez.projectmessi.module.messEntry;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.josh.gomez.projectmessi.R;
import com.josh.gomez.projectmessi.base.baseActivity;
import com.josh.gomez.projectmessi.generic.database.mess.Mess;
import com.josh.gomez.projectmessi.generic.repository.Constants;
import com.josh.gomez.projectmessi.generic.utils.DateUtil;
import com.josh.gomez.projectmessi.generic.utils.ResUtil;
import com.josh.gomez.projectmessi.generic.utils.SharedUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class MessEntryView extends baseActivity implements MessEntryContract.view {

    @BindView(R.id.day)
    TextView day;
    @BindView(R.id.month_year)
    TextView monthYear;
    @BindView(R.id.breakfast)
    CheckBox breakfast;
    @BindView(R.id.lunch)
    CheckBox lunch;
    @BindView(R.id.dinner)
    CheckBox dinner;
    @BindView(R.id.breakfast_cost)
    TextView breakfastCost;
    @BindView(R.id.lunch_cost)
    TextView lunchCost;
    @BindView(R.id.dinner_cost)
    TextView dinnerCost;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.date_container)
    RelativeLayout dateContainer;
    @BindView(R.id.imageView)
    ImageView breakfastIcon;
    @BindView(R.id.imageViewd)
    ImageView lunchIcon;
    @BindView(R.id.imageViewsd)
    ImageView dinnerIcon;
    Calendar myCalendar = Calendar.getInstance();
    private static String currentDate = DateUtil.getCurrentDate();
    MessEntryContract.action mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_entry_view);
        setBaseActivity();
        if (getIntent().hasExtra(Constants.DATE))
            initUI(getIntent().getStringExtra(Constants.DATE));
        else
            initUI(DateUtil.getCurrentDate());
    }

    DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setDate(formatDate(myCalendar));
        }

    };

    private String formatDate(Calendar myCalendar) {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        String date = sdf.format(myCalendar.getTime());
        return date;
    }

    private void initUI(String date) {
        ButterKnife.bind(this);
        updateCost();
        mPresenter = new MessEntryPresenter(this);
        setDate(date);
        breakfastCost.setText(Constants.INDIAN_RUPEE + " " + SharedUtil.getBreakfastCost());
        lunchCost.setText(Constants.INDIAN_RUPEE + " " + SharedUtil.getLunchCost());
        dinnerCost.setText(Constants.INDIAN_RUPEE + " " + SharedUtil.getDinnerCost());
        breakfastIcon.setImageDrawable(ResUtil.getMipmap(SharedUtil.getBreakfastIcon(this)));
        lunchIcon.setImageDrawable(ResUtil.getMipmap(SharedUtil.getLunchIcon(this)));
        dinnerIcon.setImageDrawable(ResUtil.getMipmap(SharedUtil.getDinnerIcon(this)));
    }

    private void setDate(String date) {
        mPresenter.getData(date);
        currentDate = date;
        day.setText(DateUtil.getDayNumber(date) + " " + DateUtil.getDayWeek(date));
        monthYear.setText(DateUtil.getMonthYear(date));
    }

    @OnClick(R.id.date_container)
    public void onDateClick() {
        new DatePickerDialog(MessEntryView.this, datePicker, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.button)
    public void onConfirmClick() {
        Mess Mess = new Mess();
        Mess.date = currentDate;
        Mess.breakfast = breakfast.isChecked();
        Mess.lunch = lunch.isChecked();
        Mess.dinner = dinner.isChecked();
        mPresenter.saveData(Mess);
    }

    @OnCheckedChanged({R.id.breakfast, R.id.lunch, R.id.dinner})
    public void onCheckedChange(CompoundButton compoundButton, boolean b) {
        updateCost();
    }

    private void updateCost() {
        int cost = 0;
        if (breakfast.isChecked()) cost += SharedUtil.getBreakfastCost();
        if (lunch.isChecked()) cost += SharedUtil.getLunchCost();
        if (dinner.isChecked()) cost += SharedUtil.getDinnerCost();
        button.setText("CONFIRM   " + Constants.INDIAN_RUPEE + " " + cost);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showData(Mess Mess) {
        breakfast.setChecked(Mess.breakfast);
        lunch.setChecked(Mess.lunch);
        dinner.setChecked(Mess.dinner);
        updateCost();
    }
}
