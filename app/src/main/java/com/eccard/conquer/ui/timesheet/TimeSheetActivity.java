package com.eccard.conquer.ui.timesheet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.eccard.conquer.BR;
import com.eccard.conquer.R;
import com.eccard.conquer.ViewModelProviderFactory;
import com.eccard.conquer.databinding.ActivityTimeSheetBinding;
import com.eccard.conquer.ui.base.BaseActivity;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class TimeSheetActivity extends BaseActivity<ActivityTimeSheetBinding,TimeSheetViewModel>
    implements TimeSheetNavigator{


    public static Intent newIntent(Context context){
        return new Intent(context,TimeSheetActivity.class);
    }

    @Inject
    ViewModelProviderFactory factory;

    private ActivityTimeSheetBinding activityTimeSheetBinding;
    private TimeSheetViewModel timeSheetViewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_time_sheet;
    }

    @Override
    public TimeSheetViewModel getViewModel() {
        timeSheetViewModel = ViewModelProviders.of(this,factory).get(TimeSheetViewModel.class);
        return timeSheetViewModel;
    }

    @Override
    public void openDaySheet(int day) {

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTimeSheetBinding = getViewDataBinding();
        timeSheetViewModel.setNavigator(this);

        setUp();
    }

    private void setUp() {

    }
}
