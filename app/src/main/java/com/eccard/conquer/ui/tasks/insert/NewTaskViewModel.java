package com.eccard.conquer.ui.tasks.insert;

import com.eccard.conquer.data.DataManager;
import com.eccard.conquer.data.model.db.Task;
import com.eccard.conquer.ui.base.BaseViewModel;
import com.eccard.conquer.utils.rx.SchedulerProvider;

import timber.log.Timber;

public class NewTaskViewModel extends BaseViewModel<NewTaskNavigator> {

    private Long taskId;
    private Long mGoalId;

    private String taskName;
    private String taskDescription;
    private int dayOfWeekend;
    private long taskTime;

    public NewTaskViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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


    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setTaskTime(long taskTime) {
        this.taskTime = taskTime;
    }

    public void setDayOfWeekend(int dayOfWeekend) {
        this.dayOfWeekend = dayOfWeekend;
    }

    public void deleteTask(Task task){
        Timber.d(" deleteTask task - " + task.toString());

        getCompositeDisposable().add(getDataManager().deleteTask(task)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        Timber.d("deleção da task deu certoo");
                        getNavigator().goTasks(mGoalId);

                    } else {
                        Timber.d("deleção da task deu errado");
                    }
                }, throwable -> Timber.e(throwable.getMessage())));
    }

    public void addNewTask(){
        Timber.d(" click to add new task for goal=" + mGoalId);
        Timber.d(" taskId=" + taskId);
        Timber.d(" taskName=" + taskName);
        Timber.d(" taskDescription=" + taskDescription);
        Timber.d(" taskTime=" + taskTime);

        Task task = new Task();
        task.id = this.taskId;
        task.name = this.taskName;
        task.description = this.taskDescription;
        task.time = this.taskTime;
        task.goalId = this.mGoalId;
        task.dayOfWeekend = this.dayOfWeekend;

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
                }, throwable -> Timber.e(throwable.getMessage())));
    }

    void getGoalFromId(int hourOfDay, int minute){


    }


    public void onCheckedChanged(boolean isChecked) {
        if (isChecked){
            getCompositeDisposable().add(getDataManager().loadTaskGoalFromTaskId(taskId)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(taskGoal -> getNavigator().startAlarm(taskTime, taskGoal),
                            Timber::e));
        }

    }
}
