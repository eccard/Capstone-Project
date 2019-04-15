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

import androidx.recyclerview.widget.LinearLayoutManager;

import com.eccard.conquer.data.DataManager;
import com.eccard.conquer.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import java.util.ArrayList;

/**
 * Created by amitshekhar on 14/09/17.
 */
@Module
public class DayFragmentModule {

    @Provides
    DayViewModel blogViewModel(DataManager dataManager,
                               SchedulerProvider schedulerProvider) {
        return new DayViewModel(dataManager, schedulerProvider);
    }

    @Provides
    DayAdapter provideBlogAdapter() {
        return new DayAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(DayFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
