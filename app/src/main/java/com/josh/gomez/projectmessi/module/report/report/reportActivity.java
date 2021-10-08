package com.josh.gomez.projectmessi.module.report.report;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.josh.gomez.projectmessi.R;
import com.josh.gomez.projectmessi.base.baseActivity;
import com.josh.gomez.projectmessi.module.report.reportDetail.reportDetailFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class reportActivity extends baseActivity implements reportContract.view,
        reportAdapter.ReportListInteractor {

    reportContract.action mPresenter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    reportAdapter adapter;
    @BindView(R.id.textView5)
    TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        initUI();
    }

    private void initUI() {
        ButterKnife.bind(this);
        mPresenter = new reportPresenter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new reportAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getData();
    }

    @Override
    public void setData(List<reportModel> reportModels) {
        noData.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        adapter.setReportModelList(reportModels);
    }

    public void showNoData() {
        noData.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @OnClick(R.id.imageView322)
    public void closeWindow() {
        this.finish();
    }

    @Override
    public void onReportClick(int month, int year) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root_view, reportDetailFragment.newInstance(month, year))
                .addToBackStack(null)
                .commit();
    }
}
