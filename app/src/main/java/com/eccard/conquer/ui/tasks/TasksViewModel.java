package com.eccard.conquer.ui.tasks;

import com.eccard.conquer.data.DataManager;
import com.eccard.conquer.data.model.db.Task;
import com.eccard.conquer.ui.base.BaseViewModel;
import com.eccard.conquer.utils.rx.SchedulerProvider;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TasksViewModel extends BaseViewModel<TasksNavigator> {

    private Long mGoalId;

    public TasksViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void setGoalId(Long mGoalId) {
        this.mGoalId = mGoalId;
    }

    LiveData<List<Task>> getTasks(){
        return getDataManager().loadAllByGoalIdWithLiveData(mGoalId);
    }

    public void goAddNewTask(){
        getNavigator().goAddNewTask(mGoalId);
    }
}
