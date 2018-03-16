package com.josh.gomez.projectmessi.module.report.reportDetail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.josh.gomez.projectmessi.R;
import com.josh.gomez.projectmessi.generic.repository.Constants;
import com.josh.gomez.projectmessi.generic.utils.DateUtil;
import com.josh.gomez.projectmessi.generic.utils.SharedUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link reportDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link reportDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class reportDetailFragment extends Fragment implements reportDetailContract.view {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String month_key = "month_key";
    private static final String year_key = "year_key";
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.current_rate)
    TextView totalRate;
    @BindView(R.id.no_breakfast)
    TextView breakfast;
    @BindView(R.id.no_lunch)
    TextView lunch;
    @BindView(R.id.no_dinner)
    TextView dinner;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    reportDetailAdapter adapter;
    reportDetailContract.action mPresenter;
    // TODO: Rename and change types of parameters
    private int month;
    private int year;
    private OnFragmentInteractionListener mListener;

    public reportDetailFragment() {
        // Required empty public constructor
    }


    public static reportDetailFragment newInstance(int month, int year) {
        reportDetailFragment fragment = new reportDetailFragment();
        Bundle args = new Bundle();
        args.putInt(month_key, month);
        args.putInt(year_key, year);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            month = getArguments().getInt(month_key);
            year = getArguments().getInt(year_key);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_detail, container, false);
        ButterKnife.bind(this, view);
        date.setText(DateUtil.getMonthWord(month) + " " + year);
        adapter = new reportDetailAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        mPresenter.getData(month, year);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = new reportDetailPresenter(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.imageViewd322)
    public void onPdfClick() {
        mPresenter.downloadPdf(month, year);
    }

    @Override
    public void showData(reportDetailModel reportDetailModel) {
        totalRate.setText(Constants.INDIAN_RUPEE + " " + reportDetailModel.totalRate + "");
        breakfast.setText(reportDetailModel.totalBreakfastCount + " * " + Constants.INDIAN_RUPEE + SharedUtil.getBreakfastCost() + " = " + Constants.INDIAN_RUPEE + reportDetailModel.totalBreakfastRate);
        lunch.setText(reportDetailModel.totalLunchCount + " * " + Constants.INDIAN_RUPEE + SharedUtil.getLunchCost() + " = " + Constants.INDIAN_RUPEE + reportDetailModel.totalLunchRate);
        dinner.setText(reportDetailModel.totalDinnerCount + " * " + Constants.INDIAN_RUPEE + SharedUtil.getDinnerCost() + " = " + Constants.INDIAN_RUPEE + reportDetailModel.totalDinnerRate);
        adapter.setMessList(reportDetailModel.messList);
    }

    @OnClick(R.id.imageView322)
    public void closeWindow() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
