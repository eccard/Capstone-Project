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

package com.eccard.conquer.di.builder;

import com.eccard.conquer.ui.goals.GoalsFragmentProvider;
import com.eccard.conquer.ui.goals.insert.AddGoalDialogProvider;
import com.eccard.conquer.ui.main.MainActivity;
import com.eccard.conquer.ui.tasks.TasksFragmentProvider;
import com.eccard.conquer.ui.tasks.alarm.AlarmActivity;
import com.eccard.conquer.ui.tasks.insert.NewTaskFragmentProvider;
import com.eccard.conquer.ui.timesheet.TimeSheetActivity;
import com.eccard.conquer.ui.timesheet.TimeSheetActivityModule;
import com.eccard.conquer.ui.timesheet.day.DayFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {
            TimeSheetActivityModule.class,
            DayFragmentProvider.class})
    abstract TimeSheetActivity bindFeedActivity();

    @ContributesAndroidInjector(modules = {
            GoalsFragmentProvider.class,
            TasksFragmentProvider.class,
            AddGoalDialogProvider.class,
            NewTaskFragmentProvider.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract AlarmActivity bindTimeSheetActivity();
}
