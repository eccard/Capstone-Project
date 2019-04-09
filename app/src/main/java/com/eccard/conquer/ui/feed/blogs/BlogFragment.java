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

package com.eccard.conquer.ui.feed.blogs;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.eccard.conquer.ViewModelProviderFactory;
import com.eccard.conquer.data.local.db.dao.TaskDao;
import com.eccard.conquer.data.model.api.BlogResponse;
import com.eccard.conquer.data.model.db.Task;
import com.eccard.conquer.ui.base.BaseFragment;
import com.eccard.conquer.BR;
import com.eccard.conquer.R;
import com.eccard.conquer.databinding.FragmentBlogBinding;

import java.util.List;
import javax.inject.Inject;

/**
 * Created by amitshekhar on 10/07/17.
 */

public class BlogFragment extends BaseFragment<FragmentBlogBinding, BlogViewModel>
        implements BlogNavigator, BlogAdapter.BlogAdapterListener {

    private static final String ARG_DAY_WEEKEND = "dayOfWeekend";
    @Inject
    BlogAdapter mBlogAdapter;
    FragmentBlogBinding mFragmentBlogBinding;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    ViewModelProviderFactory factory;
    private BlogViewModel mBlogViewModel;

    public static BlogFragment newInstance(int dayOfWeekend) {
        Bundle args = new Bundle();
        args.putInt(ARG_DAY_WEEKEND,dayOfWeekend);
        BlogFragment fragment = new BlogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_blog;
    }

    @Override
    public BlogViewModel getViewModel() {
        mBlogViewModel = ViewModelProviders.of(this, factory).get(BlogViewModel.class);
        return mBlogViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBlogViewModel.setNavigator(this);
        mBlogAdapter.setListener(this);

        if(getArguments() != null && getArguments().containsKey(ARG_DAY_WEEKEND)){
            mBlogViewModel.setDayOfWeekend(getArguments().getInt(ARG_DAY_WEEKEND));
        }
    }

    @Override
    public void onRetryClick() {
        mBlogViewModel.fetchBlogs();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentBlogBinding = getViewDataBinding();
        setUp();
    }

    @Override
    public void updateBlog(List<TaskDao.TaskGoal> taskList) {
        mBlogAdapter.addItems(taskList);

    }


    private void setUp() {
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mFragmentBlogBinding.blogRecyclerView.setLayoutManager(mLayoutManager);
        mFragmentBlogBinding.blogRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFragmentBlogBinding.blogRecyclerView.setAdapter(mBlogAdapter);

        mBlogViewModel.getTasksLiveData().observe(this, tasks -> {
            mBlogViewModel.setIsLoading(false);
            mBlogAdapter.addItems(tasks);
        });
    }
}
