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

package com.eccard.conquer.ui.timesheet;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.eccard.conquer.ViewModelProviderFactory;
import com.eccard.conquer.ui.base.BaseActivity;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.core.app.NavUtils;
import androidx.core.app.TaskStackBuilder;
import android.view.MenuItem;
import com.eccard.conquer.BR;
import com.eccard.conquer.R;
import com.eccard.conquer.databinding.ActivityFeedBinding;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

/**
 * Created by amitshekhar on 10/07/17.
 */

public class TimeSheetActivity extends BaseActivity<ActivityFeedBinding, TimeSheetViewModel> implements HasSupportFragmentInjector {

    public static final String ARG_DAY = "dayForSelectedTab";


    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    TimeSheetPagerAdapter mPagerAdapter;
    @Inject
    ViewModelProviderFactory factory;
    private ActivityFeedBinding mActivityFeedBinding;
    private TimeSheetViewModel mTimeSheetViewModel;

    public static Intent newIntent(Context context) {
        return new Intent(context, TimeSheetActivity.class);
    }

    public static Intent newIntent(Context context, int day) {
        Intent intent = new Intent(context, TimeSheetActivity.class);
        intent.putExtra(ARG_DAY,day);
        return intent;
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feed;
    }

    @Override
    public TimeSheetViewModel getViewModel() {
        mTimeSheetViewModel = ViewModelProviders.of(this,factory).get(TimeSheetViewModel.class);
        return mTimeSheetViewModel;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityFeedBinding = getViewDataBinding();
        setUp();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(ARG_DAY)){
            int day = bundle.getInt(ARG_DAY);
            mActivityFeedBinding.feedViewPager.setCurrentItem(day-1);
        }
    }

    private void setUp() {
        setSupportActionBar(mActivityFeedBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        String[] daysResource = getResources().getStringArray(R.array.daysOfWeekend);

        String[] days = new String[daysResource.length-1];
        System.arraycopy(daysResource,1,days,0,days.length);

        mPagerAdapter.setCount(days.length);

        mActivityFeedBinding.feedViewPager.setAdapter(mPagerAdapter);

        for (String day : days) {
            mActivityFeedBinding.tabLayout.addTab(mActivityFeedBinding.tabLayout.newTab().setText(day));
        }

        mActivityFeedBinding.feedViewPager.setOffscreenPageLimit(mActivityFeedBinding.tabLayout.getTabCount());

        mActivityFeedBinding.feedViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mActivityFeedBinding.tabLayout));

        mActivityFeedBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mActivityFeedBinding.feedViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
        });
    }
}
