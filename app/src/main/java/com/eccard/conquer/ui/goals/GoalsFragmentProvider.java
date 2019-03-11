package com.eccard.conquer.ui.goals;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class GoalsFragmentProvider {

    @ContributesAndroidInjector
    abstract GoalsFragment provideGoalsFragmentFactory();
}
