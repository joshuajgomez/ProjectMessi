package com.josh.gomez.projectmessi.module.settings.iconSettings;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.josh.gomez.projectmessi.R;
import com.josh.gomez.projectmessi.generic.utils.SharedUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IconFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.textView2)
    TextView title;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    IconAdapter iconAdapter;
    private static final String titleKey = "titleKey";
    private static String titleValue;
    FragmentInteractor mListener;

    public IconFragment() {
        // Required empty public constructor
    }

    public static IconFragment newInstance(String title) {
        IconFragment fragment = new IconFragment();
        Bundle bundle = new Bundle();
        bundle.putString(titleKey, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            titleValue = getArguments().getString(titleKey);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_icon, container, false);
        ButterKnife.bind(this, view);
        initUI();
        return view;
    }

    private void initUI() {
        title.setText(titleValue);
        String currentIcon = "";
        if (titleValue.equals("Breakfast")) {
            currentIcon = SharedUtil.getBreakfastIcon(getContext());
        } else if (titleValue.equals("Lunch")) {
            currentIcon = SharedUtil.getLunchIcon(getContext());
        } else if (titleValue.equals("Dinner")) {
            currentIcon = SharedUtil.getDinnerIcon(getContext());
        }
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        iconAdapter = new IconAdapter(getIconList(), this, currentIcon);
        recyclerView.setAdapter(iconAdapter);
    }

    @OnClick(R.id.imageView322)
    public void onBackClick() {
        getActivity().onBackPressed();
    }

    private List<String> getIconList() {
        List<String> list = new ArrayList<>();
        list.add("lunch");
        list.add("omelet");
        list.add("burger2");
        list.add("icecream");
        list.add("soup");
        list.add("dinner");
        list.add("broccoli");
        list.add("burger1");
        list.add("soup2");
        return list;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (FragmentInteractor) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onClick(View view) {
        String iconName = (String) view.getTag();
        if (titleValue.equals("Breakfast"))
            SharedUtil.setBreakfastIcon(iconName, getContext());
        else if (titleValue.equals("Lunch"))
            SharedUtil.setLunchIcon(iconName, getContext());
        else if (titleValue.equals("Dinner"))
            SharedUtil.setDinnerIcon(iconName, getContext());

        mListener.closeFragment();
        mListener.refresh();
    }

    public interface FragmentInteractor {
        void refresh();

        void closeFragment();
    }
}
