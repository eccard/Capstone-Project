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

import androidx.lifecycle.LiveData;
import io.reactivex.Observable;

public interface DbHelper {

    Observable<Boolean> saveTask(final Task task);
    Observable<Boolean> deleteTask(final Task task);

    Observable<Boolean> isTaskEmpty();

    Observable<List<Task>> getTasksFromGoalId(Long goalId);
    LiveData<List<Task>> loadAllTasksWithLiveData();
    LiveData<List<TaskDao.TaskGoal>> loadTaskGoalsOfDayWithLiveData(int day);
    Observable<List<Task>> loadAllByGoalId(Long goalId);
    LiveData<List<Task>> loadAllByGoalIdWithLiveData(Long goalId);

    // todo add deleteTask

    // add update ?

    Observable<Boolean> saveGoal(final Goal goal);
    Observable<Boolean> deleteGoal(Goal goal);

    Observable<Boolean> isGoalEmpty();

    Observable<List<Goal>> getAllGoals();

    LiveData<List<Goal>> getAllGoalsLiveData();

    Observable<TaskDao.TaskGoal> loadTaskGoalFromTaskId(Long taskId);



}
