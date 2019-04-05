package com.eccard.conquer.ui.timesheet;

import com.eccard.conquer.data.DataManager;
import com.eccard.conquer.ui.base.BaseViewModel;
import com.eccard.conquer.utils.rx.SchedulerProvider;

public class TimeSheetViewModel extends BaseViewModel<TimeSheetNavigator> {

    public TimeSheetViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
