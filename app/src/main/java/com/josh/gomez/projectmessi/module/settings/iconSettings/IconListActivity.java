package com.josh.gomez.projectmessi.module.settings.iconSettings;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.josh.gomez.projectmessi.R;
import com.josh.gomez.projectmessi.generic.utils.ResUtil;
import com.josh.gomez.projectmessi.generic.utils.SharedUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IconListActivity extends AppCompatActivity implements IconFragment.FragmentInteractor {

    @BindView(R.id.breakfast_icon)
    ImageView breakfastIcon;
    @BindView(R.id.lunch_icon)
    ImageView lunchIcon;
    @BindView(R.id.dinner_icon)
    ImageView dinnerIcon;
    IconFragment iconFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        breakfastIcon.setImageDrawable(ResUtil.getMipmap(SharedUtil.getBreakfastIcon(this)));
        lunchIcon.setImageDrawable(ResUtil.getMipmap(SharedUtil.getLunchIcon(this)));
        dinnerIcon.setImageDrawable(ResUtil.getMipmap(SharedUtil.getDinnerIcon(this)));
    }

    @OnClick(R.id.breakfast)
    public void onBreakfastClick() {
        getSupportFragmentManager().popBackStack();
        iconFragment = IconFragment.newInstance("Breakfast");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root_view, iconFragment, "tag")
                .addToBackStack(null)
                .commit();
    }

    @OnClick(R.id.lunch)
    public void onLunchClick() {
        getSupportFragmentManager().popBackStack();
        iconFragment = IconFragment.newInstance("Lunch");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root_view, iconFragment, "tag")
                .addToBackStack(null)
                .commit();
    }

    @OnClick(R.id.imageView322)
    public void onBackClick() {
        this.finish();
    }

    @OnClick(R.id.dinner)
    public void onDinnerClick() {
        getSupportFragmentManager().popBackStack();
        iconFragment = IconFragment.newInstance("Dinner");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root_view, iconFragment, "tag")
                .addToBackStack(null)
                .commit();
    }

    @OnClick(R.id.restore)
    public void restoredefault() {
        SharedUtil.setDinnerIcon("dinner", this);
        SharedUtil.setLunchIcon("lunch", this);
        SharedUtil.setBreakfastIcon("omelet", this);
        onResume();
    }

    @Override
    public void refresh() {
        onResume();
    }

    @Override
    public void closeFragment() {
        getSupportFragmentManager().popBackStack();
    }
}
