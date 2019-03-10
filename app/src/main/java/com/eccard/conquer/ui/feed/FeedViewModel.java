package com.eccard.conquer.ui.feed;

import com.eccard.conquer.data.DataManager;
import com.eccard.conquer.ui.base.BaseViewModel;
import com.eccard.conquer.utils.rx.SchedulerProvider;

/**
 * Created by Jyoti on 29/07/17.
 */

public class FeedViewModel extends BaseViewModel {

    public FeedViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
