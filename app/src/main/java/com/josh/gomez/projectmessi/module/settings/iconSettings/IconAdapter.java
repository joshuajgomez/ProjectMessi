package com.josh.gomez.projectmessi.module.settings.iconSettings;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.josh.gomez.projectmessi.R;
import com.josh.gomez.projectmessi.generic.utils.ResUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Joshua Gomez on 10/02/2018.
 */

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.IconViewHolder> {

    List<String> iconList;
    View.OnClickListener onClickListener;
    String currentIcon;

    public IconAdapter(List<String> iconList, View.OnClickListener onClickListener, String currentIcon) {
        this.iconList = iconList;
        this.onClickListener = onClickListener;
        this.currentIcon = currentIcon;
    }

    @Override
    public IconViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.icon, parent, false);
        return new IconViewHolder(view, onClickListener);
    }

    @Override
    public void onBindViewHolder(IconViewHolder holder, int position) {
        holder.setIcon(iconList.get(position));
    }

    @Override
    public int getItemCount() {
        return iconList != null
                ? iconList.size()
                : 0;
    }

    public class IconViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.check)
        ImageView check;

        public IconViewHolder(View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(onClickListener);
        }

        public void setIcon(String icon) {
            itemView.setTag(icon);
            this.icon.setImageDrawable(ResUtil.getMipmap(icon));
            check.setVisibility(currentIcon.equals(icon) ? View.VISIBLE : View.INVISIBLE);
        }
    }

}
