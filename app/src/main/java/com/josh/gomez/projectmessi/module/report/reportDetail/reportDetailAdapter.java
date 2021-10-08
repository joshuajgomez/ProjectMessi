package com.josh.gomez.projectmessi.module.report.reportDetail;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.josh.gomez.projectmessi.R;
import com.josh.gomez.projectmessi.generic.database.mess.Mess;
import com.josh.gomez.projectmessi.generic.repository.Constants;
import com.josh.gomez.projectmessi.generic.utils.DateUtil;
import com.josh.gomez.projectmessi.generic.utils.ResUtil;
import com.josh.gomez.projectmessi.generic.utils.SharedUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Joshua Gomez on 15/01/2018.
 */

public class reportDetailAdapter extends RecyclerView.Adapter<reportDetailAdapter.reportDetailViewHolder> {

    List<Mess> messList;
    Activity activity;

    public reportDetailAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setMessList(List<Mess> messList) {
        this.messList = messList;
        notifyDataSetChanged();
    }

    @Override
    public reportDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.report_detail_item, parent, false);
        return new reportDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(reportDetailViewHolder holder, int position) {
        holder.setData(messList.get(position));
    }

    @Override
    public int getItemCount() {
        if (messList != null)
            return messList.size();
        else
            return 0;
    }

    public class reportDetailViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView4)
        TextView date;
        @BindView(R.id.breakfast)
        ImageView breakfastIcon;
        @BindView(R.id.lunch)
        ImageView lunchIcon;
        @BindView(R.id.dinner)
        ImageView dinnerIcon;
        @BindView(R.id.cost)
        TextView cost;
        Mess Mess;

        public reportDetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(Mess Mess) {
            this.Mess = Mess;
            date.setText(DateUtil.getDayNumber(Mess.date));
            breakfastIcon.setAlpha(Mess.breakfast ? 1f : .1f);
            lunchIcon.setAlpha(Mess.lunch ? 1f : .1f);
            dinnerIcon.setAlpha(Mess.dinner ? 1f : .1f);
            int cost = 0;
            if (Mess.breakfast) cost += SharedUtil.getBreakfastCost();
            if (Mess.lunch) cost += SharedUtil.getLunchCost();
            if (Mess.dinner) cost += SharedUtil.getDinnerCost();
            this.cost.setText(Constants.INDIAN_RUPEE + " " + cost);
            breakfastIcon.setImageDrawable(ResUtil.getMipmap(SharedUtil.getBreakfastIcon(activity)));
            lunchIcon.setImageDrawable(ResUtil.getMipmap(SharedUtil.getLunchIcon(activity)));
            dinnerIcon.setImageDrawable(ResUtil.getMipmap(SharedUtil.getDinnerIcon(activity)));
        }

    }
}
