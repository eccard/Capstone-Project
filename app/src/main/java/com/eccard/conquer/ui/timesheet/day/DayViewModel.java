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

package com.eccard.conquer.ui.timesheet.day;

import com.eccard.conquer.data.DataManager;
import com.eccard.conquer.data.local.db.dao.TaskDao;
import com.eccard.conquer.ui.base.BaseViewModel;
import com.eccard.conquer.utils.rx.SchedulerProvider;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class DayViewModel extends BaseViewModel<DayNavigator> {

    private final MutableLiveData<List<TaskDao.TaskGoal>> taskListLiveData;

    private int dayOfWeekend;

    public void setDayOfWeekend(int dayOfWeekend) {
        this.dayOfWeekend = dayOfWeekend;
    }

    public DayViewModel(DataManager dataManager,
                        SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        taskListLiveData = new MutableLiveData<>();
        fetchBlogs();
//        getTasksLiveData();
    }

    public void fetchBlogs() {
        setIsLoading(true);
//        getCompositeDisposable().add(getDataManager()
//                .getBlogApiCall()
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(blogResponse -> {
//                    if (blogResponse != null && blogResponse.getData() != null) {
//                        taskListLiveData.setValue(blogResponse.getData());
//                    }
//                    setIsLoading(false);
//                }, throwable -> {
//                    setIsLoading(false);
//                    getNavigator().handleError(throwable);
//                }));
    }



    public LiveData<List<TaskDao.TaskGoal>> getTasksLiveData(){
        return getDataManager().loadTaskGoalsOfDayWithLiveData(dayOfWeekend);
    }



    public MutableLiveData<List<TaskDao.TaskGoal>> getTaskListLiveData() {
        return taskListLiveData;
    }
}
