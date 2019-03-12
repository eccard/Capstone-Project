package com.eccard.conquer.ui.tasks.insert;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class NewTaskFragmentProvider {

    @ContributesAndroidInjector
    abstract NewTaskFragment providesNewTaskFragmentFactory();
}
