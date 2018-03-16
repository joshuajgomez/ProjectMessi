package com.josh.gomez.projectmessi.module.userInfo;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.josh.gomez.projectmessi.R;
import com.josh.gomez.projectmessi.base.baseActivity;
import com.josh.gomez.projectmessi.generic.repository.Constants;
import com.josh.gomez.projectmessi.generic.utils.ResUtil;
import com.josh.gomez.projectmessi.generic.utils.SharedUtil;
import com.josh.gomez.projectmessi.module.credits.CreditActivity;
import com.josh.gomez.projectmessi.module.report.report.reportActivity;
import com.josh.gomez.projectmessi.module.settings.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends baseActivity implements UserContract.view {

    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.current_rate)
    TextView currentRate;
    @BindView(R.id.total_breakfast)
    TextView totalBreakfastCost;
    @BindView(R.id.total_lunch)
    TextView totalLunchCost;
    @BindView(R.id.total_dinner)
    TextView totalDinnerCost;
    @BindView(R.id.total_no_meal)
    TextView totalMealNo;
    @BindView(R.id.no_breakfast)
    TextView breakfastCount;
    @BindView(R.id.no_lunch)
    TextView lunchCount;
    @BindView(R.id.no_dinner)
    TextView dinnerCount;
    UserContract.action mPresenter;
    AlertDialog alertDialog;
    ProgressDialog progressDialog;
    String progressTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setBaseActivity();
        initUI();
    }

    private void initUI() {
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        mPresenter = new UserPresenter(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getData();
    }

    @Override
    public void showData(UserModel userModel) {
        userName.setText(userModel.userName);
        currentRate.setText(addIndianRupee(userModel.totalRate));
        totalBreakfastCost.setText(addIndianRupee(userModel.totalBreakfastRate));
        totalLunchCost.setText(addIndianRupee(userModel.totalLunchRate));
        totalDinnerCost.setText(addIndianRupee(userModel.totalDinnerRate));
        breakfastCount.setText(userModel.totalBreakfastCount + "");
        lunchCount.setText(userModel.totalLunchCount + "");
        dinnerCount.setText(userModel.totalDinnerCount + "");
        totalMealNo.setText(userModel.totalMealCount + "");
    }

    @Override
    public void onUpdateSuccess() {
        alertDialog.dismiss();
        mPresenter.getData();
    }

    @Override
    public void updateProgress(int progress, int total) {
        progressDialog.setProgress(progress);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(total);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
        showToast("Complete");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private String addIndianRupee(int text) {
        return Constants.INDIAN_RUPEE + " " + text;
    }

    @OnClick(R.id.imageView4)
    public void onCloseClicked() {
        this.finish();
    }

    @OnClick(R.id.edit_username)
    public void editUsername() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = View.inflate(this, R.layout.username_edit_view, null);
        builder.setView(dialogView);
        final EditText name = (EditText) dialogView.findViewById(R.id.editText);
        name.setText(SharedUtil.getUsername());
        Button saveButton = (Button) dialogView.findViewById(R.id.button2);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.updateUsername(name.getText().toString());
            }
        });
        alertDialog = builder.show();
    }

    @OnClick(R.id.pdf_view)
    public void onPdfClick() {
        mPresenter.printPdf();
    }

    @OnClick(R.id.edit_rate)
    public void editRates() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = View.inflate(this, R.layout.rate_edit_view, null);
        builder.setView(dialogView);
        final EditText breakfast = (EditText) dialogView.findViewById(R.id.breakfast);
        final EditText lunch = (EditText) dialogView.findViewById(R.id.lunch);
        final EditText dinner = (EditText) dialogView.findViewById(R.id.dinner);
        breakfast.setText(SharedUtil.getBreakfastCost() + "");
        lunch.setText(SharedUtil.getLunchCost() + "");
        dinner.setText(SharedUtil.getDinnerCost() + "");
        Button saveButton = (Button) dialogView.findViewById(R.id.button2);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.updateRates(
                        Integer.parseInt(breakfast.getText().toString()),
                        Integer.parseInt(lunch.getText().toString()),
                        Integer.parseInt(dinner.getText().toString())
                );
            }
        });
        alertDialog = builder.show();
    }

    @OnClick(R.id.import_container)
    public void importData() {
        mPresenter.startImporting();
        progressDialog.setTitle("Importing");
        progressDialog.setIcon(ResUtil.getMipmap("import1"));

    }

    @OnClick(R.id.export_container)
    public void exportData() {
        mPresenter.startExporting();
        progressDialog.setTitle("Exporting");
        progressDialog.setIcon(ResUtil.getMipmap("export"));

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
        }

        return super.onOptionsItemSelected(item);
    }
}
