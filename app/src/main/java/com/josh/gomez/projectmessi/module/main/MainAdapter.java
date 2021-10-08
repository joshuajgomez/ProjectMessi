package com.josh.gomez.projectmessi.module.main;

import android.app.Activity;
import android.content.Intent;
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
import com.josh.gomez.projectmessi.module.messEntry.MessEntryView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Joshua Gomez on 13/01/2018.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.messViewHolder> {

    List<Mess> messList;
    Activity activity;

    public MainAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setMessList(List<Mess> messList) {
        this.messList = messList;
        notifyDataSetChanged();
    }

    @Override
    public messViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.mess_list_item, parent, false);
        return new messViewHolder(view);
    }

    @Override
    public void onBindViewHolder(messViewHolder holder, int position) {
        holder.setData(messList.get(position));
    }

    @Override
    public int getItemCount() {
        if (messList != null)
            return messList.size();
        else
            return 0;
    }

    public class messViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.day_week)
        TextView dayWeek;
        @BindView(R.id.day)
        TextView date;
        @BindView(R.id.month_year)
        TextView monthYear;
        @BindView(R.id.breakfast)
        ImageView breakfastIcon;
        @BindView(R.id.lunch)
        ImageView lunchIcon;
        @BindView(R.id.dinner)
        ImageView dinnerIcon;
        @BindView(R.id.cost)
        TextView cost;
        Mess Mess;

        public messViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(Mess mess) {
            this.Mess = mess;
            dayWeek.setText(DateUtil.getDayWeek(mess.date));
            date.setText(DateUtil.getDayNumber(mess.date));
            monthYear.setText(DateUtil.getMonthYear(mess.date));
            breakfastIcon.setVisibility(mess.breakfast ? View.VISIBLE : View.GONE);
            lunchIcon.setVisibility(mess.lunch ? View.VISIBLE : View.GONE);
            dinnerIcon.setVisibility(mess.dinner ? View.VISIBLE : View.GONE);
            int cost = 0;
            if (mess.breakfast) cost += SharedUtil.getBreakfastCost();
            if (mess.lunch) cost += SharedUtil.getLunchCost();
            if (mess.dinner) cost += SharedUtil.getDinnerCost();
            this.cost.setText(Constants.INDIAN_RUPEE + " " + cost);
            breakfastIcon.setImageDrawable(ResUtil.getMipmap(SharedUtil.getBreakfastIcon(activity)));
            lunchIcon.setImageDrawable(ResUtil.getMipmap(SharedUtil.getLunchIcon(activity)));
            dinnerIcon.setImageDrawable(ResUtil.getMipmap(SharedUtil.getDinnerIcon(activity)));
        }

        @OnClick(R.id.edit)
        public void onEditClick() {
            Intent intent = new Intent(activity, MessEntryView.class);
            intent.putExtra(Constants.DATE, Mess.date);
            activity.startActivity(intent);
        }
    }
}
