package com.eccard.conquer.ui.tasks;

import android.os.Bundle;
import android.view.View;

import com.eccard.conquer.BR;
import com.eccard.conquer.R;
import com.eccard.conquer.ViewModelProviderFactory;
import com.eccard.conquer.data.model.db.Goal;
import com.eccard.conquer.data.model.db.Task;
import com.eccard.conquer.databinding.FragmentTasksBinding;
import com.eccard.conquer.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

public class TasksFragment extends BaseFragment<FragmentTasksBinding, TasksViewModel> implements TasksNavigator, TasksAdapter.OnSelectedTask {

    public static final String TAG = TasksFragment.class.getSimpleName();
    private static final String ARG_GOAL_ID = "goalId";

    private FragmentTasksBinding fragmentTasksBinding;

    @Inject
    ViewModelProviderFactory factory;
    private TasksViewModel mTasksViewModel;
    private TasksAdapter tasksAdapter;
//    private Long mGoalId;

    public static TasksFragment newInstance(Long goalId) {

        Bundle args = new Bundle();
        args.putLong(ARG_GOAL_ID,goalId);

        TasksFragment fragment = new TasksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tasks;
    }

    @Override
    public TasksViewModel getViewModel() {
        mTasksViewModel =
                ViewModelProviders.of(this,factory).get(TasksViewModel.class);
        return mTasksViewModel;
    }

    @Override
    public void goGoalsFragment() {

    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentTasksBinding = getViewDataBinding();

        if(getArguments()!=null &&
                getArguments().containsKey(ARG_GOAL_ID)){

            mTasksViewModel.setGoalId(getArguments().getLong(ARG_GOAL_ID));

            tasksAdapter = new TasksAdapter(getContext(),this);

            getViewDataBinding().recyleViewTasks.setLayoutManager(new LinearLayoutManager(getContext()));
            getViewDataBinding().recyleViewTasks.setHasFixedSize(true);
            getViewDataBinding().recyleViewTasks.setItemAnimator(new DefaultItemAnimator());
            getViewDataBinding().recyleViewTasks.setAdapter(tasksAdapter);


            mTasksViewModel.getTasks().observe(this, tasks -> tasksAdapter.setData(tasks));

        }

    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTasksViewModel.setNavigator(this);


    }

    @Override
    public void onSelectedTask(Task task) {

    }
}
