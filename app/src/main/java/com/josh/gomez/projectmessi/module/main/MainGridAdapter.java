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

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Joshua Gomez on 29/01/2018.
 */

public class MainGridAdapter extends RecyclerView.Adapter {

    List<Mess> messList;
    Activity activity;
    int daysOfMonth = 0;

    public MainGridAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setDaysOfMonth(int daysOfMonth) {
        this.daysOfMonth = daysOfMonth;
    }

    public void setMessList(List<Mess> messList) {
        this.messList = messList;
        notifyDataSetChanged();
    }

    private Mess getMess(int day) {
        for (int i = 0; i < messList.size(); i++) {
            if (Integer.parseInt(DateUtil.getDayNumber(messList.get(i).date)) == day)
                return messList.get(i);
        }
        Mess mess = new Mess();
        mess.date = changeDay(messList.get(0).date, day);
        return mess;
    }

    private String changeDay(String date, int day) {
        return new DecimalFormat("00").format(day) + "/" + date.split("/")[1] + "/" + date.split("/")[2];
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.grid_item, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((GridViewHolder) holder).setData(position);
    }

    @Override
    public int getItemCount() {
        return this.daysOfMonth;
    }

    public class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.day)
        TextView date;
        @BindView(R.id.breakfast)
        ImageView breakfastIcon;
        @BindView(R.id.lunch)
        ImageView lunchIcon;
        @BindView(R.id.dinner)
        ImageView dinnerIcon;
        Mess mess = new Mess();

        public GridViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setData(int position) {
            date.setText(position + 1 + "");
            Mess mess = getMess(position + 1);
            this.mess = mess;
            breakfastIcon.setVisibility(mess.breakfast ? View.VISIBLE : View.GONE);
            lunchIcon.setVisibility(mess.lunch ? View.VISIBLE : View.GONE);
            dinnerIcon.setVisibility(mess.dinner ? View.VISIBLE : View.GONE);
            breakfastIcon.setImageDrawable(ResUtil.getMipmap(SharedUtil.getBreakfastIcon(activity)));
            lunchIcon.setImageDrawable(ResUtil.getMipmap(SharedUtil.getLunchIcon(activity)));
            dinnerIcon.setImageDrawable(ResUtil.getMipmap(SharedUtil.getDinnerIcon(activity)));
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(activity, MessEntryView.class);
            if (mess != null)
                intent.putExtra(Constants.DATE, mess.date);
            activity.startActivity(intent);
        }
    }

}
