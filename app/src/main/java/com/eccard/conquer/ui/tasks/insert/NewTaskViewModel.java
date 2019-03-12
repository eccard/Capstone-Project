package com.eccard.conquer.ui.tasks.insert;

import com.eccard.conquer.data.DataManager;
import com.eccard.conquer.data.model.db.Task;
import com.eccard.conquer.ui.base.BaseViewModel;
import com.eccard.conquer.utils.rx.SchedulerProvider;

import timber.log.Timber;

public class NewTaskViewModel extends BaseViewModel<NewTaskNavigator> {

    private Long mGoalId;

    private String taskName;
    private String taskDescription;
    private String taskTime;

    public NewTaskViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void setGoalId(Long goalId) {
        this.mGoalId = goalId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }




    public void addNewTask(){
        Timber.d(" click to add new task for goal=" + mGoalId);
        Timber.d(" taskName=" + taskName);
        Timber.d(" taskDescription=" + taskDescription);
        Timber.d(" taskTime=" + taskTime);

        Task task = new Task();
        task.name = taskName;
        task.description = taskDescription;
        task.time = taskTime;
        task.goalId = mGoalId;

        getCompositeDisposable().add(getDataManager().saveTask(task)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        Timber.d("inserção da tarefa deu certoo");
                        getNavigator().goTasks(mGoalId);

                    } else {
                        Timber.d("inserção da tarefa deu errado");
                    }
                }, throwable -> {
                    Timber.e(throwable.getMessage());
                }));
    }

}