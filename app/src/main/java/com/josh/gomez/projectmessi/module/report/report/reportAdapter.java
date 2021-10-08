package com.josh.gomez.projectmessi.module.report.report;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.josh.gomez.projectmessi.R;
import com.josh.gomez.projectmessi.generic.utils.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Joshua Gomez on 15/01/2018.
 */

public class reportAdapter extends RecyclerView.Adapter<reportAdapter.reportViewHolder> {

    List<reportModel> reportModelList;
    Activity activity;

    public reportAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setReportModelList(List<reportModel> reportModelList) {
        this.reportModelList = reportModelList;
        notifyDataSetChanged();
    }

    @Override
    public reportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new reportViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.report_item, parent, false));
    }

    @Override
    public void onBindViewHolder(reportViewHolder holder, int position) {
        holder.setData(reportModelList.get(position));
    }

    @Override
    public int getItemCount() {
        if (reportModelList == null || reportModelList.size() < 1)
            return 0;
        else return reportModelList.size();
    }

    public class reportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.date)
        TextView date;
        ReportListInteractor mListener;
        reportModel reportModel;

        public reportViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            mListener = (ReportListInteractor) activity;
        }

        public void setData(reportModel reportModel) {
            this.reportModel = reportModel;
            date.setText(DateUtil.getMonthWord(reportModel.month) + " " + reportModel.year);
        }

        @Override
        public void onClick(View view) {
            mListener.onReportClick(reportModel.month, reportModel.year);
        }
    }

    public interface ReportListInteractor {
        void onReportClick(int month, int year);
    }
}
