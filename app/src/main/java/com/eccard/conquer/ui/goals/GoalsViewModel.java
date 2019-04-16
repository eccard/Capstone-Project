package com.eccard.conquer.ui.goals;

import com.eccard.conquer.data.DataManager;
import com.eccard.conquer.data.model.db.Goal;
import com.eccard.conquer.ui.base.BaseViewModel;
import com.eccard.conquer.utils.rx.SchedulerProvider;

import java.util.List;

import androidx.lifecycle.LiveData;
import timber.log.Timber;

public class GoalsViewModel extends BaseViewModel<GoalsNavigator> {

//    private final MutableLiveData<List<Goal>> goalsData;


    public GoalsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
//        goalsData = new MutableLiveData<>();

        loadGoals();
    }

    private void loadGoals() {
//        getCompositeDisposable().add(getDataManager()
//                .getAllGoals()
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(goalsList -> {
//                    Timber.d("try to load goals");
//                    Timber.d("try to load goals");
//                    if (goalsList != null) {
//                        goalsData.setValue(goalsList);
//                        Timber.d("goalsList != null");
//                    } else {
//                        Timber.d("goalsList == null");
//                    }
//                }, throwable -> {
//                    Timber.e(throwable);
//
//                }));
    }

    public LiveData<List<Goal>> getGoalsData(){
        return getDataManager().getAllGoalsLiveData();
    }

    public void deleteGoal(Goal goal){
            getCompositeDisposable().add(getDataManager()
                .deleteGoal(goal)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(aBoolean -> Timber.d("deleteGoa="+aBoolean),
                        Timber::e));
    }

    public void onNavTasksClick(){
        getNavigator().goTasks();
    }
}
