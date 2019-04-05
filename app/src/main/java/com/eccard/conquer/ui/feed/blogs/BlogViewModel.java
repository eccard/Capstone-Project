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

package com.eccard.conquer.ui.feed.blogs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eccard.conquer.data.DataManager;
import com.eccard.conquer.data.model.api.BlogResponse;
import com.eccard.conquer.data.model.db.Task;
import com.eccard.conquer.ui.base.BaseViewModel;
import com.eccard.conquer.utils.rx.SchedulerProvider;

import java.util.List;

/**
 * Created by amitshekhar on 10/07/17.
 */

public class BlogViewModel extends BaseViewModel<BlogNavigator> {

    private final MutableLiveData<List<Task>> taskListLiveData;

    public BlogViewModel(DataManager dataManager,
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



    public LiveData<List<Task>> getTasksLiveData(){
        return getDataManager().loadAllTasksWithLiveData();
    }

    public MutableLiveData<List<Task>> getTaskListLiveData() {
        return taskListLiveData;
    }
}
