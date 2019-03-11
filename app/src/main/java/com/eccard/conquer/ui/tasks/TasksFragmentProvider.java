package com.eccard.conquer.ui.tasks;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TasksFragmentProvider {

    @ContributesAndroidInjector
    abstract TasksFragment provideNewGoalFragmentFactory();
}
