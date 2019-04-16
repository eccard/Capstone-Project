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

package com.eccard.conquer.data;

import android.content.Context;

import com.eccard.conquer.data.local.db.DbHelper;
import com.eccard.conquer.data.local.db.dao.TaskDao;
import com.eccard.conquer.data.model.db.Goal;
import com.eccard.conquer.data.model.db.Task;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import io.reactivex.Observable;

@Singleton
public class AppDataManager implements DataManager {

    private final Context mContext;

    private final DbHelper mDbHelper;

    private final Gson mGson;

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, Gson gson) {
        mContext = context;
        mDbHelper = dbHelper;
        mGson = gson;
    }

    @Override
    public Observable<Boolean> saveTask(Task task) {
        return mDbHelper.saveTask(task);
    }

    @Override
    public Observable<Boolean> deleteTask(Task task) {
        return mDbHelper.deleteTask(task);
    }

    @Override
    public Observable<Boolean> isTaskEmpty() {
        return mDbHelper.isTaskEmpty();
    }

    @Override
    public Observable<List<Task>> getTasksFromGoalId(Long goalId) {
        return mDbHelper.getTasksFromGoalId(goalId);
    }

    @Override
    public LiveData<List<TaskDao.TaskGoal>> loadTaskGoalsOfDayWithLiveData(int day) {
        return mDbHelper.loadTaskGoalsOfDayWithLiveData(day);
    }

    private List<TaskDao.TaskGoal> taskGoals;

    @Override
    public void cacheWidgetList(List<TaskDao.TaskGoal> taskGoals) {
        this.taskGoals = taskGoals;
    }

    @Override
    public List<TaskDao.TaskGoal> getWidgetList() {
        return this.taskGoals;
    }

    @Override
    public LiveData<List<Task>> loadAllTasksWithLiveData() {
        return mDbHelper.loadAllTasksWithLiveData();
    }

    @Override
    public Observable<Boolean> saveGoal(Goal goal) {
        return mDbHelper.saveGoal(goal);
    }

    @Override
    public Observable<Boolean> deleteGoal(Goal goal) {
        return mDbHelper.deleteGoal(goal);
    }

    @Override
    public Observable<Boolean> isGoalEmpty() {
        return mDbHelper.isGoalEmpty();
    }

    @Override
    public Observable<List<Goal>> getAllGoals() {
        return mDbHelper.getAllGoals();
    }

    @Override
    public LiveData<List<Goal>> getAllGoalsLiveData() {
        return mDbHelper.getAllGoalsLiveData();
    }

    @Override
    public Observable<List<Task>> loadAllByGoalId(Long goalId) {
        return mDbHelper.loadAllByGoalId(goalId);
    }

    @Override
    public LiveData<List<Task>> loadAllByGoalIdWithLiveData(Long goalId) {
        return mDbHelper.loadAllByGoalIdWithLiveData(goalId);
    }

    @Override
    public Observable<TaskDao.TaskGoal> loadTaskGoalFromTaskId(Long taskId) {
        return mDbHelper.loadTaskGoalFromTaskId(taskId);
    }

}
