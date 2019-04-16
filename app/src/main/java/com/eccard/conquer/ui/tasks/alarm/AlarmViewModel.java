package com.eccard.conquer.ui.tasks.alarm;

import com.eccard.conquer.data.DataManager;
import com.eccard.conquer.ui.base.BaseViewModel;
import com.eccard.conquer.utils.rx.SchedulerProvider;

import androidx.databinding.ObservableField;

public class AlarmViewModel extends BaseViewModel<AlarmNavigator> {


    public ObservableField<String> goalName = new ObservableField<>();
    public ObservableField<String> TaskDescription = new ObservableField<>();

    public AlarmViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public String getGoalName() {
        return goalName.get();
    }

    public void setGoalName(String goalName) {
        this.goalName.set(goalName);
    }

    public String getTaskDescription() {
        return TaskDescription.get();
    }

    public void setTaskDescription(String taskDescription) {
        TaskDescription.set(taskDescription);
    }

    public void setAlarm(){
        getNavigator().stopAlarm();
    }
}
