package com.eccard.conquer.ui.goals.insert;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AddGoalDialogProvider {
    @ContributesAndroidInjector
    abstract AddGoalDialog provideAddDialogFactory();
}
