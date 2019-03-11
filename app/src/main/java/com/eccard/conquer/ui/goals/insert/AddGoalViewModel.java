package com.eccard.conquer.ui.goals.insert;

import com.eccard.conquer.data.DataManager;
import com.eccard.conquer.data.model.db.Goal;
import com.eccard.conquer.ui.base.BaseViewModel;
import com.eccard.conquer.utils.rx.SchedulerProvider;

import timber.log.Timber;

public class AddGoalViewModel extends BaseViewModel<AddGoalCallback> {

    private String goalName;

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }


    public AddGoalViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onCancelClick(){
        getNavigator().dismissDialog();
    }
    public void onSubmitClick(){

        Timber.d("testes goalName="+goalName);

        Goal goal = new Goal();
        goal.name = goalName;

        insertGoalInDb(goal);

        getNavigator().dismissDialog();
    }


    private void insertGoalInDb(Goal goal){
        getCompositeDisposable().add(getDataManager()
        .saveGoal(goal)
        .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        Timber.d("inserção deu certoo");

                    } else {
                        Timber.d("inserção deu errado");
                    }
                }, throwable -> {
                         Timber.e(throwable.getMessage());
                }));

    }


}
