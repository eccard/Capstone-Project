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

import androidx.databinding.ObservableField;

import com.eccard.conquer.data.local.db.dao.TaskDao;
import com.eccard.conquer.utils.CommonUtils;

/**
 * Created by amitshekhar on 10/07/17.
 */

public class BlogItemViewModel {

    public final ObservableField<String> goalName;

    public final ObservableField<String> taskDescription;

    public final ObservableField<String> taskTime;

    public final BlogItemViewModelListener mListener;

    public final ObservableField<String> taskName;

    private final TaskDao.TaskGoal taskGoal;

    public BlogItemViewModel(TaskDao.TaskGoal taskGoal, BlogItemViewModelListener listener) {
        this.taskGoal = taskGoal;
        this.mListener = listener;
        taskName = new ObservableField<>(this.taskGoal.taskName);
        goalName = new ObservableField<>(this.taskGoal.goalName);
        taskTime = new ObservableField<>(CommonUtils.getStringValueFromTime(this.taskGoal.taskTime));
        taskDescription = new ObservableField<>(this.taskGoal.taskDescription);
    }

    public void onItemClick() {
        mListener.onItemClick(taskGoal.taskName+"");
    }

    public interface BlogItemViewModelListener {

        void onItemClick(String blogUrl);
    }
}
