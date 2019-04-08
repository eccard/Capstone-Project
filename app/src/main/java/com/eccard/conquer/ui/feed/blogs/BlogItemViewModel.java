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

import com.eccard.conquer.data.model.db.Goal;
import com.eccard.conquer.data.model.db.Task;
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

    private final Task task;

    public BlogItemViewModel(Task task, BlogItemViewModelListener listener) {
        this.task = task;
        this.mListener = listener;
//        imageUrl = new ObservableField<>(this.goal.getCoverImgUrl());
        taskName = new ObservableField<>(this.task.name);
        goalName = new ObservableField<>("goalNameeee");
        taskTime = new ObservableField<>(CommonUtils.getStringValueFromTime(this.task.time));
        taskDescription = new ObservableField<>(this.task.description);
    }

    public void onItemClick() {
        mListener.onItemClick(task.id+"");
    }

    public interface BlogItemViewModelListener {

        void onItemClick(String blogUrl);
    }
}
