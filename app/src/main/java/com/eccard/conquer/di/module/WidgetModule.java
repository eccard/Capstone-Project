package com.eccard.conquer.di.module;

import com.eccard.conquer.widget.TasksAppWidget;
import com.eccard.conquer.widget.TasksWidgetViewService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class WidgetModule {
    @ContributesAndroidInjector
    abstract TasksAppWidget contributesTasksWidget();

    @ContributesAndroidInjector
    abstract TasksWidgetViewService contributesWidgetViewService();

}
