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

import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.eccard.conquer.data.local.db.dao.TaskDao;
import com.eccard.conquer.ui.base.BaseViewHolder;
import com.eccard.conquer.utils.AppLogger;
import com.eccard.conquer.databinding.ItemBlogEmptyViewBinding;
import com.eccard.conquer.databinding.ItemBlogViewBinding;

import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    private List<TaskDao.TaskGoal> taskList;

    private BlogAdapterListener mListener;

    public DayAdapter(List<TaskDao.TaskGoal> taskList) {
        this.taskList = taskList;
    }

    @Override
    public int getItemCount() {
        if (taskList != null && taskList.size() > 0) {
            return taskList.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (taskList != null && !taskList.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                ItemBlogViewBinding blogViewBinding = ItemBlogViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new BlogViewHolder(blogViewBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ItemBlogEmptyViewBinding emptyViewBinding = ItemBlogEmptyViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new EmptyViewHolder(emptyViewBinding);
        }
    }


    public void addItems(List<TaskDao.TaskGoal> newData) {
        if ( newData !=null) {
            taskList.addAll(newData);
            notifyDataSetChanged();
        }
    }



    public void clearItems() {
        taskList.clear();
    }

    public void setListener(BlogAdapterListener listener) {
        this.mListener = listener;
    }

    public interface BlogAdapterListener {

        void onRetryClick();
    }

    public class BlogViewHolder extends BaseViewHolder implements DayItemViewModel.BlogItemViewModelListener {

        private ItemBlogViewBinding mBinding;

        private DayItemViewModel mDayItemViewModel;

        public BlogViewHolder(ItemBlogViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            TaskDao.TaskGoal taskGoal = taskList.get(position);
            mDayItemViewModel = new DayItemViewModel(taskGoal, this);
            mBinding.setViewModel(mDayItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(String blogUrl) {
            if (blogUrl != null) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(blogUrl));
                    itemView.getContext().startActivity(intent);
                } catch (Exception e) {
                    AppLogger.d("url error");
                }
            }
        }
    }

    public class EmptyViewHolder extends BaseViewHolder implements DayEmptyItemViewModel.BlogEmptyItemViewModelListener {

        private ItemBlogEmptyViewBinding mBinding;

        public EmptyViewHolder(ItemBlogEmptyViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            DayEmptyItemViewModel emptyItemViewModel = new DayEmptyItemViewModel(this);
            mBinding.setViewModel(emptyItemViewModel);
        }

        @Override
        public void onRetryClick() {
            mListener.onRetryClick();
        }
    }
}
