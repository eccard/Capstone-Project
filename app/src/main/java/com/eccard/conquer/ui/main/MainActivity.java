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

package com.eccard.conquer.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eccard.conquer.BR;
import com.eccard.conquer.BuildConfig;
import com.eccard.conquer.ConquerApp;
import com.eccard.conquer.R;
import com.eccard.conquer.ViewModelProviderFactory;
import com.eccard.conquer.data.model.db.Task;
import com.eccard.conquer.databinding.ActivityMainBinding;
import com.eccard.conquer.databinding.NavHeaderMainBinding;
import com.eccard.conquer.ui.base.BaseActivity;
import com.eccard.conquer.ui.goals.GoalsFragment;
import com.eccard.conquer.ui.goals.insert.AddGoalDialog;
import com.eccard.conquer.ui.tasks.TasksFragment;
import com.eccard.conquer.ui.tasks.insert.NewTaskFragment;
import com.eccard.conquer.ui.timesheet.TimeSheetActivity;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProviderFactory factory;
    private ActivityMainBinding mActivityMainBinding;
    private DrawerLayout mDrawer;
    private MainViewModel mMainViewModel;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private Tracker tracker;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        mMainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        return mMainViewModel;
    }

    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .remove(fragment)
                    .commitNow();
            unlockDrawer();
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);
        setUp();

        ConquerApp application = (ConquerApp) getApplication();
        tracker = application.getDefaultTracker();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    private void lockDrawer() {
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    private void setUp() {
        mDrawer = mActivityMainBinding.drawerView;
        mToolbar = mActivityMainBinding.toolbar;
        mNavigationView = mActivityMainBinding.navigationView;

        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                mToolbar,
                R.string.open_drawer,
                R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }
        };
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        setupNavMenu();
        String version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
        mMainViewModel.updateAppVersion(version);

        showGoalsFragment();
    }


    private void setupNavMenu() {
        NavHeaderMainBinding navHeaderMainBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.nav_header_main, mActivityMainBinding.navigationView, false);
        mActivityMainBinding.navigationView.addHeaderView(navHeaderMainBinding.getRoot());
        navHeaderMainBinding.setViewModel(mMainViewModel);

        mNavigationView.setNavigationItemSelectedListener(
                item -> {
                    mDrawer.closeDrawer(GravityCompat.START);
                    switch (item.getItemId()) {
                        case R.id.navItemAbout:
                            //showAboutFragment();
                            showGoalsFragment();

                            tracker.setScreenName("GoalsFragment" );
                            tracker.send(new HitBuilders.ScreenViewBuilder().build());
                            return true;
                        case R.id.navItemFeed:
                            startActivity(TimeSheetActivity.newIntent(MainActivity.this));
                            tracker.setScreenName("TimeSheetActivity" );
                            tracker.send(new HitBuilders.ScreenViewBuilder().build());
                            return true;
                        default:
                            return false;
                    }
                });
    }

    private void showGoalsFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
//                .addToBackStack(GoalsFragment.TAG)
                .replace(R.id.container_layout, GoalsFragment.newInstance(), GoalsFragment.TAG)
                .commit();
    }

    private void unlockDrawer() {
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    public void goAddNewGoal(){

        AddGoalDialog.newInstance().show(getSupportFragmentManager());

        tracker.setScreenName("AddGoalDialog" );
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    public void goTasks(Long goalId){
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(TasksFragment.TAG)
                .replace(R.id.container_layout, TasksFragment.newInstance(goalId), TasksFragment.TAG)
                .commit();

        tracker.setScreenName("TasksFragment" );
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

    }


    public void goAddNewTaskForGoalId(Long goalId){

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(NewTaskFragment.TAG)
                .replace(R.id.container_layout, NewTaskFragment.newInstance(goalId), NewTaskFragment.TAG)
                .commit();

        tracker.setScreenName("NewTaskFragment" );
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    public void goShowTask(Task task){
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(NewTaskFragment.TAG)
                .replace(R.id.container_layout, NewTaskFragment.newInstance(task), NewTaskFragment.TAG)
                .commit();

        tracker.setScreenName("NewTaskFragment" );
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
