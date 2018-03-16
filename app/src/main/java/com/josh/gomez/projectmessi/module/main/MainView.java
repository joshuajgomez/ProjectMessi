package com.josh.gomez.projectmessi.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.josh.gomez.projectmessi.R;
import com.josh.gomez.projectmessi.base.baseActivity;
import com.josh.gomez.projectmessi.generic.database.mess.Mess;
import com.josh.gomez.projectmessi.generic.utils.DateUtil;
import com.josh.gomez.projectmessi.generic.utils.ResUtil;
import com.josh.gomez.projectmessi.service.alarmReceiver.AlarmManager;
import com.josh.gomez.projectmessi.module.credits.CreditActivity;
import com.josh.gomez.projectmessi.module.main.spinner.CustomSpinnerAdapter;
import com.josh.gomez.projectmessi.module.main.spinner.SpinnerModel;
import com.josh.gomez.projectmessi.module.messEntry.MessEntryView;
import com.josh.gomez.projectmessi.module.report.report.reportActivity;
import com.josh.gomez.projectmessi.module.settings.SettingsActivity;
import com.josh.gomez.projectmessi.module.settings.iconSettings.IconListActivity;
import com.josh.gomez.projectmessi.module.userInfo.UserActivity;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.josh.gomez.projectmessi.module.main.spinner.CustomSpinnerAdapter.month_key;
import static com.josh.gomez.projectmessi.module.main.spinner.CustomSpinnerAdapter.year_key;


public class MainView extends baseActivity implements MainContract.view,
        AdapterView.OnItemSelectedListener {

    MainContract.action mPresenter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_data)
    LinearLayout emptyData;
    MainAdapter adapter;
    MainGridAdapter gridAdapter;
    @BindView(R.id.imageView3d)
    ImageView view;
    @BindView(R.id.spinner)
    Spinner spinner;
    CustomSpinnerAdapter spinnerAdapter;
    String date = DateUtil.getCurrentDate();
    int selectedMonth = Integer.parseInt(date.split("/")[1]);
    int selectedYear = Integer.parseInt(date.split("/")[2]);

    private enum TYPE {
        GRID_VIEW, LIST_VIEW
    }

    private int VIEW_TYPE = TYPE.LIST_VIEW.ordinal();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        setBaseActivity();
        initUI();
    }

    private void initUI() {
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mPresenter = new MainPresenter(this);
        AlarmManager.initAlarmManager(this);
        spinner.setOnItemSelectedListener(this);
        spinner.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getSpinnerData();
    }

    @OnClick(R.id.imageView3)
    public void onAddClick() {
        startActivity(new Intent(this, MessEntryView.class));
    }

    @Override
    public void showData(List<Mess> messList, int noOfDays) {
        recyclerView.setVisibility(View.VISIBLE);
        emptyData.setVisibility(View.GONE);
        if (VIEW_TYPE == TYPE.GRID_VIEW.ordinal())
            showGridData(messList, noOfDays);
        else showListData(messList);
    }

    @OnClick(R.id.imageView322)
    public void onPersonClick() {
        startActivity(new Intent(this, UserActivity.class));
    }

    @OnClick(R.id.imageView3d)
    public void onViewChange() {
        if (VIEW_TYPE == TYPE.LIST_VIEW.ordinal())
            VIEW_TYPE = TYPE.GRID_VIEW.ordinal();
        else VIEW_TYPE = TYPE.LIST_VIEW.ordinal();
        onItemSelected(null, null, spinner.getSelectedItemPosition(), 0);
    }

    public void showListData(List<Mess> messList) {
        adapter = new MainAdapter(this);
        adapter.setMessList(messList);
        view.setImageDrawable(ResUtil.getDrawable("list"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void showGridData(List<Mess> messList, int daysOfMonth) {
        view.setImageDrawable(ResUtil.getDrawable("grid"));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 7));
        gridAdapter = new MainGridAdapter(this);
        gridAdapter.setDaysOfMonth(daysOfMonth);
        gridAdapter.setMessList(messList);
        recyclerView.setAdapter(gridAdapter);
    }

    @Override
    public void showEmptyData() {
        recyclerView.setVisibility(View.GONE);
        emptyData.setVisibility(View.VISIBLE);
    }

    @Override
    public void setSpinner(List<SpinnerModel> spinnerModel) {
        spinner.setVisibility(View.VISIBLE);
        spinnerAdapter = new CustomSpinnerAdapter();
        spinnerAdapter.setSpinnerModelList(spinnerModel);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(spinnerAdapter.getPosition(selectedMonth, selectedYear));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_credits: {
                startActivity(new Intent(this, CreditActivity.class));
                return true;
            }

            case R.id.action_settings: {
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }

            case R.id.action_report: {
                startActivity(new Intent(this, reportActivity.class));
                return true;
            }

            case R.id.action_icon: {
                startActivity(new Intent(this, IconListActivity.class));
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (spinnerAdapter == null) return;
        HashMap<String, Integer> hashmap = spinnerAdapter.getValues(i);
        selectedMonth = hashmap.get(month_key);
        selectedYear = hashmap.get(year_key);
        mPresenter.getData(selectedMonth, selectedYear);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
