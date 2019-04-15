/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.eccard.conquer.data.local.db;

import com.eccard.conquer.data.local.db.dao.TaskDao;
import com.eccard.conquer.data.model.db.Goal;
import com.eccard.conquer.data.model.db.Task;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Created by amitshekhar on 07/07/17.
 */

@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }


    @Override
    public Observable<Boolean> saveTask(Task task) {

        return Observable.fromCallable(() -> {
            long ret = mAppDatabase.taskDao().insert(task);
            Timber.d("mAppDatabase.taskDao().insert(task) -> ret="+ret);
            return true;
        });

    }

    @Override
    public Observable<Boolean> deleteTask(Task task) {
        return Observable.fromCallable(() -> {
            long ret = mAppDatabase.taskDao().delete(task);
            Timber.d("mAppDatabase.taskDao().deleteTask(task) -> ret="+ret);
            return true;
        });
    }

    @Override
    public Observable<Boolean> isTaskEmpty() {
        return Observable.fromCallable(() -> mAppDatabase.taskDao().loadAll().isEmpty());

    }

    @Override
    public Observable<List<Task>> getTasksFromGoalId(Long goalId) {
        return Observable.fromCallable(() -> mAppDatabase.taskDao().loadAllByGoalId(goalId));
    }

    @Override
    public LiveData<List<TaskDao.TaskGoal>> loadTaskGoalsOfDayWithLiveData(int day) {
        return mAppDatabase.taskDao().loadTaskGoalsOfDayWithLiveData(day);
    }

    @Override
    public LiveData<List<Task>> loadAllTasksWithLiveData() {
        return mAppDatabase.taskDao().loadAllTasksWithLiveData();
    }

    @Override
    public Observable<Boolean> saveGoal(Goal goal) {
        return Observable.fromCallable(() -> {
            long ret = mAppDatabase.goalDao().insert(goal);
            Timber.d("mAppDatabase.goalDao().insert(goal) -> ret="+ret);
            return true;
        });
    }

    @Override
    public Observable<Boolean> deleteGoal(Goal goal) {
        return Observable.fromCallable(() -> {
            long ret = mAppDatabase.goalDao().delete(goal);
            Timber.d("mAppDatabase.taskDao().deleteGoal(goal) -> ret="+ret);
            return true;
        });
    }

    @Override
    public Observable<Boolean> isGoalEmpty() {
        return Observable.fromCallable(() -> mAppDatabase.goalDao().loadAll().isEmpty());

    }

    @Override
    public Observable<List<Goal>> getAllGoals() {
        return Observable.fromCallable(() -> mAppDatabase.goalDao().loadAll());

    }

    @Override
    public LiveData<List<Goal>> getAllGoalsLiveData() {
        return mAppDatabase.goalDao().loadAllWithLiveData();
    }

    @Override
    public Observable<List<Task>> loadAllByGoalId(final Long goalId) {
        return Observable.fromCallable(() -> mAppDatabase.taskDao().loadAllByGoalId(goalId));
    }

    @Override
    public LiveData<List<Task>> loadAllByGoalIdWithLiveData(Long goalId) {
        return mAppDatabase.taskDao().loadAllByGoalIdWithLiveData(goalId);
    }

    @Override
    public Observable<TaskDao.TaskGoal> loadTaskGoalFromTaskId(Long taskId) {
        return Observable.fromCallable(() -> mAppDatabase.taskDao().loadTaskGoalFromTaskId(taskId));
    }

}
