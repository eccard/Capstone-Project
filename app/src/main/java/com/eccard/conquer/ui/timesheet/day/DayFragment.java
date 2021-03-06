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

import android.os.Bundle;
import android.view.View;

import com.eccard.conquer.BR;
import com.eccard.conquer.R;
import com.eccard.conquer.ViewModelProviderFactory;
import com.eccard.conquer.data.local.db.dao.TaskDao;
import com.eccard.conquer.databinding.FragmentDayBinding;
import com.eccard.conquer.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DayFragment extends BaseFragment<FragmentDayBinding, DayViewModel>
        implements DayNavigator, DayAdapter.BlogAdapterListener {

    private static final String ARG_DAY_WEEKEND = "dayOfWeekend";
    @Inject
    DayAdapter mDayAdapter;
    FragmentDayBinding mFragmentBlogBinding;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    ViewModelProviderFactory factory;
    private DayViewModel mDayViewModel;

    public static DayFragment newInstance(int dayOfWeekend) {
        Bundle args = new Bundle();
        args.putInt(ARG_DAY_WEEKEND,dayOfWeekend);
        DayFragment fragment = new DayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_day;
    }

    @Override
    public DayViewModel getViewModel() {
        mDayViewModel = ViewModelProviders.of(this, factory).get(DayViewModel.class);
        return mDayViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDayViewModel.setNavigator(this);
        mDayAdapter.setListener(this);

        if(getArguments() != null && getArguments().containsKey(ARG_DAY_WEEKEND)){
            mDayViewModel.setDayOfWeekend(getArguments().getInt(ARG_DAY_WEEKEND));
        }
    }

    @Override
    public void onRetryClick() {
        mDayViewModel.fetchBlogs();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentBlogBinding = getViewDataBinding();
        setUp();
    }

    @Override
    public void updateBlog(List<TaskDao.TaskGoal> taskList) {
        mDayAdapter.addItems(taskList);

    }


    private void setUp() {
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mFragmentBlogBinding.blogRecyclerView.setLayoutManager(mLayoutManager);
        mFragmentBlogBinding.blogRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFragmentBlogBinding.blogRecyclerView.setAdapter(mDayAdapter);

        mDayViewModel.getTasksLiveData().observe(this, tasks -> {
            mDayViewModel.setIsLoading(false);
            mDayAdapter.addItems(tasks);
        });
    }
}
